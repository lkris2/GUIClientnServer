import java.io.Serializable;
import java.util.ArrayList;

public class PrivateMessage implements Serializable {

    private static final long serialVersionUID = 1L;
	String privateMessage;
    ArrayList<String> cList, clients;
    boolean bool;

    PrivateMessage(){
        cList = new ArrayList<String>();
        clients = new ArrayList<String>();
        bool = false;
    }
    public String readMessage(){
        return privateMessage;
    }
    public void writeMessage(String message){
        privateMessage = message;
    }






}
