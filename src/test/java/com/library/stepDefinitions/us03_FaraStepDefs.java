package com.library.stepDefinitions;

import com.library.pages.BookPage;
import com.library.pages.LoginPage;
import com.library.utilities.BrowserUtil;
import com.library.utilities.DB_Util;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class us03_FaraStepDefs {
    LoginPage loginPage = new LoginPage();
    BookPage bookPage = new BookPage();

    @Given("the {string} on the home page")
    public void the_on_the_home_page(String librarian) {

        loginPage.login(librarian);
    }

    @When("the user navigates to {string} page")
    public void theUserNavigatesToPage(String Books) {
        bookPage.navigateModule("Books");
    }

    @And("the user clicks book categories")
    public void theUserClicksBookCategories() {
        bookPage.mainCategoryElement.click();

    }


    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db()
    {

        List<String> actualCategoryList = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        actualCategoryList.remove(0);
        System.out.println("actualCategoryList = " + actualCategoryList);

        String query = "select name from book_categories";
        DB_Util.runQuery(query);

        List<String> expectedCategoryList = DB_Util.getColumnDataAsList("name");

        System.out.println("expectedData = " + expectedCategoryList);

        Assert.assertEquals(expectedCategoryList, actualCategoryList);


    }

}
