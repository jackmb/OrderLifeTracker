import java.util.ArrayList;

public class Order {
    private ArrayList<Event> history;
    private char orderType; // 'i', 'm', or 'l' for "iceberg", "market", or "limit" respectively.
    private double lifetime;
    private boolean dead;

    public Order() {
        history = new ArrayList<>();
        orderType = 'x';
        dead = false;
        lifetime = 0.0;
    }

    public Order(Event conceptionEvent, char _orderType) {
        history = new ArrayList<>(); //originally was ArrayList<Event> but IDE was giving me a warning ¯\_(ツ)_/¯
        this.history.add(conceptionEvent);
        orderType = _orderType;
        dead = false;
        lifetime = 0.0;
    }

    public void recordEvent(Event e) {
        history.add(e);
    }

    public void updateLifetime(double currTime) {
        //lifetime = time of most recent event in history - time of first event in history.
        lifetime = (this.history.get(this.history.size() - 1).get_time()) - this.history.get(0).get_time();
    }

    public void kill(double currTime) {
        history.add(new Event(0, currTime));
        dead = true;
    }
}