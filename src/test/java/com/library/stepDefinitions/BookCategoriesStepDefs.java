package com.library.stepDefinitions;

import com.library.pages.BookPage;
import com.library.utilities.BrowserUtil;
import com.library.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;

import java.util.List;

public class BookCategoriesStepDefs {
    BookPage bookPage;
  //  BookPage bookPage=new BookPage();
    /*
    if we initialize our pages at the class level and run DB we will have one blank browser why ?
    We are calling constructor of Base age and inside the constructor we are calling Driver.getDriver() method and
    this method is opening one empty browser for us.
     */

    @When("the user navigates to {string} page")
    public void the_user_navigates_to_page(String moduleName) {
        bookPage=new BookPage();
        bookPage.navigateModule(moduleName);
        BrowserUtil.waitFor(2);

    }
    List<String> actualCategories;
    @When("the user clicks book categories")
    public void the_user_clicks_book_categories() {
         actualCategories = BrowserUtil.getAllSelectOptions(bookPage.mainCategoryElement);
        System.out.println("actualCategories = " + actualCategories);

        // EXCLUDE ALL FROM UI
        actualCategories.remove(0);
        System.out.println("----AFTER EXCLUDE ALL FROM LIST ----");
        System.out.println("actualCategories = " + actualCategories);



    }
    @Then("verify book categories must match book_categories table from db")
    public void verify_book_categories_must_match_book_categories_table_from_db() {

        String query="select name from book_categories";

        DB_Util.runQuery(query);

        List<String> expectedCategories = DB_Util.getColumnDataAsList(1);

        Assert.assertEquals(expectedCategories,actualCategories);

    }

    @When("I execute query to find most popular book genre")
    public void i_execute_query_to_find_most_popular_book_genre() {
      String queryOfMostPopularCategory="select name from book_categories\n" +
              "            where id=(select book_category_id from books\n" +
              "             where id=(select book_id from book_borrow group by book_id order by count(*) desc limit 1))\n" +
              ";";
      DB_Util.runQuery(queryOfMostPopularCategory);
    }
    @Then("verify {string} is the most popular book genre.")
    public void verify_is_the_most_popular_book_genre(String expectedFromUser) {
        String actualFromDB = DB_Util.getFirstRowFirstColumn();
        System.out.println("actualFromDB = " + actualFromDB);
        Assert.assertEquals(expectedFromUser,actualFromDB);
        System.out.println("expectedFromUser = " + expectedFromUser);
    }


}
