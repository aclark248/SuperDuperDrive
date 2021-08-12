package models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignUpPage {

    //first name    inputFirstName
    @FindBy(id = "inputFirstName")
    private WebElement firstName;

    //last name      inputLastName
    @FindBy(id = "inputLastName")
    private WebElement lastName;

    //username      inputUsername
    @FindBy(id = "inputUsername")
    private WebElement userName;

    //password      inputPassword
    @FindBy(id = "inputPassword")
    private WebElement password;

    public SignUpPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }

}
