package com.test.run;

import java.io.File;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.swabunga.spell.examples.SpellCheckExample;

import jxl.Sheet;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class GetAllTextofWebpage {
	boolean bSuccess = false;

	WebDriver wd;
	public String text;
	ResourceBundle rb = ResourceBundle.getBundle("Elements");
	Logger log = Logger.getLogger("devpinoyLogger");

	String dir = System.getProperty("user.dir");
	String file_path = dir + rb.getString("datafile");
	String sDriverPath = dir + rb.getString("driverfolder");

	@Parameters({ "browser", "bname" })
	@BeforeMethod
	public void start(String browser, String bname) {
		log.debug("************Spell Check Utility Started***************");
		deleteSpellFiles();
		if (browser.equalsIgnoreCase("chrome") && bname.equalsIgnoreCase("chromefrombatch")) {
			System.setProperty(rb.getString("chromeproperty"), sDriverPath + "\\chromedriver.exe");
			wd = new ChromeDriver();
			log.debug("Spell check started in chrome browser");
		} else if (browser.equalsIgnoreCase("firefox") || bname.equalsIgnoreCase("firefoxfrombatch")) {
			System.setProperty(rb.getString("firefoxproperty"), sDriverPath + "\\geckodriver.exe");
			wd = new FirefoxDriver();
			log.debug("Spell check started in firefox browser");
		} else if (browser.equalsIgnoreCase("ie")) {
			System.setProperty(rb.getString("ieproperty"), sDriverPath + "\\IEDriverServer.exe");
			wd = new InternetExplorerDriver();
			log.debug("Spell check started in internet explorer browser");
		} else {
			log.debug("Incorrect browser is specified in testng.xml file.");
			log.debug("************Spell Check Utility Stopped***************");
			Assert.fail();
		}
	}

	@Test
	public void getalltext() {
		File f = new File(file_path);
		try {
			int i;
			Workbook wb = Workbook.getWorkbook(f);
			Sheet s1 = wb.getSheet(0);
			int columns = s1.getColumns();
			int rows = s1.getRows();

			for (i = 1; i < rows; i++) {
				String url = s1.getCell(1, i).getContents();
				wd.get(url);
				log.debug("Spell check started for URL: " + url);
				text = wd.findElement(By.tagName("body")).getText();
				text = text.replace("\n", " ").replace("\r", "");
				try {
					new SpellCheckExample(text, i);
					log.debug("Spell check completed for URL: " + url);
					log.debug("Misspelt word file created: " + i + ".txt");
					bSuccess = true;
				} catch (Exception e) {

					log.debug("Exception occured: " + e.getMessage());
					e.printStackTrace();
					bSuccess = false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@AfterMethod
	public void end() {
		wd.quit();
		log.debug("Browser is closed.");
		log.debug("************Spell Check Utility Completed***************");
	}

	public void GenerateResult(int i, boolean success) {
		try {
			Workbook workbook = Workbook.getWorkbook(new File(file_path));
			// create a new excel and copy from existing
			WritableWorkbook copy = Workbook.createWorkbook(new File(file_path), workbook);
			WritableSheet sheet = copy.getSheet(0);
			if (success == true) {
				String FileCreated = "Yes";
				Label label = new Label(2, i, FileCreated);
				sheet.addCell(label);
			}
			if (success == false) {
				String FileCreated = "No";
				Label label = new Label(2, i, FileCreated);
				sheet.addCell(label);
			}
			copy.write();
			copy.close();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			log.debug("Exception Occured while generating the result: " + e.getMessage());
		}
	}

	public void deleteSpellFiles() {
		boolean bFilefound = false;
		File folder = new File(".//spell");
		log.debug("Deleting old txt files from spell folder.");
		for (File file : folder.listFiles()) {
			bFilefound = true;
			if (file.getName().endsWith(".txt")) {
				file.delete();
				log.debug("File deleted: " + file.getName());
			}
		}
		if (bFilefound == false) {
			log.debug("No txt file was found in spell folder.");
		}
	}
}
