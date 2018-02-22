import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.ArrayList;
/**
 * Write a description of class Menu here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class MenuDisplay
{
    private TestOrganizer data;
    private TestFileManager dataManager;

    public MenuDisplay() throws Exception
    {
        data = new TestOrganizer();
        dataManager = new TestFileManager();
    }

    public MenuDisplay(TestOrganizer data, TestFileManager dataManager)throws Exception
    {
        if (data != null) this.data = data;
        else this.data = new TestOrganizer();

        if (dataManager != null) this.dataManager = dataManager;
        else this.dataManager = new TestFileManager();
    }

    public void mainMenu() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String enteredValue = "";
        double classAverage = FunctionsCalculator.getClassAverage(data);
        while (!enteredValue.equals("3"))
        {
            System.out.print("\f");
            System.out.println("Hello! Welcome to the Class MarkBook!");
            System.out.println("Your class average is: " + classAverage + "%");
            System.out.println("What would you like to do?");
            System.out.println("1.View test information");
            System.out.println("2.View student information");
            System.out.println("3.Save and exit");
			
			// test for valid input
            do
            {
                enteredValue = reader.readLine();
                if (!enteredValue.equals("1") && !enteredValue.equals("2") && !enteredValue.equals("3")) System.out.println("Invalid input. Please enter a number from 1 - 3.");
            } while (!enteredValue.equals("1") && !enteredValue.equals("2") && !enteredValue.equals("3"));
			
			//perform tasks according to input
            switch (Integer.parseInt(enteredValue))
            {
                case 1: 
                {
                    testMenu();
                    break;
                }
                case 2: 
                {
                    studentMenu();
                    break;
                }
                case 3: 
                {
                    dataManager.save(data);
                    System.exit(0);
                    break;
                }
            }
        }
    }

    public void testMenu() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String enteredValue = "";

        while (!enteredValue.equals("B")){
            System.out.print("\f");
            System.out.println("All tests done by students:");

            for (int i = 0; i < data.getNumberOfTests(); i++)
            {
                String testName = data.getTestInformation().get(i).getName();
                int testIndex = i + 1;
                System.out.println(testIndex + ". " + testName);
            }

            System.out.println("Type the corresponding number to view the specific information of a test.");
            System.out.println("(A) Add test");
            System.out.println("(B) Back");

            do
            {
                enteredValue = reader.readLine();
                if (!isValidTestInput(enteredValue)) System.out.println("Please Enter an appropriate input.") ;

            }
            while  (!isValidTestInput(enteredValue));

            if (enteredValue.equals("A"))
            {
                newTestMenu();
            }
            else if (!enteredValue.equals("B"))
            {
                int indexOfSelectedTest = Integer.parseInt(enteredValue) - 1;
                specificTestMenu(indexOfSelectedTest);
            }
        }
    }

    public void studentMenu() throws IOException
    {
        //Runtime.getRuntime().exec("cls");
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String enteredValue = "";

        while (!enteredValue.equals("B"))
        {
            System.out.print("\f");
            System.out.println("All the students in this classroom");
            for (int i = 0; i < data.getNumberOfStudents(); i++)
            {
                String studentName = data.getStudents().get(i).getLegalName();
                int studentIndex = i + 1;
                System.out.println(studentIndex + ". " + studentName);

            }

            System.out.println("Type the corresponding number to view the specific information of a student.");
            System.out.println("(A) Add student");
            System.out.println("(R) rank");
            System.out.println("(B) Back");

            do
            {
                enteredValue = reader.readLine();
                if (!isValidStudentInput(enteredValue)) System.out.println("Please Enter an appropriate input.") ;

            }
            while (!isValidStudentInput(enteredValue));

            if (enteredValue.equals("A"))
            {
                newStudentMenu();
            }
            else if (enteredValue.equals("R"))
            {
                rankingMenu();
            }
            else if (!enteredValue.equals("B"))
            {
                int indexOfStudent = Integer.parseInt(enteredValue) - 1;
                specificStudentMenu(indexOfStudent);
            }
        }
    }

    public void rankingMenu() throws IOException 
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int[] studentIndexRankings = FunctionsCalculator.getRankings(data);
        double[] StudentMarkRankings = FunctionsCalculator.getRankingMarks(data);
        int lengthOfRankingList = FunctionsCalculator.getRankings(data).length;

        System.out.print("\f");
        System.out.println("Student Rankings:");
        for (int i = 0; i < lengthOfRankingList; i++)
        {
            String studentName = data.getStudents().get(studentIndexRankings[i]).getLegalName();
            double studentGrade = StudentMarkRankings[i];
            System.out.println(i + 1 + ". " + studentName + ", " + studentGrade + "%");
        }

        System.out.println("Type in something to back");
        reader.readLine();
    }

    public void specificTestMenu(int indexOfTest) throws IOException 
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String enteredValue = "";

        System.out.print("\f");
        while (!enteredValue.equals("B")) 
        {
            Test test = data.getTestInformation().get(indexOfTest);
            //calculate the percentile, deviation and average related to this student of index indexOfStudent 

            double averageScore = FunctionsCalculator.getTestAverage(indexOfTest, data);
            String date = test.getDay() + "/" + test.getMonth() +"/" + test.getYear();

            System.out.println ("(N) Name: " + test.getName());
            System.out.println ("(D) Date: " + date);
            System.out.println ("(M) total Marks: " + test.getMark());
            System.out.println ("Average score: " + averageScore + "%");
            System.out.println ("Scores for student scores are listed below:");
            System.out.println ("");

            //display all the tests and the scores that this student have gotten
            for (int i = 0; i < data.getNumberOfStudents(); i++)
            {
                int studentMark = data.getTestMarks().get(indexOfTest).get(i);
                String studentName = data.getStudents().get(i).getLegalName();

                if (studentMark > test.getMark() || studentMark < 0) System.out.println((i+1) + ". " + studentName + ": " + "NoMark");
                else System.out.println((i+1)+ ". "  + studentName + ": " + studentMark + "/" + test.getMark());
            }

            System.out.println ("");
            System.out.println ("(B)Back");
            System.out.println ("(X)Delete test");

            do
            {
                enteredValue = reader.readLine();
                if (!isValidSpecificTestInput(enteredValue)) System.out.println("Please Enter an appropriate input.") ;                    
            }
            while (!isValidSpecificTestInput(enteredValue));

            // delete test accordingly
            if (enteredValue.equals("X")){
                data.deleteTest(indexOfTest);
                dataManager.removeTest(indexOfTest);
                return;
            }
            
       
            changeSpecificTestMenu(indexOfTest, enteredValue);
        }
    }

    public void specificStudentMenu(int indexOfStudent) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String enteredValue = "";

        while (!enteredValue.equals("B")) 
        {
            Student student = data.getStudents().get(indexOfStudent);
            //calculate the percentile, deviation and average related to this student of index indexOfStudent 
            double percentile = FunctionsCalculator.getPercentile(indexOfStudent, data);
            double deviation = FunctionsCalculator.getStudentClassDifference(indexOfStudent, data);
            double Average = FunctionsCalculator.getStudentAverage(indexOfStudent, data);

            System.out.print("\f");
            System.out.println ("(L) Legal Name: " + student.getLegalName());
            System.out.println ("(C) Chinese Name: " + student.getChineseName());
            System.out.println ("(G) Age: " + student.getAge());
            System.out.println (" Average: " + Average + "%");
            System.out.println (" Percentile: " + percentile);
            System.out.println (" Deviation from class average: " + deviation + "%");
            System.out.println ("Scores for each tests are listed below:");
            System.out.println ("");

            //display all the tests and the scores that this student have gotten
            for (int i = 0; i < data.getNumberOfTests(); i++)
            {
                int totalTestMark = data.getTestInformation().get(i).getMark();
                int studentMark = data.getTestMarks().get(i).get(indexOfStudent);
                String testName = data.getTestInformation().get(i).getName();

                if (studentMark > totalTestMark || studentMark < 0) System.out.println(testName + ": " + "NoMark");
                else System.out.println((i + 1) + ". " + testName + ": " + studentMark + "/" + totalTestMark);
            }

            System.out.println ("");
            System.out.println ("(B)Back");
            System.out.println ("(X)Delete student");

            do
            {
                enteredValue = reader.readLine();
                if (!isValidSpecificStudentInput(enteredValue)) System.out.println("Please Enter an appropriate input.") ;                    
            }
            while (!isValidSpecificStudentInput(enteredValue));

            // delete student accordingly
            if (enteredValue.equals("X")){
                data.deleteStudent(indexOfStudent);
                dataManager.removeStudent(indexOfStudent);
                return;
            }

            changeSpecificStudentMenu(indexOfStudent, enteredValue);
        }
    }

    public void newTestMenu() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        int day = 0;
        int month = 0;
        int year = 0; 
        int totalMarks = 0;
        String name = "";
        boolean isValidDate = false;
        boolean isValidMark = false;
        boolean isValidTest = false;
        
        System.out.print("\f");
        System.out.println("Test name:");
        name = reader.readLine();

        for (int i = 0; i < 3; i++)
        {
            if (i == 0) System.out.println("day: ");
            else if (i == 1)System.out.println("month: ");
            else if (i == 2)System.out.println("year: ");
            String enteredValue = "";
            isValidDate = false;
            do
            {
                try 
                {
                    enteredValue = reader.readLine();
                    if (i == 0)
                    {
                        day = Integer.parseInt(enteredValue);
                        if (day > 0 && day <= 31) isValidDate = true;
                        else System.out.println("Please enter a positive integer from, 1 - 31");
                    }
                    else if (i == 1)
                    {
                        month = Integer.parseInt(enteredValue);
                        if (month > 0 && month <= 12) isValidDate = true;
                        else System.out.println("Please enter a positive integer from, 1 - 12");
                    }
                    else if (i == 2)
                    {
                        year = Integer.parseInt(enteredValue);
                        if (year > 0) isValidDate = true;
                        else System.out.println("Please enter a positive integer.");
                    }
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter a positive integer.");
                }
            } while (!isValidDate);
        }

        System.out.println("Total Marks: ");

        do
        {
            try 
            {
                totalMarks = Integer.parseInt(reader.readLine());
                if (totalMarks > 0) isValidMark = true;
                else System.out.println("Please enter a positive integer.");
            }
            catch (NumberFormatException e)
            {
                isValidMark = false;
                System.out.println("Please enter a positive integer.");
            }
        } while (!isValidMark);

        Test newTest = new Test(day, month, name, totalMarks, year);
        ArrayList<Integer> studentMarks = new ArrayList<Integer>();

        for (int i = 0; i < data.getNumberOfStudents(); i++)
        {
            isValidTest = false;
            String studentName = data.getStudents().get(i).getLegalName();
            System.out.println("Test score of " + studentName + " (out of " + totalMarks + ")" );
            System.out.println("Enter a mark from 0 - " + totalMarks + " to give a student a score.");
            System.out.println("Enter a mark outside of the above boundary to indicate that the student was not part of the test.");
            do
            {         
                try 
                {
                    int currentStudentMark = Integer.parseInt(reader.readLine());
                    studentMarks.add(currentStudentMark);
                    isValidTest = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter an integer.");
                }
            } while (!isValidTest);
        }

        data.addNewTest(newTest, studentMarks);
    }

    public void newStudentMenu() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String legalName = "";
        String chineseName = "";
        int age = 0;
        boolean isValidAge = false;
        boolean isValidTest= false; 
        
        System.out.print("\f");
        System.out.println("Legal name:");
        legalName = reader.readLine();

        System.out.println("Chinese name: ");
        chineseName = reader.readLine();

        System.out.println("Age: ");

        do
        {
            try 
            {
                age = Integer.parseInt(reader.readLine());
                if (age > 0) isValidAge = true;
                else System.out.println("Please enter a positive integer.");
            }
            catch (NumberFormatException e)
            {
                isValidAge = false;
                System.out.println("Please enter a positive integer.");
            }
        } while (!isValidAge);

        Student newStudent = new Student(age, chineseName, legalName);
        ArrayList<Integer> studentMarks = new ArrayList<Integer>();
        
        for (int i = 0; i < data.getNumberOfTests(); i++)
        {
            String testName = data.getTestInformation().get(i).getName();
            int testMarks = data.getTestInformation().get(i).getMark();
            System.out.println("Student score for " + testName + " (out of " + testMarks + ")" );
            System.out.println("Enter a mark from 0 - " + testMarks + " to give a student a score.");
            System.out.println("Enter a mark outside of the above boundary to indicate that the student was not part of the test.");
            do
            {
                isValidTest = false;
                try 
                {
                    int currentTestMark = Integer.parseInt(reader.readLine());
                    studentMarks.add(currentTestMark);
                    isValidTest = true;
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter an integer.");
                }
            } while (!isValidTest);
        }

        data.addNewStudent(newStudent, studentMarks);
    }

    public void changeSpecificStudentMenu(int indexOfStudent, String input) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        
        System.out.print("\f");
        if (input.equals("L"))
        {
            System.out.println("New legal name: ");
            String enteredValue = reader.readLine();

            data.getStudents().get(indexOfStudent).setLegalName(enteredValue);
        }
        else if (input.equals("C"))
        {
            System.out.println("New Chinese name: ");
            String enteredValue = reader.readLine();

            data.getStudents().get(indexOfStudent).setLegalName(enteredValue);
        }
        else if (input.equals("G"))
        {
            System.out.println("New Age: ");
            boolean isValid = false;
            String enteredValue = "";
            int age = 0;
            do
            {
                try 
                {
                    enteredValue = reader.readLine();
                    age = Integer.parseInt(enteredValue);
                    if (age > 0) isValid = true;
                    else System.out.println("Please enter a positive integer.");
                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                    System.out.println("Please enter a positive integer.");
                }
            } while (!isValid);

            data.getStudents().get(indexOfStudent).setAge(age);
        }
        else if (!input.equals("B"))
        {
            int testIndex = Integer.parseInt(input) - 1;
            int totalTestMarks = data.getTestInformation().get(testIndex).getMark();
            String testName = data.getTestInformation().get(testIndex).getName();        
            boolean isValid = false;
            String enteredValue = "";
            int mark = 0;

            System.out.println("New mark for the test: " + testName);
            System.out.println("Enter a mark from 0 - " + totalTestMarks + " to give a student a score.");
            System.out.println("Enter a mark outside of the above boundary to indicate that the student was not part of the test.");

            do
            {
                try 
                {
                    enteredValue = reader.readLine();
                    mark = Integer.parseInt(enteredValue);

                    isValid = true;

                    data.setSpecificTest(testIndex, indexOfStudent, mark);
                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                    System.out.println("Please enter an integer.");
                }
            } while (!isValid);

        }
    }

    public void changeSpecificTestMenu(int indexOfTest, String input) throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("\f");
        if (input.equals("M"))
        {
            boolean isValid = false;
            String enteredValue = "";
            int mark = 0;
            System.out.println("New Mark: ");

            do
            {
                try 
                {
                    enteredValue = reader.readLine();
                    mark = Integer.parseInt(enteredValue);
                    if (mark > 0) isValid = true;
                    System.out.println("Please enter a positive integer.");
                }
                catch (NumberFormatException e)
                {
                    System.out.println("Please enter a positive integer.");
                }
            } while (!isValid);

            data.getTestInformation().get(indexOfTest).setMark(mark);
        }
        else if (input.equals("N"))
        {
            System.out.println("New name: ");

            String name = reader.readLine();

            data.getTestInformation().get(indexOfTest).setName(name);
        }
        else if (input.equals("D"))
        {
            boolean isDateValid = false;
            String enteredValue = "";
            int day = 0;
            int month = 0;
            int year = 0;     
            for (int i = 0; i < 3; i++)
            {
                if (i == 0) System.out.println("New day: ");
                else if (i == 1)System.out.println("New month: ");
                else if (i == 2)System.out.println("New year: ");
                isDateValid = false;
                do
                {
                    try 
                    {
                        enteredValue = reader.readLine();
                        if (i == 0)
                        {
                            day = Integer.parseInt(enteredValue);
                            if (day > 0 && day <= 31) isDateValid = true;
                            else System.out.println("Please enter a positive integer from, 1 - 31");
                        }
                        else if (i == 1)
                        {
                            month = Integer.parseInt(enteredValue);
                            if (month > 0 && month <= 12) isDateValid = true;
                            else System.out.println("Please enter a positive integer from, 1 - 12");
                        }
                        else if (i == 2)
                        {
                            year = Integer.parseInt(enteredValue);
                            if (year > 0) isDateValid = true;
                            else System.out.println("Please enter a positive integer.");
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Please enter a positive integer.");
                    }
                } while (!isDateValid);
            }

            data.getTestInformation().get(indexOfTest).setDay(day);
            data.getTestInformation().get(indexOfTest).setMonth(month);
            data.getTestInformation().get(indexOfTest).setYear(year);
        }
        else if (!input.equals("B"))
        {
            int studentIndex = Integer.parseInt(input) -1;
            int totalTestMark = data.getTestInformation().get(indexOfTest).getMark();
            String studentName = data.getStudents().get(studentIndex).getLegalName();        
            boolean isValid = false;
            String enteredValue = "";
            int mark = 0;

            System.out.println("New mark for the test: " + studentName);
            System.out.println("Enter a mark from 0 - " + totalTestMark + " to give a student a score.");
            System.out.println("Enter a mark outside of the above boundary to indicate that the student was not part of the test.");

            do
            {
                try 
                {
                    enteredValue = reader.readLine();
                    mark = Integer.parseInt(enteredValue);

                    isValid = true;

                    data.setSpecificTest(indexOfTest, studentIndex, mark);
                }
                catch (NumberFormatException e)
                {
                    isValid = false;
                    System.out.println("Please enter an integer.");
                }
            } while (!isValid);
        }
    }

    // private methods 
    private boolean  isValidStudentInput(String input)
    {
        if (input.equals("B") || input.equals("A") || input.equals("R")) return true;

        try 
        {
            int integerInput = Integer.parseInt(input);
            for(int i = 0; i < data.getNumberOfStudents(); i++)
            {
                if(integerInput - 1 == i) return true;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }

        return false;
    }

    private boolean isValidTestInput (String input)
    {
        if (input.equals("B") || input.equals("A")) return true;

        try 
        {
            int integerInput = Integer.parseInt(input);
            for(int i = 0; i < data.getNumberOfTests(); i++)
            {
                if(integerInput - 1 == i) return true;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return false;
    }

    //
    private boolean isValidSpecificStudentInput (String input)
    {
        if (input.equals("L") || input.equals("C") || input.equals("G") 
        || input.equals("B") || input.equals("X")) return true;

        try 
        {
            int integerInput = Integer.parseInt(input);
            for(int i = 0; i < data.getNumberOfTests(); i++)
            {
                if(integerInput - 1 == i) return true;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return false;
    }

    private boolean isValidSpecificTestInput(String input)
    {
        if (input.equals("M") || input.equals("N") || input.equals("D") 
        || input.equals("B") || input.equals("X")) return true;

        try 
        {
            int integerInput = Integer.parseInt(input);
            for(int i = 0; i < data.getNumberOfStudents(); i++)
            {
                if(integerInput - 1 == i) return true;
            }
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        return false;
    }
}

