package com.ericsson.raso.sef.bes.engine.transaction.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.raso.sef.bes.engine.transaction.Constants;
import com.ericsson.raso.sef.bes.engine.transaction.ServiceResolver;
import com.ericsson.raso.sef.bes.engine.transaction.TransactionException;
import com.ericsson.raso.sef.bes.engine.transaction.entities.CreateSubscriberRequest;
import com.ericsson.raso.sef.bes.engine.transaction.entities.CreateSubscriberResponse;
import com.ericsson.raso.sef.bes.engine.transaction.entities.DeleteSubscriberRequest;
import com.ericsson.raso.sef.bes.engine.transaction.entities.DeleteSubscriberResponse;
import com.ericsson.raso.sef.bes.engine.transaction.orchestration.AbstractStepResult;
import com.ericsson.raso.sef.bes.engine.transaction.orchestration.Orchestration;
import com.ericsson.raso.sef.bes.engine.transaction.orchestration.OrchestrationManager;
import com.ericsson.raso.sef.bes.prodcat.CatalogException;
import com.ericsson.raso.sef.bes.prodcat.SubscriptionLifeCycleEvent;
import com.ericsson.raso.sef.bes.prodcat.entities.Offer;
import com.ericsson.raso.sef.bes.prodcat.service.IOfferCatalog;
import com.ericsson.raso.sef.bes.prodcat.tasks.Persistence;
import com.ericsson.raso.sef.bes.prodcat.tasks.PersistenceMode;
import com.ericsson.raso.sef.bes.prodcat.tasks.TransactionTask;
import com.ericsson.sef.bes.api.entities.Subscriber;
import com.ericsson.sef.bes.api.entities.TransactionStatus;
import com.ericsson.sef.bes.api.subscriber.ISubscriberResponse;

public class DeleteSubscriber extends AbstractTransaction {
	private static final long	serialVersionUID	= 8085575039162225609L;
	private static final Logger LOGGER = LoggerFactory.getLogger(DeleteSubscriber.class);
	
	public DeleteSubscriber(String requestId, Subscriber subscriber) {
		super(requestId, new CreateSubscriberRequest(requestId, subscriber));
		this.setResponse(new CreateSubscriberResponse(requestId));
		LOGGER.debug("RequestID: " + requestId + ", SubscriberID: " + subscriber);
		
	}
	
	
	@Override
	public Boolean execute() throws TransactionException {
		List<TransactionTask> tasks = new ArrayList<TransactionTask>(); 
		
		com.ericsson.raso.sef.core.db.model.Subscriber subscriberEntity = ((DeleteSubscriberRequest)this.getRequest()).persistableEntity();
		LOGGER.debug("Cnverted from API to DB Format: " + subscriberEntity);
		
		tasks.add(new Persistence<com.ericsson.raso.sef.core.db.model.Subscriber>(PersistenceMode.REMOVE, subscriberEntity, subscriberEntity.getMsisdn()));
		
		IOfferCatalog catalog = ServiceResolver.getOfferCatalog();
		Offer workflow = catalog.getOfferById(Constants.DELETE_SUBSCRIBER.name());
		if (workflow != null) {
			LOGGER.debug("Workflow found for delete...");
			String subscriberId = ((DeleteSubscriberRequest)this.getRequest()).getSubscriberId();
			try {
				tasks.addAll(workflow.execute(subscriberId, SubscriptionLifeCycleEvent.PURCHASE, true, new HashMap<String,Object>()));
			} catch (CatalogException e) {
				this.getResponse().setReturnFault(new TransactionException(this.getRequestId(), "Unable to pack the workflow tasks for this use-case", e));
			}
			
		}
		Orchestration execution = OrchestrationManager.getInstance().createExecutionProfile(this.getRequestId(), tasks);
		
		OrchestrationManager.getInstance().submit(this, execution);
		
		return true;
	}
	
	public void sendResponse() {
		
		LOGGER.debug("Invoking delete subscriber response");
		TransactionStatus txnStatus=null;
		boolean result = true;
		if (this.getResponse() != null) {
			if (this.getResponse().getAtomicStepResults() != null) {
				for (AbstractStepResult stepResult: this.getResponse().getAtomicStepResults().values()) {
					if (stepResult.getResultantFault() != null) {
						txnStatus.setComponent(stepResult.getResultantFault().getComponent());
						txnStatus.setCode(stepResult.getResultantFault().getStatusCode().getCode());
						txnStatus.setDescription(stepResult.getResultantFault().getStatusCode().getMessage());
						LOGGER.debug("DeleteSubscriber::=> Transaction Status: " + txnStatus);
						result = false;
						break;
					}
				}
			}
		}
		
		if (result != false)
			result = true;
		
		LOGGER.debug("DeleteSubscriber::=> Functional Result: " + result);
		
		LOGGER.debug("Invoking delete subscriber response!!");
		ISubscriberResponse subscriberClient = ServiceResolver.getSubscriberResponseClient();
		if (subscriberClient != null) {
			subscriberClient.deleteSubscriber(this.getRequestId(), 
					txnStatus, 
					result);
			LOGGER.debug("delete susbcriber response posted");
		} else {
			LOGGER.error("Unable to acquire client access to response interface. Request will time-out in the consumer side!!");
		}
		LOGGER.debug("delete susbcriber response posted");
		
	}

}
