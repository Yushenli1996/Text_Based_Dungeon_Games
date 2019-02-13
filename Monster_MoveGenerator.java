/**
 * Monster_MoveGenerator Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 * <p>
 * This class generates a move for a Monster, generating one of three moves:
 * Go, Use, Pass
 */

import java.util.ArrayList;
import java.util.Random;

public class Monster_MoveGenerator implements DecisionMaker {

    public Move getMove(Character C, Place P) {

        //declare variables
        Move randomMove;
        String randomCommand = "";
        String randomArgument = "";
        ArrayList<String> monsterMoves = new ArrayList<String>();

        String use = "Use";
        String go = "Go";
        String pass = "Pass";

        if (C.getID() == 15) { //big boss
            monsterMoves.add(use); //available moves for monster (type in more times for bigger frequency
            monsterMoves.add(use);
            monsterMoves.add(pass);
        } else {
            monsterMoves.add(use);//available moves for monster (type in more times for bigger frequency
            monsterMoves.add(go);
            monsterMoves.add(use);
            monsterMoves.add(go);
            monsterMoves.add(pass);
        }
        int numMoves = monsterMoves.size();
        int randomInt = new Random().nextInt(numMoves);
        randomCommand = monsterMoves.get(randomInt);

        //Go
        if (randomCommand.equals("Go")) {
            Direction D = P.getRandomDirection(C);
            randomArgument = D.getText();
            if (D.getTo().getPlaceId() == 1) {
                randomArgument = "";
                C.getIO().displayln(C.getName() + " attempting to exit yet unable, such a bad monster.");
            }
        }

        //Use
        else if (randomCommand.equals("Use")) {
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
