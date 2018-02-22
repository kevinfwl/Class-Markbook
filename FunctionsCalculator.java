import java.util.ArrayList;
/**
 * A class which performs functions to determine the average, ranking, and perentile of a class, student or test (to be used for testOrganizers)
 * 
 * @author Kevin Feng 
 * @version 1.0 03/05/2017
 */
public class FunctionsCalculator
{
    public FunctionsCalculator()
    {
    }

    public static double getStudentAverage(int indexOfStudent, TestOrganizer testData) 
    {   
        int totalMarksScored = 0;
        int totalMarksForTest = 0;
        int totalNumberOfTests = testData.getNumberOfTests();
		//catch arithmetic exception when the denominator is 0
        try {
            try
            {       
                //iterate through all the tests of the testData for the particular student to find the total number of marks that the student scored
                for (int i = 0; i < totalNumberOfTests; i++)
                {
                    int testScoreOfAParticularTest = testData.getTestInformation().get(i).getMark();
                    int testScoreScoredbyStudent = testData.getTestMarks().get(i).get(indexOfStudent);

                    //marks can only be counter if they are poaster and are below the mark threshold for the specific test
                    if (testScoreScoredbyStudent < (testScoreOfAParticularTest + 1) && testScoreScoredbyStudent > -1) 
                    {
                        //add up all the testMarks and the marks that the student scored
                        totalMarksScored += testScoreScoredbyStudent;
                        totalMarksForTest += testScoreOfAParticularTest;
                    }//end of if (testScoreScoredbyStudent < (testScoreOfAParticularTest + 1) && testScoreScoredbyStudent > -1) 
                }// end of for (int i = 0; i < totalNumberOfTests; i++)
                
                return (double) totalMarksScored/totalMarksForTest * 100.0;
            }
            catch (IndexOutOfBoundsException e)
            {
                return -1.0;
            }// end of try catch statement
        } catch (ArithmeticException f)
        {
            return -1.0;
        } //  end of try catch statement
    }// end of method getStudentAverage(int indexOfStudent, TestOrganizer testData)

    public static double getClassAverage(TestOrganizer testData)
    {
        double totalStudentAverage = 0.0;
        int studentNumber = testData.getNumberOfStudents();

        for (int i = 0; i < studentNumber; i++)
        {
            totalStudentAverage += getStudentAverage(i, testData);
        }// end of for...

        return (double) totalStudentAverage/studentNumber;
    }// end of 

    public static double getStudentClassDifference(int indexOfStudent, TestOrganizer testData)
    {
        //subtract average of student to average of hte entire class
        return getStudentAverage(indexOfStudent, testData) - getClassAverage(testData);
    }// end of...

    public static double getPercentile(int indexOfStudent,TestOrganizer testData)
    {
        try
        {
            try 
            {
                int[] rankingList = getRankings(testData);
                int numberOfStudents = testData.getNumberOfStudents();
                int studentRanking = 0;

                //compare student index to the ranked student classes 
                for (int i = 0; i < rankingList.length; i++)
                {
                    if (indexOfStudent == rankingList[i]) 
                    {
                        studentRanking = i;
                    }// end of if if (indexOfStudent == rankingList[i]) 
                }// end of for (int i = 0; i < rankingList.length; i++)
                
                //the percentile is 1 - the quotient betweenthe student ranking and number of students
                double percentile =  (1.0 - (double) studentRanking / numberOfStudents) * 100.0;
                return percentile;
            }
            catch (IndexOutOfBoundsException e)
            {
                return -1.0;
            }
        } catch (ArithmeticException f)
        {
            return -1.0;
        }
    }

