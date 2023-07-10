package automatingCompassWebsite;

import java.io.FileWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.opencsv.CSVWriter;

public class ImageCount {
	
	WebDriver driver;
	
	@BeforeTest
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "/Users/kailash.k/Downloads/chromedriver_mac64 (4)/chromedriver");
		
		driver = new ChromeDriver();
		
		driver.manage().window().maximize();
		
		driver.manage().deleteAllCookies();
		
		Date d = new Date();
		
		System.out.println("Test Report Execution Date : " + d.toString());
		
		System.out.println("___________________________________________________________________________________________");
		
	}
	
	@Test
	public void imageCount()
	{
		String url = "https://www.getcompass.ai/";
		
		driver.get(url);
		
		System.out.println("Page Link : " + url);
		
		System.out.println("Page Title : " + driver.getTitle());
		
		List<WebElement> countOfImages = driver.findElements(By.tagName("img"));
		
		System.out.println("Count of Images on this page : " + countOfImages.size());
		
		try 
		{
			
			
			FileWriter outputfile = new FileWriter("/Users/kailash.k/Documents/AvailableImageListOnCompassHomePage.csv");
			
			CSVWriter writer = new CSVWriter(outputfile);
			
			//Adding header to CSV file
			
			String[] header = {"S_No","Page Link", "Image source", "Image Size in Bytes"};
			
			writer.writeNext(header);
			
			//adding data to CSV file
			
			int sno = 1;
			
			for(WebElement record : countOfImages)
			{
				
				URLConnection urlConnection = new URL(record.getAttribute("src")).openConnection();
				
				int size = urlConnection.getContentLength();
				
				
				String imgSrc = record.getAttribute("src");
				
				String id = Integer.toString(sno);
				
				String imgSize = Integer.toString(size);
				
				String[] data1 = {id, url, imgSrc, imgSize};
				
				writer.writeNext(data1);
				
				sno++;
				
			}
			
			writer.close();
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
		
		
	}
	
	@AfterTest
	public void exitBrowser()
	{
		driver.close();
	}

}
