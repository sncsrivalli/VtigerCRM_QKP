package testNGImplementation;

import java.util.Map;

import org.testng.annotations.Test;

import genericUtilities.BaseClass;
import genericUtilities.TabNames;
import objectRepo.ContactInformationPage;
import objectRepo.ContactsPage;
import objectRepo.CreatingNewContactPage;

public class CreateContactWithExistingOrgTest extends BaseClass{

	@Test
	public void createContact() {
		ContactsPage contact = pageObjectManager.getContacts();
		CreatingNewContactPage createContact = pageObjectManager.getCreateContact();
		ContactInformationPage contactInfo = pageObjectManager.getContactInfo();
		
		home.clickRequiredTab(driverUtil, TabNames.CONTACTS);
		if (driver.getTitle().contains("Contacts"))
			System.out.println("Contacts Page Displayed");
		else
			driverUtil.quitAllWindows();
		
		contact.clickCreateContactBTN();

		if (createContact.getPageHeader().equalsIgnoreCase("Creating new contact"))
			System.out.println("Creating New Contact Page is Displayed");
		else
			driverUtil.quitAllWindows();
		
		Map<String, String> map = excel.readFromExcel("ContactsTestData", "Create Contact With Organization");
		
		createContact.setContactLastName(map.get("Last Name"));
		createContact.selectExistingOrganization(driverUtil, map.get("Organization Name"));

		createContact.clickSaveBTN();

		if (contactInfo.getPageHeader().contains(map.get("Last Name")))
			System.out.println("Contact created successfully");
		else
			driverUtil.quitAllWindows();
		
		contactInfo.clickDeleteBTN();
		driverUtil.handleAlert("ok");
		
		if(driver.getTitle().contains("Contacts")) {
			System.out.println("Contacts Page is Displayed");
			excel.writeToExcel("ContactsTestData", "Create Contact With Organization", "Pass");
		}
		else {
			driverUtil.quitAllWindows();
			excel.writeToExcel("ContactsTestData", "Create Contact With Organization", "Fail");
		}
	}

}
