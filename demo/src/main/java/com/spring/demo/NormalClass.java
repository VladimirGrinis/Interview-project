package com.spring.demo;

public class NormalClass extends GeneralClass implements Observer {

	public NormalClass() {
		message = "I am Normal Class";
	}

	public void print() {
		System.out.println(message);
	}

	@Override
	public void update(String message) {
		this.message = message;
		notifyObservers();
	}

	private void notifyObservers() {
		System.out.println("Normal class message updated to: "  + message);
	}
}
