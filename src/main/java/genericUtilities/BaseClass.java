package genericUtilities;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import objectRepo.HomePage;
import objectRepo.LoginPage;
import objectRepo.PageObjectManager;

public class BaseClass {

	//@BeforeSuite
	//@BeforeTest
	protected PropertiesUtility propertyUtil;
	protected ExcelUtility excel;
	protected JavaUtility jutil;
	protected WebDriverUtility driverUtil;
	
	protected WebDriver driver;
	
	protected PageObjectManager pageObjectManager;
	
	protected LoginPage login;
	protected HomePage home;
	
	@BeforeClass
	public void classConfiguration() {
		propertyUtil = new PropertiesUtility();
		excel = new ExcelUtility();
		jutil = new JavaUtility();
		driverUtil = new WebDriverUtility();

		propertyUtil.propertiesInit(IConstantPath.PROPERTIES_FILE_PATH);
		excel.excelInit(IConstantPath.EXCEL_PATH);
		
		driver = driverUtil.launchBrowser(propertyUtil.readFromProperties("browser"));
		driverUtil.maximizeBrowser();
		long time = (Long) jutil.convertStringToAnyDataType(propertyUtil.readFromProperties("timeouts"), DataType.LONG);
		driverUtil.waitTillElementFound(time);
	}
	
	@BeforeMethod
	public void methodConfiguration() {
		driverUtil.navigateToApp(propertyUtil.readFromProperties("url"));

		pageObjectManager = new PageObjectManager(driver);
		login = pageObjectManager.getLogin();
		home = pageObjectManager.getHome();
		
		if (driver.getTitle().contains("vtiger CRM"))
			System.out.println("Login Page Displayed");
		else
			driverUtil.quitAllWindows();

		login.loginToVtiger(propertyUtil.readFromProperties("username"), propertyUtil.readFromProperties("password"));

		if (driver.getTitle().contains("Home"))
			System.out.println("Home Page is Displayed");
		else
			driverUtil.quitAllWindows();
	}	
	
	@AfterMethod
	public void methodTeardown() {
		excel.saveExcel(IConstantPath.EXCEL_PATH);
		home.signOutOfVtiger(driverUtil);
	}
	
	@AfterClass
	public void classTeardown() {
		excel.closeExcel();
		driverUtil.quitAllWindows();
	}
	//@AfterTest
	//@AfterSuite
}
