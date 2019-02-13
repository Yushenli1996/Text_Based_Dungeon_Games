public class NonPlayerGUI implements UserInterface {

    public NonPlayerGUI(Character C) {

    }

    public void display(String s) {
        Console.display(s);
    }

    public void displayln(String s) {
        Console.displayln(s);
    }

    @Override
    public String getLine() {
        String s = "";
        return s;
    }
}
