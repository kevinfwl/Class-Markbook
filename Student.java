import java.util.ArrayList;

public class Student
{
    //class fields 
    public static final int DEFAULT_AGE = 6;

    //instance fields
    private int age;
    private String chineseName;
    private String legalName;


    /**
     * Constructs a new student with default characteristics.
     */
    public Student()
    {
        legalName = "";
        chineseName = "";
        age = DEFAULT_AGE;
    }//end of constructor Student()


    /**
     * Constructs a student with specified student.
     * 
     * @param age the age of this student <br> <i> Pre-condition: cannot be lower than 0. </i> 
     * @param chineseName the name of this student in chinese characters 
     * @param grade the day grade of this student <br><i>Pre-condition: </i> cannot be lower than -1. 
     * (A grade of 0 represents that the student is in senior kindergarden and a grade that is -1 represents a student that is in senior kindergarden)
     * @param legalName the legal name of this student 
     * @param ontarioEducationNumber the ontario education number of this student
     */
    public Student(int age, String chineseName,  String legalName)
    {
        if (age >= 0) this.age = age;
        else age = DEFAULT_AGE;

        this.chineseName = chineseName;

        this.legalName = legalName;
    }// end of constructor Student (int age.....)

    /*
     * Acessors
     */
    /**
     * 
     */
    public int getAge() 
    {
        return age;
    }// end of method getAge()

    public String getChineseName() 
    {
        return chineseName;
    }// end of method getChineseName()

    public String getLegalName() 
    {
        return legalName;
    }// end of method getLegalName()
    
    /*
     * Mutators
     */
    /**
     * Sets the age of this student.
     * 
     * @param age the age of this student ()
     */
    public void setAge (int age) 
    {
        if (age >= 0) this.age = age;
    }// end of method setAge()

    /**
     * Sets the age of this student.
     * 
     * @param chineseName the chinese name of this student
     */
    public void setChineseName (String chineseName) 
    {
        this.chineseName = chineseName;
    }// end of method getChineseName()


    /**
     * Sets the legal name of this student.
     * 
     * @param legalName the legal name of this student
     */
    public void setLegalName (String legalName) 
    {
        this.legalName = legalName;
    }// end of method getLegalName()
}//end of class Student
