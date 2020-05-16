import java.io.IOException;

public class Simulator {
    public static void main(String[] args) throws InterruptedException, IOException {

        StockExchange ibm = new StockExchange("C:\\This\\Is\\An\\Example\\Directory\\.csv", 4); //CHANGE THIS LINE

        ibm.run("ibmout.txt");
        System.out.println("IBM Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\ibmout.txt");

        StockExchange spy = new StockExchange("C:\\This\\Is\\An\\Example\\Directory\\SPYCSV.csv", 40); //CHANGE THIS LINE

        spy.run("spyout.txt");
        System.out.println("SPY Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\spyout.txt");

        StockExchange vxx = new StockExchange("C:\\This\\Is\\An\\Example\\Directory\\VXXCSV.csv", 40); //CHANGE THIS LINE

        vxx.run("vxxout.txt");
        System.out.println("VXX Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\vxxout.txt");
    }
}