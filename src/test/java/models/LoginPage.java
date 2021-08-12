package models;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {



    @FindBy(id = "inputUsername")
    private WebElement userName;


    @FindBy(id = "inputPassword")
    private WebElement password;

    @FindBy(id = "loginButton")
    private WebElement loginButton;

    @FindBy(id = "loginButtonssss")
    private WebElement loginButtonsss;

    @FindBy(id = "signUpButton")
    private WebElement signUpButton;

    public LoginPage(WebDriver driver)
    {
        PageFactory.initElements(driver, this);
    }


    public void clickLoginButton()
    {
        loginButton.click();
    }

    public void clickSignUpButton()
    {
        signUpButton.click();
    }

}
