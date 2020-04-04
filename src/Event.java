public class Event{
    private double time ;
    private int type;
    /*
    type 0 = kill event
    type 1 = conception event

     */
    public Event ( int _type , double _time ){
        type = _type ;
        time = _time ;
    }

    public double get_time(){
        return time ;
    }

    public int get_type (){
        return type;
    }

    public Event copy() { return new Event(this.get_type(), this.get_time()); }
}