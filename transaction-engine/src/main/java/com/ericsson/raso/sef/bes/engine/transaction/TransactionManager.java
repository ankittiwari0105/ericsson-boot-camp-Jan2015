package com.ericsson.raso.sef.bes.engine.transaction;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ericsson.raso.sef.bes.engine.transaction.commands.CreateSubscriber;
import com.ericsson.raso.sef.bes.engine.transaction.commands.DeleteSubscriber;
import com.ericsson.raso.sef.bes.engine.transaction.commands.DiscoverOffers;
import com.ericsson.raso.sef.bes.engine.transaction.commands.DiscoverOffersForUser;
import com.ericsson.raso.sef.bes.engine.transaction.commands.FetchOffer;
import com.ericsson.raso.sef.bes.engine.transaction.commands.FetchOfferByHandleForUser;
import com.ericsson.raso.sef.bes.engine.transaction.commands.FetchOfferForUser;
import com.ericsson.raso.sef.bes.engine.transaction.commands.GetAdviceOfCharge;
import com.ericsson.raso.sef.bes.engine.transaction.commands.HandleLifeCycle;
import com.ericsson.raso.sef.bes.engine.transaction.commands.HandleSubscriptionEvent;
import com.ericsson.raso.sef.bes.engine.transaction.commands.QuerySubscription;
import com.ericsson.raso.sef.bes.engine.transaction.commands.ReadSubscriber;
import com.ericsson.raso.sef.bes.engine.transaction.commands.ReadSubscriberMeta;
import com.ericsson.raso.sef.bes.engine.transaction.commands.UpdateSubscriber;
import com.ericsson.raso.sef.bes.engine.transaction.service.ISubscriberRequest;
import com.ericsson.raso.sef.bes.engine.transaction.service.ISubscriptionRequest;
import com.ericsson.raso.sef.bes.prodcat.SubscriptionLifeCycleEvent;
import com.ericsson.raso.sef.bes.prodcat.service.IOfferCatalog;
import com.ericsson.raso.sef.core.Constants;
import com.ericsson.raso.sef.core.SefCoreServiceResolver;
import com.ericsson.sef.bes.api.entities.Subscriber;

public class TransactionManager implements ISubscriberRequest, ISubscriptionRequest {

	IOfferCatalog offerCatalog = null;
	ExecutorService executor = null;

	Logger logger = LoggerFactory.getLogger(TransactionManager.class);

	public TransactionManager() {
		offerCatalog = com.ericsson.raso.sef.bes.prodcat.ServiceResolver.getOfferCatalog();
		executor = SefCoreServiceResolver.getExecutorService(com.ericsson.raso.sef.bes.engine.transaction.Constants.USE_CASE_EVAL.name());
	}

