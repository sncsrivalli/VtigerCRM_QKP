package popup;

import java.time.Duration;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class ChildBrowserPopUp {

	public static void main(String[] args) throws InterruptedException {
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://www.flipkart.com/");
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		
		driver.findElement(By.name("q")).sendKeys("mobiles");
		driver.findElement(By.className("_2iLD__")).click();
		
		driver.findElement(By.xpath("//div[text()='OPPO Find X8 5G (Star Grey, 256 GB)']")).click();
		
		Set<String> windowIDs = driver.getWindowHandles();
		String parentID = driver.getWindowHandle();
		
		for (String s : windowIDs) {
			driver.switchTo().window(s);
			if(driver.getTitle().contains("OPPO Find X8 5G"))
				break;
		}
		
		driver.findElement(By.xpath("//button[text()='Add to cart']")).click();
		Thread.sleep(3000);
		driver.close();
		
		driver.switchTo().window(parentID);
		
		driver.navigate().refresh();
		System.out.println(driver.findElement(By.className("ZuSA--")).getText());
		driver.quit();
	}

}
