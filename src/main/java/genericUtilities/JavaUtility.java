package genericUtilities;

import java.util.Random;

/**
 * This class contains reusable methods to perform java related operations
 * @author sncsr
 *
 */
public class JavaUtility {
	
	/**
	 * This method generates random number within specified limit
	 * @param limit
	 * @return
	 */
	public int generateRandomNum(int limit) {
		Random random = new Random();
		return random.nextInt(limit);
	}
	
	/**
	 * This method pauses the script for the specified time
	 * @param time
	 */
	public void waiting(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}
