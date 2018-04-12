import java.util.ArrayList;

public class Round {
    //The counter of Round.
    private static int roundCounter = 0;

    //The number of Round.
    private int roundNumber;

    //The selected algorithm.
    private int algorithm;

    //Constructor.
    public Round(int algorithm, ArrayList<Processor> allProcessor) {
        roundCounter++;
        roundNumber = roundCounter;
        this.algorithm = algorithm;
    }

    //The run method of Round.
    public boolean run() {
        for(Processor processor: Simulator.allProcessor) {
            if (processor.isFail() == false) {
                if (algorithm == 1) {
                    processor.runFloodSet(roundNumber);
                } else {
                    processor.runOptFloodSet(roundNumber);
                }
            }
        }
        for(Processor processor: Simulator.allProcessor) {
            processor.getIncomingMessage().addAll(processor.getIncomingLink());
            processor.getIncomingLink().clear();
        }
        return isAllDecide();
    }

    //Check if all the no-faulty processors have decisions.
    private boolean isAllDecide() {
        boolean isAllDecided = true;
        for (Processor processor: Simulator.allProcessor) {
            if(processor.isDecide() == false && processor.isFail() == false)
                isAllDecided = false;
        }
        return isAllDecided;
    }

    //Get the roundCounter of Round Class.
    public static int getRoundCounter() {
        return roundCounter;
    }

}
