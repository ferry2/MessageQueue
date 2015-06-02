package com.jpmorgan.messagequeue.message;

public class Message implements IMessage {

	private int groupId = -1;
	private String messageText;
	
	public Message(Integer groupId, String messageText) {
		this.groupId = groupId;
		this.messageText = messageText;
	}

	public void completed() {
		
	}

	public int groupId() {
		return groupId;
	}

	public String getMessageText() {
		return messageText;
	}
}
