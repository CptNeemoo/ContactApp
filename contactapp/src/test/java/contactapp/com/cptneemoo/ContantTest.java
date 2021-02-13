package contactapp.com.cptneemoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class ContantTest {
	
	private static String baseUrl;
	private static WebDriver driver;
	
	@BeforeClass
	public static void initializeSelenium() {
		System.setProperty("webdriver.chrome.driver","C:\\java\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://localhost:8080/index";
	}
	
	@Test
	public void fullFlow() {;
		String contactName = RandomStringUtils.randomAlphabetic(3,10) + " " + RandomStringUtils.randomAlphabetic(3,10);
		String contactNumber = RandomStringUtils.randomNumeric(6,13);
		WebDriverWait wait = new WebDriverWait(driver, 1);
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		WebElement webElem0 = driver.findElement(By.id("addnewcontact"));
		webElem0.click();
		
		//adding contact
		
		WebElement webElem1 = driver.findElement(By.id("name"));
		webElem1.sendKeys(contactName);
		WebElement webElem2 = driver.findElement(By.id("contactnumber"));
		webElem2.sendKeys(contactNumber);
		WebElement webElem3 = driver.findElement(By.id("submit"));
		webElem3.submit();
		
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//searching by name
		
		
		WebElement webElem4 = driver.findElement(By.id("keyword"));
		webElem4.sendKeys(contactName);
		WebElement webElem5 = driver.findElement(By.id("submitsearch"));
		webElem5.submit();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//getting id of contact
		
		WebElement webElem6 = driver.findElement((By.tagName("td")));
		String dirtyId = webElem6.getAttribute("id");
		String id = dirtyId.replaceFirst(Pattern.quote("contactname"), "");
		
		driver.get(baseUrl);
		
		//editing contact
		String newcontactName = RandomStringUtils.randomAlphabetic(3,10) + " " + RandomStringUtils.randomAlphabetic(3,10);
		String newcontactNumber = RandomStringUtils.randomNumeric(6,13);
		
		WebElement webElem7 = driver.findElement(By.id("contactedit" + id));
		webElem7.click();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		WebElement webElem8 = driver.findElement(By.id("name"));
		webElem8.sendKeys(newcontactName);
		WebElement webElem9 = driver.findElement(By.id("contactnumber"));
		webElem9.sendKeys(newcontactNumber);
		WebElement webElem10 = driver.findElement(By.id("submit"));
		webElem10.submit();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		//deleting contact
		
		WebElement webElem11 = driver.findElement(By.id("contactdelete" + id));
		webElem11.click();
	}
	
	@Test
	public void testTitle() {
		driver.get(baseUrl);
		assertEquals("Index", driver.getTitle());
	}
	
	
	
	@AfterClass
	public static void cleanUp() {
		driver.quit();
	}

}
