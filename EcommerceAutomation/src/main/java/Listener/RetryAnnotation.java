package Listener;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.IDataProviderAnnotation;
import org.testng.annotations.ITestAnnotation;

import Base.Generic;

public class RetryAnnotation implements IAnnotationTransformer{

	@Override
	public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
			annotation.setRetryAnalyzer(RetryFailedTest.class);
	}
	
	

}
