package org.ig.messaging.rest;

import org.ig.messaging.api.Broker;
import org.ig.messaging.api.BrokerService;
import org.ig.messaging.api.Constants;
import org.ig.messaging.api.MessagingService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MessagingController {
	private final BrokerService brokerService;
	private final MessagingService messagingService;

	public MessagingController(BrokerService brokerService, MessagingService messagingService) {
		this.brokerService = brokerService;
		this.messagingService = messagingService;
	}

	@GetMapping(Constants.URL_BROKER)
	public String brokerForm(Model model) {
		Broker broker = new Broker();
		broker.setUrl(Constants.SAMPLE_BROKER_URL);
		broker.setUsername(Constants.SAMPLE_USERNAME);
		broker.setPassword(Constants.SAMPLE_PASSWORD);
		broker.setDestinationName(Constants.SAMPLE_DESTINATION_NAME);
		broker.setUseQueue(Constants.SAMPLE_USE_QUEUE);
		model.addAttribute("broker", broker);
		return "broker";
	}

	@PostMapping(Constants.URL_BROKER)
	public String brokerSubmit(@ModelAttribute Broker broker) {
		this.brokerService.updateBrokerInfo(broker);
		return "result";
	}

	@GetMapping(Constants.URL_SEND_MESSAGE)
	public String sendMessageForm() {
		return "sendmessage";
	}

	@PostMapping(Constants.URL_SEND_MESSAGE)
	public String sendFileMessage(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			this.messagingService.sendFileMessage(file);
			redirectAttributes.addFlashAttribute("result", "File was sent successfully.");
		} catch (Exception ex) {
			redirectAttributes.addFlashAttribute("result", "Failed to send file: " + ex.getMessage());
			System.out.println(ex);
		}
		return "redirect:" + Constants.URL_SEND_MESSAGE;
	}

}
