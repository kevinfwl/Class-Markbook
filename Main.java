
/**
 * Write a description of class Main here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Main
{
    public static void main(String[] args) throws Exception
    {
        TestFileManager manager = new TestFileManager();
        MenuDisplay display = new MenuDisplay(manager.loadTestOrganizer(), manager);
        display.mainMenu();
    }
}
