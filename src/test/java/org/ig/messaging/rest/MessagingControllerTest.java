package org.ig.messaging.rest;

import java.io.FileNotFoundException;
import java.io.IOException;

import org.ig.messaging.api.Broker;
import org.ig.messaging.api.BrokerService;
import org.ig.messaging.api.Constants;
import org.ig.messaging.api.MessagingService;
import org.ig.messaging.api.TestUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RunWith(MockitoJUnitRunner.class)
public class MessagingControllerTest {
	@Mock
	private BrokerService brokerService;

	@Mock
	private MessagingService messagingService;

	@Mock
	private MultipartFile file;

	@Mock
	private RedirectAttributes attributes;

	@Captor
	private ArgumentCaptor<Broker> brokerCaptor;

	@InjectMocks
	private MessagingController controller;

	@Before
	public void setup() {
		Mockito.reset(this.brokerService, this.messagingService, this.file, this.attributes);
	}

	@Test
	public void testMessagingController() {
		Assert.assertNotNull(this.controller);
	}

	@Test
	public void testBrokerForm() {
		Model model = Mockito.mock(Model.class);
		Assert.assertEquals("broker", this.controller.brokerForm(model));
		Mockito.verify(model).addAttribute(Mockito.eq("broker"), this.brokerCaptor.capture());
		Assert.assertEquals(TestUtils.createSampleBroker(), this.brokerCaptor.getValue());
	}

	@Test
	public void testBrokerSubmit() {
		Broker broker = new Broker();
		Assert.assertEquals("result", this.controller.brokerSubmit(broker));
		Mockito.verify(this.brokerService).updateBrokerInfo(broker);
	}

	@Test
	public void testSendMessageForm() {
		Assert.assertEquals("sendmessage", this.controller.sendMessageForm());
	}

	@Test
	public void testHandleFileUpload() throws FileNotFoundException, IOException {
		MultipartFile file = Mockito.mock(MultipartFile.class);

		Assert.assertEquals("redirect:" + Constants.URL_SEND_MESSAGE, this.controller.sendFileMessage(file,
				this.attributes));
		Mockito.verify(this.messagingService).sendFileMessage(file);
		Mockito.verify(this.attributes).addFlashAttribute(Mockito.eq("result"), Mockito.isNotNull());
	}

	@Test
	public void testHandleFileUpload_Exception() throws FileNotFoundException, IOException {
		MultipartFile file = Mockito.mock(MultipartFile.class);
		Mockito.doThrow(new IOException("")).when(this.messagingService).sendFileMessage(file);
		Assert.assertEquals("redirect:" + Constants.URL_SEND_MESSAGE, this.controller.sendFileMessage(file,
				this.attributes));
		Mockito.verify(this.messagingService).sendFileMessage(file);
		Mockito.verify(this.attributes).addFlashAttribute(Mockito.eq("result"), Mockito.isNotNull());
	}

}
