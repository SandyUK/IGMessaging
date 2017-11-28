package org.ig.messaging.impl;

import java.io.IOException;

import org.ig.messaging.api.MessagingService;
import org.ig.messaging.api.MyGateway;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MessagingServiceImpl implements MessagingService {
	private final MyGateway gateway;

	public MessagingServiceImpl(MyGateway gateway) {
		this.gateway = gateway;
	}

	@Override
	public void sendFileMessage(MultipartFile file) throws IOException {
		this.gateway.sendFileMessage(file.getInputStream());

	}

}
