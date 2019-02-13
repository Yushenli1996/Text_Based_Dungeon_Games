import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Yushens_GUI Class of CS 342 Term Project
 * Yushen Li
 * netid:
 */

public class Yushens_GUI extends JFrame implements UserInterface {

    private final static String newline = "\n";
    private final JFrame mapFrame = new JFrame("Map");
    private final JFrame helpFrame = new JFrame("Help");
    private Panel p1, p2, p3;
    private JButton a, b, c, d, x, f, g;
    private JTextField textField1 = new JTextField();
    private JTextArea JTA, JTA1, JTA2, JTA3;
    private JFrame popup;
    private JScrollPane scroller;
    private boolean myTurn = true;
    private JProgressBar bar;

    public Yushens_GUI(Character C) {  //constructor
        super(C.name + "'s GUI");
        a = new JButton();
        b = new JButton("Go");
        c = new JButton("Map");
        d = new JButton("GUI_1");
        x = new JButton("GUI_2");
        f = new JButton("Terminal");
        g = new JButton("Help");

        popup = new JFrame();

        JPanel p1 = new JPanel();
        JPanel p2 = new JPanel();
        JPanel p3 = new JPanel();

        p1.setLayout(null);
        b.setBounds(730, 10, 50, 30); //Go button
        c.setBounds(675, 50, 100, 100); //Map button
        d.setBounds(0, 100, 100, 30); //GUI1
        x.setBounds(0, 140, 100, 30); // GUI2
        f.setBounds(0, 180, 100, 30);
        g.setBounds(675, 150, 100, 100); //help button

        JLabel label = new JLabel("Character in this location: ");
        label.setBounds(150, 75, 200, 30);
        JLabel label1 = new JLabel("GUI options:");
        label1.setBounds(10, 75, 130, 30);
        JLabel label2 = new JLabel("Character Info: ");
        label2.setBounds(10, 5, 130, 15);
        JLabel label3 = new JLabel("Name: " + C.name);
        label3.setBounds(10, 20, 250, 15);
        JLabel label4 = new JLabel("Health: ");
        label4.setBounds(10, 35, 50, 15);
        JLabel label5 = new JLabel("Current Location: " + C.getCurrentPlace().placeName);
        label5.setBounds(10, 50, 250, 15);
        JLabel label6 = new JLabel("Player's Inventory:");
        label6.setBounds(350, 75, 160, 30);
        JLabel label7 = new JLabel("Logged History:");
        label7.setBounds(150, 270, 130, 30);

        textField1.setBounds(450, 10, 275, 30);

        JTA = new JTextArea();
        JTA.setBounds(350, 100, 300, 400);
        JTA1 = new JTextArea();
        JTA1.setBounds(150, 100, 160, 150);
        JTA2 = new JTextArea();
        JTA2.setBounds(350, 100, 160, 150);
        JTA3 = new JTextArea();
        scroller = new JScrollPane(JTA, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setBounds(150, 300, 550, 250);
        bar = new JProgressBar(); //health progress bar
        bar.setMinimum(0);
        bar.setMaximum(C.Defaulthealth);
        bar.setValue(C.health);
        bar.setBounds(60, 35, 200, 15);
        bar.setStringPainted(true);
        bar.setForeground(Color.green);
        JLabel DPicutre = new JLabel(new ImageIcon(this.getClass().getResource("/Titan.png")));
        DPicutre.setBounds(550, 100, 100, 100);
        p1.add(label);
        p1.add(label1);
        p1.add(label2);
        p1.add(label3);
        p1.add(label4);
        p1.add(label5);
        p1.add(label6);
        p1.add(label7);
        p1.add(textField1);
        p1.add(b);
        p1.add(c);
        p1.add(d);
        p1.add(x);
        p1.add(f);
        p1.add(g);
        p1.add(JTA1);
        p1.add(JTA2);
        p1.add(scroller);
        p1.add(bar);
        p1.add(DPicutre);
        try {
            Thread.sleep(50);
        } catch (InterruptedException f) {
        }

        itemPrinting1(C);

        try {
            Thread.sleep(50);
        } catch (InterruptedException f) {
        }

        itemPrinting2(C);
        setResizable(false);
        add(p1);

        /*The follow is to activate the map button and present a map when users
          click on the map on the GUI. Map would show up as a separate frame with
          an map image on it. Map is located in the game file directory
        */
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (textField1.getText() != null) {
                    textField1.setText(textField1.getText() + "::");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException f) {
                    }
                    label2.setText("Character Info: ");
                    label3.setText("Name: " + C.name);
                    label4.setText("Health: ");
                    bar.setValue(C.health);
                    label5.setText("Current Location: " + C.getCurrentPlace().placeName);
                    label6.setText("Player's Inventory:");
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException f) {
                    }
                    itemPrinting1(C);
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException f) {
                    }
                    itemPrinting2(C);
                }
            }
        });

        c.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel MapPicutre = new JLabel(new ImageIcon(this.getClass().getResource("/map.png")));
                p2.setSize(913, 621);
                p2.add(MapPicutre);
                mapFrame.setSize(913, 621);
                mapFrame.add(p2);
                mapFrame.setVisible(true);
            }
        });

        d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(popup, "Sorry, GUI is under Construction.", "Error", JOptionPane.ERROR_MESSAGE);
                myTurn = true;
            }
        });

        x.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                C.getIO().selectInterface(2); //Matt's GUI, using the same IO from current player//
                myTurn = false;
            }
        });

        f.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                C.getIO().selectInterface(0);
                myTurn = false;
                System.out.println("Switching to text-terminal");
            }
        });

        g.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTA3.setBounds(0, 0, 450, 100);
                //p3.setSize(600, 300);

                //p3.add(JTA3);
                JTA3.append("Hint: find golden key in Room 108 to enter the Treasure Storeroom. \n");
                JTA3.append("There you can find the secret key.\n");
                JTA3.append("Spoiler: The excalibur is in Poison Room 1, but first find the obsidian key!\n");
                JTA3.append("Use the map button to see the game map is very helpful\n");
                JTA3.append("For switching back to gui, type in gui1, gui2, or gui3 to switch GUI.");

                helpFrame.setSize(475, 100);
                helpFrame.add(JTA3);
                helpFrame.setVisible(true);
            }
        });

        JTA1.setEditable(false);
        JTA2.setEditable(false);
        JTA3.setEditable(false);
        //close all frame at once
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 6 - this.getSize().width / 6, dim.height / 6 - this.getSize().height / 6);

        setDefaultCloseOperation(EXIT_ON_CLOSE);  //comment out to close only one GUI
        setSize(800, 600);
        setVisible(false);
    }

    @Override
    public void display(String s) {
        Console.display(s);
        JTA.append(s);
    }

    @Override
    public void displayln(String s) {
        Console.display(s + "\n");
        JTA.append(s + newline);
    }

    @Override
    public String getLine() {
        String textInput = "";
        setVisible(true);
        b.setEnabled(true);
        d.setEnabled(true);
        f.setEnabled(true);
        x.setEnabled(true);
        while (myTurn == true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException f) {

            }
            if (textField1.getText().contains("::")) {
                textInput = textField1.getText();
                textInput = textInput.substring(0, textInput.length() - 2);
                b.setEnabled(false);
                d.setEnabled(false);
                f.setEnabled(false);
                x.setEnabled(false);
                textField1.setText("");
                break;
            }
        }
        return textInput;
    }

    //Show all characters
    public void itemPrinting1(Character C) {
        JTA1.setText("");
        for (int i = 0; i < C.getCurrentPlace().characters.size(); i++) {
            JTA1.append(C.getCurrentPlace().characters.get(i).name + "\n");
        }
    }

    public void itemPrinting2(Character C) {
        JTA2.setText("");
        for (int i = 0; i < C.inventory.size(); i++) {
            JTA2.append(C.inventory.get(i).zName + "\n");
        }
    }
}
