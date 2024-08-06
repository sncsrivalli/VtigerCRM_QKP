package additionalScenarios;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.Test;

public class SpiceJetDatePickerTest {

	@Test
	public void datePickerTest() {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("--disable-geolocation");
		options.addArguments("--disable-notifications");
		
		WebDriver driver = new ChromeDriver(options);
		driver.manage().window().maximize();
		driver.get("https://www.spicejet.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		
		driver.findElement(By.xpath("//div[text()='From']")).click();
		driver.findElement(By.xpath("//div[text()='From']/following-sibling::div/input")).sendKeys("hyd");
		
		driver.findElement(By.xpath("//div[text()='To']/following-sibling::div/input")).sendKeys("chennai");
		
		int reqYear = 2025;
		int reqMonth = 2;
		String reqDate = "9";
		
		String currentMonthYear = driver.findElement(By.xpath("(//div[@class='css-76zvg2 r-homxoj r-adyw6z r-1kfrs79'])[1]")).getText();
		String[] str = currentMonthYear.split(" ");
		int currentYear = Integer.parseInt(str[1]);
		
		while(currentYear < reqYear) {
			
		}
		
	}
}
