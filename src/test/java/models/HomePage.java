package models;

import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class HomePage {

    @FindBy(id = "logoutBtn")
    public WebElement logoutBtn;

    @FindBy(id = "nav-notes-tab")
    public WebElement notesTab;

    @FindBy(id = "nav-credentials-tab")
    public WebElement credentialsTab;

    @FindBy(id = "addNewNoteButton")
    public WebElement addNewNoteBtn;

    @FindBy(id = "addNewCredentialButton")
    public WebElement addNewCredentialButton;




    //notes table
    @FindBy(id = "notesTable")
    public WebElement notesTable;

    @FindBy(id = "tableNoteTitle")
    public WebElement tableNoteTitle;

    @FindBy(id = "tableNoteDescription")
    public WebElement tableNoteDescription;

    @FindBy(id="btnEditNote")
    public WebElement btnEditNote;

    @FindBy(id="btnDeleteNote")
    public WebElement btnDeleteNote;



    //new note form
    @FindBy(id = "note-title")
    public WebElement noteTitle;

    @FindBy(id = "note-description")
    public WebElement noteDescription;

    @FindBy(id = "note-save-changes")
    public WebElement submitNoteBtn;

    //credential form
    @FindBy(id = "credential-url")
    public WebElement credentialURL;

    @FindBy(id = "credential-username")
    public WebElement credentialUsername;

    @FindBy(id = "credential-password")
    public WebElement credentialPassword;

    @FindBy(id = "credential-save-changes")
    public WebElement credentialSubmit;



    //credential table
    @FindBy(id = "credentialTable")
    public WebElement credentialTable;

    @FindBy(id = "deleteCredentialButton")
    public WebElement deleteCredentialButton;




    public HomePage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }


}


/****
 *         WebElement notesTable = driver.findElement(By.id("notesTable"));
 *
 *         //note title
 *         List<WebElement> notesTitles = notesTable.findElements(By.tagName("th"));
 *         var noteTitleTag = notesTitles.get(0);
 *         var noteTitle = noteTitleTag.getAttribute("innerHTML");
 *
 *         //note description
 *         List<WebElement> notesDescriptions = notesTable.findElements(By.tagName("td"));
 *         var firstDescription = notesDescriptions.get(0);
 *         var noteDescription = firstDescription.getAttribute("innerHTML");
 */