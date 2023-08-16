package com.library.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UsersPage extends BasePage{

    @FindBy(id = "user_status")
    public WebElement statusDropdown;

    @FindBy(className = "dataTables_info")
    private WebElement userCount;

    public String getUserCount() {
        // Extract data
        String allText = userCount.getText();
        System.out.println(allText);

        int start = allText.indexOf("of") + 3;
        int end = allText.indexOf("entries") - 1;

        // filter and replace
        return allText.substring(start, end).replace(",", "");

    }


}