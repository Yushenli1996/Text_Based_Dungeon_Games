/**
 * IO Class of CS 342 Term Project
 */

//Middleman
public class IO {
    public static final int TEXT = 0;  //TextInterface
    public static final int GUI_1 = 1; //Biren's
    public static final int GUI_2 = 2; //Yushen's
    public static final int GUI_3 = 3; //Matt's
    public static final int GUI_4 = 4;
    private UserInterface implementor;
    private Character ioOwner;

    //constructor
    public IO(Character c) {
        ioOwner = c;
        if (c instanceof Player)
            selectInterface(3); //Player
        else
            selectInterface(4); //Non-player
    }

    //display(): prints out a string; used instead of C.getIO().display();
    public void display(String s) {
        implementor.display(s);
        //Console.display(s);
    }

    //displayln(): prints out a string; used instead of C.getIO().displayln();
    public void displayln(String s) {
        implementor.displayln(s);
        //Console.displayln(s);
    }

    public UserInterface returnImplementor() {
        return implementor;
    }

    public String getLine() {
        //get override by other anyone's GUI getline or TextInterface getline()
        return implementor.getLine();
    }

    //switch between the 4 interfaces
    public void selectInterface(int interfaceNum) { //When user switch gui, it should create a new GUI and delete the old one.

        switch (interfaceNum) {
            case TEXT:
                implementor = new TextInterface(ioOwner);
                break;

            case GUI_1:
                implementor = new Birens_GUI(ioOwner);
                break;

            case GUI_2:
                //access Matts_GUI
                implementor = new Matts_GUI(ioOwner);
                break;

            case GUI_3:
                implementor = new Yushens_GUI(ioOwner);
                break;

            case GUI_4:
                implementor = new NonPlayerGUI(ioOwner);

            default:
                //implementor = new TextInterface(ioOwner);
                break;
        }
    }
}