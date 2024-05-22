package demo;

import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class TestCases 
{
    ChromeDriver driver;

    
    @BeforeSuite
    public void setUp()
    {
        System.out.println("Constructor: TestCases");

        WebDriverManager.chromedriver().browserVersion("125.0.6422.61").setup();
        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

      
        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("start-maximized");
        options.addArguments("--disable-blink-features=AutomationControlled");

      
        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "chromedriver.log");

        driver = new ChromeDriver(options);        

    }

    @AfterSuite
    public void tearDown()
    {
        System.out.println("End Test: TestCases");
        driver.close();
        driver.quit();

    }

    @Test(priority=1)
    public void testCase01()
    {
        
        navigateTo(driver, "http://www.flipkart.com");

        searchBar(driver, By.xpath("//input[@title='Search for Products, Brands and More']"),"Washing Machine",By.xpath("//button[@type='submit']"));

        sortAndCount(driver, By.xpath("//div[text()='Popularity']"), By.xpath("//div[@class='XQDdHH']"));
    }

    @Test(priority = 2)
    public void testCase02()
    {
        searchBar(driver, By.xpath("//input[@class='zDPmFV']"),"iPhone",By.xpath("//button[@type='submit']"));

        titleandDiscount(driver,By.xpath("//div[@class='UkUFwK']"),By.xpath("//div[@class='KzDlHZ']"));
    }

    @Test(priority = 3)
    public void testCase03()
    {
        searchBar(driver, By.xpath("//input[@class='zDPmFV']"),"Coffee mug",By.xpath("//button[@type='submit']"));

        coffeeMug(driver, By.xpath("(//div[@class='XqNaEv'])[1]"));
    }
    private static void navigateTo(ChromeDriver driver,String url)
    {
        try
        {
            if(!(driver.getCurrentUrl().equals(url)))
            {
                driver.get(url);
                System.out.println("Automating Flipkart Application"+url);
            }
        }
        catch(Exception e)
        {
            System.out.println("Exception occured while navigating"+e.getMessage());
        }


    }

    private static void searchBar(ChromeDriver driver,By search,String texttosend,By srchbtn)
    {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        String productName = "Washing Machine";
        String productName1 = "iPhone";
        String productName2 = "Coffee mug";
        try 
        {
            WebElement searchBar = driver.findElement(search);
            searchBar.click();
            Thread.sleep(2000);
            searchBar.clear();
            searchBar.sendKeys(texttosend);
            
            WebElement searchbtn = driver.findElement(srchbtn);
            searchbtn.click(); 

           Thread.sleep(2000);
            WebElement getProductName = driver.findElement(By.xpath("//span[@class='BUOuZu']"));
            
            String name = getProductName.getText();
            
            if(name.contains(productName))
            {
                System.out.println(productName+" searched successfully");
            }

            else if(name.contains(productName1))
            {
                System.out.println(productName1+" searched successfully");
            }
            else if(name.contains(productName2))
            {
                System.out.println(productName1+" searched successfully");
            }
            else
            {
                System.out.println("Error searching for"+productName);
            }


        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occured while searching"+e.getMessage());
        }
    }

    private static void sortAndCount(ChromeDriver driver,By sort,By count)
    {
        
        try 
        {
            int fourratingcount = 0;
            WebElement popularity = driver.findElement(sort);
            popularity.click();   
            Thread.sleep(1000);
            List<WebElement> rating = driver.findElements(count);
            
            for (WebElement rate : rating) 
            {
                Thread.sleep(1000);
                String fourrating = rate.getText();
                if(fourrating.equals("4.2"))
                {
                    fourratingcount++;    
                }  
            }
            System.out.println(fourratingcount+" Products found with 4.2 Ratings");

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("Exception occured while sorting and counting"+e.getMessage());
        }
    }

    private static void titleandDiscount(ChromeDriver driver,By discount,By title)
    {
        try
        {
            List<WebElement> discountretrieve = driver.findElements(discount);
            List<WebElement> titleretrieve = driver.findElements(title);
            
            if(discountretrieve.size() == titleretrieve.size())
            {
                for(int i=0;i<titleretrieve.size();i++)
                {
                    WebElement titleElement = titleretrieve.get(i);
                    WebElement discountElement = discountretrieve.get(i);

                    String titletext = titleElement.getText();
                    String discountText = discountElement.getText();

                    String numericDiscount = discountText.replaceAll("[^0-9]", "");
                    try {
                        int discountpercent = Integer.parseInt(numericDiscount);
                        if (discountpercent > 17) {
                            System.out.println("Title: " + titletext + ", Discount: " + discountText);
                        }
                    } catch (NumberFormatException ex) 
                    {
                        System.out.println("Error: Invalid discount format: " + discountText);
                    }
                }
            } else {
                System.out.println("Error: Number of titles and discounts do not match.");
            }

        }
        catch(Exception e)
        {
            System.out.println("Exception occured while searching iPhone title and discount"+e.getMessage());
        }

    }

    private static void coffeeMug(ChromeDriver driver,By rate)
    {
        try
        {
            WebElement rating = driver.findElement(rate);
            rating.click();

       
            //List<WebElement> product = driver.findElements(By.xpath("//div[@class='slAVV4']"));
            List<WebElement> reviews = driver.findElements(By.xpath("//span[@class='Wphh3N']"));
            List<WebElement> title = driver.findElements(By.xpath("//a[@class='wjcEIp']"));
            List<WebElement> img = driver.findElements(By.cssSelector("a.VJA3rP div._4WELSP img.DByuf4"));

            if(title.size() == img.size())
            {
                for(int i=0;i<Math.min(5,reviews.size());i++)
                {
                    WebElement titlelist = title.get(i);
                    WebElement imglist = img.get(i);

                    String titledisplay = titlelist.getText();
                    String imgURL = imglist.getAttribute("src");

                    System.out.println(titledisplay);
                    System.out.println(imgURL);
                    
                }
            }
        
        }
        catch(Exception e)
        {
            System.out.println("Exception occured while searching Coffee title and Image"+e.getMessage());
        }
        
    }
}

