package genericUtilityImplementationScripts;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoField;
import java.util.Locale;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import genericUtilities.DataType;
import genericUtilities.ExcelUtility;
import genericUtilities.IConstantPath;
import genericUtilities.JavaUtility;
import genericUtilities.PropertiesUtility;
import genericUtilities.WebDriverUtility;

public class CreateEventTest {

	public static void main(String[] args) {
		PropertiesUtility propertyUtil = new PropertiesUtility();
		ExcelUtility excel = new ExcelUtility();
		JavaUtility jutil = new JavaUtility();
		WebDriverUtility driverUtil = new WebDriverUtility();
		
		propertyUtil.propertiesInit(IConstantPath.PROPERTIES_FILE_PATH);
		excel.excelInit(IConstantPath.EXCEL_PATH);
		
		WebDriver driver = driverUtil.launchBrowser(propertyUtil.readFromProperties("browser"));
		driverUtil.maximizeBrowser();
		driverUtil.navigateToApp(propertyUtil.readFromProperties("url"));
		
		long time = (Long) jutil.convertStringToAnyDataType(propertyUtil.readFromProperties("timeouts"), 
																				DataType.LONG);
		driverUtil.waitTillElementFound(time);
		
		if(driver.getTitle().contains("vtiger CRM"))
			System.out.println("Login Page Displayed");
		else
			driverUtil.quitAllWindows();
		
		driver.findElement(By.name("user_name")).sendKeys(propertyUtil.readFromProperties("username"));
		driver.findElement(By.name("user_password")).sendKeys(propertyUtil.readFromProperties("password"));
		driver.findElement(By.id("submitButton")).submit();
		
		if(driver.getTitle().contains("Home"))
			System.out.println("Home Page is Displayed");
		else
			driverUtil.quitAllWindows();
		
		Map<String, String> map = excel.readFromExcel("EventsTestData", "Create New Event");
		WebElement quickCreateDD = driver.findElement(By.id("qccombo"));
		driverUtil.handleDropdown(quickCreateDD, map.get("Quick Create"));
		
		driver.findElement(By.name("subject")).sendKeys(map.get("Subject"));
		driver.findElement(By.id("jscal_trigger_date_start")).click();
		
		String[] startDate = jutil.splitString(map.get("Start Date"), "-"); 
		int reqStartYear = (Integer) jutil.convertStringToAnyDataType(startDate[0], DataType.INT);
		String reqStartDate = startDate[2];
		int reqStartMonth = jutil.convertMonthToInt(startDate[1]);
		
		String currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
		String[] str = currentMonthYear.split(", ");
		int currentYear = Integer.parseInt(str[1]);
		
		while(currentYear < reqStartYear) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[text()='»']")).click();
			
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentYear = Integer.parseInt(str[1]);
		}
		
		int currentMonth = DateTimeFormatter
							.ofPattern("MMMM")
							.withLocale(Locale.ENGLISH)
							.parse(str[0])
							.get(ChronoField.MONTH_OF_YEAR);
		int reqStartMonthInNum = DateTimeFormatter
							.ofPattern("MMMM")
							.withLocale(Locale.ENGLISH)
							.parse(reqStartMonth)
							.get(ChronoField.MONTH_OF_YEAR);
		
		while(currentMonth < reqStartMonthInNum) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='›']")).click();
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentMonth = DateTimeFormatter
					.ofPattern("MMMM")
					.withLocale(Locale.ENGLISH)
					.parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		
		while(currentMonth > reqStartMonthInNum) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='‹']")).click();
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentMonth = DateTimeFormatter
					.ofPattern("MMMM")
					.withLocale(Locale.ENGLISH)
					.parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		
		driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='"+reqStartDate+"']")).click();
		
		driver.findElement(By.id("jscal_trigger_due_date")).click();
		
		int reqEndYear = 2027;
		String reqEndDate = "9";
		String reqEndMonth = "February";
		
		currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
		str = currentMonthYear.split(", ");
		currentYear = Integer.parseInt(str[1]);
		
		while(currentYear < reqEndYear) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[text()='»']")).click();
			
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentYear = Integer.parseInt(str[1]);
		}
		
		currentMonth = DateTimeFormatter
							.ofPattern("MMMM")
							.withLocale(Locale.ENGLISH)
							.parse(str[0])
							.get(ChronoField.MONTH_OF_YEAR);
		int reqEndMonthInNum = DateTimeFormatter
							.ofPattern("MMMM")
							.withLocale(Locale.ENGLISH)
							.parse(reqEndMonth)
							.get(ChronoField.MONTH_OF_YEAR);
		
		while(currentMonth < reqEndMonthInNum) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='›']")).click();
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentMonth = DateTimeFormatter
					.ofPattern("MMMM")
					.withLocale(Locale.ENGLISH)
					.parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		
		while(currentMonth > reqEndMonthInNum) {
			driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='‹']")).click();
			currentMonthYear = driver.findElement(By.xpath("//div[@class='calendar' and contains(@style,'block')]/descendant::td[@class='title']")).getText();
			str = currentMonthYear.split(", ");
			currentMonth = DateTimeFormatter
					.ofPattern("MMMM")
					.withLocale(Locale.ENGLISH)
					.parse(str[0])
					.get(ChronoField.MONTH_OF_YEAR);
		}
		
		driver.findElement(By.xpath("//div[@class='calendar' and contains(@style, 'block')]/descendant::td[text()='"+reqEndDate+"']")).click();
		
		driver.findElement(By.xpath("//input[@value='  Save']")).click();
		WebElement adminWidget = driver.findElement(By.xpath("//img[@src='themes/softed/images/user.PNG']"));
		driverUtil.mouseHover(adminWidget);
		
		driver.findElement(By.xpath("//a[text()='Sign Out']")).click();
		
		driverUtil.quitAllWindows();
	}

}
