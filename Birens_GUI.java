
import javax.swing.*;
import java.awt.*;

/**
 * Birens_GUI Class of CS 342 Term Project
 * Biren Patel
 * netid:
 */

/**
 * Birens_GUI Class of CS 342 Term Project
 * Biren Patel
 * netid:
 */

public class Birens_GUI extends JFrame implements UserInterface {

    JTextArea ta; // Text Area for output
    JProgressBar bar; // progress bar for health
    JLabel labelPlaceName; //label for Place Name
    JTextArea InventoryTA; // Text Area for Inventory
    String input;
    boolean visible = false;
    Character character;
    boolean isCreated = false;//idetifier for UI created or not

    public Birens_GUI(Character c) {
        character = c; // Set Character for this UI
        JPanel leftPanel = new JPanel(new GridLayout(0, 1)); //JPanel with grid Layout for  left side 0 rows, 1 column
        JPanel rightPanel = new JPanel(new GridLayout(0, 1)); //JPanel with grid Layout for  right side 0 rows 1 column
        getContentPane().add(leftPanel, "West"); //added to the main contentPane
        getContentPane().add(rightPanel, "East"); //added to the main contentPane

        JPanel rightUpPanel = new JPanel(new FlowLayout()); //JPanel with flow Layout for  right side
        JPanel rightDownPanel = new JPanel(new FlowLayout()); //JPanel with flow Layout for  right side

        rightPanel.add(rightUpPanel, "North");//added to the right side panel
        rightPanel.add(rightDownPanel, "South");///added to the right side panel

        InventoryTA = new JTextArea(8, 20); //Text Area for Inventory initialisation
        JLabel inventoryLabel = new JLabel("Inventory");

        rightUpPanel.add(inventoryLabel); //added inventory components to right up panel
        rightUpPanel.add(new JScrollPane(InventoryTA));

        setSize(500, 500);

        JButton btn = new JButton("Done");
        ta = new JTextArea(20, 40); // //Text Area for output initialisation
        ta.setLineWrap(true);
        JLabel labelOut = new JLabel("Output");
        rightDownPanel.add(labelOut); //added output components right down panel
        rightDownPanel.add(new JScrollPane(ta));
//	    rightDownPanel.add(btn);

        JPanel leftUpPanel = new JPanel(new FlowLayout()); //JPanel with flow Layout for  left side
        JPanel leftDownPanel = new JPanel(new FlowLayout());//JPanel with flow Layout for  left side

        leftPanel.add(leftUpPanel, "North");
        leftPanel.add(leftDownPanel, "South");

        JLabel labelHealth = new JLabel("Health");

        bar = new JProgressBar(); //health progress bar
        bar.setMinimum(0);
        bar.setMaximum(100);
        bar.setValue(c.health);
        bar.setSize(200, 50);
        bar.setStringPainted(true);
        bar.setForeground(Color.green);

        leftUpPanel.add(labelHealth); // adding health components
        leftUpPanel.add(bar);

        JLabel labelPlace = new JLabel("Place");
        labelPlaceName = new JLabel(c.currentPlace.placeName);

        leftDownPanel.add(labelPlace); // adding place components
        leftDownPanel.add(labelPlaceName);
        displayInventory();
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, rightPanel);
        getContentPane().add(splitPane);

        setTitle(c.name); // setting character name as Frame name
        pack();
        setAlwaysOnTop(false);
        setVisible(true);
        //display("onr");
        // display("teo");
        //display(getLine());
    }

    @Override
    public void display(String s) {
        // TODO Auto-generated method stub
        ta.append(s);
    }

    public void displayln(String s) {
        Console.display(s + "\n");
    }

    @Override
    public String getLine() {
        JOptionPane optionPane = new JOptionPane("Enter Move Here" //receiving the input from input dialog box.
                , JOptionPane.PLAIN_MESSAGE
                , JOptionPane.DEFAULT_OPTION
                , null, null, "");
        optionPane.setWantsInput(true);
        JDialog dialog = optionPane.createDialog(null, "Enter Value");
        dialog.setLocation(500, 300);
        dialog.setAlwaysOnTop(false);
        dialog.setVisible(true);
        return optionPane.getInputValue().toString()/*JOptionPane.showInputDialog(this,"Enter Move")*/;
    }

    public void setUIVisible(boolean visible) {
        if (!visible && isCreated) { // given oppertunity for user to close or keep the UI
            setAlwaysOnTop(false);
            int response = JOptionPane.showConfirmDialog(null, "Do you want to close this UI? If you click 'NO' you have to close UI by manully.", "Confirm",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (response == JOptionPane.NO_OPTION) {
                display("No Selected.If you want to close this please click the close button top right corner.");
            } else if (response == JOptionPane.YES_OPTION) {
                setVisible(false);
            } else if (response == JOptionPane.CLOSED_OPTION) {
                setVisible(false);
            }

        } else {
            setAlwaysOnTop(true);
            setVisible(visible);
        }
        isCreated = true; // identify that UI is created
        //setVisible(visible);
        //setAlwaysOnTop(false);
    }

    public void updateDetails() {
        bar.setValue(character.health);//update health
        labelPlaceName.setText(character.currentPlace.placeName); // update place
        displayInventory();
    }

    public void displayInventory() {
        InventoryTA.setText("");
        for (Artifact a : character.inventory) {
            InventoryTA.append(a.name() + "\n"); // update inventory
        }
    }

//    @Override
//    public void setCharacter(Character c) {
//        // TODO Auto-generated method stub
//        this.character = c;
//        updateDetails();
//    }


}