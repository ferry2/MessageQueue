package com.jpmorgan.messagequeue;

import org.apache.log4j.Logger;

public class MessageReader extends Thread {

	private MessageQueue messageQueue;
	
	public MessageReader(MessageQueue messageQueue) {
		this.messageQueue = messageQueue;
	}

	@Override
	public void run() {
		try {
			messageQueue.process();
		} catch (InterruptedException e) {
			Logger.getLogger(MessageReader.class).debug(e.getMessage(), e);
		}
	}

}
