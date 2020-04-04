public class MainClass{
    public static void main(String argv[]){
        Simulator ss = new Simulator();
        ss.FutureEventList = new EventList();
        ss.Customers = new Queue();
        ss.stream=new Rand();
        ss.Clock = 0.0;
        ss.MeanInterArrivalTime = 7.7;
        ss.MeanArrivalTime = 6.21;
        ss.Initialization();
        //Loop until clock is greater than 10000
        while (ss.Clock <= 10000){
            Event evt = ss.FutureEventList.getMin();  //get imminent event
            ss.FutureEventList.dequeue();             //delete the event
            ss.Clock = evt.get_time();                //advance in time
            if (evt.get_type() == Simulator.orderCreation)
                ss.ProcessArrival(evt);
            else
                ss.ProcessDeparture(evt);
        }
        ss.ReportGeneration();
    }
}
