/**
 * DecisionMaker interface of CS 342 Term Project
 */

public interface DecisionMaker {
    /*
     * This interface has only one method, getMove
     * which is implemented in AI and UI classes
     * */
    public Move getMove(Character character, Place place);
}



