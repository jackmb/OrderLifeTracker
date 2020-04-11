
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;


public class Brokerage {

    public ArrayList<Client> clients = new ArrayList<>();
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

    public static double randomNetworth(int id){
        Random r = new Random();
        if (id >= 0 && id < 3574){ // top 57% , unlimited trades
            double x = 1;
            while (x < 25000.0) x = Math.abs(r.nextGaussian() * 37500.0 + 4166.33);
            return x;
        }else{
            double x = 25000.0;
            while (x >= 25000.0) x = Math.abs(r.nextGaussian() * 12500.0 + 4166.33);
            return x;
        }
    }
    public static void setClients(ArrayList<Client> clients) {
        int i = -1;
        while(i++ < 6270){
            clients.add(new Client(i, randomNetworth(i)));
            //System.out.println(clients.get(i));
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
