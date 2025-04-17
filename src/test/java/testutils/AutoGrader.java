package testutils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;

public class AutoGrader {

	// Test if the code correctly implements lambda expressions
	public boolean testLambdaExpressions(String filePath) throws IOException {
		System.out.println("Starting testLambdaExpressions with file: " + filePath);

		File participantFile = new File(filePath); // Path to participant's file
		if (!participantFile.exists()) {
			System.out.println("File does not exist at path: " + filePath);
			return false;
		}

		FileInputStream fileInputStream = new FileInputStream(participantFile);
		JavaParser javaParser = new JavaParser();
		CompilationUnit cu;
		try {
			cu = javaParser.parse(fileInputStream).getResult()
					.orElseThrow(() -> new IOException("Failed to parse the Java file"));
		} catch (IOException e) {
			System.out.println("Error parsing the file: " + e.getMessage());
			throw e;
		}

		System.out.println("Parsed the Java file successfully.");

		// Flags to check for lambda expression usage
		boolean hasMainMethod = false;
		boolean usesLambdaExpression = false;
		boolean usesArrayList = false;
		boolean filtersUsingLambda = false;

		// Check for method declarations
		for (MethodDeclaration method : cu.findAll(MethodDeclaration.class)) {
			String methodName = method.getNameAsString();
			// Check for the presence of the main method
			if (methodName.equals("main")) {
				hasMainMethod = true;
			}
		}

		// Check for lambda expressions
		for (LambdaExpr lambdaExpr : cu.findAll(LambdaExpr.class)) {
			usesLambdaExpression = true;
		}

		// Check for ArrayList usage
		for (MethodCallExpr methodCallExpr : cu.findAll(MethodCallExpr.class)) {
			if (methodCallExpr.getNameAsString().equals("add") || methodCallExpr.getNameAsString().equals("stream")) {
				usesArrayList = true;
			}
			// Check for filtering using lambda expression
			if (methodCallExpr.getNameAsString().equals("filter") && methodCallExpr.getScope().isPresent()
					&& methodCallExpr.getScope().get().toString().contains("stream")) {
				filtersUsingLambda = true;
			}
		}

		// Log results of the checks
		System.out.println("Lambda expression is used: " + (usesLambdaExpression ? "YES" : "NO"));
		System.out.println("ArrayList is used: " + (usesArrayList ? "YES" : "NO"));
		System.out.println("Lambda expression filters ArrayList: " + (filtersUsingLambda ? "YES" : "NO"));

		// Final result - all conditions should be true
		boolean result = hasMainMethod && usesLambdaExpression && usesArrayList && filtersUsingLambda;

		System.out.println("Test result: " + result);
		return result;
	}
}
