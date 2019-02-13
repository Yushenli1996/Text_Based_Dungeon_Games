import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Matts_GUI Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */

public class Matts_GUI extends JFrame implements UserInterface {

    private final String newline = "\n";

    //declare panels
    private JPanel panel; //window (background)
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;

    //declare components
    private JLabel enterLabel;
    private JButton yushensButton; //button
    private JButton birensButton; //button
    private JButton terminalButton; //button
    private JButton submitButton;
    private JTextField inputBox;
    private JTextArea textArea;
    private JTextArea inventory;
    private JTextArea pets;
    private JLabel inventoryLabel;
    private JLabel petsLabel;
    private JLabel artifactsLabel;
    private JTextArea artifacts;
    private JLabel movesLabel;
    private JComboBox moves;
    private JLabel label;
    private JFrame popup;
    private JOptionPane op;
    private JScrollPane scroller;

    private boolean myTurn = true;

    //consturctor: set up GUI
    public Matts_GUI(Character C) {

        super(C.name);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation(screenSize.width / 2 - this.getPreferredSize().width / 2 - 250, screenSize.height / 2 - this.getPreferredSize().height / 2 - 200); //set location of window on screen
        toBack(); //?
        setSize(920, 500); //set window size
        setResizable(false);

        //panel with gridlayout components
        panel = new JPanel(new GridLayout(2, 2, 5, 5));
        panel_1 = new JPanel();
        panel_2 = new JPanel();
        panel_3 = new JPanel();
        textArea = new JTextArea();
        textArea.setEditable(false);

        //define colors
        Color reddish = new Color(204, 204, 255);
        Color red = new Color(102, 0, 0);
        //set colors
        panel.setBackground(Color.BLACK);
        panel_1.setBackground(red);
        panel_2.setBackground(red);
        panel_3.setBackground(reddish);
        textArea.setBackground(reddish);


        //panel_1:
        enterLabel = new JLabel("Enter move: ");
        enterLabel.setForeground(reddish);
        inputBox = new JTextField();
        inputBox.setPreferredSize(new Dimension(280, 30));
        submitButton = new JButton("Enter");

        //add scroll bar to text area
        scroller = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        movesLabel = new JLabel("Moves catalog: ");
        movesLabel.setForeground(reddish);
        //make string array which will display in JComboBox
        String[] movesArray = {" ", "Go <direction>", "Get <artifact>", "Drop <artifact>", "Use <artifact>",
                "Tame <pet>", "Look (freebie)", "Sleep", "Dance", "Pass", "Help", "Exit"};

        moves = new JComboBox(movesArray);
        movesLabel.setBounds(10, 5, 130, 15); //set dimentions
        moves.setBounds(111, 2, 130, 15);

        //add components to panel_1
        panel_1.add(enterLabel);
        panel_1.add(inputBox);
        panel_1.add(submitButton);
        panel_1.add(movesLabel);
        panel_1.add(moves);


        //panel_2:
        label = new JLabel("Switch Interface:");
        label.setForeground(reddish);
        label.setBounds(10, 200, 200, 15);
        yushensButton = new JButton("Yushen's GUI");
        yushensButton.addActionListener(new ActionListener() { //button listener ->
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                C.getIO().selectInterface(3);
                myTurn = false;
            }
        });

        birensButton = new JButton("Biren's GUI");
        popup = new JFrame(); //for biren's GUI message
        op = new JOptionPane();
        popup.add(op);

        birensButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(popup, "Sorry, GUI not available here.");
                myTurn = true;
            }
        });

        terminalButton = new JButton("Terminal");
        terminalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                C.getIO().selectInterface(0);
                myTurn = false;
            }
        });

        //add to 2:
        panel_2.add(label);
        panel_2.add(yushensButton);
        panel_2.add(birensButton);
        panel_2.add(terminalButton);


        //panel_3:
        panel_3.setLayout(null);

        //text area 1
        inventoryLabel = new JLabel("inventory: ");
        inventory = new JTextArea();
        inventory.setBackground(reddish);
        inventoryLabel.setBounds(0, 0, 75, 15);
        inventory.setBounds(0, 20, 159, 300);
        for (int i = 0; i < C.inventory.size(); i++) {
            inventory.append(C.inventory.get(i).zName + "\n");
        }
        inventory.setEditable(false);

        //text area 2

        petsLabel = new JLabel("pets: ");
        petsLabel.setBounds(159, 0, 75, 15);
        pets = new JTextArea();
        pets.setBackground(reddish);
        pets.setBounds(159, 20, 159, 300);

        Player P = (Player) C;
        for (int i = 0; i < P.getPets().size(); i++) {
            System.out.println("Here");
            pets.append(P.getPets().get(i).getName() + "\n");
        }
        pets.setEditable(false);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inputBox.getText() != null) {
                    inputBox.setText(inputBox.getText() + "::");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException f) {
                    }
                    //reprint again
                    inventory.setText("");
                    for (int i = 0; i < C.inventory.size(); i++) {
                        inventory.append(C.inventory.get(i).zName + "\n");
                    }
                    pets.setText("");
                    for (int i = 0; i < P.getPets().size(); i++) {
                        pets.append(P.getPets().get(i).getName() + "\n");
                    }
                    yushensButton.setEnabled(false);
                    birensButton.setEnabled(false);
                    terminalButton.setEnabled(false);
                }
            }
        });

        panel_3.add(inventoryLabel);
        panel_3.add(inventory);
        panel_3.add(petsLabel);
        panel_3.add(pets);


        //add everything to big panel
        panel.add(panel_1);
        panel.add(panel_2);
        panel.add(panel_3);
        panel.add(scroller);

        add(panel);


        //other
        setVisible(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }


    @Override
    public void display(String s) {
        Console.display(s);
        textArea.append(s);
    }

    @Override
    public void displayln(String s) {
        Console.display(s + "\n");
        textArea.append(s + newline);
    }

    @Override
    public String getLine() {
        String textInput = "";
        setVisible(true);

        yushensButton.setEnabled(true);
        birensButton.setEnabled(true);
        terminalButton.setEnabled(true);
        submitButton.setEnabled(true);
        while (myTurn == true) {
            try {
                Thread.sleep(50);
            } catch (InterruptedException f) {
            }
            if (inputBox.getText().contains("::")) {
                textInput = inputBox.getText();
                textInput = textInput.substring(0, textInput.length() - 2);
                submitButton.setEnabled(false);
                inputBox.setText("");
                break;
            }
        }
        return textInput;
    }
}