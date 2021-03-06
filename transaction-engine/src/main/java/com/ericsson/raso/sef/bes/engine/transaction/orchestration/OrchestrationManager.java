package com.ericsson.raso.sef.bes.engine.transaction.orchestration;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.raso.sef.bes.engine.transaction.Constants;
import com.ericsson.raso.sef.bes.engine.transaction.TransactionException;
import com.ericsson.raso.sef.bes.engine.transaction.commands.AbstractTransaction;
import com.ericsson.raso.sef.bes.engine.transaction.orchestration.Orchestration.Mode;
import com.ericsson.raso.sef.bes.prodcat.tasks.TransactionTask;
import com.ericsson.raso.sef.core.SefCoreServiceResolver;

public class OrchestrationManager {
	
	private static final Logger logger = LoggerFactory.getLogger(OrchestrationManager.class); 

	private ExecutorService						grinder					= SefCoreServiceResolver.getExecutorService(Constants.ORCHESTRATION.name());
	private Map<String, AbstractTransaction>	nbUseCaseStore			= SefCoreServiceResolver.getCloudAwareCluster().getMap(Constants.USECASES.name());
	private Map<String, Orchestration>			nbTransactionStore		= SefCoreServiceResolver.getCloudAwareCluster().getMap(Constants.ORCHESTRATION.name());

	private Map<String, Orchestration>			sbOrchestrationTaskMapper	= SefCoreServiceResolver.getCloudAwareCluster().getMap(
																				Constants.ORCHESTRATION_TASK_MAPPER.name());
	private Map<String, AbstractStepResult>		sbRequestResultMapper		= SefCoreServiceResolver.getCloudAwareCluster().getMap(
																				Constants.TRANSACTION_STEP_STATUS.name());
	private Map<String, Step>					sbRequestStepMapper		= SefCoreServiceResolver.getCloudAwareCluster().getMap(
																				Constants.TRANSACTION_STEPS.name());
	
	private static Map<String, Status> sbExecutionStatus = SefCoreServiceResolver.getCloudAwareCluster().getMap(Constants.TRANSACTION_STATUS.name());
	

	private static OrchestrationManager instance = null;
	
	public static synchronized OrchestrationManager getInstance() {
		if (instance == null)
			instance = new OrchestrationManager();
		return instance;
	}
	
	
	public Orchestration createExecutionProfile(String northBoundCorrelator, List<TransactionTask> tasks) throws TransactionException {
		Orchestration result = new Orchestration(northBoundCorrelator, tasks);
		
		
		return result;
	}


	public void submit(AbstractTransaction usecase, Orchestration execution) {
		this.nbUseCaseStore.put(execution.getNorthBoundCorrelator(), usecase);
		this.nbTransactionStore.put(execution.getNorthBoundCorrelator(), execution);
		this.grinder.submit(execution);
	}
	
	public void promoteFulfillmentExecution(String southboundCorrelator, FulfillmentStepResult fulfillmentResult) {
		logger.debug("Entering promoteFulfillmentExecution!!");
		Orchestration requiredOrchestration = this.sbOrchestrationTaskMapper.get(southboundCorrelator);
		
		logger.debug("Sanity Check FFE: SBCorrelator=" + southboundCorrelator + ", fulfillmentResult=" + fulfillmentResult);
		this.sbRequestResultMapper.put(southboundCorrelator, fulfillmentResult);
		
		if (fulfillmentResult.getResultantFault() != null)
			this.sbExecutionStatus.put(southboundCorrelator, Status.DONE_FAULT);
		else
			this.sbExecutionStatus.put(southboundCorrelator, Status.DONE_SUCCESS);
		
		if (requiredOrchestration != null) {
			
			if (fulfillmentResult.getMetas() != null) {
				requiredOrchestration.addMetas(fulfillmentResult.getMetas());
			}
			
			this.grinder.submit(requiredOrchestration);
			logger.debug("Orchestration found for correlation: " +  southboundCorrelator + 
					"& submitted for further execution. Status: " + requiredOrchestration.getStatus() + " Mode: " + requiredOrchestration.getMode() + 
					" Phase: " + requiredOrchestration.printPhasingProgress());
		} else {
			logger.debug("Orchestration not found for south bound correlatorID: " + southboundCorrelator);
			//TODO: Logger - if the required Orchestration is already evicted, then no point complaining or throwing an exception!!
		}
	}
	

	public void sendResponse(String nbCorrelator, Orchestration orchestration) {
		AbstractTransaction usecase = this.nbUseCaseStore.get(nbCorrelator);
		usecase.getResponse().setAtomicStepResults(orchestration.getAtomicStepResults());
		logger.debug("Use case response received!!");
		// if the transaction had failed, then we will have to execute rollback in background....
		if (orchestration.getStatus() != Status.DONE_SUCCESS && orchestration.getMode() == Mode.FORWARD) {
			logger.debug("Use case failed. Rollback flow preparation");
//			Orchestration rollback = orchestration.getRollbackProfile();
//			this.submit(usecase, rollback);
		}

		logger.debug("Use case response to be sent");
		
		usecase.setMetas(orchestration.getMetas());
		usecase.getResponse().setReturnFault(orchestration.getExecutionFault());
		usecase.sendResponse();
		orchestration.cleanupTransaction();
		
		this.nbUseCaseStore.remove(nbCorrelator);
		this.nbTransactionStore.remove(nbCorrelator);
	}

	
	



	public ExecutorService getGrinder() {
		return grinder;
	}


	public void setGrinder(ExecutorService grinder) {
		this.grinder = grinder;
	}
}
