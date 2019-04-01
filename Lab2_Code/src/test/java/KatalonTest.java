import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;

@RunWith(Parameterized.class)
public class KatalonTest {
  private static WebDriver driver;
  private static String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();
  private Student student;

  public KatalonTest(Student s)
  {
      this.student=s;
  }

    @Parameterized.Parameters
    public static Collection studentData() throws Exception {

        List<Student> k=Student.readXls();
        Object [][]res=new Object[k.size()][1];
        int count=0;
        for (Student st:k)
        {
            res[count][0]=st;
            count++;
        }
        return Arrays.asList(res);
    }


  @BeforeClass
  public static void setUp() throws Exception {
      System.setProperty("webdriver.chrome.driver","chromedriver");
      driver = new ChromeDriver();
      baseUrl = "https://www.katalon.com/";
      driver.get("http://121.193.130.195:8800/login");
      driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  public boolean useKatalon(Student student) throws Exception {
      String stuId=student.getStudentId();
      driver.findElement(By.name("id")).sendKeys(new String[]{stuId});
      driver.findElement(By.name("password")).sendKeys(new String[]{stuId.substring(stuId.length()-6)});
      driver.findElement(By.id("btn_login")).click();
      String webStudentId=driver.findElement(By.id("student-id")).getText();
      String webStudentName=driver.findElement(By.id("student-name")).getText();
      String webStudentGit=driver.findElement(By.id("student-git")).getText();

      System.out.println(student.getGit());
      driver.findElement(By.id("btn_logout")).click();
      driver.findElement(By.id("btn_return")).click();
      System.out.println(webStudentName.equals(student.getName()));
      System.out.println(webStudentId.equals(student.getStudentId()));
      System.out.println(webStudentGit.equals(student.getGit()));



      return webStudentName.equals(student.getName())&&webStudentId.equals(student.getStudentId())&&webStudentGit.equals(student.getGit());
  }

  @Test
  public void testKatalon() throws Exception {
      assertTrue(useKatalon(this.student));
  }

  @After
  public void tearDown() throws Exception {
    String verificationErrorString = verificationErrors.toString();
    if (!"".equals(verificationErrorString)) {
      fail(verificationErrorString);
    }
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
