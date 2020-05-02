import java.io.IOException;

public class Simulator {
    public static void main(String[] args) throws InterruptedException, IOException {
        StockExchange ibm = new StockExchange("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\IBMCSV2.csv", 4);
        ibm.run("ibmout.txt");
        System.out.println("IBM Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\");
        StockExchange spy = new StockExchange("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\SPYCSV2.csv", 40);
        spy.run("spyout.txt");
        System.out.println("SPY Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\");
        StockExchange vxx = new StockExchange("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\VXX_Trimmed.csv", 40);
        vxx.run("vxxout.txt");
        System.out.println("VXX Market Day Simulated\n\tSee " + System.getenv("APPDATA") + "\\");
    }
}