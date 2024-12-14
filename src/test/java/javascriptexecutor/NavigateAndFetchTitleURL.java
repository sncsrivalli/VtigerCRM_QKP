package javascriptexecutor;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class NavigateAndFetchTitleURL {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		JavascriptExecutor js = (JavascriptExecutor)driver;
		//Navigate to App
		js.executeScript("window.location=arguments[0]", "https://www.amazon.in/");
		
		//Fetch the title
		System.out.println(js.executeScript("return document.title"));
		
		//Fetch the URL
		System.out.println(js.executeScript("return document.URL"));
		
		Thread.sleep(2000);
		//Refresh the page
		js.executeScript("history.go(0)");
		
		WebElement searchTF = driver.findElement(By.id("twotabsearchtextbox"));
		//Pass data to text field
		js.executeScript("arguments[0].value=arguments[1]", searchTF, "handbags");
		
		WebElement searchBTN = driver.findElement(By.id("nav-search-submit-button"));
		//Click on an element
		js.executeScript("arguments[0].click()", searchBTN);
		
		Thread.sleep(3000);
		
		driver.quit();
	}

}