    //mehods for each test
    public static int[] getRankings(TestOrganizer testData)
    {
        //create two arrays, one to store the index of all the students and one to store their corresponding averages
        int studentNumber = testData.getNumberOfStudents();
        int[] studentIndex = new int[studentNumber];
        double[] studentAverages = new double[studentNumber];
        for (int i = 0; i < studentNumber; i++)
        {
            studentIndex[i] = i;
            studentAverages[i] = getStudentAverage(i, testData);
        }// end of for...

        //use bubble sort to sort though the two arrays from the students with the highest marks to the lowest marks
        boolean sortNotCompleted = true;
        while (sortNotCompleted) 
        {
            sortNotCompleted = false;
            for (int i = 1; i < studentNumber; i++)
            {
                if (studentAverages[i - 1] < studentAverages [i])
                {
                    //swap the two array index if the index with a higher average is detected at the end of the array
                    double temporaryStudentMarkValue = studentAverages[i - 1];
                    studentAverages[i - 1] = studentAverages [i];
                    studentAverages [i] = temporaryStudentMarkValue;

                    int temporaryIndexValue = studentIndex[i - 1];
                    studentIndex[i - 1] = studentIndex[i];
                    studentIndex [i] = temporaryIndexValue;

                    sortNotCompleted = true;
                }// end of if (studentAverages[i - 1] < studentAverages [i])
            }// end of for (int i = 1; i < studentNumber; i++)
        }// end of while (sortNotCompleted) 

        return studentIndex;
    }// end of getRankings(TestOrganizer testData)

    public static double[] getRankingMarks(TestOrganizer testData)
    {
        //create two arrays, one to store the index of all the students and one to store their corresponding averages
        int studentNumber = testData.getNumberOfStudents();
        double[] studentAverages = new double[studentNumber];
        for (int i = 0; i < studentNumber; i++)
        {
            studentAverages[i] = getStudentAverage(i, testData);
        }// end of for...

        //use bubble sort to sort though the two arrays from the students with the highest marks to the lowest marks

        boolean sortNotCompleted = true;
        while (sortNotCompleted) 
        {
            sortNotCompleted = false;
            for (int i = 1; i < studentNumber; i++)
            {
                System.out.println("asd");
                if (studentAverages[i - 1] < studentAverages [i])
                {
                    //swap the two array index if the index with a higher average is detected at the end of the array
                    double temporaryStudentMarkValue = studentAverages[i - 1];
                    studentAverages[i - 1] = studentAverages [i];
                    studentAverages [i] = temporaryStudentMarkValue;
                    sortNotCompleted = true;
                }

            }
        }

        return studentAverages;
    }

    public static double getTestAverage (int indexOfTest, TestOrganizer testData)
    {
        try
        {
            try
            {   
                int totalMarksScored = 0;
                //total possible marks scored by all students is the number of students * the number of marks of the test 
                int totalPossibleMarks = 0;
                int testMark = testData.getTestInformation().get(indexOfTest).getMark();
                int totalNumberOfStudents = testData.getNumberOfStudents();

                //iterate through all the tests of the testData for the particular student to find the total number of marks that the student scored
                for (int i = 0; i < totalNumberOfStudents; i++)
                {
                    int testScoredbyStudent = testData.getTestMarks().get(indexOfTest).get(i);

                    if (testScoredbyStudent < testMark + 1 && testScoredbyStudent > -1) 
                    {
                        //add up all the testMarks and the marks that the student scored
                        totalMarksScored += testData.getTestMarks().get(indexOfTest).get(i);
                        totalPossibleMarks += testMark;
                    }// end of for
                }

                return (double) totalMarksScored/totalPossibleMarks;
            }
            catch (IndexOutOfBoundsException e)
            {
                return -1.0;
            }// end of try catch statment
        }
        catch (ArithmeticException f)
        {
            return -1.0;
        }
    }

    public static double getSpecificTestPercentage(int indexOfStudent, int indexOfTest, TestOrganizer testData)
    {
        try
        {
            try 
            {
                // assign the total maximum points of hte specific test and the speicfic test scored scored by the student
                int studentMark = testData.getTestInformation().get(indexOfTest).getMark();
                int totalTestMark = testData.getTestMarks().get(indexOfTest).get(indexOfStudent);

                return (double) studentMark / totalTestMark;
            }
            catch (IndexOutOfBoundsException e)
            {
                return -1.0;
            }
        } catch (ArithmeticException f)
        {
            return -1.0;
        }
    }
}
