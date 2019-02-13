//Biren

import java.util.Scanner;

public class Artifact {
    protected int iValue; // Value of the artifact
    protected int iMobilityValue; // Mobility value of the artifact
    protected int iKeyPattern; // Key pattern
    protected String zName;
    protected String zDesctiptipn;
    protected int iOriginalLocation; // Initial location of the artifact
    protected int iID;
    protected String Type;
    protected int iArtifactUsageVal;

    /*
     * Constrictor to create artifact from given open file scanner
     * */
    public Artifact(Scanner scanner, double version) {
        Type = "A";

        // Ignore empty lines
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty()) {
                continue;
            }
            iOriginalLocation = Integer.parseInt(line);
            break;
        }
        zName = "";

        //read parameters
        if (scanner.hasNextLine()) {
            String line = Game.getCleanLine(scanner);
            String[] data = line.split("\\s+");
            if (version == 5.1) {
                iID = Integer.parseInt(data[0]);
                iValue = Integer.parseInt(data[1]);
                iMobilityValue = Integer.parseInt(data[2]);
                iKeyPattern = Integer.parseInt(data[3]);
                iArtifactUsageVal = Integer.parseInt(data[4]);
                for (int i = 5; i < data.length; i++) {
                    zName = zName + data[i] + " ";
                }
                zName = zName.trim();
            } else {
                iID = Integer.parseInt(data[0]);
                iValue = Integer.parseInt(data[1]);
                iMobilityValue = Integer.parseInt(data[2]);
                iKeyPattern = Integer.parseInt(data[3]);
                for (int i = 4; i < data.length; i++) {
                    zName = zName + data[i] + " ";
                }
                zName = zName.trim();
            }
        }

        int iDesLineCount = 0;
        if (scanner.hasNextLine()) {
            iDesLineCount = Integer.parseInt(scanner.nextLine());
        }

        zDesctiptipn = "";
        for (; iDesLineCount > 0; iDesLineCount--) {
            zDesctiptipn = zDesctiptipn + scanner.nextLine() + '\n';
        }

        // This artifact is related to a character
        if (iOriginalLocation < 0) {
            int characterID = -iOriginalLocation;
            Character.getCharacterByID(characterID).addToInventory(this);
        }
        //This artifact will be placed in random place
        else if (iOriginalLocation == 0) {
            Place.getRandomePlace().addArtifact(this);
        }
        //or this wull be placed in specified place
        else {
            Place.getPlaceByID(iOriginalLocation).addArtifact(this);
        }
    }

    public int getiKeyPattern() {
        return iKeyPattern;
    }

    public int value() {
        return iValue;
    }

    public int weight() {
        return iMobilityValue;
    }

    public String name() {
        return zName;
    }

    public String description() {
        return zDesctiptipn;
    }

    public void print(Character C) {

        if (Game.getGameVersion() < 5.1) {
            C.getIO().displayln("Artifact ID : " + iID);
            C.getIO().displayln("Original location : " + iOriginalLocation);
            C.getIO().displayln("Value : " + iValue);
            C.getIO().displayln("size or weight : " + iMobilityValue + " Kg");
            C.getIO().displayln("Key pattern : " + iKeyPattern);
            C.getIO().displayln("name : " + zName);
            C.getIO().displayln("Description : " + zDesctiptipn);

        } else {
            C.getIO().displayln("Type : " + Type);
            C.getIO().displayln("Artifact ID : " + iID);
            C.getIO().displayln("Original location : " + iOriginalLocation);
            C.getIO().displayln("Value : " + iValue);
            C.getIO().displayln("size or weight : " + iMobilityValue + " Kg");
            C.getIO().displayln("Key pattern : " + iKeyPattern);
            C.getIO().displayln("Artifact special effect value: " + iArtifactUsageVal);
            C.getIO().displayln("name : " + zName);
            C.getIO().displayln("Description : " + zDesctiptipn);
        }
    }

    public void display(Character C) {
//        C.getIO().displayln("Type : " + Type);
        C.getIO().displayln("* Name : " + zName);
        C.getIO().displayln("  Description : " + zDesctiptipn);
        C.getIO().displayln("  Value : " + iValue + "\t" + "size or weight : " + iMobilityValue +
                " Kg" + "\t");
    }

    /*
     * This method will be used to use key(artifact)
     * This will call Place userKey method
     * */
    public void use(Place place, Character character) {

    }
}