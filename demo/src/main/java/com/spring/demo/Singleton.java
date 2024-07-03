package com.spring.demo;

public class Singleton extends GeneralClass implements Observer {

	private static Singleton instance;

	private Singleton() {
		message = "I am Singleton Class";
	};

	public static Singleton getInstance() {
		if (instance == null) {
			instance = new Singleton();
		}

		return instance;
	}

	public void print() {
		System.out.println(message);
	}

	@Override
	public void update(String message) {
		this.message = message;
		System.out.println("Singleton message updated to: " + message);
	}
}
