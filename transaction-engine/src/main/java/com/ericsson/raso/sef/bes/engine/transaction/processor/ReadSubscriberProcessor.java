package com.ericsson.raso.sef.bes.engine.transaction.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import com.ericsson.raso.sef.bes.engine.transaction.ServiceResolver;
import com.ericsson.raso.sef.bes.engine.transaction.TransactionManager;
import com.ericsson.sef.bes.api.entities.Subscriber;
//import com.ericsson.raso.sef.core.db.model.Subscriber;

public class ReadSubscriberProcessor implements Processor{

	@Override
	public void process(Exchange arg0) throws Exception {
		String requestId = arg0.getIn().getBody(String.class);
		String subscriberId = arg0.getIn().getBody(String.class);
		TransactionManager transactionManager = ServiceResolver.getTransactionManager();
		transactionManager.readSubscriber(requestId, subscriberId);
		
	}

	
	
	
	
}
