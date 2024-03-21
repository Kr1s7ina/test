import org.example.Skaiciuotuvas;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SkaiciuotuvasTest {
    public static final String USER_NAME = Skaiciuotuvas.generateUniqueUserName();
    public static final int FIRST_NUMBER = Skaiciuotuvas.generateRandomNumber();
    public static final int SECOND_NUMBER = Skaiciuotuvas.generateRandomNumber();



    @BeforeAll
    public static void setUp(){
        Skaiciuotuvas.setup();
    }

    @Test
    @Order(1)
    public void createNewUserSuccessful(){

        Skaiciuotuvas.createNewUser(USER_NAME, true);
        String result = Skaiciuotuvas.browser.findElement(By.xpath("/html/body/nav/div/ul[2]/a")).getText();
        Assertions.assertEquals("Logout, " + USER_NAME, result);
       Skaiciuotuvas.clickLogoutButton();
    }

    @Test
    @Order(2)
    public void createNewUserNegative(){

        Skaiciuotuvas.createNewUser(USER_NAME, false);
        String result = Skaiciuotuvas.browser.findElement(By.id("passwordConfirm.errors")).getText();
        Assertions.assertEquals("Įvesti slaptažodžiai nesutampa", result);
        Skaiciuotuvas.browser.get(Skaiciuotuvas.HOME_URL);
    }

    @Test
    @Order(3)
    public void loginSuccessful(){

        Skaiciuotuvas.fillLoginForm(USER_NAME);
        String result = Skaiciuotuvas.browser.findElement(By.xpath("/html/body/nav/div/ul[2]/a")).getText();
        Assertions.assertEquals("Logout, " + USER_NAME, result);
        Skaiciuotuvas.clickLogoutButton();
    }

    @Test
    @Order(4)
    public void loginNegative(){

        Skaiciuotuvas.fillLoginForm("wrongUserName");
        String result = Skaiciuotuvas.browser.findElement(By.xpath("/html/body/div/form/div/span[2]")).getText();
        Assertions.assertEquals("Įvestas prisijungimo vardas ir/ arba slaptažodis yra neteisingi", result);
        Skaiciuotuvas.browser.get(Skaiciuotuvas.HOME_URL);
    }

    @Test
    @Order(5)
    public void calculateSuccessful(){

        Skaiciuotuvas.fillLoginForm(USER_NAME);
        Skaiciuotuvas.fillCalculatorForm(String.valueOf(FIRST_NUMBER), String.valueOf(SECOND_NUMBER));
        String result = Skaiciuotuvas.browser.findElement(By.xpath("/html/body/h4")).getText();
        Assertions.assertEquals(FIRST_NUMBER +" + "+ SECOND_NUMBER+" = "+(FIRST_NUMBER+SECOND_NUMBER), result);
        Skaiciuotuvas.clickLogoutButton();
    }

    @Test
    @Order(6)
    public void calculateNegative(){

        Skaiciuotuvas.fillLoginForm(USER_NAME);
        Skaiciuotuvas.fillCalculatorForm("-5", "8");
        String result = Skaiciuotuvas.browser.findElement(By.id("sk1.errors")).getText();
        Assertions.assertEquals("Validacijos klaida: skaičius negali būti neigiamas", result);
        Skaiciuotuvas.clickLogoutButton();
    }

    @Test
    @Order(7)
    public void searchSuccessful(){

        Skaiciuotuvas.fillLoginForm(USER_NAME);
        boolean result = Skaiciuotuvas.searchForOperation(String.valueOf(FIRST_NUMBER), String.valueOf(SECOND_NUMBER), String.valueOf(FIRST_NUMBER + SECOND_NUMBER));
        Assertions.assertTrue(result);
        Skaiciuotuvas.clickLogoutButton();
    }

    @Test
    @Order(8)
    public void searchNegative(){

        Skaiciuotuvas.fillLoginForm(USER_NAME);
        boolean result = Skaiciuotuvas.searchForOperation("-2", "-5", "-9");
        Assertions.assertFalse(result);
        Skaiciuotuvas.clickLogoutButton();
    }

    @AfterAll
    public static void close(){
        Skaiciuotuvas.closeBrowser();
    }
}
