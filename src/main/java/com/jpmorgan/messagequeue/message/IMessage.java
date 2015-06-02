package com.jpmorgan.messagequeue.message;

public interface IMessage {

	public int groupId();
	
	public String getMessageText();
	
	public void completed();
}
