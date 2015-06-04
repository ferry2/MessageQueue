package com.jpmorgan.exdception;


public class GroupNotFoundException extends Exception {

	private static final long serialVersionUID = -8029333891344562134L;

	public GroupNotFoundException() {
		super();
	}

	public GroupNotFoundException(String message) {
		super(message);
	}
}
