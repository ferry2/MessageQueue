package com.jpmorgan.messagequeue.sender;

import java.util.Comparator;
import java.util.concurrent.PriorityBlockingQueue;

import com.jpmorgan.messagequeue.message.IMessage;

public class MessageSender {

	private PriorityBlockingQueue<IMessage> messageQueue;
	
	public MessageSender() {
		messageQueue = new PriorityBlockingQueue<IMessage>(10, new Comparator<IMessage>() {
			public int compare(IMessage o1, IMessage o2) {
				if (o1.groupId() < o2.groupId()) {
					return -1;
				} else if (o1.groupId() > o2.groupId()) {
					return 1;
				} else {
					return 0;
				}
			}
		});
	}

	public void receive(IMessage message) {
		
	}
	
	public void process(IMessage message) {
		
	}
}
