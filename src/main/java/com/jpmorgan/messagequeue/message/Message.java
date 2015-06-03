package com.jpmorgan.messagequeue.message;

public class Message implements IMessage {

	private int groupId;
	private String messageText;
	private boolean complete = false;
	
	public Message(Integer groupId, String messageText) {
		this.groupId = groupId != null ? groupId : -1;
		this.messageText = messageText;
	}

	public void completed() {
		complete = true;
	}
	
	public boolean isComplete() {
		return complete;
	}

	public int groupId() {
		return groupId;
	}

	public String getMessageText() {
		return messageText;
	}
}
