package genericUtilities;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * This class is used to retry the failed test scripts
 * @author sncsr
 *
 */
public class RetryImplementation implements IRetryAnalyzer {

	int count = 0;
	int maxRetries = 3;
	
	@Override
	public boolean retry(ITestResult result) {
		if(count < maxRetries) {
			count++;
			return true;
		}
		return false;
	}	
}
