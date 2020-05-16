import java.io.IOException;

public class Simulator {
    public static void main(String[] args) throws InterruptedException, IOException {

        StockExchange ibm = new StockExchange("LOCATION_OF_IBMCSV.csv", 4); //CHANGE THIS LINE

        ibm.run("ibmout.txt");
        System.out.println("IBM Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\ibmout.txt");

        StockExchange spy = new StockExchange("LOCATION_OF_SPYCSV.csv", 40); //CHANGE THIS LINE

        spy.run("spyout.txt");
        System.out.println("SPY Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\spyout.txt");

        StockExchange vxx = new StockExchange("LOCATION_OF_VXXCSV.csv", 40); //CHANGE THIS LINE

        vxx.run("vxxout.txt");
        System.out.println("VXX Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\vxxout.txt");
    }
}