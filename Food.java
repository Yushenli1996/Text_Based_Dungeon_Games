import java.util.Scanner;

/*
 * This is Food class which is sub class of Artifact.
 * iArtifacrUsageVal variable will act as food strength for this class.
 * Basically it will be used to increase the health of a player
 * */
public class Food extends Artifact {
    public Food(Scanner scanner, double version) {
        super(scanner, version);
        super.Type = "F";
    }

    /*
     * Display the details of Food
     * */
    public void display(Character C) {
//        C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("* Food name : " + zName);
        C.getIO().displayln("  Description : " + zDesctiptipn);
        C.getIO().displayln("  Value : " + iValue + "\t" + "size or weight : " + iMobilityValue + " Kg" + "\t" + "Strength : " + iArtifactUsageVal + "\n");
    }

    /*
     * This method will be used to use Food
     * this should increase the player health by using food.
     * Need addition parameter such as Player
     * */
    public void use(Place place, Character character) {
        //increase player health
        if (character.health == 100)
            character.getIO().displayln("Health is full, let's save the " + zName + " for later.");
        else {
            character.health += iArtifactUsageVal;
            if (character.health > 100)
                character.health = 100;
            character.getIO().displayln("Eating the " + zName + ". " + character.name + "'s health is raised to " + character.health + "/100.");
            character.removeFromInventory(this); //food is eaten, so remove from inventory
        }
    }
}
