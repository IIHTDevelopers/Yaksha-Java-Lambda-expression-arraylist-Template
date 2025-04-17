package com.yaksha.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class LambdaExpressionExample {

	public static void main(String[] args) {
		// Task 1: Create a simple lambda expression
		Runnable printMessage = () -> System.out.println("Hello from Lambda Expression!");
		printMessage.run(); // Execute the lambda expression

		// Task 2: Lambda expression with ArrayList
		List<String> names = new ArrayList<>();
		names.add("John");
		names.add("Jane");
		names.add("Mark");
		names.add("Paul");

		// Task 3: Use lambda expression with ArrayList to filter names
		Predicate<String> startsWithJ = name -> name.startsWith("J");
		names.stream().filter(startsWithJ).forEach(System.out::println); // Prints names starting with "J"
	}
}
