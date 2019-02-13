

/**
 * TextInterface Class of CS 342 Term Project
 */

public class TextInterface implements UserInterface {

    private Character ioOwner;

    public TextInterface(Character character) {
        ioOwner = character;
    }

    @Override
    public void display(String s) {
        System.out.print(s);
    }


    @Override
    public void displayln(String s) {
        System.out.print(s + "\n");
    }

    @Override
    public String getLine() {
        System.out.print(">>");
        String line = KeyboardScanner.getKeyboardScanner().nextLine();
        if (line.equalsIgnoreCase("GUI1"))
            System.out.println("Sorry, GUI is under construction");
        else if (line.equalsIgnoreCase("GUI2"))
            ioOwner.getIO().selectInterface(2);
        else if (line.equalsIgnoreCase("GUI3"))
            ioOwner.getIO().selectInterface(3);
        else
            return line;

        return line;
    }
}