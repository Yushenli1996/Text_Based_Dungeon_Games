//Author: Yushen Li and Matt Jankowski
//This is the Console class.

import javax.swing.*;


//Text-based GUI acts similar to a Terminal without getline, only display logs (no input just output)
public class Console extends JFrame {
    //private String history = ""; //start out empty
    private static Console con;
    //private JPanel p1;
    private static JTextArea JTA;
    private final static String newline = "\n";

    private JScrollPane scroller;

    //Constructor
    public Console() {
        super("Game Console");
        //create jframe
        //create jpanel
        //create jtextarea
        JTA = new JTextArea();
        scroller = new JScrollPane(JTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JTA.setBounds(0, 0, 600, 500);
        JTA.setText("Welcome to the console,");
        JTA.append(" where you can find all logs\n");
        add(scroller);


        setSize(600, 500); //Console size
        setVisible(true);
    }

    public static void display(String s) {
        JTA.append(s);
        //Pass the action from program to Console and display it here
    }

    public static void displayln(String s) {
        JTA.append(s + newline);
    }
}