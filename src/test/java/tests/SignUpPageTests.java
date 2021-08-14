package tests;


import com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplication;
import com.udacity.jwdnd.course1.cloudstorage.controllers.HomeController;
import io.github.bonigarcia.wdm.WebDriverManager;
import models.HomePage;
import models.LoginPage;
import models.SignUpPage;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runners.Parameterized;

import java.time.Instant;

import static org.junit.Assert.assertEquals;

//@SpringBootApplication
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CloudStorageApplication.class)
public class SignUpPageTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private SignUpPage signUpPage;
    private LoginPage loginPage;

    @BeforeAll
    public static void beforeAll() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
    }

    @AfterAll
    public static void afterAll() {
        driver.quit();
    }


    @BeforeEach
    public void beforeEach() {
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
    }

    //Write a test that verifies that an unauthorized user can only access the login and signup pages
    @Test
    public void authorizationTest() {
        //unauthorized can only access login page
        driver.get("http://localhost:" + port + "/home");
        String currentPage = driver.getCurrentUrl();
        assertEquals(currentPage, getLoginPageUrl());

        driver.get("http://localhost:" + port + "/signup");
        currentPage = driver.getCurrentUrl();
        assertEquals(currentPage, getSignUpPageUrl());
    }

    //Write a test that signs up a new user,
    // logs in,
    // verifies that the home page is accessible,
    // logs out,
    // and verifies that the home page is no longer accessible.
    @Test
    public void createUser() {
        //navigate to sign up page
        driver.get("http://localhost:" + port + "/signup");
        signUpPage = new SignUpPage(driver);

        //fill out sign up form
        signUpPage.firstName.sendKeys("John");
        signUpPage.lastName.sendKeys("Doe");
        signUpPage.userName.sendKeys("johndoe324");
        signUpPage.password.sendKeys("ApeCook2443");

        //click sign up button
        signUpPage.signUpUserBtn.click();

        //navigate to login page
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.userName.sendKeys("johndoe324");
        loginPage.password.sendKeys("ApeCook2443");
        loginPage.loginButton.click();

        //expect home page
        String currentPage = driver.getCurrentUrl();

        assertEquals(currentPage, getHomePageURL());

        //log user out
        var homePage = new HomePage(driver);
        homePage.logoutBtn.click();
        currentPage = driver.getCurrentUrl();
        assertEquals(currentPage, getLoginPageUrl());

        //can navigate to home page
        driver.get(getHomePageURL());
        currentPage = driver.getCurrentUrl();
        assertEquals(currentPage, getLoginPageUrl());
    }

    public String getLoginPageUrl() {
        var url = "http://localhost:" + port + "/login";
        return url;
    }

    public String getSignUpPageUrl() {
        var url = "http://localhost:" + port + "/signup";
        return url;
    }

    public String getHomePageURL()
    {
        var url = "http://localhost:" + port + "/home";
        return url;
    }




}


