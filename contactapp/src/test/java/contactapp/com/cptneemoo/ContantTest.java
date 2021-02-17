package contactapp.com.cptneemoo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.cptneemoo.SpringBootContactApp;
import com.cptneemoo.entity.Contact;
import com.cptneemoo.service.ContactService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SpringBootContactApp.class, webEnvironment = WebEnvironment.RANDOM_PORT)
public class ContantTest {
	
	@LocalServerPort
	private int port;
	
	private static String baseUrl;
	private static WebDriver driver;
	
	@Autowired
	private ContactService contactService;
			
	
	@BeforeClass
	public static void initializeSelenium() {
		System.setProperty("webdriver.chrome.driver","C:\\java\\chromedriver\\chromedriver.exe");
		driver = new ChromeDriver();
	}
	
	@Before
	public void setUp() {
		baseUrl = "http://localhost:" + port + "/index";
	}
	
	@Test
	public void contextLoads() {
		
	}
	
	@Test
	public void testTitle() {
		driver.get(baseUrl);
		assertEquals("Index", driver.getTitle());
	}
	
	@Test
	public void addContactTest() {
		Contact randomContact = contactService.getRandomContact();
		driver.get(baseUrl);
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		WebElement webElem0 = driver.findElement(By.id("addnewcontact"));
		webElem0.click();
		
		//adding contact
		
		WebElement webElem1 = driver.findElement(By.id("name"));
		webElem1.sendKeys(randomContact.getName());
		WebElement webElem2 = driver.findElement(By.id("contactnumber"));
		webElem2.sendKeys(randomContact.getContactnumber());
		WebElement webElem3 = driver.findElement(By.id("submit"));
		webElem3.submit();
				
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);		
		
		List<Contact> actualContactList = contactService.findByName(randomContact.getName());
		boolean isContactFound = false;
		for(Contact contact : actualContactList) {
			if (contact.getName().equals(randomContact.getName()) && contact.getContactnumber().equals(randomContact.getContactnumber())) {
				isContactFound |= true;
			}
		}
		if (!isContactFound) fail();
	}
	
	@Test
	public void searchByNameTest() {
		Contact randomContact = contactService.getRandomContact();
		Contact savedContact = contactService.save(randomContact);
		driver.get(baseUrl);
		WebElement webElem4 = driver.findElement(By.id("keyword"));
		webElem4.sendKeys(savedContact.getName());
		WebElement webElem5 = driver.findElement(By.id("submitsearch"));
		webElem5.submit();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		WebElement webElem6 = driver.findElement((By.tagName("td")));
		String dirtyId = webElem6.getAttribute("id");
		String id = dirtyId.replaceFirst(Pattern.quote("contactname"), "");
		
		assertEquals(Long.parseLong(id), savedContact.getId());
	}
	
	@Test
	public void editTest() {
		Contact originalRandomContact = contactService.getRandomContact();
		Contact originalsavedContact = contactService.save(originalRandomContact);
		Contact newRandomContact = contactService.getRandomContact();
		driver.get(baseUrl);
		WebElement webElem7 = driver.findElement(By.id("contactedit" + originalsavedContact.getId()));
		webElem7.click();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		WebElement webElem8 = driver.findElement(By.id("name"));
		webElem8.clear();
		webElem8.sendKeys(newRandomContact.getName());
		WebElement webElem9 = driver.findElement(By.id("contactnumber"));
		webElem9.clear();
		webElem9.sendKeys(newRandomContact.getContactnumber());
		WebElement webElem10 = driver.findElement(By.id("submit"));
		webElem10.submit();
		
		driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		
		Contact resultContact = contactService.findById(originalsavedContact.getId());
		assertEquals(newRandomContact.getName(), resultContact.getName());
		assertEquals(newRandomContact.getContactnumber(), resultContact.getContactnumber());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void deleteTest() {
		Contact randomContact = contactService.getRandomContact();
		Contact savedContact = contactService.save(randomContact);
		driver.get(baseUrl);
		WebElement webElem11 = driver.findElement(By.id("contactdelete" + savedContact.getId()));
		webElem11.click();
		contactService.findById(savedContact.getId());
	}
	
	@After
	public void clean() {
		contactService.deleteAll();
	}
	
	@AfterClass
	public static void cleanUp() {
		driver.quit();
	}

}
