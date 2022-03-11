package Base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import ExcelFunctions.WriteOutput;
import io.github.bonigarcia.wdm.WebDriverManager;

public class Generic {
  public static WebDriver driver ;
  public static ExtentTest test;
  protected static final Logger log = Logger.getLogger(Generic.class.getName());  
	public void initBrowser(String browser) {
		switch(browser) {
		case "chrome":

			WebDriverManager.chromedriver().version("97.0.4692.71").setup();
			ChromeOptions choption = new ChromeOptions();
			choption.addArguments("start-maximized");
			driver=new ChromeDriver(choption);
			log.info("Chrome browser lunched");
			break;
		case "fireFox":
			WebDriverManager.firefoxdriver().setup();
			FirefoxOptions fioption = new FirefoxOptions();
			fioption.addArguments("start-maximized");
			driver=new FirefoxDriver(fioption);
			log.info("Firefox browser lunched");
			break;
		default:
			System.out.println("No browser selected to run");
			
		}
	}
	public void setUrl() throws IOException {
		Properties prop = initialize_Properties("envInfo");
	   driver.get(prop.getProperty("baseUrl"));
	   log.info("hitting app url: "+prop.getProperty("baseUrl"));
	}
	public String getDateTime() {
		Date date=new Date();
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
		String dateTime= sdf.format(date);
		System.out.println(dateTime);
		return dateTime;
	}
	public Properties initialize_Properties(String file) throws IOException {
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"\\src\\main\\resources\\Properties\\"+file+".properties");
		Properties prop = new Properties();
		prop.load(fis);	
		return prop;
	}
	
	public ExtentReports createHtmlReport(String reportName) {
		ExtentReports report = new ExtentReports(System.getProperty("user.dir")+"\\src\\test\\resources\\ExtentReport\\"+reportName+getDateTime()+".html");
		return report;
	}
	
	public void switchToChildWindow() {
		//parent window id
		String parent=driver.getWindowHandle();
		// set of child window id
		Set st = driver.getWindowHandles();
		//iterating the set of ids
		Iterator<String> it = st.iterator();
		
		while(it.hasNext()) {
			String popupWindow = it.next();
			if (!(parent.equals(popupWindow))) {
				driver.switchTo().window(popupWindow);
			}
		}
	}
	public void getScreenshot(String name) throws IOException {
		File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\"+name+getDateTime()+".jpg");
		FileHandler.copy(sourceFile,destFile);
	}
	
	public void getFailedScreenshot(String name) throws IOException {
		File sourceFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File(System.getProperty("user.dir")+"\\src\\test\\resources\\Screenshots\\FailedScreenShot\\"+name+getDateTime()+".jpg");
		FileHandler.copy(sourceFile,destFile);
	}
	
	public void moveToElement(WebElement element) {
		Actions act = new Actions(driver);
		act.moveToElement(element).build().perform();
	}
	
	public void implicitWait(int waitingTime) {
		driver.manage().timeouts().implicitlyWait(waitingTime, TimeUnit.SECONDS);
	}
	public void waitingForElementToBeClickable(WebElement element) {
		WebDriverWait wait = new WebDriverWait(driver,30);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		
	public void clickByJavaScript(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("argument[0].click();", element);
				
	}
	public void javaScriptByScrollDown() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("windows.scrollBy(0,2000)","");
	}
	
	public void javaScriptByScrollUp() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("windows.scrollBy(2000,0)","");
	}
	public void DragNDrop(WebElement ToElement , WebElement FromElement) {
		Actions act = new Actions(driver);
		act.clickAndHold(FromElement)
		.moveToElement(ToElement).release(ToElement).build().perform();
		
	}
	public void logOut() {
		driver.findElement(By.xpath("//a[contains(text(),'Logout')]")).click();	
	}
	
	public void quitBrowser() {
		driver.quit();
	}
	
	public void initAll() throws IOException {
		initBrowser("chrome");
		setUrl();
	}
	
	public void updateResult(String OutputFile, String Sheet, String TestCaseID,String TestCaseName, String Status,String ExpectedResult, String ActualResult) throws IOException {
		String path =System.getProperty("user.dir")+"\\src\\test\\resources\\OutputExcel\\"+OutputFile+".xlsx";
		WriteOutput.writeExcel(path, Sheet , TestCaseID, TestCaseName, Status, ExpectedResult, ActualResult);
	}
	
	public void renameOutputFile(String OutputFile) {
		File oldFile =new File(System.getProperty("user.dir")+"\\src\\test\\resources\\OutputExcel\\"+OutputFile+".xlsx");
		File newFile=new File(System.getProperty("user.dir")+"\\src\\test\\resources\\OutputExcel\\"+OutputFile+getDateTime()+".xlsx");
		
		oldFile.renameTo(newFile);
	}
	
	public Alert alertPopup() {
		Alert alert = driver.switchTo().alert();
		return alert;
		
	}
	
	public void loginToapp(String UserName, String Password,String Session) {
		WebElement userNamebtn= driver.findElement(By.xpath("//input[@id='username']"));
		userNamebtn.sendKeys(UserName);
		test.log(LogStatus.INFO, "Entered UserName "+UserName);
		WebElement pwdBtn= driver.findElement(By.xpath("//input[@id='password']"));
		pwdBtn.sendKeys(Password);
		test.log(LogStatus.INFO, "Entered PassWord "+Password);
		
		WebElement selectInpatentSession= driver.findElement(By.xpath("//ul[@id='sessionLocation']/li[text()='"+Session.trim()+"']"));
		if(!(selectInpatentSession.isSelected())) {
			selectInpatentSession.click();
		test.log(LogStatus.INFO, "Session Selected "+Session);
		
		WebElement loginBtn=driver.findElement(By.xpath("//input[@id='loginButton']"));
		loginBtn.click();
		test.log(LogStatus.INFO, "Clicked On Login Button");
	 }
	}
}
