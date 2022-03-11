package Listener;

import org.apache.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryFailedTest implements IRetryAnalyzer {
	static final Logger log = Logger.getLogger(RetryFailedTest.class.getName());  
	int counter=0;
	int retryCount=4;
	
	@Override
	public boolean retry(ITestResult result) {
		if(!result.isSuccess()) {
			if(counter<retryCount) {
				log.info("Retrying failed test and the counter is "+counter);
				counter++;
				return true;
			}
		}
		return false;
	}
	

}
