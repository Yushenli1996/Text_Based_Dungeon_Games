import java.util.Scanner;

/*
 * This is Key class which is sub class of Artifact.
 * iArtifacrUsageVal variable will act as key pattern for this class.
 * Basically it will be used to unlock doors of a place
 * */
public class Key extends Artifact {
    public Key(Scanner scanner, double version) {
        super(scanner, version);
        super.Type = "K";
    }

    /*
     * display the details of Key
     * */
    public void display(Character C) {
        //C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("* Key name : " + zName);
        C.getIO().displayln("  Description : " + zDesctiptipn);
        C.getIO().displayln("  Value : " + iValue + "\t" + "size or weight : " + iMobilityValue + " Kg" + "\t" + "Key pattern : " + iKeyPattern + "\n");
    }

    /*
     * This method will be used to use key
     * This will call Place userKey method.
     * It will unlock the doors, if the ikeyPattern(key patters) is matched
     * */
    public void use(Place place, Character C) {
        if (iKeyPattern == 0) {
            C.getIO().displayln("Item is not a key and cannot be used");
            return;
        } else
            // check if key
            place.useKey(this, C);
    }
}
