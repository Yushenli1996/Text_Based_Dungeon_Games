import java.util.Scanner;


/*
 * This is Medicine class which is sub class of Artifact.
 * iArtifacrUsageVal variable will act as Medicine healing power for this class.
 * Basically it will be used to increase the health of a player
 * */
public class Medicine extends Artifact {
    public Medicine(Scanner scanner, double version) {
        super(scanner, version);
        super.Type = "M";
    }


    /*
     * Display the details of Medicine
     * */
    public void display(Character C) {
        //C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("* Medicine name : " + zName);
        C.getIO().displayln("  Description : " + zDesctiptipn);
        C.getIO().displayln("  Value : " + iValue + "\t" + "size or weight : " + iMobilityValue + " Kg" + "\t" + "Healing power : " + iArtifactUsageVal + "\n");
    }

    /*
     * This method will be used to use Medicine
     * It must increase the helth of a player by using Medicine,
     * Need addition parameter such as Player for this method
     * */
    public void use(Place place, Character character) {
        //increase player health
        if (character.health == 100)
            character.getIO().displayln("Health is full, let's save the precious " + zName + " for later.");
        else {
            character.health += iArtifactUsageVal;
            if (character.health > 100)
                character.health = 100;
            character.getIO().displayln("Sipping up the " + zName + ". " + character.name + "'s health is boosted to " + character.health + "/100.");
            character.removeFromInventory(this); //medicine is taken, so remove from inventory
        }
    }
}
