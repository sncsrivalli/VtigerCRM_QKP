package testNGImplementation;

import java.util.Map;

import org.testng.annotations.Test;

import genericUtilities.BaseClass;
import genericUtilities.TabNames;
import objectRepo.CreatingNewLeadPage;
import objectRepo.DuplicatingPage;
import objectRepo.LeadInformationPage;
import objectRepo.LeadsPage;

public class CreateAndDuplicateLeadTest extends BaseClass {

	@Test
	public void createAndDuplicateLeadTest() {
		LeadsPage leads = pageObjectManager.getLeads();
		CreatingNewLeadPage createLead = pageObjectManager.getCreateLead();
		DuplicatingPage duplicateLead = pageObjectManager.getDuplicateLead();
		LeadInformationPage leadInfo = pageObjectManager.getLeadInfo();

		home.clickRequiredTab(driverUtil, TabNames.LEADS);

		if (driver.getTitle().contains("Organizations"))
			System.out.println("Organizations Page is Displayed");
		else
			driverUtil.quitAllWindows();

		leads.clickCreateLeadBTN();

		if (createLead.getPageHeader().equalsIgnoreCase("creating new lead"))
			System.out.println("Creating New lead Page is Displayed");
		else
			driverUtil.quitAllWindows();

		Map<String, String> map = excel.readFromExcel("LeadsTestData", "Create and Duplicate Lead");
		String lastName = map.get("Last Name") + jutil.generateRandomNum(100);
		createLead.setLeadLastName(lastName);
		createLead.setCompanyName(map.get("Company"));
		createLead.clickSaveBTN();

		if (leadInfo.getPageHeader().contains(lastName))
			System.out.println("Lead created successfully");
		else
			driverUtil.quitAllWindows();

		leadInfo.clickDuplicateBTN();
		if (duplicateLead.getPageHeader().contains("Duplicating"))
			System.out.println("Duplicating lead page displayed");
		else
			driverUtil.quitAllWindows();

		String newLastName = map.get("New Last Name") + jutil.generateRandomNum(100);
		duplicateLead.setLeadLastName(newLastName);
		duplicateLead.clickSaveBTN();

		if (leadInfo.getPageHeader().contains(newLastName)) {
			System.out.println("Duplicate Lead created successfully");
			excel.writeToExcel("LeadsTestData", "Create and Duplicate Lead", "Pass");
		} else {
			driverUtil.quitAllWindows();
			excel.writeToExcel("LeadsTestData", "Create and Duplicate Lead", "Fail");
		}
	}

}
