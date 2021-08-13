package models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {



    @FindBy(id = "inputUsername")
    public WebElement userName;


    @FindBy(id = "inputPassword")
    public WebElement password;

    @FindBy(id = "loginButton")
    public WebElement loginButton;


    @FindBy(id = "signUpButton")
    public WebElement signUpButton;

    public void clickLoginButton()
    {
        loginButton.click();
    }

    public void clickSignUpButton()
    {
        signUpButton.click();
    }

    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }



}
