package StepDefinitions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.SendKeysAction;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.github.bonigarcia.wdm.WebDriverManager;

public class TotalApplication {

	public static Properties config = null;
	public static WebDriver driver = null;
	String bookName;
	

	@Given("Login in to demowebshop with {string} user")
	public void login_in_to_demowebshop_with_user(String arg) throws IOException, InterruptedException 
	{
		
	    // Fetching data for login from a properties file
		config = new Properties();
		FileInputStream ip = new FileInputStream(
				System.getProperty("user.dir") + "//Data//UserDataInfo//config.properties");
		config.load(ip);
		if(arg.equalsIgnoreCase("valid"))
		{
		String userName = config.getProperty("username");
		String password = config.getProperty("password");
		System.out.println(userName);
		System.out.println(password);
		
		//Intiating chrome driver and logging in to demoWebShop
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		WebDriverWait wait=new WebDriverWait(driver, 20);
		driver.get("http://demowebshop.tricentis.com/");
		driver.manage().window().maximize();
		driver.findElement(By.xpath("//*[@class='ico-login']")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@class='page-title']")));
		String welcomeMessage = driver.findElement(By.xpath("//*[@class='page-title']")).getText();
		if (welcomeMessage.equals("Welcome, Please Sign In!")) 
		{
			System.out.println("Login message is as expected");
		} 
		else 
		{
			System.out.println("Login message is not proper");
		}
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@class='email']")).sendKeys(userName);
		driver.findElement(By.xpath("//*[@class='password']")).sendKeys(password);
		driver.findElement(By.xpath("//*[@class='button-1 login-button']")).click();
		String accountLoggedin = driver.findElement(By.xpath("(//*[@class='account'])[1]")).getText();
		if (accountLoggedin.equals(userName)) 
		{
			System.out.println("Logged in with the same user which we mention in config file");
		} 
		else 
		{
			System.out.println("Did not logged in with the same user which we mention in config file");
		}
		}
	}
	@Then("Clear cart and shop for books and add quantity more than {int}")
	public void shop_for_books_and_add_quantity_more_than(Integer int1) throws InterruptedException 
	{
		
		//Checking the cart and making sure the cart is empty if not make it empty
		//WebDriverWait wait=new WebDriverWait(driver, 20);
		Thread.sleep(10000);
		driver.findElement(By.xpath("//*[@id='topcartlink']")).click();
		WebDriverWait wait=new WebDriverWait(driver, 20);
		String cartStatus = driver.findElement(By.xpath("//*[@class='page-body']")).getText();
		if (cartStatus.equals("Your Shopping Cart is empty!"))
		{
			System.out.println("Cart is already empty");
		} 
		else 
		{
			int count = driver.findElements(By.xpath("(//*[@name='removefromcart'])")).size();
			System.out.println("no of items need to be removed from cart "+ count);
			for (int i = 1; i <= count; i++) 
			{
				System.out.println("In for loop for deleting all items in cart");
				driver.findElement(By.xpath("(//*[@name='removefromcart'])["+i+"]")).click();
			}
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@name='updatecart'])")));
			driver.findElement(By.xpath("(//*[@name='updatecart'])")).click();
			System.out.println("Cleaning cart is done");
		}
		Thread.sleep(10000);
		
		//Adding a book to cart with quantity of more than 1 
		driver.findElement(By.xpath("(//a[@href='/books'])[1]")).click();
		bookName =driver.findElement(By.xpath("(//*[@class='product-title'])[1]")).getText();
		driver.findElement(By.xpath("(//*[@class='product-title'])[1]")).click();
		driver.findElement(By.xpath("(//*[@id='addtocart_13_EnteredQuantity'])")).clear();
		Thread.sleep(2000);
		driver.findElement(By.xpath("(//*[@id='addtocart_13_EnteredQuantity'])")).sendKeys("2");
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='add-to-cart-button-13'])")));
		driver.findElement(By.xpath("(//*[@id='add-to-cart-button-13'])")).click();
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("(//*[@class='bar-notification success'])")));
		String productAddinToCartStatus = driver.findElement(By.xpath("(//*[@class='bar-notification success'])")).getText();
		System.out.println("Message after adding "+productAddinToCartStatus);
		if (productAddinToCartStatus.contains("The product has been added")) 
		{
			System.out.println("Product is successfully added");
		} 
		else 
		{
			System.out.println("Product is not added");
		}
	}
	@Then("Check whether the item got added in the cart and validate the price then checkout")
	public void check_whether_the_item_got_added_in_the_cart_and_validate_the_price_then_checkout() 
	{
		//Vlidating the item which we added and which got added are same with their price too
		driver.findElement(By.xpath("//*[@id='topcartlink']")).click();
		String expectedBookName = driver.findElement(By.xpath("(//*[@class='product-name'])")).getText();
		System.out.println(expectedBookName+" and other "+bookName);
		if (expectedBookName.equals(bookName)) 
		{
			System.out.println("The selected book is only added into cart");
			String productUnitPrize = driver.findElement(By.xpath("//*[@class='product-unit-price']")).getText();
			String numberOfItems = driver.findElement(By.xpath("//*[@class='qty-input']")).getDomAttribute("value");
			String totalCost = driver.findElement(By.xpath("//*[@class='product-subtotal']")).getText();
			System.out.println("No.of products "+productUnitPrize+" total no of items "+numberOfItems+" toatl cose "+totalCost);
			int i=Integer.valueOf(productUnitPrize.replace(".00", ""));  
			int j=Integer.valueOf(numberOfItems.replace(".00", ""));
			int k=Integer.valueOf(totalCost.replace(".00", ""));
			WebDriverWait wait=new WebDriverWait(driver, 20);
			if (k==i*j) 
			{
				System.out.println("Amount is exact");
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='termsofservice']")));
				driver.findElement(By.xpath("//*[@id='termsofservice']")).click();
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='checkout']")));
				driver.findElement(By.xpath("//*[@id='checkout']")).click();
			} 
			else 
			{
				System.out.println("Amount is wrong");
			}
		} 
		else 
		{
			System.out.println("Different book got added into cart");
		}
	}
	@Then("Fill all the manditory fields in the check out tab and make the order confirm")
	public void fill_all_the_manditory_fields_in_the_check_out_tab_and_make_the_order_confirm() throws InterruptedException 
	{
		//Filling the billing Address deatails
		Select billinAddress = new Select(driver.findElement(By.xpath("//*[@id='billing-address-select']")));
		billinAddress.selectByVisibleText("New Address");
		Select drpCountry = new Select(driver.findElement(By.xpath("//*[@id='BillingNewAddress_CountryId']")));
		drpCountry.selectByVisibleText("India");
		driver.findElement(By.xpath("//*[@id='BillingNewAddress_City']")).sendKeys("Hyderabad");
		driver.findElement(By.xpath("//*[@id='BillingNewAddress_Address1']")).sendKeys("Sanathnagar");
		driver.findElement(By.xpath("//*[@id='BillingNewAddress_ZipPostalCode']")).sendKeys("500018");
		driver.findElement(By.xpath("//*[@id='BillingNewAddress_PhoneNumber']")).sendKeys("1234567890");
		driver.findElement(By.xpath("(//*[@class='button-1 new-address-next-step-button'])[1]")).click();
		WebDriverWait wait=new WebDriverWait(driver, 20);
		
		//Working on shipping address tab
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='shipping-address-select']")));
		Select shippingAddress = new Select(driver.findElement(By.xpath("//*[@id='shipping-address-select']")));
		List<WebElement> numberOfAdrreses = shippingAddress.getOptions();
		int l = numberOfAdrreses.size();
		System.out.println("No of addresses in the shipping dropdown - "+l);
		shippingAddress.selectByIndex(l-2);
		driver.findElement(By.xpath("(//*[@class='button-1 new-address-next-step-button'])[2]")).click();
		
		//Shipping method
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='shippingoption_1'])")));
		driver.findElement(By.xpath("(//*[@id='shippingoption_1'])")).click();
		driver.findElement(By.xpath("(//*[@class='button-1 shipping-method-next-step-button'])")).click();
		
		//selecting payment method
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@id='paymentmethod_0'])")));
		driver.findElement(By.xpath("(//*[@id='paymentmethod_0'])")).click();
		driver.findElement(By.xpath("(//*[@class='button-1 payment-method-next-step-button'])")).click();
		
		//Validate the message for COD
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='info'])")));
		String CODmessage = driver.findElement(By.xpath("(//*[@class='info'])")).getText();
		if (CODmessage.equals("You will pay by COD")) 
		{
			Thread.sleep(5000);
			driver.findElement(By.xpath("(//*[@class='button-1 payment-info-next-step-button'])")).click();
		} 
		else 
		{
			System.out.println("Payment method is not COD");
		}
		
		//Confirming the order and validate the message
		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("(//*[@class='button-1 confirm-order-next-step-button'])")));
		driver.findElement(By.xpath("(//*[@class='button-1 confirm-order-next-step-button'])")).click();
		String orderPlcedMessage = driver.findElement(By.xpath("(//*[@class='title'])")).getText();
		if (orderPlcedMessage.equals("Your order has been successfully processed!")) 
		{
			String ordernumber = driver.findElement(By.xpath("(//*[@class='details'])")).getText();
			System.out.println("Order has placed and the "+ordernumber);
			driver.findElement(By.xpath("(//*[@class='button-2 order-completed-continue-button'])")).click();
		} 
		else 
		{
			System.out.println("Order is not placed");
		}

	}
	@Then("Logout the application")
	public void logout_the_application() 
	{
		//Logging out and closing the application
		driver.findElement(By.xpath("(//*[@class='ico-logout'])")).click();
		driver.close();
	}
}
