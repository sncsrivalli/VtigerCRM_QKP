package ddt;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class WriteToProperties {

	public static void main(String[] args) throws IOException {
		// Step 1: Convert physical file into java readable object
		FileInputStream fis = new FileInputStream("./src/test/resources/skillraryData.properties");

		// Step 2: Create an instance of Properties class
		Properties property = new Properties();

		// Step 3: Load all key-value pairs from fis to property
		property.load(fis);
		
		// Step 4: Write to Properties
		property.put("subject", "selenium");
		
		// Step 5: Save the file
		FileOutputStream fos = new FileOutputStream("./src/test/resources/skillraryData.properties");
		property.store(fos, "Updated Successfully");

	}

}
