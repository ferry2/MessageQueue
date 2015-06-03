package com.jpmorgan.messagequeue.gateway;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.messagequeue.message.IMessage;
import com.jpmorgan.messagequeue.message.Message;

public class TestGateway {

	@Before
	public void setUp() {
		BasicConfigurator.configure();
	}
	
	@Test
	public void testSend() {
		IMessage message = new Message(4, "Message text");
		IGateway gateway = new Gateway();
		
		gateway.send(message);
		assertTrue(message.isComplete());
	}

}
