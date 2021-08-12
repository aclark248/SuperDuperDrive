package tests;


import com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplication;
import com.udacity.jwdnd.course1.cloudstorage.controllers.HomeController;
import io.github.bonigarcia.wdm.WebDriverManager;
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
        var y = loginPage;
        loginPage.clickSignUpButton();

        //var wait = new WebDriverWait(driver, 60);
        //wait.until(ExpectedConditions.presenceOfElementLocated(By.id("signUpButton")));
        String currentPage = driver.getCurrentUrl();

        var x = 12;


        //current page is login
        assertEquals(currentPage, getLoginPageUrl());

        //navigate to signup page


        currentPage = driver.getCurrentUrl();
        //assert correct

        //navigate to home page
        //assert on login page

    }


    public String getLoginPageUrl() {
        var url = "http://localhost:" + port + "/login";
        return url;
    }





}


