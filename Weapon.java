import java.util.Random;
import java.util.Scanner;

/*
 * This is Weapon class which is sub class of Artifact.
 * iArtifacrUsageVal variable will act as Weapon damage value for this class.
 * Basically it will be used to decrease/damage the health of a player
 * */
public class Weapon extends Artifact {
    public Weapon(Scanner scanner, double version) {
        super(scanner, version);
        super.Type = "W";
    }

    /*
     * Print the details of Weapon
     * */
    public void print(Character C) {
        C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("Weapon ID : " + iID);
        C.getIO().displayln("Weapon Original location : " + iOriginalLocation);
        C.getIO().displayln("Weapon Value : " + iValue);
        C.getIO().displayln("Weapon size or weight : " + iMobilityValue + " Kg");
        C.getIO().displayln("Weapon Damage : " + iArtifactUsageVal);
        C.getIO().displayln("Weapon name : " + zName);
        C.getIO().displayln("Weapon descriptipn : " + zDesctiptipn);
    }

    /*
     * display the details of Weapon
     * */
    public void display(Character C) {
        //C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("* Weapon name : " + zName);
        C.getIO().displayln("  Description : " + zDesctiptipn);
        C.getIO().displayln("  Value : " + iValue + "\t" + "size or weight : " + iMobilityValue + " Kg" + "\t" + "damage power: " + iArtifactUsageVal);

    }

    /*
     * This method will be used to use Weapon
     * this should decrease the players health in current place, by using Weapon.
     *
     * */

    //use = attack
    public void use(Place place, Character C) {
        Random rand = new Random();

        int n = rand.nextInt(2 * (int) ((double) iArtifactUsageVal * 0.15) + 1) + (iArtifactUsageVal - (int) ((double) iArtifactUsageVal * 0.15));
        int x = rand.nextInt(100);
        place.showAllOtherCharactersInCurrentPlace(C);

        if (place.characters.size() > 1) { //if there are other characters in the room beside the monster
            Character c = C;
            if (c instanceof Player) { //if it's a player: ask user
                C.getIO().displayln("Which character do you want to attack?: ");
                String s = C.getIO().getLine();
                s = s.trim();
                c = place.getCharacter(s);
            } else { //if it's not a player, choose victim randomly from the room

                //keep choosing until non-player finds a character other than itself
                while (c == C || c == null) {
                    c = place.getRandomCharacter();
                }
            }

            if (c != null && c.getName() != C.getName()) {
                C.getIO().displayln(C.name + " is attacking " + c.name);
                if (x > 15) { //15% chance of missing (was 25% but that was too much)
                    if (n > iArtifactUsageVal)
                        C.getIO().displayln("Critical Hit");
                    c.health -= n;
                    if (c.health <= 0 && c.getID() != 15) {
                        C.getIO().displayln("Attack successful, " + c.getName() + "'s remaining health is 0/" + c.Defaulthealth);
                        C.getIO().displayln("Unfortunately, " + c.getName() + " died!");
                        c.DropAllAritfacts();
                        if (c instanceof Player) {
                            c.freepet();
                        }
                        C.getCurrentPlace().removeCharacter(c);

                    } else {
                        if (c.health <= 0)
                            C.getIO().displayln("Attack successful, " + c.getName() + "'s remaining health is 0/" + c.Defaulthealth);
                        else
                            C.getIO().displayln("Attack successful, " + c.getName() + "'s remaining health is " + c.health + "/" + c.Defaulthealth);
                    }
                } else
                    C.getIO().displayln("Attack missed");
            } else if (c != null && c.getName().equalsIgnoreCase(C.getName()))
                C.getIO().displayln("Cannot attack yourself");
            else
                C.getIO().displayln(c + " doesn't exist in this place");
        } else
            C.getIO().displayln("No Character to attack in this room");
    }
}

