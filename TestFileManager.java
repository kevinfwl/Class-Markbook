import java.util.Properties;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Write a description of class PropertiesManager here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class TestFileManager
{
    
    //class variables
    public final String TEST_INFORMATION_FILE_NAME = "testInformation.txt";
    public final String STUDENT_INFORMATION_FILE_NAME = "studentInformation.txt";
    public final String TEST_MARKS_FILE_NAME = "testMarks.txt";     

    //instance variables
    private Properties testInformationProperties;
    private Properties studentProperties;
    private Properties testMarkProperties;

    public TestFileManager() throws FileNotFoundException, IOException
    {
        testInformationProperties = new Properties();
        studentProperties = new Properties();
        testMarkProperties = new Properties();

    }//end of constructor TestFileManager()

    public TestFileManager(Properties testInformationProperties, Properties StudentProperties, Properties testMarkProperties) throws FileNotFoundException, IOException
    {
        if (testInformationProperties != null ) this.testInformationProperties = testInformationProperties;
        else this.testInformationProperties = new Properties();

        if (studentProperties != null ) this.studentProperties = StudentProperties;
        else this.studentProperties = new Properties();

        if (testMarkProperties != null ) this.testMarkProperties = testMarkProperties;
        else this.testMarkProperties = new Properties();

    }//end of constructor TestFileManager()

    public ArrayList<Test> loadTestInformation() throws FileNotFoundException, IOException
    {
        InputStream reader = new FileInputStream(TEST_INFORMATION_FILE_NAME);
        testInformationProperties.load(reader);
        ArrayList<Test> loadedTestInformation = new ArrayList<Test>();      
        int counter = 0;
        int day;
        int month;
        String name;
        int mark;
        int year;

        //loads all the test information into ana arrayList
        while (testInformationProperties.getProperty(counter + " " + "day") != null)
        {
            //load all the parameters for the test object of the current index
            day = Integer.parseInt(testInformationProperties.getProperty(counter + " " + "day"));
            month = Integer.parseInt(testInformationProperties.getProperty(counter + " " + "month"));
            name = testInformationProperties.getProperty(counter + " " + "name");
            mark = Integer.parseInt(testInformationProperties.getProperty(counter + " " + "mark"));
            year = Integer.parseInt(testInformationProperties.getProperty(counter + " " + "year"));

            //add a test object with the above paramters to the array
            loadedTestInformation.add(new Test(day, month, name, mark, year));

            counter++;
        }

        return loadedTestInformation;
    }

    public ArrayList<Student> loadStudentInformation()throws FileNotFoundException, IOException
    {
        InputStream reader = new FileInputStream(STUDENT_INFORMATION_FILE_NAME);
        studentProperties.load(reader);
        ArrayList<Student> loadedStudentInformation = new ArrayList<Student>();     
        int counter = 0;
        int age;
        String chineseName;
        int grade;
        String legalName;
        String ontarioEducationNumber;

        //loads all the studentInformation  into an arrayList
        while (studentProperties.getProperty(counter + " " + "age") != null)
        {
            age = Integer.parseInt(studentProperties.getProperty(counter + " " + "age"));
            chineseName = studentProperties.getProperty(counter + " " + "chineseName");
            legalName = studentProperties.getProperty(counter + " " + "legalName");

            //add a test object with the above paramters to the array
            loadedStudentInformation.add(new Student(age, chineseName, legalName));

            counter++;
        }

        return loadedStudentInformation;
    }

    public ArrayList<ArrayList<Integer>> loadTestMarks() throws FileNotFoundException, IOException
    {
        InputStream reader = new FileInputStream(TEST_MARKS_FILE_NAME);

        int studentIndexCounter = 0;
        int testIndexCounter = 0;   

        testMarkProperties.load(reader);

        ArrayList<ArrayList<Integer>> testData = new ArrayList<ArrayList<Integer>>();

        // loop through all the indexes to extract all the values from the double ArrayList
		// loop through all the tests that was stored 
        while (testMarkProperties.getProperty(testIndexCounter + " " + studentIndexCounter) != null) 
        {
            ArrayList<Integer> testArrayList = new ArrayList<Integer>();
			
			//get all the students marks of a particular test
            while (testMarkProperties.getProperty(testIndexCounter + " " + studentIndexCounter) != null)
            {
                int grade = Integer.parseInt(testMarkProperties.getProperty(testIndexCounter + " " + studentIndexCounter));
                testArrayList.add(grade);
                studentIndexCounter++;
            }// end of while(testMarkProperties.getProperty(testIndexCounter + " " + studentIndexCounter) != null)

            testData.add(testArrayList);
            studentIndexCounter = 0;
            testIndexCounter++;
        }// end of while(testMarkProperties.getProperty(testIndexCounter + " " + studentIndexCounter) != null) 
        reader.close();

        return testData;
    }// end of method loadTestMarks() throws FileNotFoundException, IOException

    public void removeStudent(int indexOfStudent) throws FileNotFoundException, IOException
    {
        FileOutputStream studentWriter = new FileOutputStream(STUDENT_INFORMATION_FILE_NAME);

        studentProperties.remove(indexOfStudent +" " + "age");
        studentProperties.remove(indexOfStudent +" " + "chineseName");
        studentProperties.remove(indexOfStudent +" " + "legalName");

        studentProperties.store(studentWriter, null);
        studentWriter.close();

        FileOutputStream testWriter = new FileOutputStream(TEST_MARKS_FILE_NAME);
        int counter = 0;        

        while (testMarkProperties.getProperty(counter + " " + indexOfStudent) != null)
        {
            testMarkProperties.remove(counter + " " + indexOfStudent);
            counter++;
        }

        testMarkProperties.store(testWriter, null);
        testWriter.close();
    }// end of method removeStudent(int indexOfStudent) throws FileNotFoundException, IOException

    public void removeTest (int indexOfTest) throws FileNotFoundException, IOException
    {
        OutputStream testInformationWriter = new FileOutputStream(TEST_INFORMATION_FILE_NAME);

        testInformationProperties.remove(indexOfTest + " " + "day");
        testInformationProperties.remove(indexOfTest + " " + "month");
        testInformationProperties.remove(indexOfTest + " " + "name");
        testInformationProperties.remove(indexOfTest + " " + "mark");
        testInformationProperties.remove(indexOfTest + " " + "year");

        testInformationProperties.store(testInformationWriter, null);
        testInformationWriter.close();

        FileOutputStream testMarkWriter = new FileOutputStream(TEST_MARKS_FILE_NAME);
        int counter = 0;        

        while (testMarkProperties.getProperty(indexOfTest + " " + counter) != null)
        {
            testMarkProperties.remove(indexOfTest + " " + counter);
            counter++;
        }

        testMarkProperties.store(testMarkWriter, null);
        testMarkWriter.close();
    }// end of method removeTest (int indexOfTest) throws FileNotFoundException, IOException

    public void save(TestOrganizer testData) throws FileNotFoundException, IOException
    {
        OutputStream testInformationWriter = new FileOutputStream(TEST_INFORMATION_FILE_NAME);
        OutputStream studentInformationWriter = new FileOutputStream(STUDENT_INFORMATION_FILE_NAME);
        OutputStream testMarkWriter = new FileOutputStream(TEST_MARKS_FILE_NAME);

        //load test information
        for (int i = 0; i < testData.getNumberOfTests(); i++)
        {
            testInformationProperties.setProperty(i + " " + "day", testData.getTestInformation().get(i).getDay() + "");
            testInformationProperties.setProperty(i + " " + "month", testData.getTestInformation().get(i).getMonth() + "");
            testInformationProperties.setProperty(i + " " + "name", testData.getTestInformation().get(i).getName());
            testInformationProperties.setProperty(i + " " + "mark", testData.getTestInformation().get(i).getMark() + "");
            testInformationProperties.setProperty(i + " " + "year", testData.getTestInformation().get(i).getYear() + "");
        }

        testInformationProperties.store(testInformationWriter, null);
        testInformationWriter.close();

        //load student information
        for (int i = 0; i < testData.getNumberOfStudents(); i++)
        {
            studentProperties.setProperty(i + " " + "age", testData.getStudents().get(i).getAge() + "");
            studentProperties.setProperty(i + " " + "chineseName", testData.getStudents().get(i).getChineseName());
            studentProperties.setProperty(i + " " + "legalName", testData.getStudents().get(i).getLegalName());
        }

        studentProperties.store(studentInformationWriter, null);
        studentInformationWriter.close();

        //load testMarksInformation
        for (int testIndex = 0; testIndex < testData.getNumberOfTests(); testIndex++)
        {
            for (int studentIndex = 0; studentIndex < testData.getNumberOfStudents(); studentIndex++)
            {
                String specificTestMark = testData.getTestMarks().get(testIndex).get(studentIndex) + "";
                String specificKeyName = testIndex + " " + studentIndex;
                testMarkProperties.setProperty(specificKeyName, specificTestMark);
            }// end of for (int studentIndex = 0; studentIndex < testData.getNumberOfStudents(); studentIndex++)
        }// end of (int testIndex = 0; testIndex < testData.getNumberOfTests(); testIndex++)

        testMarkProperties.store(testMarkWriter, null);
        testMarkWriter.close();
    }

    public TestOrganizer loadTestOrganizer()throws FileNotFoundException, IOException
    {
        TestOrganizer loadedTestData = new TestOrganizer(loadStudentInformation(), loadTestInformation(), loadTestMarks());
        return loadedTestData;
    }
}