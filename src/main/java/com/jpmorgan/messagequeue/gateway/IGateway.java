package com.jpmorgan.messagequeue.gateway;

import com.jpmorgan.messagequeue.message.IMessage;

public interface IGateway {

	public void send(IMessage message);
}
