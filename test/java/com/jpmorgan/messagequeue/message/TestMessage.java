package com.jpmorgan.messagequeue.message;

import org.junit.Assert;
import org.junit.Test;

public class TestMessage {

	@Test
	public void testMessageCreation() {
		IMessage message = new Message(4, "Message text");
		
		Assert.assertEquals(4, message.groupId());
		Assert.assertEquals("Message text", message.getMessageText());
	}

	@Test
	public void testNegativeGroupId() {
		IMessage message = new Message(null, "Message text");
		
		Assert.assertTrue(message.groupId() < 0);
	}
}
