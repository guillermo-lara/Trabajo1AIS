package es.codeurjc.web.nitflex.web;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import es.codeurjc.web.nitflex.Application;

@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FilmWebUtilsTests {
    @LocalServerPort
    private int port;
    private WebDriver driver;
    private String title;
    private String year;
    private String synopsis;
    private String ageRating;

    @BeforeEach
    public void setUpTest() {
        driver = new ChromeDriver();
        title = "Holaa";
        year = "22";
        synopsis = "Muy buena peli";
        ageRating = "+7";
    }

    @AfterEach
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void apareceLaPeliculaNueva() {
        driver.get("http://localhost:8080/films/new");
        driver.findElement(By.name("title")).sendKeys(title);
        driver.findElement(By.name("releaseYear")).clear();
        driver.findElement(By.name("releaseYear")).sendKeys(year);
        driver.findElement(By.name("synopsis")).sendKeys(synopsis);
        driver.findElement(By.name("ageRating")).sendKeys(ageRating);
        driver.findElement(By.id("Save")).click();

        String titleObteined = driver.findElement(By.id("film-title")).getText();
        String yearObteined = driver.findElement(By.id("film-releaseYear")).getText();
        String synopsisObteined = driver.findElement(By.id("film-synopsis")).getText();
        String ageRartingObteined = driver.findElement(By.cssSelector(".ui.large.label")).getText();
        assertEquals(title, titleObteined);
        assertEquals(year, yearObteined);
        assertEquals(synopsis, synopsisObteined);
        assertEquals(ageRating, ageRartingObteined);
    }

    @Test
    public void intentarSubiPeliculaSinTitulo(){
        driver.get("http://localhost:8080/films/new");
        driver.findElement(By.name("releaseYear")).clear();
        driver.findElement(By.name("releaseYear")).sendKeys(year);
        driver.findElement(By.name("synopsis")).sendKeys(synopsis);
        driver.findElement(By.name("ageRating")).sendKeys(ageRating);
        driver.findElement(By.id("Save")).click();

        String errorMessage = driver.findElement(By.cssSelector("#error-list li")).getText();
        assertEquals("The title is empty", errorMessage);
        driver.findElement(By.cssSelector(".ui.button")).click();

        boolean peliculaSinTituloEncontrada = false;
        List<WebElement> filmTitles = driver.findElements(By.cssSelector(".ui.grid .film .film-title"));
        for (WebElement titleI : filmTitles) {
            if (titleI.getText().isEmpty()) {
                peliculaSinTituloEncontrada = true;
                break;
            }
        }
        assertEquals(false,peliculaSinTituloEncontrada);
    }

}
