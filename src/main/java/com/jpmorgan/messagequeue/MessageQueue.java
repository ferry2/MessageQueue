package com.jpmorgan.messagequeue;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import com.jpmorgan.exdception.GroupNotFoundException;
import com.jpmorgan.messagequeue.gateway.Gateway;
import com.jpmorgan.messagequeue.gateway.IGateway;
import com.jpmorgan.messagequeue.message.IMessage;

public class MessageQueue {

	private int queueCapacity = 10;
	private Set<Integer> canceledGroupIds;
	private Queue<IMessage> messageQueue;
	private IGateway gateway;
	
	public MessageQueue() {
		canceledGroupIds = new HashSet<Integer>();
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
			
			Iterator<IMessage> messageIterator = messageQueue.iterator();
			while (messageIterator.hasNext()) {
				IMessage message = messageIterator.next();
				
				if (isCanceled(message.groupId())) {
					messageIterator.remove();
				} else {
					gateway.send(message);
					messageIterator.remove();
				}
			}
			
			notifyAll();
		}
	}
	
	public synchronized void cancelMessageGroup(int groupId) throws GroupNotFoundException {
		boolean found = false;
		for (IMessage message : messageQueue) {
			if (message.groupId() == groupId) {
				found = true;
				break;
			}
		}
		
		if (found) {
			canceledGroupIds.add(groupId);
		} else {
			throw new GroupNotFoundException(String.format("Cannot find messages from group ID %d. Maybe already sended?", groupId));
		}
	}
	
	private boolean isCanceled(int groupId) {
		return canceledGroupIds.contains(groupId);
	}
}
