
/**
 * Write a description of class Test here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Test
{
    //instance fields 
    private int day;
    private int month;
    private String name;
    private int mark;
    private int year;

    /**
     * Creates a test with default characteristics.
     */
    public Test()
    {
        day = 1;
        month = 1;
        name = "";
        mark = 0;
        year = 0;
    }// end of constructor test()

    public Test(int day, int month, String name, int mark, int year)
    {
        this.day = day;
        this.month = month;
        this.name = name;
        this.mark = mark;
        this.year = year;
    }// end of constructor test(int day, int month...)

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return year;
    }

    public int getMark (){
        return mark;
    }

    public String getName() {
        return name;
    }

    /*
     * Mutator
     */
    public void setDay(int day) {
        if (day > 0) this.day = day; 
    }

    public void setMonth(int month) {
        if (month > 0) this.month = month; 
    }

    public void setYear(int year ) {
        if (year > 0) this.year = year; 
    }

    public void setMark(int mark) {
        if (mark >= 0) this.mark = mark; 
    }

    public void setName(String name) {
        if (name != null) this.name = name; 
    }
    
}
