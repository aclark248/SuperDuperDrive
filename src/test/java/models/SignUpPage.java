package models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    //first name    inputFirstName
    @FindBy(id = "inputFirstName")
    public WebElement firstName;

    //last name      inputLastName
    @FindBy(id = "inputLastName")
    public WebElement lastName;

    //username      inputUsername
    @FindBy(id = "inputUsername")
    public WebElement userName;

    //password      inputPassword
    @FindBy(id = "inputPassword")
    public WebElement password;

    @FindBy(id = "signUpUserBtn")
    public WebElement signUpUserBtn;

    public SignUpPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

}
