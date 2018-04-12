public class Main {

    //The entry of the program.
    public static void main(String[] args) {
        System.out.println("Welcome to Simulator!");
        System.out.println("Please input the algorithm to simulate:\n1. FloodSet\n2. OptFloodSet");
        int algorithmSelected = SystemMethod.inputNumber();
        System.out.println("Please input the number of Processor:");
        int numberOfProcessor = SystemMethod.inputNumber();
        System.out.println("Please input the number of f:");
        int failNumber = SystemMethod.inputNumber();
        System.out.println("Do you want to print all processors decisions in the output?\n1. Yes\n2. No");
        int ifPrintProcessors = SystemMethod.inputNumber();
        Simulator simulator = new Simulator(numberOfProcessor, failNumber, algorithmSelected, ifPrintProcessors);
        simulator.run();
    }
}
