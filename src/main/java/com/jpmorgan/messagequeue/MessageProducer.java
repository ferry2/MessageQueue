package com.jpmorgan.messagequeue;

import org.apache.log4j.Logger;

import com.jpmorgan.messagequeue.message.IMessage;

public class MessageProducer extends Thread {

	private MessageQueue messageQueue;
	private IMessage message;
	
	public MessageProducer(MessageQueue messageQueue, IMessage message) {
		this.messageQueue = messageQueue;
		this.message = message;
	}


	@Override
	public void run() {
		try {
			messageQueue.push(message);
		} catch (InterruptedException e) {
			Logger.getLogger(MessageProducer.class).debug(e.getMessage(), e);
		}
	}

}
