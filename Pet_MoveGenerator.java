/**
 * Pet_MoveGenerator Class of CS 342 Term Project
 * Matthew Jankowski
 * netid: mjanko5
 * <p>
 * This class generates a move for a Pet, generating one of three moves:
 * Go, Get, Pass
 */

import java.util.Random;

public class Pet_MoveGenerator implements DecisionMaker {

    public Move getMove(Character C, Place P) {
        C.getIO().displayln("Place: " + P.name());

        //declare variables
        Move randomMove;
        String randomCommand = "";
        String randomArgument = "";

        String[] petsMoves = {"Go", "Get", "Pass", "Get", "Get", "Get"}; //available moves for pet
        int numMoves = petsMoves.length;
        int randomInt = new Random().nextInt(numMoves);
        randomCommand = petsMoves[randomInt];


        //Go
        if (randomCommand.equals("Go")) {
            Direction D = P.getRandomDirection(C);
            randomArgument = D.getText();
            if (D.getTo().getPlaceId() == 1) {
                randomArgument = "";
                C.getIO().displayln(C.getName() + " attempting to exit yet unable, such a bad pet.");
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

        randomMove = new Move(randomCommand, randomArgument); //new move based on aquired fields
        C.getIO().displayln("Entered move: " + randomCommand + " " + randomArgument); //output

        return randomMove;
    }
}
