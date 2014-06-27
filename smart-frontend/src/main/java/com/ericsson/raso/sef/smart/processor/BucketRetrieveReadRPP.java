package com.ericsson.raso.sef.smart.processor;


import java.util.ArrayList;
import java.util.List;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.raso.sef.core.RequestContextLocalStore;
import com.ericsson.raso.sef.core.ResponseCode;
import com.ericsson.raso.sef.core.SefCoreServiceResolver;
import com.ericsson.raso.sef.core.SmException;
import com.ericsson.raso.sef.smart.SmartServiceResolver;
import com.ericsson.raso.sef.smart.subscriber.response.SubscriberInfo;
import com.ericsson.raso.sef.smart.subscriber.response.SubscriberResponseStore;
import com.ericsson.raso.sef.smart.usecase.BucketRetrieveReadRPPRequest;
import com.ericsson.raso.sef.smart.usecase.Usecase;
import com.ericsson.sef.bes.api.entities.Meta;
import com.ericsson.sef.bes.api.subscriber.ISubscriberRequest;
import com.hazelcast.core.ISemaphore;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.CommandResponseData;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.CommandResult;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.Operation;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.OperationResult;
import com.nsn.ossbss.charge_once.wsdl.entity.tis.xsd._1.TransactionResult;


public class BucketRetrieveReadRPP implements Processor {
	private static final Logger logger = LoggerFactory
			.getLogger(BucketRetrieveReadRPP.class);
	@Override
	public void process(Exchange exchange) throws Exception {
		try {
			// get the method signature of web service [Standard]
			logger.debug("Getting exchange body....");
			BucketRetrieveReadRPPRequest bucketRetrieveReadRPPRequest = (BucketRetrieveReadRPPRequest) exchange
					.getIn().getBody();
			logger.debug("Got it!");
			
			List<Meta> metas = new ArrayList<Meta>();
			metas.add(new Meta("command", bucketRetrieveReadRPPRequest.getCommand()));
			metas.add(new Meta("customerid", bucketRetrieveReadRPPRequest.getCustomerId()));
			Usecase smartUsecase = bucketRetrieveReadRPPRequest.getUsecase();
			String operationName = smartUsecase.getOperation();
			String operationModifier = smartUsecase.getModifier();
			String requestId = RequestContextLocalStore.get().getRequestId();
			
			SubscriberInfo subscriberinfo = readSubscriber(requestId, bucketRetrieveReadRPPRequest.getCustomerId(),metas);
			// need error code response for EntireReadSubscriber
					if(subscriberinfo == null) {
						/*throw new SmException(new ResponseCode(-111,
								"13423#EntireRead Entity - Customer with primary key Keyname:PK,CustomerId: " + readRequest.getCustomerId()
										+ " does not exist"));*/
						throw new SmException(new ResponseCode(504, "13423#Bucket Retrieve Read RPP Entity - Customer with primary key Keyname:PK,CustomerId: " + bucketRetrieveReadRPPRequest.getCustomerId()
								+ " does not exist"));
					}
			
			
			CommandResponseData responseData = createResponse(bucketRetrieveReadRPPRequest.getUsecase().getOperation(), bucketRetrieveReadRPPRequest.getUsecase().getModifier(),bucketRetrieveReadRPPRequest.isTransactional());
			String edrIdentifier = (String)exchange.getIn().getHeader("EDR_IDENTIFIER");
			exchange.getOut().setBody(responseData);
			exchange.getOut().setHeader("EDR_IDENTIFIER", edrIdentifier);
		} catch (Exception e) {
			logger.error("Error in processor class:",this.getClass().getName(),e);
		}
		
	}
	private SubscriberInfo readSubscriber(String requestId, String customerId, List<Meta> metas) {
		logger.info("Invoking bucket retrieve read rop subscriber on tx-engine subscriber interface");
		ISubscriberRequest iSubscriberRequest = SmartServiceResolver.getSubscriberRequest();
		SubscriberInfo subInfo = new SubscriberInfo();
		SubscriberResponseStore.put(requestId, subInfo);
		iSubscriberRequest.readSubscriber(requestId, customerId, metas);
		ISemaphore semaphore = SefCoreServiceResolver.getCloudAwareCluster().getSemaphore(requestId);
		try {
		semaphore.init(0);
		semaphore.acquire();
		} catch(InterruptedException e) {
			logger.error("Error while acquire() call",this.getClass().getName(),e);
		}
		semaphore.destroy();
		logger.info("Check if response received for Read subscriber");
		SubscriberInfo subscriberInfo = (SubscriberInfo) SubscriberResponseStore.remove(requestId);
		return subscriberInfo;
	}
	private CommandResponseData createResponse(String operationName, String modifier, boolean isTransactional) {
		logger.info("Invoking create Response");
		CommandResponseData responseData = new CommandResponseData();
		CommandResult result = new CommandResult();
		responseData.setCommandResult(result);

		Operation operation = new Operation();
		operation.setName(operationName);
		operation.setModifier(modifier);

		OperationResult operationResult = new OperationResult();

		if(isTransactional) {
			TransactionResult transactionResult = new TransactionResult();
			result.setTransactionResult(transactionResult);
			transactionResult.getOperationResult().add(operationResult);
		} else {
			result.setOperationResult(operationResult);
		}
			
		return responseData;
	}

	
}
