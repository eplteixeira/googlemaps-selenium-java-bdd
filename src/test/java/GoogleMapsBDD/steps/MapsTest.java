package GoogleMapsBDD.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class MapsTest {

    private WebDriver driver;
    private WebDriverWait wait;

    private final By acceptCookiesButton = By.cssSelector("div:nth-child(1) > form:nth-child(2) button");
    private final By searchBar = By.id("searchboxinput");
    private final By searchTitle = By.cssSelector("div > h1");
    private final By buttonDirection = By.xpath("//button[contains(@data-value,'Directions')]");
    private final By inputDestination = By.cssSelector("div#directions-searchbox-1 input.tactile-searchbox-input");

    public MapsTest(){
        String chromeDriverPath = "drivers/chromedriver.exe";

        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        
        driver = new ChromeDriver();
        driver.get("https://www.google.com/maps/");

        wait = new WebDriverWait(driver, 
                                 Duration.ofSeconds(20), 
                                 Duration.ofMillis(100));
    }

    @Given("I am at Google Maps page")
    public void I_am_at_Google_Maps_page() {
        wait.until(ExpectedConditions.elementToBeClickable(acceptCookiesButton))
            .click();
    }

    @When("I write {string} on Search bar and press Enter")
    public void I_write_on_Search_bar_and_press_Enter(String location) {
        var searchInput = wait.until(ExpectedConditions.elementToBeClickable(searchBar));        

        searchInput.clear();
        searchInput.sendKeys(location);
        searchInput.sendKeys(Keys.ENTER);
    }

    @Then("I should have {string} on the title")
    public void I_should_have_Dublin_on_the_title(String location) {
        wait.until(ExpectedConditions.presenceOfElementLocated(searchTitle));
        wait.until(ExpectedConditions.textToBePresentInElementLocated(searchTitle, location));
        var actualLocation = wait.until(ExpectedConditions.visibilityOfElementLocated(searchTitle))
                                    .getText();
                
        assertEquals(location, actualLocation);
    }

    @And("I click on the directions icon")
    public void i_click_on_the_directions_icon() {
        wait.until(ExpectedConditions.elementToBeClickable(buttonDirection)).click();
    }

    @And("I should have {string} has destination")
    public void i_should_have_dublin_has_destination(String location) {
        String actualDestination = wait.until(ExpectedConditions.visibilityOfElementLocated(inputDestination))
                                       .getAttribute("value");
        
        assertTrue(actualDestination.startsWith(location));
    }

    @After
    public void fecharBrowser() {
        driver.quit();
    }

}
