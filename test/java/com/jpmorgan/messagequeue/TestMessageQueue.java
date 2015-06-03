package com.jpmorgan.messagequeue;

import java.lang.reflect.Field;
import java.util.Queue;

import org.apache.log4j.BasicConfigurator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.jpmorgan.messagequeue.message.IMessage;
import com.jpmorgan.messagequeue.message.Message;

public class TestMessageQueue {

	private Queue<IMessage> queue;
	private MessageQueue messageQueue;
	
	@SuppressWarnings("unchecked")
	@Before
	public void setUp() throws Exception {
		BasicConfigurator.configure();
		
		messageQueue = new MessageQueue();
		Field messageQueueField = messageQueue.getClass().getDeclaredField("messageQueue");
		messageQueueField.setAccessible(true);
		queue = (Queue<IMessage>)messageQueueField.get(messageQueue);
		messageQueueField.setAccessible(false);
	}

	@Test
	public void testPushWithOneMessage() throws InterruptedException {
		IMessage message = new Message(2, "Message text");
		messageQueue.push(message);
		
		Assert.assertTrue(queue.size() == 1);
	}
	
	@Test
	public void testPushWithTenMessages() throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			IMessage message = new Message(i, "Content of message " + i);
			messageQueue.push(message);
		}
		
		Assert.assertTrue(queue.size() == 10);
	}

	@Test
	public void testProcessWithOneMessage() throws InterruptedException {
		final IMessage message = new Message(2, "Message text");
		
		Thread thread1 = new Thread(new Runnable() {
			
			public void run() {
				try {
					messageQueue.process();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		Thread thread2 = new Thread(new Runnable() {
			
			public void run() {
				try {
					messageQueue.push(message);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		thread1.start();
		thread2.start();
		
		Assert.assertTrue(queue.size() == 0);
	}
}
