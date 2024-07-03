package com.spring.demo;

import org.springframework.stereotype.Component;

@Component
public class ClassFactory implements Factory {

	public final static String SINGLE = "SINGLE";
	public final static String NORMAL = "NORMAL";

	@Override
	public GeneralClass createClass(String ClassType) {

		if (ClassType.equals(SINGLE)) {
			return Singleton.getInstance();
		}

		if (ClassType.equals(NORMAL)) {
			return new NormalClass();
		}

		return null;

	}

}
