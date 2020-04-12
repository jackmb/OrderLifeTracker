import java.util.PriorityQueue;
import java.util.Comparator;

public class EventList{
    public PriorityQueue<Event> event_list;
    public int size (){
        return event_list.size();
    }
    public EventList()  {
        event_list = new PriorityQueue<Event>(100,
                new Comparator<Event>(){
                    public int compare(Event e1, Event e2){
                        if (e1.get_time() < e2.get_time())
                            return -1;
                        if (e1.get_time() > e2.get_time())
                            return 1;
                        return 0;
                    }
                });
    }
    public Event getMin(){
        return event_list.peek();
    }
    public void dequeue(){
        event_list.remove();
    }
    public void enqueue(Event e){
        event_list.add(e);
    }
}

