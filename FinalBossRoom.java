import java.util.Random;
import java.util.Scanner;

public class FinalBossRoom extends Place {
    public FinalBossRoom(Scanner scanner, double version) {
        super(scanner, version);
        Type = "F";
    }

    public void display(char extent, Character C) { //extent: 'b' basic print; 'f' full print
        C.getIO().displayln("Place name : " + placeName);
        C.getIO().display("Place description : " + placeDescription);

        if (extent == 'b') { //displays just a list of artifacts
            C.getIO().display(" * artifacts here: [ ");
            for (int t = 0; t < artifacts.size(); t++) {
                C.getIO().display(artifacts.get(t).name() + ", ");
            }
            C.getIO().displayln("]");

            String charactersInRoom = "";
            for (Character c : characters) {
                if (c != C) {
                    charactersInRoom = charactersInRoom + c.getName() + ", ";
                }
            }
            C.getIO().displayln(" * other characters here: [ " + charactersInRoom + "]");
        } else if (extent == 'f') { //displays all artifacts and all their information
            C.getIO().displayln("------------------------Artifacts in place---------------------------");
            //print artifact list which stored in place
            for (int t = 0; t < artifacts.size(); t++) {
                artifacts.get(t).display(C);
            }
        }
    }

    @Override
    public void UseSpeicalEffect(Character C) {
        Random rand = new Random();
        int n = rand.nextInt(100);
        if (n > 85) {
            C.getIO().displayln("The Angel has visited this place.\nWarrior " + C.getName() + " is blessed by the angel");
            C.getIO().displayln("Health is fully healed");
            C.health = C.Defaulthealth;
        }
    }
}
