package com.library.stepDefinitions;

import com.library.pages.BookPage;
import com.library.utilities.DB_Util;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.Keys;

import java.util.Map;

public class us04_FaraStepDefs {

    BookPage bookpage = new BookPage();

    @When("the user searches for {string} book")
    public void the_user_searches_for_book(String cleanCode) {
        bookpage.search.sendKeys("Clean Code" + Keys.ENTER);
    }

    @When("the user clicks edit book button")
    public void the_user_clicks_edit_book_button() {
        bookpage.editBook("Clean Code").click();
    }

    @Then("book information must match the Database")
    public void book_information_must_match_the_database() {
        String actualBookName = bookpage.bookName.getAttribute("value");
        System.out.println("bookNameText = " + actualBookName);
        String actualIsbn = bookpage.isbn.getAttribute("value");
        System.out.println("isbnText = " + actualIsbn);
        String actualYear = bookpage.year.getAttribute("value");
        System.out.println("yearText = " + actualYear);
        String actualAuthor = bookpage.author.getAttribute("value");
        System.out.println("authorText = " + actualAuthor);
        String actualDescription = bookpage.description.getAttribute("value");
        System.out.println("descriptionText = " + actualDescription);

        String query = "select * from books where name='Clean Code' AND description LIKE 'Test'";
        DB_Util.runQuery(query);

        Map<String, String> bookInfo = DB_Util.getRowMap(1);

        String expectedBookName = bookInfo.get("name");
        System.out.println("expectedBookName = " + expectedBookName);
        String expectedISBN = bookInfo.get("isbn");
        System.out.println("expectedISBN = " + expectedISBN);
        String expectedYear = bookInfo.get("year");
        System.out.println("expectedYear = " + expectedYear);
        String expectedAuthorName =bookInfo.get("author");
        System.out.println("expectedAuthorName = " + expectedAuthorName);
        String expectedDesc = bookInfo.get("description");
        System.out.println("expectedDesc = " + expectedDesc);

        Assert.assertEquals(expectedBookName,actualBookName);
        Assert.assertEquals(expectedISBN,actualIsbn);
        Assert.assertEquals(expectedYear,actualYear);
        Assert.assertEquals(expectedAuthorName,actualAuthor);
        Assert.assertEquals(expectedDesc,actualDescription);

    }

}
