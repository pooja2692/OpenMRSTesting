package LoginFunctionality;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.LogStatus;

import Base.Generic;
import DomainFunctionality.Login;
import ExcelFunctions.ReadExcel;

@Listeners(Listener.TestListener.class)
public class VerifyLoginFunctionality extends Generic{
	static Logger log = Logger.getLogger(VerifyLoginFunctionality.class.getName()); 
	
	Login login;
    ExtentReports report;
	String TestCase,RunFlag;
	String reportName="VerifyLogin";
	
	@DataProvider(name="LoginData")
	public Object[][] getData() throws IOException{		
		Object[][] getInputData=ReadExcel.readExcel("Login");
		return getInputData;
	}
	
	@BeforeTest
	public void CreateExtent() {
		report= createHtmlReport("VerifyLoginFunctionality");
	}
	
	@AfterMethod
	public void tearDown(ITestResult result) {
		if(result.getStatus()==ITestResult.FAILURE) {
			try {
				getFailedScreenshot(TestCase.replace(" ",""));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if(RunFlag.equalsIgnoreCase("Y")) {
			quitBrowser();
		}
	}
	
	@AfterTest
	public void flushExtent() {
		report.flush();
		renameOutputFile(reportName);
	}
	
	@Test(dataProvider = "LoginData" , retryAnalyzer = Listener.RetryFailedTest.class)
	public void verifyLogin(String TestCaseID, String TestCaseName, String RunMode, String UserName, String PassWord, String Session, String ExpectedResult) throws IOException {
		login= new Login();
		TestCase=TestCaseName;
		RunFlag=RunMode;
		test=report.startTest(TestCaseName);
		test.assignAuthor("Pooja");
		
		if (RunMode.equalsIgnoreCase("Y")) {
			initAll();
			test.log(LogStatus.INFO, "Execution Started for "+TestCaseName);
			log.info("Execution Start for "+TestCaseName);
			login.loginScenarios(TestCaseName, UserName, PassWord,Session, ExpectedResult);
			updateResult(reportName, "loginOutput", TestCaseID, TestCaseName, "", ExpectedResult, "");
			
		}
		else {
			log.info( TestCaseName+" Test Skipped. Since marked as no run");
			test.log(LogStatus.SKIP, TestCaseName+" Test Skipped. Since marked as no run");
			throw new SkipException(TestCaseName+" Test Skipped. Since marked as no run");
			
		}
	}
}
