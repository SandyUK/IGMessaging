package org.ig.messaging.impl;

import javax.jms.ConnectionFactory;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.ig.messaging.api.Broker;
import org.ig.messaging.api.BrokerService;
import org.springframework.integration.jms.JmsSendingMessageHandler;
import org.springframework.stereotype.Service;

@Service
public class BrokerServiceImpl implements BrokerService {
	private final ActiveMQConnectionFactory connectionFactory;
	private final JmsSendingMessageHandler handler;
	private final ActiveMQQueue queue;
	private final ActiveMQTopic topic;

	public BrokerServiceImpl(ConnectionFactory connectionFactory, JmsSendingMessageHandler handler, ActiveMQQueue queue,
			ActiveMQTopic topic) {
		this.connectionFactory = (ActiveMQConnectionFactory) connectionFactory;
		this.handler = handler;
		this.queue = queue;
		this.topic = topic;
	}

	@Override
	public void updateBrokerInfo(Broker broker) {
		this.connectionFactory.setBrokerURL(broker.getUrl());
		this.connectionFactory.setUserName(broker.getUsername());
		this.connectionFactory.setPassword(broker.getPassword());

		if (broker.isUseQueue()) {
			this.queue.setPhysicalName(broker.getDestinationName());
			this.handler.setDestination(this.queue);
		} else {
			this.topic.setPhysicalName(broker.getDestinationName());
			this.handler.setDestination(this.topic);
		}

	}

}
