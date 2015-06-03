package com.jpmorgan.messagequeue.gateway;

import org.apache.log4j.Logger;

import com.jpmorgan.messagequeue.message.IMessage;

public class Gateway implements IGateway {

	public Gateway() {
		
	}
	
	public void send(IMessage message) {
		message.completed();
		Logger.getLogger(Gateway.class).debug(String.format("Sended message: %s from group %d", message.getMessageText(), message.groupId()));
	}
}
