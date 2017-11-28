package org.ig.messaging.api;

public class TestUtils {
	public static Broker createSampleBroker() {
		Broker broker = new Broker();
		broker.setDestinationName(Constants.SAMPLE_DESTINATION_NAME);
		broker.setUsername(Constants.SAMPLE_USERNAME);
		broker.setPassword(Constants.SAMPLE_PASSWORD);
		broker.setUrl(Constants.SAMPLE_BROKER_URL);
		broker.setUseQueue(Constants.SAMPLE_USE_QUEUE);
		return broker;
	}

}
