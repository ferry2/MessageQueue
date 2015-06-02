package com.jpmorgan.messagequeue.entrypoint;

import org.apache.log4j.BasicConfigurator;

import com.jpmorgan.messagequeue.MessageProducer;
import com.jpmorgan.messagequeue.MessageQueue;
import com.jpmorgan.messagequeue.MessageReader;
import com.jpmorgan.messagequeue.message.Message;

public class Messenger {

	public static void main(String[] args) {
		BasicConfigurator.configure();
		
		MessageQueue messageQueue = new MessageQueue();
		MessageReader reader = new MessageReader(messageQueue);
		reader.start();
		
		for (int i = 0; i < 100; i++) {
			int groupId = i;
			
			if (groupId % 5 == 0) {
				groupId--;
			}
			
			MessageProducer producer = new MessageProducer(messageQueue, new Message(groupId, "Some message"));
			producer.start();
		}
	}
}
