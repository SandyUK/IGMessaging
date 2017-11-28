package org.ig.messaging.api;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public interface MessagingService {
	void sendFileMessage(MultipartFile file) throws FileNotFoundException, IOException;
}
