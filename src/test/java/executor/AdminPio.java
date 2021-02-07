 package executor;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.ITest;
import org.testng.Reporter;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import applib.AppLib;
import weblib.PDFReporter;
import weblib.WebLib;

public class AdminPio implements ITest {
	WebDriver driver = null;
	String url = "";
	HttpURLConnection huc = null;
	int respCode = 200;
	String homePage = "https://test.piotest.com";
	String testCaseName = "";
	PDFReporter pdf= new PDFReporter();
	
	@BeforeSuite
	public void Setup() {
		AppLib app = new AppLib();
		WebLib wl = new WebLib();

//		driver = wl.lunchBrowser();
		app.login("superadmin", "superadmin@123", driver);

	}

	@Test()
	public void Execute() {
		List<WebElement> links = driver.findElements(By.tagName("a"));
		System.out.println("Number: " + links.size());
		Iterator<WebElement> it = links.iterator();

		while (it.hasNext()) {

			try {

				String linkUrl = it.next().getAttribute("href");

				System.out.println("URL:" + linkUrl);
				if (linkUrl == null || linkUrl.isEmpty()) {
					System.out.println("URL is either not configured for anchor tag or it is empty");
					continue;
				}

				if (!linkUrl.startsWith(homePage)) {
					System.out.println("URL belongs to another domain, skipping it.");
					continue;
				}

				URL url = new URL(linkUrl);

				HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

				httpURLConnect.setConnectTimeout(3000);

				httpURLConnect.connect();

				if (httpURLConnect.getResponseCode() == 200) {
					System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
					Reporter.log(linkUrl + " - " + httpURLConnect.getResponseMessage()+"--> Valid URL");
					pdf.addParagraph(httpURLConnect.getResponseMessage());
				}
				if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
					System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
							+ HttpURLConnection.HTTP_NOT_FOUND);
					Reporter.log(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - "
							+ HttpURLConnection.HTTP_NOT_FOUND);
				}
			} catch (Exception e) {
				System.out.println("Exception");
			}
		}
	}
	
	@Override
	public String getTestName() {

	    this.testCaseName = "Verify Links in Admin Page";

	    return this.testCaseName;
	}
	
	@AfterSuite
	public void closeBrowser() {
		driver.close();
	}

}
