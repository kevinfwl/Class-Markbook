import java.util.ArrayList;
/**
 * A matrix which stores all the test scores for each class of students.
 * 
 * @author Feng Kevin
 * @version (a version number or a date)
 */
public class TestOrganizer
{
    // instance fields 
    private ArrayList<Student> students;
    private ArrayList<Test> testInformation;
    private ArrayList<ArrayList<Integer>> testMarks;

    /**
     * Creates a testOrganizer with default characteristics
     */
    public TestOrganizer()
    {
        students = new ArrayList<Student>();
        testInformation = new ArrayList<Test>();
        testMarks = new ArrayList<ArrayList<Integer>>();
    }// end of constructor testOrganizer

    /**
     * Creates a testOrganizer with specified characteristics
     * 
     * @param studentNumbers the student numbers of the class for which this 
     * @param 
     * @param studentMarks 
     */
    public TestOrganizer(ArrayList<Student> students, ArrayList<Test> testInformation,ArrayList<ArrayList<Integer>> testMarks)
    {
        if (students != null) this.students = students;
        else students = new ArrayList<Student>();

        if (testInformation != null)this.testInformation = testInformation;
        else testInformation = new ArrayList<Test>();

        if (testMarks != null)this.testMarks = testMarks;
        else this.testMarks = new ArrayList<ArrayList<Integer>>();
    }// end of constructor testOrganizer

    public void addNewTest(Test test, ArrayList<Integer> testMarks)
    {
        testInformation.add(test);
        this.testMarks.add(testMarks);
    }// end of method addNewTest(Test test, ArrayList<Integer> testMarks)

    public void changeSpecificTest (int studentIndex, int testIndex, int newTestMark)
    {
        testMarks.get(testIndex).set(studentIndex, newTestMark);
    }// end of 

    public void addNewStudent(Student student, ArrayList<Integer> testMarks) 
    {
        students.add(student);
		// adds all the tests of the student into the database testMarks
        for (int i = 0 ; i < testMarks.size(); i++)
        {
            int currentStudentMarks = testMarks.get(i);
            this.testMarks.get(i).add(currentStudentMarks); 
        }// end of for (int i = 0; i < students.size(); i++)
    }// end of method addNewStudent(Student student, ArrayList<Integer> testMarks) 

    // index cant be lower than 
    public void deleteTest(int testIndex) 
    {
        testInformation.remove(testIndex);
        testMarks.remove(testIndex);
    }

    // test Index cant be lower than 0
    public void deleteStudent(int studentIndex)
    {
        students.remove(studentIndex);
        for (int i = 0; i < testMarks.size(); i++)
        {
            testMarks.get(i).remove(studentIndex);
        }// end of for
    }// end of 

    public int getSpecificTest(int testIndex, int studentIndex)
    {
        // test whether or not the array is out of bounds
        if (testIndex < testInformation.size() && studentIndex < students.size() && testIndex >= 0 && studentIndex >= 0)
        {
            return testMarks.get(testIndex).get(studentIndex);
        }
        else
        {
            return -1;
        }
    }// end of method getTestOfSpecificStudent(int index1, int index2)

    public void setSpecificTest(int testIndex, int studentIndex, int mark)
    {
        // test whether or not the array is out of bounds
        if (testIndex < testInformation.size() && studentIndex < students.size() && testIndex >= 0 && studentIndex >= 0)
        {
            testMarks.get(testIndex).set(studentIndex, mark);
        }
        else
        {
            return;
        }
    }// end of method getTestOfSpecificStudent(int index1, int index2)

    public ArrayList<Test> getTestInformation()
    {
        return testInformation;
    }

    public ArrayList<Student> getStudents()
    {
        return students;
    }

    public ArrayList<ArrayList<Integer>> getTestMarks()
    {
        return testMarks;
    }

    public int getNumberOfTests() 
    {
        return testInformation.size();
    }

    public int getNumberOfStudents() 
    {
        return students.size();
    }  
}
