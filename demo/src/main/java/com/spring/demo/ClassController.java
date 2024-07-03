package com.spring.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.v3.oas.annotations.Operation;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
public class ClassController {

	private final ClassFactory classFactory;
	private final MessageSubject singletonSubject;
	private final MessageSubject normalClassSubject;
	private final Singleton singleton;
	private final Map<Integer, NormalClass> normalClassInstances = new HashMap<>();
	private final AtomicInteger idCounter = new AtomicInteger();

	public ClassController(ClassFactory classFactory) {
		this.classFactory = classFactory;
		this.singletonSubject = new MessageSubject();
		this.normalClassSubject = new MessageSubject();

		// Create singleton instance and add it as an observer
		this.singleton = Singleton.getInstance();
		singletonSubject.addObserver(singleton);
	}

	@GetMapping("/api/classes/{classType}")
	@Operation(summary = "Create class", description = "Create class according to received value (SINGLE=Singleton, NORMAL=NormalClass)")
	public void getClass(@PathVariable String classType) {
		classFactory.createClass(classType).print();
	}

	@GetMapping("/api/classes/singleton")
	@Operation(summary = "Create singleton", description = "Get Singleton class")
	public void getSingleton() {
		singleton.print();
	}

	@PostMapping("/api/classes/normal")
	@Operation(summary = "Create normal", description = "Create a new NormalClass instance and returns its id")
	public int createNormal() {
		int id = idCounter.incrementAndGet();
		NormalClass normalClass = (NormalClass) classFactory.createClass(ClassFactory.NORMAL);
		normalClassInstances.put(id, normalClass);
		normalClassSubject.addObserver(normalClass);
		return id;
	}

	@GetMapping("/api/classes/normal/{id}")
	@Operation(summary = "Get normal", description = "Get a specific NormalClass instance by id")
	public void getNormal(@PathVariable int id) {
		NormalClass normalClass = normalClassInstances.get(id);
		if (normalClass != null) {
			normalClass.print();
		} else {
			System.out.println("NormalClass instance not found");
		}
	}

	@PostMapping("/api/classes/singleton/message")
	@Operation(summary = "Update singleton message", description = "Update the message for the Singleton class")
	public void updateSingletonMessage(@RequestParam String message) {
		singletonSubject.setMessage(message);
	}

	@PostMapping("/api/classes/normal/{id}/message")
	@Operation(summary = "Update normal class message", description = "Update the message for a specific NormalClass instance by id")
	public void updateNormalMessage(@PathVariable int id, @RequestParam String message) {
		NormalClass normalClass = normalClassInstances.get(id);
		if (normalClass != null) {
			normalClass.update(message);
		} else {
			System.out.println("NormalClass instance not found");
		}
	}
}
