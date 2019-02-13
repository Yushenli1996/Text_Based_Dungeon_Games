//Biren

import java.util.Scanner;

//Enum Class for the possible direction
public class Direction {

    //private enum to store all directions
    private enum DirType {
        NONE("None", "None"), N("North", "N"),
        S("South", "S"), E("East", "E"),
        W("West", "W"), U("Up", "U"),
        D("Down", "D"), NE("Northeast", "NE"),
        NW("Northwest", "NW"), SE("Southeast", "SE"),
        SW("Southwest", "SW"), NNE("North-Northeast", "NNE"),
        NNW("North-Northwest", "NNW"), ENE("East-Northeast", "ENE"),
        WNW("West-Northwest", "WNW"), ESE("East-Southeast", "ESE"),
        WSW("West-Southwest", "WSW"), SSE("South-Southeast", "SSE"),
        SSW("South-Southwest", "SSW");

        private String text;
        private String abbreviation;

        DirType(String text, String abbreviation) {
            this.text = text;
            this.abbreviation = abbreviation;
        }

        public String toString() {
            return this.text;
        }

        public String getAbbreviation() {
            return abbreviation;
        }

        // matching given direction string with current direction. If it matches, return true, else false
        public boolean match(String sDir) {
            if (sDir.equalsIgnoreCase(this.text) || sDir.equalsIgnoreCase(this.abbreviation)) {
                return true;
            } else {
                return false;
            }
        }
    }

    private int directionId; //Id of the place
    private Place from;
    private Place to;
    private boolean dirLock;
    private DirType dir;
    private int lockPattern;
    private int fromPlaceId;
    private int toPlaceId;

	/*constructor for creating direction object and assigning
	to relavent variables*/

    public Direction(Scanner scanner) {
        //read parameters
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            if (line.isEmpty() || line.startsWith("//")) {
                continue;
            }
            String[] data = line.split("\\s+");

            for (int i = 0; i < data.length; i++) {
                switch (i) {
                    case 0:
                        directionId = Integer.parseInt(data[i].trim());
                        break;
                    case 1:
                        fromPlaceId = Integer.parseInt(data[i].trim());
                        break;
                    case 2:
                        dir = DirType.valueOf(data[i].trim());
                        break;
                    case 3:
                        toPlaceId = Integer.parseInt(data[i].trim());
                        break;
                    case 4:
                        lockPattern = Integer.parseInt(data[i].trim());
                        break;
                    default:
                        break;
                }
            }
            break;
        }
        this.dirLock = false;  //Initially set to false
        from = Place.getPlaceByID(fromPlaceId);
        from.addDirection(this);

        //door should be locked for Nowhere
        if (toPlaceId <= 0) {
            toPlaceId = -toPlaceId;
            dirLock = true;
        }
        to = Place.getPlaceByID(toPlaceId);
    }

    //returns direction ID
    public int getID() {
        return directionId;
    }

    // Returns true if string matches the direction
    public boolean match(String s) {
        return dir.match(s);
    }

    //Locks the direction
    public void lock() {
        this.dirLock = true;
    }

    //Unlocks the direction
    public void unlock() {
        this.dirLock = false;
    }

    public boolean isLocked() {
        return dirLock;
    }

    /*If direction is locked, return original location else
     * return the place that we will be going to
     */

    public Place follow(Character C) {
        if (dirLock == true) {
            C.getIO().displayln("Locked. Cannot go " + getText() + ".");
            return from;
        } else {
            C.getIO().displayln("Moving " + getText() + " to " + to.name() + ".");
            return to;
        }
    }

    // If the key pattern of given artifact is matched with lock pattern, toggle the log
    public void useKey(Artifact artifact, Character C) {
        if (artifact.getiKeyPattern() > 0 && artifact.getiKeyPattern() == this.lockPattern) {
            dirLock = !dirLock;
            C.getIO().displayln(artifact.name() + " used successfully");
        }
    }

    //direction string getter ex:"North"
    public String getText() {
        return dir.toString();
    }

    public Place getTo() {
        return to;
    }

    //gets the dirType for a given string.
    public static DirType returnDir(String direction) {
        for (DirType DT : DirType.values()) {
            if (DT.match(direction)) {
                return DT;
            }
        }
        return DirType.NONE;
    }

    public String getDirAbbreviation() {
        return dir.getAbbreviation();
    }
}