package GithubAutomation;

import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import java.io.File;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

@Test
public class AppTest 
{
	static WebDriver driver;
	static String updatedline, commitmessage;
	
	private final static Logger logger = Logger.getLogger(AppTest.class.getName());
	FileHandler fh;  
	
	@BeforeTest
	public void beforTest() throws Exception
	{
		//Logger maitainance
		fh = new FileHandler("MyLogFile.log");  
        logger.addHandler(fh);
        SimpleFormatter formatter = new SimpleFormatter();  
        fh.setFormatter(formatter);  
        // the following statement is used to log any messages  
        logger.info("Log maintainance for GitAutomation"); 
		//Taking input username password repositoryname etc.
 		DataInput.dataInput();		
 		logger.info("This is BeforeTest : Credentials Takken as Input with reponame.");	
		logger.warning("This is Warning : Be carefull Username and Password are case Sensitive");
	}
	
	@Test
	public void testOne()
	{
		//Login into Github
		driver = LoginMathod.loginMathod();
		logger.info("This is info : Login credentials filled in reqired field");
		//Test for login done
		AssertJUnit.assertEquals("GitHub",driver.getTitle());
		logger.info("This is info : Login done successfully");	
	}
	
	@Test(dependsOnMethods={"testOne"})
	public void testTwo()
	{
		//Clicking create new repository on dashboard
		DashboardMathod.dashboardMathod(driver);
		logger.info("This is info : Reached your dashboard");
		//Check for new repository click
		AssertJUnit.assertEquals("Create a New Repository",driver.getTitle());
	}
	
	@Test(dependsOnMethods={"testTwo"})
	public void testThree()
	{
		//Creating WebDriver drivernew repository by giving repository name 
		CreateRepositoryMathod.createRepositoryMathod(driver);
		logger.info("This is info : Ready for creating new repository");	
		logger.warning("This is warn : Repository name must be unique");
		//Test for made repository
		AssertJUnit.assertEquals("Vikash-Kumar-P/"+DataInput.repositoryname,driver.getTitle());
		logger.info("This is info : Your repository is created");
	}
	
	@Test(dependsOnMethods={"testThree"})
	public void testFour()
	{
		//Coping file of project to upload to new created repository
		SelectFiles.selectFiles();
		logger.info("This is info : Direcrtory is selected");	
		//Test for non-emptiness of selected directory
		String path1 = DataInput.path.replace("\\","");
		logger.warning("This is warn : Do not select empty directory");
		Assert.assertNotEquals(0,new File(path1).listFiles().length);
	}
	
	@Test(dependsOnMethods={"testFour"})
	public void testFive() throws Exception
	{
		//Running shell script to push the project files to github
		ShellScriptRunner.shellScriptRunner();
		logger.info("This is info : Data written to shell script");
		//Test for presence of pushed project
		AssertJUnit.assertEquals(0,PushedFileCheck.pushedFileCheck(driver));
		logger.warning("This is warn : You internet connection must be good");
	}
	
	@Test(dependsOnMethods={"testFive"})
	public static void testSix()
	{
		//Test for commitment of comment
		commitmessage = CommitComment.commitComment(driver);
		AssertJUnit.assertEquals(DataInput.comment,commitmessage);
		logger.info("This is info : Commitment of message done successfully");	
		logger.warning("This is warn : Commit message must have atleast one character");
	}
	
	@Test(dependsOnMethods={"testSix"})
	public static void testSeven() throws Exception
	{
		//Making change in readme file
		ChangeReadmeMathod.changeReadmeMathod(driver);
		logger.info("This is info : Updation in readme file is done sucessfully");
		logger.warning("This is warn : Updation text must not be empty");
		//Cloning the project to local directory
		updatedline = CloningRepository.cloningRepository();
		logger.info("This is info : Cloning done successwfully");	
		logger.warning("This is warn : You internet might be slow");
		//Test for cloning success
		AssertJUnit.assertEquals(0,CloneSuccessTest.cloneSuccessTest(driver));
		logger.info("This is info : Every file is clonned");	
		logger.warning("This is warn : Your file must not be too large");
	}
	
	@Test(dependsOnMethods={"testSeven"})
	public static void testEight()
	{
		
		//Test for change in readme file
		AssertJUnit.assertEquals(updatedline,DataInput.updatetext);
		driver.close();
		logger.info("This is info : Updation text given by you matches Updated text og ReadMe.txt file");	
		logger.warning("This is warn : ReadMe may not be present");
	}
}