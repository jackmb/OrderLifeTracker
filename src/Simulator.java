public class Simulator {
    public Simulator(){}
    public final static int orderCreation = 1;
    public final static int departure = 2;
    public final static int departure2 = 3;
    public double Clock, MeanInterArrivalTime, MeanArrivalTime, MeanArrivalTime2, LastEventTime, TotalBusy, SumWaitTime, wightedQueueLength;
    public long NumberOfOrders, Queuelength, TotalCustomers, NumberInService, MaxQueueLength;
    public int counter, counters, s1status, s2status;
    public EventList FutureEventList;
    public Queue Customers;
    public Rand stream;

    public void Initialization(){
        Clock = 0.0;
        NumberInService = 0;
        Queuelength = 0;
        LastEventTime = 0.0;
        TotalBusy = 0.0 ;
        MaxQueueLength = 0 ;
        SumWaitTime = 0.0;
        wightedQueueLength = 0.0;
        s1status = 0;
        s2status = 0;
        MeanArrivalTime = 6.21;
        MeanArrivalTime2 = 8.0;
        //Create First orderCreation Event
        Event evt = new Event(orderCreation, exponential(stream, MeanInterArrivalTime));
        FutureEventList.enqueue(evt);
    }

    public void ProcessArrival(Event evt){
        Customers.enqueue(evt);
        wightedQueueLength += (Clock-LastEventTime) * Queuelength;
        Queuelength++;
        //if there is an idle server, fetch the event, do statistics and put into service
        if(NumberInService < 2) {
            ScheduleDeparture();
        }
        //adjust max Queue Length statistics
        if(MaxQueueLength < Queuelength)
            MaxQueueLength = Queuelength;
        //Schedule the next orderCreation
        Event next_arrival = new Event(orderCreation,Clock+exponential(stream,MeanInterArrivalTime));
        FutureEventList.enqueue(next_arrival);
        LastEventTime = Clock;
    }

    public void ScheduleDeparture(){
        //get the job at the head of the queue
        NumberOfOrders++;
        double lifetime = 0;
        int evtid = -1;
        // Add codes below to determine which server will serve the customer
        // You should first check the status of the first server as it is the faster one and you want to use it first if both servers are idle
        // Then you should properly determine the service time and the event type based on which server is selected
        if(s1status == 0) {
            s1status = 1;
            evtid = departure;
            lifetime = exponential(stream, MeanArrivalTime);
        } else if(s2status == 0) {
            s2status = 1;
            evtid = departure2;
            lifetime = exponential(stream, MeanArrivalTime2);
        }
        Event depart= new Event(evtid,Clock+lifetime);
        FutureEventList.enqueue(depart);
        //get the customers description
        Event finished= (Event) Customers.dequeue();
        double arrive = finished.get_time();
        double wait= Clock-arrive;
        SumWaitTime+=wait;
        NumberInService++;
        Queuelength--;
    }

    public void ProcessDeparture(Event e){
        // Add codes below to determine which server's status should be set to idle
        if(e.get_type() == 2) {
            s1status = 0;
        } else if(e.get_type() == 3) {
            s2status = 0;
        }
        // if there are customers in the queue then schedule the departure of the next one
        wightedQueueLength+=(Clock-LastEventTime)*Queuelength;
        TotalBusy+=(Clock-LastEventTime)*NumberInService;
        NumberInService--;
        if (Queuelength > 0)
            ScheduleDeparture();
        LastEventTime=Clock;
    }

    public static double exponential(Rand rng, double mean) {
        return -mean * Math.log(rng.next());
    }

    public  void ReportGeneration(){
        double RHO=TotalBusy/Clock;
        System.out.println("\n  Server Utilization                         " +RHO );
        double AverageWaittime=SumWaitTime/NumberOfOrders;
        System.out.println("\n  Average Wait Time In Queue                 " +  AverageWaittime);
        double AverageQueueLength=wightedQueueLength/Clock;
        System.out.println("\n  Average Number Of Customers In Queue       " +  AverageQueueLength);
        System.out.println("\n  Maximum Number Of Customers In Queue       " +  MaxQueueLength);
    }
}