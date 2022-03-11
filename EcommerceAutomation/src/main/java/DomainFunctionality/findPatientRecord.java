package DomainFunctionality;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import Base.Generic;

public class findPatientRecord extends Generic{
	static Logger log = Logger.getLogger(findPatientRecord.class.getName()); 
	public void findPatientScenarios(String testCaseName, String UserName, String Password,String Session,String patientName,String patientId,String ExpectedResult) {
		
		List<String> StringList= new ArrayList<>();
		loginToapp(UserName, Password, Session);
		driver.findElement(By.xpath("//a[@id='coreapps-activeVisitsHomepageLink-coreapps-activeVisitsHomepageLink-extension']")).click();
		WebElement searchBox=driver.findElement(By.xpath("//input[@id='patient-search']"));
		
		String strMsg=null;
		WebElement actualMsg;
		
		
		switch(testCaseName) {
		case "Verify find patient record by Name":
			searchBox.sendKeys(patientName);
			String[] ids = patientId.split(",");
			for(String id : ids) {
				List<WebElement> webList=driver.findElements(By.xpath(""));
				for(WebElement wb: webList) {
					StringList.add(wb.getText());
				}
				if(!StringList.contains(id)) {
					Assert.fail("Id "+id+" Not found in the list");
				}
			}
			
			break;
		
		case "Verify find patient record by Id":
			searchBox.sendKeys(patientId);
			break;
			
		}
		actualMsg =driver.findElement(By.xpath("//div[@class='dataTables_info']"));
		strMsg=actualMsg.getText();
		if(!strMsg.contains("Showing")) {	
			Assert.fail("No record found for the search");
		}
		

	}
}
