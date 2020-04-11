
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Brokerage {

    public ArrayList<Population.Client> clients = new ArrayList<Population.Client>();
    public ArrayList<Trade> trades = new ArrayList<>();

    public Brokerage(){
        setClients(clients);
    }

    public static void readTrades(ArrayList<Trade> trades){
        String csvFile = "C:\\Users\\Ash\\OneDrive\\GMU\\Fourth Semester\\OR  335\\or335projectdata\\IBM.TradesOnly.012815.csv";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(line.contains("TRADE NB")){
                    Trade t = new Trade(line);
                    trades.add(t);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void setClients(ArrayList<Population.Client> clients) {
        int i = -1;
        while(i++ < 6270){
            clients.add(Population.createClient());
        }

    }
    public static void main (String [] args){
        Brokerage b = new Brokerage();
        setClients(b.clients);
        /*int count = 0, count1 = 0, count2 = 0;
        int a = 0;
        int i = -1;
        while(i++ < 6270){
            if (b.clients.get(i).getNetworth() > 25000.0){
                count++;
            }else{
                count1++;
            }
            if(b.clients.get(i).getNetworth() < 151.00){
                System.out.println(++a);
            }
            count2++;
        }
        System.out.println("\nMore than 25k "+count +" Less than 25k "+count1+ " Total number of clients "+ count2);
         */
        readTrades(b.trades);
        System.out.println(b.trades);
    }
}
