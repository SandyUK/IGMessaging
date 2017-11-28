package org.ig.messaging.api;

import java.io.InputStream;

import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(name = "myGateway")
public interface MyGateway {

	@Gateway(requestChannel = "streamChannel", replyTimeout = 0)
	String sendFileMessage(InputStream inputStream);

}
