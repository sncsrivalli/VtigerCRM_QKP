package datepickers;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class MakeMyTripTest {

	public static void main(String[] args) throws InterruptedException, IOException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.makemytrip.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		driver.findElement(By.cssSelector("span.commonModal__close")).click();
		driver.findElement(By.xpath("//label[@for=\"fromCity\"]")).click();
		WebElement fromTF = driver.findElement(By.xpath("//input[@placeholder=\"From\"]"));
		Actions actions = new Actions(driver);
		actions.sendKeys(fromTF, "Hyderabad").pause(2000).sendKeys(Keys.ARROW_DOWN ).sendKeys(Keys.ENTER).build().perform();

		driver.findElement(By.xpath("//label[@for=\"toCity\"]")).click();
		WebElement toTF = driver.findElement(By.xpath("//input[@placeholder=\"To\"]"));
		actions.sendKeys(toTF, "Bangalore").pause(2000).sendKeys(Keys.ARROW_DOWN ).sendKeys(Keys.ENTER).build().perform();

		String reqDate = "9";
		int reqMonth = 5;
		int reqYear = 2025;

		String currentMonthYear = driver
				.findElement(By.xpath("//div[@class='DayPicker-Month'][1]/div[@class='DayPicker-Caption']")).getText();
		String[] str = currentMonthYear.split(" ");
		int currentYear = Integer.parseInt(str[1]);

		while (currentYear < reqYear) {
			driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
			currentMonthYear = driver
					.findElement(By.xpath("//div[@class='DayPicker-Month'][1]/div[@class='DayPicker-Caption']"))
					.getText();
			str = currentMonthYear.split(" ");
			currentYear = Integer.parseInt(str[1]);
		}

		int currentMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
				.get(ChronoField.MONTH_OF_YEAR);

		while (currentMonth < reqMonth) {
			driver.findElement(By.xpath("//span[@aria-label='Next Month']")).click();
			currentMonthYear = driver
					.findElement(By.xpath("//div[@class='DayPicker-Month'][1]/div[@class='DayPicker-Caption']"))
					.getText();
			str = currentMonthYear.split(" ");
			currentMonth = DateTimeFormatter.ofPattern("MMMM").withLocale(Locale.ENGLISH).parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}

		driver.findElement(By.xpath("//div[@class = 'DayPicker-Day']/descendant::p[text()='" + reqDate + "']")).click();
		
		driver.findElement(By.xpath("//span[text()='Travellers & Class']")).click();
		driver.findElement(By.xpath("//li[@data-cy='adults-2']")).click();
		driver.findElement(By.xpath("//li[@data-cy='children-1']")).click();
		driver.findElement(By.xpath("//li[@data-cy='infants-1']")).click();
		driver.findElement(By.xpath("//li[.='Premium Economy']")).click();
		
		driver.findElement(By.xpath("//button[.='APPLY']")).click();
		driver.findElement(By.xpath("//a[text()='Search']")).click();
		
		Thread.sleep(3000);
		TakesScreenshot ts = (TakesScreenshot)driver;
		File src = ts.getScreenshotAs(OutputType.FILE);
		File dest = new File("./Screenshots/makemytrip.png");
		FileUtils.copyFile(src, dest);
		driver.quit();
	
	}

}
