import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Processor {

    //The ID of the processor.
    private int processorID;

    //The default value of decision.
    private String defaultValue;

    //The initial value of decision.
    private String inputValue;

    //The value of decision.
    private String decision = null;

    //The incomingLink of the processor acts as a buffer.
    private ArrayDeque<Message> incomingLink = new ArrayDeque<Message>();

    //All the received messages.
    private ArrayDeque<Message> incomingMessage = new ArrayDeque<Message>();

    //The value of upperBound.
    private int upperBound;

    //To mark if processor has the new value.
    private boolean newInformation = false;

    //To mark if it first receive the new value.
    private boolean isFirstReceiveNewValue = true;

    //To mark if this processor has the possibility of fail.
    private boolean canFail = false;

    //To mark if this processor fail.
    private boolean isFail = false;

    //Store the value of round which canFail processor will fail.
    private int failRound;

    //Store the value set known by the processor.
    private ArrayList<String> W = new ArrayList<String>();

    //The value set of all the possible values.
    private static ArrayList<String> X = new ArrayList<String>();


    //The Constructor.
    public Processor(int processorID, int failNumber) {
        this.processorID = processorID;

        this.defaultValue = "Abort";

        Random random = new Random();
        this.inputValue = X.get(random.nextInt(2));

        upperBound = failNumber;

        W.add(inputValue);
    }

    //Get the X.
    public static ArrayList<String> getX() {
        return X;
    }

    //The action of processor in FloodSet.
    public void runFloodSet(int round) {
        if (round <= upperBound + 1) {
            send(new Message(W), round);
            handleReceiveMessage();
        }

        if (round == upperBound + 1) {
            if(W.size() == 1) {
                decision = W.get(0);
            }
            else {
                decision = defaultValue;
            }
        }
        testCrash(round);
    }

    //The action of processor in OptFloodSet.
    public void runOptFloodSet(int round) {
        if (round <= upperBound + 1) {
            if (round == 1) {
                send(new Message(W), round);
            }
            else if (newInformation == true) {
                String newValue = selectNewValue();
                send(new Message(newValue), round);
                newInformation = false;
            }
            handleReceiveMessageOpt();
            if(W.size()>1 && isFirstReceiveNewValue) {
                newInformation = true;
                isFirstReceiveNewValue = false;
            }
        }
        if (round == upperBound + 1) {
            if(W.size() == 1) {
                decision = W.get(0);
            }
            else {
                decision = defaultValue;
            }
        }

        testCrash(round);
    }

    //Send the message.
    private void send(Message message, int round) {
        int messageNumber;
        if (round != failRound) {
            messageNumber = Simulator.allProcessor.size();
        }
        else {
            Random random = new Random();
            messageNumber = random.nextInt(Simulator.allProcessor.size());
        }
        for(int i=0; i<messageNumber; i++) {
            Processor processor = Simulator.allProcessor.get(i);
            if(this.processorID != processor.processorID) {
                processor.incomingLink.add(message);
                Simulator.messageAmount++;
            }
        }
    }

    //Handle the received message in FloodSet.
    private void handleReceiveMessage() {
        while(!incomingMessage.isEmpty()) {
            Message message = incomingMessage.poll();
            union(message);
        }
    }

    //Implement the union operation.
    private void union(Message message) {
        Iterator itr = message.getValue().iterator();
        while(itr.hasNext()) {
            String receivedValue = (String)itr.next();
            if (!W.contains(receivedValue )) {
                W.add(receivedValue);
            }
        }
    }

    //Handle the received message in OptFloodSet.
    private void handleReceiveMessageOpt() {
        while (!incomingMessage.isEmpty()) {
            Message message = incomingMessage.poll();
            String receivedValue = message.getValue().get(0);
            if (!W.contains(receivedValue)) {
                W.add(receivedValue);
            }
        }
    }

    //Select a new value in W in OptFloodSet.
    public String selectNewValue() {
        String newValue = null;
        while(true) {
            Random random = new Random();
            newValue = W.get(random.nextInt(W.size()));
            if(!newValue.equals(inputValue)) break;
        }
        return newValue;
    }

    //Get all messages in buffer.
    public ArrayDeque<Message> getIncomingLink() {
        return incomingLink;
    }

    //Get all received messages.
    public ArrayDeque<Message> getIncomingMessage() {
        return incomingMessage;
    }

    //Check if this processor has a decision.
    public boolean isDecide() {
        return decision != null;
    }

    //Check if this processor has the possibility of failure.
    public boolean isCanFail() {
        return canFail;
    }

    //Set value of canFail.
    public void setCanFail(boolean canFail) {
        this.canFail = canFail;
    }

    //Check if this processor is failed.
    public boolean isFail() {
        return isFail;
    }

    //Set the number of round which the processor will fail.
    public void setFailRound(int failRound) {
        this.failRound = failRound;
    }

    //Check if the processor crash.
    public void testCrash(int round) {
        if (canFail == true && failRound == round) {
            isFail = true;
        }
    }

    //Get the ID of processor.
    public int getProcessorID() {
        return processorID;
    }


    //Print the decision of processor.
    public void printResult() {
        System.out.print("Pro_"+processorID+":"+decision+ " ");
    }

    //Get the decision of processor.
    public String getDecision() {
        return decision;
    }
}
