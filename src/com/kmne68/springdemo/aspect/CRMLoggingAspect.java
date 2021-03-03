package com.kmne68.springdemo.aspect;

import java.util.logging.Logger;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class CRMLoggingAspect {

	// set up logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	// set up pointcut declarations
	// in the pointcut expression:
	// - the first asterisk indicates a match on any CLASS in the package
	// - the second asterisk inidicates a match on any METHOD in the class
	// - the double periods in parenthesis indicate a match on any number of arguments
	@Pointcut("execution(* com.kmne68.springdemo.controller.*.*(..))")
	private void forControllerPackage() {
		
	}
	
	
	@Pointcut("execution(* com.kmne68.springdemo.service.*.*(..))")
	private void forServicePackage() {
		
	}
	
	
	@Pointcut("execution(* com.kmne68.springdemo.dao.*.*(..))")
	private void forDAOPackage() {
		
	}
	
	
	@Pointcut("forControllerPackage() || forServicePackage() || forDAOPackage()")
	private void forAppFlow() {
		
	}
	
	
	// add @Before advice
	@Before("forAppFlow()")
	public void before(JoinPoint joinPoint) {
		
		// display method we're calling
		String method = joinPoint.getSignature().toShortString();
		logger.info("=====>> in @Before: calling method: " + method);
		
		// display the arguments to the method
		// get the arguments
		Object[] args = joinPoint.getArgs();
		
		// loop through and display the arguments
		for(Object tempArg : args) {
			logger.info("----->> argument: " + tempArg);
		}
		
	}
	
	// add @AfterReturning advice
	@AfterReturning(
			pointcut="forAppFlow()",
			returning="result"
			)
	public void afterReturning(JoinPoint joinPoint, Object result) {
		
		// display method we are returning from
		String method = joinPoint.getSignature().toShortString();
		logger.info("=====>> in @AfterReturning: from method: " + method);
		
		// display data returned
		logger.info("=====>> result: " + result);
	}
	
}
