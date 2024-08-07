package additionalScenarios;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.List;
import java.util.Locale;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.Test;

public class SpiceJetDatePickerTest {

	@Test
	public void datePickerTest() throws InterruptedException {
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
		List<WebElement> monthAndYearList = driver.findElements(By.xpath("//div[contains(@data-testid, 'undefined-month')]/descendant::div[@class='css-76zvg2 r-homxoj r-adyw6z r-1kfrs79']"));
		for(int i = 0; i < monthAndYearList.size(); i++) {
			String[] str = monthAndYearList.get(i).getText().split(" ");
			int currentYear = Integer.parseInt(str[1]);
			int currentMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0]).get(ChronoField.MONTH_OF_YEAR);

			if(currentYear == reqYear && currentMonth == reqMonth) {
				monthAndYearList.get(i).click();
				driver.findElement(By.xpath("//div[contains(text(),'"+str[0]+"')]/../../descendant::div[contains(text(),'"+reqDate+"')]")).click();
				break;
			}
		}
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[@data-testid=\"home-page-flight-cta\"]")))).click();
		Thread.sleep(4000);
		driver.quit();
	}
}
