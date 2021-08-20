package tests;


import com.udacity.jwdnd.course1.cloudstorage.CloudStorageApplication;
import com.udacity.jwdnd.course1.cloudstorage.controllers.HomeController;
import com.udacity.jwdnd.course1.cloudstorage.mappers.NotesMapper;
import com.udacity.jwdnd.course1.cloudstorage.mappers.SDDUserMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.SDDUser;
import io.github.bonigarcia.wdm.WebDriverManager;
import models.HomePage;
import models.LoginPage;
import models.SignUpPage;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.runners.Parameterized;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

//@SpringBootApplication
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CloudStorageApplication.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SignUpPageTests {

    @LocalServerPort
    private int port;

    private static WebDriver driver;

    private SignUpPage signUpPage;
    private LoginPage loginPage;
    private HomePage homePage;

    final String noteTitle =  "first note title";
    final String updatedNoteTitle = "second note title";

    final String credentialURL = "wwww.google.com";
    final String credentialUserName = "testusername234";
    final String credentialPassword = "password1342";

    final String credentialUserName2 = "testusername84848332";

    final String userName = "johndoe324";
    final String password = "ApeCook2443";

    @Autowired
    private NotesMapper notesMapper;

    @Autowired
    private SDDUserMapper sddUserMapper;


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
        loginPage.userName.sendKeys(userName);
        loginPage.password.sendKeys(password);
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


    //Write a test that creates a note,
    // and verifies it is displayed.
    @Test
    public void createNoteAndVerifyItExists() throws InterruptedException {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create note
        createNote(homePage);

        //verify note exists
        homePage.notesTab.click();
        TimeUnit.SECONDS.sleep(4);
        var noteExist = noteExists(noteTitle);

        deleteNote();

        //assert
        assertEquals(noteExist, true);
    }

    //Write a test that edits an existing note
    // and verifies that the changes are displayed.

    @Test
    public void editNote() throws InterruptedException {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create note
        createNote(homePage);

        //edit note
        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.notesTab));
        homePage.notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.btnEditNote));
        homePage.btnEditNote.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.noteTitle));
        homePage.noteTitle.clear();
        homePage.noteTitle.sendKeys(updatedNoteTitle);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.submitNoteBtn));
        homePage.submitNoteBtn.click();

        //verify note exists
        homePage.notesTab.click();
        TimeUnit.SECONDS.sleep(4);
        var noteExist = noteExists(updatedNoteTitle);
        assertEquals(noteExist, true);
        homePage.btnDeleteNote.click();
    }


    //Write a test that deletes a note and
    // verifies that the note is no longer displayed.
    @Test
    public void deleteNote()  {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create note
        createNote(homePage);

        //delete note
        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.notesTab));
        homePage.notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.btnDeleteNote));
        homePage.btnDeleteNote.click();

        var noteExist = noteExists(updatedNoteTitle);
        assertEquals(noteExist, false);

    }

    //Write a test that creates a set of credentials,
    // verifies that they are displayed, and
    // verifies that the displayed password is encrypted.
    @Test
    public void createCredential() {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create credential
        createCredential(homePage);

        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();

        //delete credential
        var credentialExists = credentialExists(credentialUserName);
        assertEquals(credentialExists, true);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.deleteCredentialButton));
        homePage.deleteCredentialButton.click();

    }


    //The user should be able to view/edit
    // When the user views the credential, they should be able to see the unencrypted password.
    @Test
    public void editCredential() {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create credential
        createCredential(homePage);

        //navigate to credentials tab
        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();

        //edit credential
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialEditButton));
        homePage.credentialEditButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialUsername));
        homePage.credentialUsername.clear();
        homePage.credentialUsername.sendKeys(credentialUserName2);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialSubmit));
        homePage.credentialSubmit.click();


        //navigate to credentials tab
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();

        //delete credential
        var credentialExists = credentialExists(credentialUserName2);
        assertEquals(credentialExists, true);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.deleteCredentialButton));
        homePage.deleteCredentialButton.click();

    }


    // or delete individual credentials.
    @Test
    public void deleteCredential()  {
        homePage = new HomePage(driver);

        //create user and sign in
        createUserAndSignIn();

        //create credential
        createCredential(homePage);

        //navigate to credentials tab
        WebDriverWait wait = new WebDriverWait (driver, 20);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();

        wait.until(ExpectedConditions.elementToBeClickable(homePage.deleteCredentialButton));
        homePage.deleteCredentialButton.click();

        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();

        //delete credential
        var credentialExists = credentialExists(credentialUserName);
        assertEquals(credentialExists, false);

    }


    public void createCredential(HomePage homePage)
    {
        WebDriverWait wait = new WebDriverWait (driver, 20);

        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialsTab));
        homePage.credentialsTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.addNewCredentialButton));
        homePage.addNewCredentialButton.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialURL));
        homePage.credentialURL.sendKeys(credentialURL);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialUsername));
        homePage.credentialUsername.sendKeys(credentialUserName);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialPassword));
        homePage.credentialPassword.sendKeys(credentialPassword);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.credentialSubmit));
        homePage.credentialSubmit.click();

    }





    public void createNote(HomePage homePage)
    {
        WebDriverWait wait = new WebDriverWait (driver, 20);

        //add new note
        wait.until(ExpectedConditions.elementToBeClickable(homePage.notesTab)).click();
        homePage.notesTab.click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.addNewNoteBtn)).click();
        wait.until(ExpectedConditions.elementToBeClickable(homePage.noteTitle));
        homePage.noteTitle.sendKeys(noteTitle);
        homePage.noteDescription.sendKeys("first note description");
        //wait.until(ExpectedConditions.elementToBeClickable(homePage.submitNoteBtn));
        homePage.submitNoteBtn.click();

    }


    public boolean noteExists(String title){
        List<WebElement> notesList = homePage.notesTable.findElements(By.tagName("th"));

        for(int i=0; i<notesList.size(); i++)
        {
            var currentNote = notesList.get(i);
            var noteTitle = currentNote.getAttribute("innerHTML");
            if (noteTitle.equals(title))
            {
                return true;
            }
        }
        return false;
    }

    public boolean credentialExists(String userName)
    {
        List<WebElement> credentialsList = homePage.credentialTable.findElements(By.tagName("td"));

        for(int i=0; i<credentialsList.size(); i++)
        {
            var currentCredential = credentialsList.get(i);
            var currentCredentialUserName = currentCredential.getAttribute("innerHTML");
            if (currentCredentialUserName.equals(credentialUserName))
            {
                return true;
            }
        }
        return false;

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

    public void createUserAndSignIn()
    {
        createTestUser();
        signIn();
    }

    public void createTestUser()
    {
        WebDriverWait wait = new WebDriverWait (driver, 20);
        var testUser = sddUserMapper.getUser(userName);
        if (testUser == null) {
            //navigate to sign up page
            driver.get("http://localhost:" + port + "/signup");
            signUpPage = new SignUpPage(driver);

            //fill out sign up form
            signUpPage.firstName.sendKeys("John");
            signUpPage.lastName.sendKeys("Doe");
            signUpPage.userName.sendKeys(userName);
            signUpPage.password.sendKeys(password);
            wait.until(ExpectedConditions.elementToBeClickable(signUpPage.signUpUserBtn)).click();
            //signUpPage.signUpUserBtn.click();
        }
    }

    public void signIn()
    {
        WebDriverWait wait = new WebDriverWait (driver, 20);

        //navigate to login page
        driver.get("http://localhost:" + port + "/login");
        loginPage = new LoginPage(driver);
        loginPage.userName.sendKeys(userName);
        loginPage.password.sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(loginPage.loginButton)).click();
    }

    /****
    public void deleteNote() throws InterruptedException {
        driver.get("http://localhost:" + port + "/home");
        homePage = new HomePage(driver);

        //verify note exists
        homePage.notesTab.click();
        TimeUnit.SECONDS.sleep(4);
        WebDriverWait wait = new WebDriverWait (driver, 20);


        List<WebElement> notesList = homePage.notesTable.findElements(By.tagName("td"));

        for(int i=3; i<notesList.size(); i++)
        {
            wait.until(ExpectedConditions.elementToBeClickable(homePage.notesTab));
            homePage.notesTab.click();
            var deleteNoteButtonLink = notesList.get(i).findElement(By.tagName("a"));
            wait.until(ExpectedConditions.elementToBeClickable(deleteNoteButtonLink));
            deleteNoteButtonLink.click();
        }
    }****/



}


