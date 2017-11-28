package org.ig.messaging.impl;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.ig.messaging.api.Broker;
import org.ig.messaging.api.TestUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.integration.jms.JmsSendingMessageHandler;

@RunWith(MockitoJUnitRunner.class)
public class BrokerServiceImplTest {

	@Mock
	private ActiveMQConnectionFactory connectionFactory;
	@Mock
	private ActiveMQQueue queue;

	@Mock
	private ActiveMQTopic topic;

	@Mock
	private JmsSendingMessageHandler handler;

	@InjectMocks
	private BrokerServiceImpl brokerService;

	@Test
	public void testUpdateBrokerInfo_Queue() {
		Broker broker = TestUtils.createSampleBroker();
		this.brokerService.updateBrokerInfo(broker);
		Mockito.verify(this.connectionFactory).setBrokerURL(broker.getUrl());
		Mockito.verify(this.connectionFactory).setUserName(broker.getUsername());
		Mockito.verify(this.connectionFactory).setPassword(broker.getPassword());
		Mockito.verify(this.handler).setDestination(this.queue);
		Mockito.verify(this.queue).setPhysicalName(broker.getDestinationName());
		;
	}

	@Test
	public void testUpdateBrokerInfo_Topic() {
		Broker broker = TestUtils.createSampleBroker();
		broker.setUseQueue(false);
		this.brokerService.updateBrokerInfo(broker);
		Mockito.verify(this.connectionFactory).setBrokerURL(broker.getUrl());
		Mockito.verify(this.connectionFactory).setUserName(broker.getUsername());
		Mockito.verify(this.connectionFactory).setPassword(broker.getPassword());
		Mockito.verify(this.handler).setDestination(this.topic);
		Mockito.verify(this.topic).setPhysicalName(broker.getDestinationName());
		;
	}

}
