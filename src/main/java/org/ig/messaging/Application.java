
package org.ig.messaging;

import javax.jms.ConnectionFactory;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.activemq.spring.ActiveMQConnectionFactory;
import org.ig.messaging.api.Constants;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.annotation.Transformer;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.transformer.StreamTransformer;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
@IntegrationComponentScan
@EnableIntegration
@ImportResource("classpath:spring/messaging-integration-context.xml")
public class Application {

	@Bean("connectionFactory")
	public ConnectionFactory connectionFactory() {
		return new ActiveMQConnectionFactory();
	}

	@Bean(name = "amqQueue")
	public ActiveMQQueue amqQueue() {
		return new ActiveMQQueue(Constants.SAMPLE_DESTINATION_NAME);
	}

	@Bean(name = "amqTopic")
	public ActiveMQTopic amqTopic() {
		return new ActiveMQTopic(Constants.SAMPLE_DESTINATION_NAME);
	}

	@Bean
	@Transformer(inputChannel = "streamChannel", outputChannel = "stringChannel")
	public StreamTransformer transformer() {

		StreamTransformer transformer = new StreamTransformer("UTF-8");
		return transformer;
	}

	public static void main(String[] args) {
		// Launch the application
		SpringApplication.run(Application.class, args);
	}

}
