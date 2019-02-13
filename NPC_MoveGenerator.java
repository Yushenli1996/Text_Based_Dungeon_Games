/**
 * NPC_MoveGenerator Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 * <p>
 * This class supports the NPC, generating one of four moves:
 * Go, Get, Drop, Use
 */

import java.util.Random;

public class NPC_MoveGenerator implements DecisionMaker {

    public Move getMove(Character C, Place P) {

        //declare variables
//        Move randomMove;
//        String randomCommand = "";
//        String randomArgument = "";
//        int numMoves = Move.MoveType.values().length - 8;
//        int randomInt = new Random().nextInt(numMoves); //generate int randomly
//        int iterator = 0;
//
//        //Get random moveType based on generated integer
//        for (Move.MoveType MT : Move.MoveType.values()) {
//            if (iterator == randomInt) {    //if matches
//                randomCommand = MT.toString();//get movetype
//                break;
//            }
//            iterator++;
//        }

        //declare variables
        Move randomMove;
        String randomCommand = "";
        String randomArgument = "";

        String[] petsMoves = {"Go", "Get", "Drop", "Use", "Pass"}; //available moves for monster (type in more times for bigger frequency
        int numMoves = petsMoves.length;
        int randomInt = new Random().nextInt(numMoves);
        randomCommand = petsMoves[randomInt];

        //Go
        if (randomCommand.equals("Go")) {
            Direction D = P.getRandomDirection(C);
            randomArgument = D.getText();
            if (D.getTo().getPlaceId() == 1) {
                randomArgument = "";
                C.getIO().displayln("NPC attempting to exit yet unable, such a bad npc.");
            }
        }

        //Get
        else if (randomCommand.equals("Get")) {
            Artifact A = P.getRandomArtifact(C); //get random artifact from P
            if (A != null) {
                randomArgument = A.name();
            } else {
                randomArgument = "artifact"; //a trick that will fill in a user message
            }
        }

        //Drop, Use
        else if (randomCommand.equals("Drop") ||
                randomCommand.equals("Use")) {
            Artifact A = C.getRandomArtifact(); //get random artifact from C's inventory
            if (A != null) {
                randomArgument = A.name();
            } else {
                randomArgument = "artifact";
            }
        }

        randomMove = new Move(randomCommand, randomArgument); //new move based on aquired fields
        C.getIO().displayln("Entered move: " + randomCommand + " " + randomArgument); //output

        return randomMove;
    }
}
