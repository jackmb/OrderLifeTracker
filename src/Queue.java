import java.util.Vector;
public class Queue{
    private Vector<Event> all_data;

    public Queue(){
        all_data = new Vector<Event>();
    }

    public void enqueue(Event e){
        all_data.addElement(e);
    }

    public Event dequeue(){
        Event res = all_data.elementAt(0);
        all_data.removeElementAt(0);
        return res;
    }

    public Event Get(int i){
        return all_data.elementAt(i);
    }
}