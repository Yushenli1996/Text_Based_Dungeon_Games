/**
 * Move Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 */
public class Move {

    public enum MoveType {
        //Listed with NPC-compatible moves first
        GO("Go"),
        GET("Get"),
        DROP("Drop"),
        USE("Use"),
        PASS("Pass"), //skip a turn
        EXIT("Exit"),
        INVE("Inve"),
        LOOK("Look"),
        HELP("Help"),
        DANCE("Dance"),
        SLEEP("Sleep"),
        TAME("Tame"),
        NONE("None");

        private String text;

        //constructor
        private MoveType(String inputCommand) {
            text = inputCommand;
        }

        public String toString() {
            return this.text;
        }

        //checks if string command matches with either text of abbreviation
        public boolean match(String inputString) {
            return (inputString.equalsIgnoreCase(text));
        }
    }


    //move data fields
    private MoveType Mov;
    private String argument;

    public Move(String inputMove, String inputArgument) {
        Mov = MoveType.NONE;
        for (MoveType MT : MoveType.values()) {
            if (MT.match(inputMove)) {
                Mov = MT;    //initialize Move
                break;
            }
        }
        argument = inputArgument;
    }

    public MoveType getMov() {
        return Mov;
    }

    public String getArgument() {
        return argument;
    }
}