	@Override
	public String createSubscriber(String requestId, com.ericsson.sef.bes.api.entities.Subscriber subscriber) {
		logger.debug("Transaction Manager method crete subscriber and metas are of size" + subscriber.getMetas().size());
		CreateSubscriber command = new CreateSubscriber(requestId, subscriber);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String discoverOffers(String requestId, String resource) {
		DiscoverOffers command = new DiscoverOffers(requestId, resource);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String discoverOffersForUser(String requestId, String resource, String subscriberId) {
		DiscoverOffersForUser command = new DiscoverOffersForUser(requestId, resource, subscriberId);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String discoverOfferById(String requestId, String offerId) {
		FetchOffer command = new FetchOffer(requestId, offerId);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String discoverOfferByFederatedId(String requestId, String handle, String subscriberId) {
		FetchOfferByHandleForUser command = new FetchOfferByHandleForUser(requestId, handle, subscriberId);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String getAdviceOfCharge(String requestId, String offerId, String subscriberId, Map<String, String> metas) {
		GetAdviceOfCharge command = new GetAdviceOfCharge(requestId, offerId, subscriberId, metas);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String purchase(String requestId, String offerId, String subscriberId, Boolean override, Map<String, Object> metas) {
		logger.debug("Entering TXManager.Purchase..........");
		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, offerId, subscriberId, null,
				SubscriptionLifeCycleEvent.PURCHASE, override, metas);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String terminate(String requestId, String subscriptionId, Boolean override, Map<String, Object> metas) {

		String subscriberId = null, offerId = null;

		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_SUBSCRIBER_ID))
			subscriberId = (String) metas.get(Constants.TXN_ENGINE_SUBSCRIBER_ID);
		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_OFFER_ID))
			offerId = (String) metas.get(Constants.TXN_ENGINE_OFFER_ID);

		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, null, null, subscriptionId,
				SubscriptionLifeCycleEvent.TERMINATE, override, metas);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String expiry(String requestId, String subscriptionId, Boolean override, Map<String, Object> metas) {
		String subscriberId = null, offerId = null;

		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_SUBSCRIBER_ID))
			subscriberId = (String) metas.get(Constants.TXN_ENGINE_SUBSCRIBER_ID);
		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_OFFER_ID))
			offerId = (String) metas.get(Constants.TXN_ENGINE_OFFER_ID);

		
		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, offerId, subscriberId, subscriptionId,
				SubscriptionLifeCycleEvent.EXPIRY, override, metas);
		
		
		executor.submit(command);
		return requestId;
	}

	@Override
	public String renew(String requestId, String subscriptionId, Boolean override, Map<String, Object> metas) {

		String subscriberId = null, offerId = null;

		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_SUBSCRIBER_ID))
			subscriberId = (String) metas.get(Constants.TXN_ENGINE_SUBSCRIBER_ID);
		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_OFFER_ID))
			offerId = (String) metas.get(Constants.TXN_ENGINE_OFFER_ID);
		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, offerId, subscriberId, subscriptionId,
				SubscriptionLifeCycleEvent.RENEWAL, override, metas);
		executor.submit(command);// TODO Auto-generated catch block
		return requestId;
	}

	@Override
	public String preExpiry(String requestId, String subscriptionId, Boolean override, Map<String, Object> metas) {

		String subscriberId = null, offerId = null;

		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_SUBSCRIBER_ID))
			subscriberId = (String) metas.get(Constants.TXN_ENGINE_SUBSCRIBER_ID);
		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_OFFER_ID))
			offerId = (String) metas.get(Constants.TXN_ENGINE_OFFER_ID);
		
		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, offerId, subscriberId, subscriptionId,
				SubscriptionLifeCycleEvent.PRE_EXPIRY, override, metas);
	
		executor.submit(command);
		return requestId;
	}

	@Override
	public String preRenewal(String requestId, String subscriptionId, Boolean override, Map<String, Object> metas) {

		String subscriberId = null, offerId = null;

		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_SUBSCRIBER_ID))
			subscriberId = (String) metas.get(Constants.TXN_ENGINE_SUBSCRIBER_ID);
		if (metas != null && metas.containsKey(Constants.TXN_ENGINE_OFFER_ID))
			offerId = (String) metas.get(Constants.TXN_ENGINE_OFFER_ID);
		HandleSubscriptionEvent command = new HandleSubscriptionEvent(requestId, offerId, subscriberId, subscriptionId,
				SubscriptionLifeCycleEvent.PRE_RENEWAL, override, metas);
	
		executor.submit(command);
		return requestId;
	}

	@Override
	public String querySubscription(String requestId, String subscriptionId) {
		QuerySubscription command = new QuerySubscription(requestId, subscriptionId);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String readSubscriber(String requestId, String subscriberId, Map<String, String> metas) {
		ReadSubscriber command = new ReadSubscriber(requestId, subscriberId, metas);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String readSubscriberMeta(String requestId, String subscriberId, Set<String> metaNames) {
		ReadSubscriberMeta command = new ReadSubscriberMeta(requestId, subscriberId, metaNames);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String updateSubscriber(String requestId, String subscriberId, Map<String, String> metas, String useCase) {
		logger.debug("Entered into the Transaction manager :update subscriber with metas of size" + metas.size() + "use case is " + useCase);
		UpdateSubscriber command = new UpdateSubscriber(requestId, subscriberId, metas, useCase);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String deleteSubscriber(String requestId, String subscriberId) {
		Subscriber subscriber = new Subscriber();
		subscriber.setMsisdn(subscriberId);
		DeleteSubscriber command = new DeleteSubscriber(requestId, subscriber);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String handleLifeCycle(String requestId, String subscriberId, String lifeCycleState, Map<String, String> metas) {
		HandleLifeCycle command = new HandleLifeCycle(requestId, subscriberId, lifeCycleState, metas);
		executor.submit(command);
		return requestId;
	}

	@Override
	public String discoverOfferForUserById(String requestId, String offerId, String subscriberId) {
		FetchOfferForUser command = new FetchOfferForUser(requestId, offerId, subscriberId);
		executor.submit(command);
		return requestId;
	}

}
