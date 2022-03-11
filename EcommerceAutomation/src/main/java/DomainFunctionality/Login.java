package DomainFunctionality;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.LogStatus;

import Base.Generic;

public class Login extends Generic {
	static Logger log = Logger.getLogger(Login.class.getName()); 
	String Status="";
	public void loginScenarios(String testCaseName, String UserName, String Password,String Session,String ExpectedResult) {
		if(!testCaseName.equals("Verify Can't Login")) {
			loginToapp(UserName, Password, Session);
		}
		String strMsg=null;
		WebElement actualMsg;
		switch(testCaseName) {
		case "Verify Invalid login with missing UserName":
			actualMsg=driver.findElement(By.xpath("//div[@class='alert alert-danger']"));
			strMsg=actualMsg.getText();
			break;
		case "Verify Invalid login with missing Password":
			actualMsg=driver.findElement(By.xpath("//div[@class='alert alert-danger']"));
			strMsg=actualMsg.getText();
			 break;
		case "Verify Valid Login" :		
			actualMsg=driver.findElement(By.xpath("//div/h4"));
			strMsg=actualMsg.getText().trim();
			break;
		case "Verify Inpatent Session After Login" :
		case "Verify Laboratory Session After Login" :
		case "Verify Isolation Ward Session After Login" :
			actualMsg=driver.findElement(By.xpath("//div/h4"));
			strMsg=actualMsg.getText().trim();
			if(strMsg.contains(Session)) {
				strMsg=Session;	
			}
			break;
		case"Verify Can't Login" :
			WebElement verifycantlogin =driver.findElement(By.xpath("//a[@id='cantLogin']"));
			verifycantlogin.click();
			WebElement PopupText =driver.findElement(By.xpath("//div[@id='cannotLoginPopup']/child::div/child::p[@class='dialog-instructions']"));
			strMsg=PopupText.getText();
			WebElement acceptPopup=driver.findElement(By.xpath("//div[@id='cannotLoginPopup']/child::div/child::button[@class='confirm']"));
			acceptPopup.click();
			break;
		}
		try {
			getScreenshot(testCaseName);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(!strMsg.equals(ExpectedResult)) {
		test.log(LogStatus.FAIL, "Expected msg is "+ExpectedResult+" But Actual is "+strMsg);
		Status="FAIL";
		Assert.fail("Expected msg is "+ExpectedResult+" But Actual is "+strMsg);
		}
		else {
			test.log(LogStatus.PASS, "test Case Passed");
			Status="PASS";
		}	
		
	}

}
