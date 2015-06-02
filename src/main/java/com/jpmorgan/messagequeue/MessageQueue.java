package com.jpmorgan.messagequeue;

import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

import com.jpmorgan.messagequeue.gateway.Gateway;
import com.jpmorgan.messagequeue.gateway.IGateway;
import com.jpmorgan.messagequeue.message.IMessage;

public class MessageQueue {

	private int queueCapacity = 10;
	private Queue<IMessage> messageQueue;
	private IGateway gateway;
	
	public MessageQueue() {
		messageQueue = new PriorityQueue<IMessage>(new Comparator<IMessage>() {

			public int compare(IMessage o1, IMessage o2) {
				if (o1.groupId() > o2.groupId()) {
					return 1;
				} else if(o1.groupId() < o2.groupId()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
		gateway = new Gateway();
	}

	public synchronized void push(IMessage message) throws InterruptedException {
		while (queueCapacity == messageQueue.size()) {
			wait();
		}
		
		messageQueue.add(message);
		notify();
	}
	
	public synchronized void process() throws InterruptedException {
		while (true) {
			while (messageQueue.isEmpty()) {
				wait();
			}
			//messageQueue.remove();
			Iterator<IMessage> messageIterator = messageQueue.iterator();
			
			while (messageIterator.hasNext()) {
				IMessage message = (IMessage) messageIterator.next();
				gateway.send(message);
				message.completed();
				messageIterator.remove();
			}
			
			notifyAll();
		}
	}
}
