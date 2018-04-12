import java.util.ArrayList;

public class Message {
    //Store the value set sent to other processor.
    private ArrayList<String> value;

    //The constructor.
    public Message(ArrayList<String> value) {
        this.value = value;
    }

    //the constructor.
    public Message(String value) {
        this.value = new ArrayList<String>();
        this.value.add(value);
    }

    //Get the value set of message.
    public ArrayList<String> getValue() {
        return value;
    }

}
