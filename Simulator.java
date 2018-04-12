import java.util.ArrayList;
import java.util.Random;

public class Simulator {
    //Store all the processors.
    public static ArrayList<Processor> allProcessor = new ArrayList<Processor>();

    //The value of input processor amount.
    private int processorNumber;

    //The value of input f.
    private int failNumber;

    //The selected algorithm.
    private int algorithm;

    //The amount of messages used in the selected algorithm.
    public static int messageAmount;

    //The amount of actual failed processors.
    private int numberOfFailedProcessor;

    //The option of
    private int ifPrintProcessors;

    //Constructor
    public Simulator(int processorNumber, int failNumber, int algorithm ,int ifPrintProcessors) {
        this.processorNumber = processorNumber;
        this.failNumber = failNumber;
        this.algorithm = algorithm;
        this.ifPrintProcessors = ifPrintProcessors;
    }

    //The run method of the simulator.
    public void run() {
        initialise();
        long startTime = System.currentTimeMillis();
        runAlgorithm(algorithm);
        long endTime = System.currentTimeMillis();
        System.out.println("\nTime used: " + (endTime-startTime)/1000.0 + " s");
        printResult();
    }

    //Initialise the configuration of simulator.
    private void initialise() {
        setXValue();
        for (int i = 0; i < processorNumber; i++) {
            Processor processor = new Processor(i + 1, failNumber);
            allProcessor.add(processor);
        }

        Random random = new Random();
        int failAmount = random.nextInt(failNumber + 1);
        while (failAmount > 0) {
            Processor processor = allProcessor.get(random.nextInt(allProcessor.size()));
            if (processor.isCanFail() == false) {
                processor.setCanFail(true);
                processor.setFailRound(random.nextInt(failNumber) + 1);
                numberOfFailedProcessor++;
                failAmount--;
            }
        }
    }

    //Set the value range of X.
    private void setXValue() {
        Processor.getX().add("Commit");
        Processor.getX().add("Abort");
    }

    //Run the selected algorithm.
    private void runAlgorithm(int algorithm) {
        boolean isTerminated = false;
        while (!isTerminated) {
            Round round = new Round(algorithm, allProcessor);
            isTerminated = round.run();
        }
    }

    //Print the result of simulator.
    public void printResult() {
        System.out.println("\nSimulator results:");
        System.out.println("Number of Processors: " + processorNumber);
        System.out.println("Number of Decided Processors: " + getDecidedProcessorNumber());
        System.out.println("Number of Failed Processors: " + numberOfFailedProcessor);
        System.out.println("Number of Rounds: " + Round.getRoundCounter());
        System.out.println("Number of Messages: " + messageAmount);
        System.out.println("Satisfy Agreement: " + isAgree());
        System.out.println("Agreement Decision: " + getDecision());
        if (ifPrintProcessors == 1) {
            printFailNode();
            printProcessorDecision();
        }
    }

    //Print the information of failed processors.
    public void printFailNode() {
        System.out.println("\nFail processor:");
        for (Processor processor : Simulator.allProcessor) {
            if (processor.isFail() == true) {
                System.out.print("Pro_" + processor.getProcessorID() + " ");
            }
        }
    }

    //Print the no-faulty processors decisions.
    public void printProcessorDecision() {
        System.out.println("\n\nProcessor decision:");
        for (Processor processor : allProcessor) {
            processor.printResult();
        }
    }

    //Check if all the no-faulty processors reach an agreement.
    public boolean isAgree() {
        boolean isAgree = false;
        ArrayList<String> decision = new ArrayList<String>();
        for (Processor processor : allProcessor) {
            if (!processor.isFail()) {
                if (!decision.contains(processor.getDecision())) {
                    decision.add(processor.getDecision());
                }
            }
        }
        if (decision.size() == 1) {
            isAgree = true;
        }
        return isAgree;
    }

    //Get the final agreed decision.
    public String getDecision() {
        ArrayList<String> decision = new ArrayList<String>();
        for (Processor processor : allProcessor) {
            if (!processor.isFail()) {
                if (!decision.contains(processor.getDecision())) {
                    decision.add(processor.getDecision());
                }
            }
        }
        if (decision.size() == 1) {
            return decision.get(0);
        } else {
            return null;
        }
    }

    //Get the amount of processors which have the decisions.
    public int getDecidedProcessorNumber() {
        int amount = 0;
        for (Processor processor : allProcessor) {
            if (!processor.isFail() && processor.isDecide()) {
                amount++;
            }
        }
        return amount;
    }

}
