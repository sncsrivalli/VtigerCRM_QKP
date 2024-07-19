package pom;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	// Declaration
	@FindBy(id="email")
	private WebElement emailTF;
	
	@FindBy(id="password")
	private WebElement pwdTF;
	
	@FindBy(id="keepLoggedInCheckBox")
	private WebElement keepMeLoggedInCB;
	
	@FindBy(id="toPasswordRecoveryPageLink")
	private WebElement forgotPWDLink;
	
	@FindBy(name="login")
	private WebElement loginBTN;
	
	// Initialization
	public LoginPage(WebDriver driver) {
		PageFactory.initElements(driver, this);
	}
	
	//Utilization
	public void setEmailTF(String email) {
		emailTF.sendKeys(email);
	}
	
	public void setPasswordTF(String pwd) {
		pwdTF.sendKeys(pwd);
	}
	
	public void clickKeepMeLoggedInCB() {
		keepMeLoggedInCB.click();
	}
	
	public void clickForgotPWDLink() {
		forgotPWDLink.click();
	}
	
	public void clickLoginBTN() {
		loginBTN.click();
	}
	
}
