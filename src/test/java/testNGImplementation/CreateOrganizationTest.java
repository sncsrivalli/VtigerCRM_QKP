package testNGImplementation;

import java.util.Map;

import org.testng.annotations.Test;

import genericUtilities.BaseClass;
import genericUtilities.TabNames;
import objectRepo.CreatingNewOrganizationPage;
import objectRepo.OrganizationInformationPage;
import objectRepo.OrganizationsPage;

public class CreateOrganizationTest extends BaseClass {

	@Test(groups = "organizations")
	public void createOrgTest() {
		OrganizationsPage organization = pageObjectManager.getOrganization();
		CreatingNewOrganizationPage createOrg = pageObjectManager.getCreateOrg();
		OrganizationInformationPage orgInfo = pageObjectManager.getOrgInfo();
		
		home.clickRequiredTab(driverUtil, TabNames.ORGANIZATIONS);

		soft.assertTrue(driver.getTitle().contains("Organizations"));
		
		organization.clickCreateOrgBTN();

		soft.assertTrue(createOrg.getPageHeader().equalsIgnoreCase("creating new organization"));
		
		Map<String, String> map = excel.readFromExcel("OrganizationsTestData", "Create Organization");

		createOrg.setOrganizationName(map.get("Organization Name"));
		createOrg.clickSaveBTN();

		soft.assertTrue(orgInfo.getPageHeader().contains(map.get("Organization Name")));
		
		orgInfo.clickDeleteBTN();
		driverUtil.handleAlert("ok");

		soft.assertTrue(driver.getTitle().contains("Organizations"));
		if (driver.getTitle().contains("Organizations")) 
			excel.writeToExcel("OrganizationsTestData", "Create Organization", "Pass");
		else 
			excel.writeToExcel("OrganizationsTestData", "Create Organization", "Fail");
		soft.assertAll();
	}

}
