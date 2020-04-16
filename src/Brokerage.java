
import com.sun.deploy.cache.BaseLocalApplicationProperties;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;


public class Brokerage {


    public ArrayList<Population.Client> clients = new ArrayList<>();
    private static ArrayList<Trade> trades = new ArrayList<>();
    //private static RiskManager manager = new RiskManager();
    public Brokerage(){
        setClients(clients);
    }

    public static void readTrades(ArrayList<Trade> trades){
        String csvFile = "C:\\Users\\Ash\\OneDrive\\GMU\\Fourth Semester\\OR  335\\or335projectdata\\IBM.TradesOnly.012815.csv";
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            while ((line = br.readLine()) != null) {
                // use comma as separator
                if(line.contains("TRADE NB") || line.contains("TRADE")){
                    Trade t = new Trade(line);
                    //manager.checkRisk(t);
                    trades.add(t);

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static int getClientID(double random){
        return ((int) (random*100000.0)) % 6270;
    }

    public void assignTrade(){
        Rand randomint = new Rand();
        ArrayList<Trade> tradescopy = trades;
        int x = 0, y = 0;
        for(int i = 0; i < tradescopy.size(); i++){

            Trade trade = tradescopy.get(i);

            int clientid = getClientID(randomint.next());
            Population.Client client = clients.get(clientid);

            double tradeDirection = (trade.tradeType)? -1.0: 1.0;
            double commisioncost = Math.max(trade.shares / 10, 10);
            double transactioncost = ((trade.price*trade.shares)+commisioncost) * tradeDirection;

            if(trade.tradenb){
                boolean tradeFilled = false;

                while(!tradeFilled){

                    if(!client.pdt){
                        if( Math.abs(transactioncost) < client.netWorth) {
                            client.netWorth = client.netWorth - transactioncost;

                            if (client.netWorth < 25000.0) {
                                client.pdt = true;
                            }else{
                                client.pdt = false;
                            }
                            client.numberOfTrades++;
                            client.tradesmade.add(trade);
                            //tradescopy.remove(clientid);
                            tradeFilled = true;
                            //System.out.println(client.toString());

                            clients.set(clientid, client);
                            if(clientid == 4269) {
                                System.out.println(client);
                            }
                            //System.out.println("Number of trades filled: "+ ++x +" not filled: "+y);
                            break;
                        }else{
                            // skip to the next client
                            clientid = getClientID(randomint.next());
                            //System.out.println(clientid);
                            client = clients.get(clientid);
                            //System.out.println("Number of trades filled: "+ x+" not filled: "+ ++y);
                        }
                    }else{
                        if(client.numberOfTrades < 3){
                            if( Math.abs(transactioncost) < client.netWorth) {
                                client.netWorth = client.netWorth - transactioncost;
                                if (client.netWorth > 25000.0) {
                                    client.pdt = false;
                                }else{
                                    client.pdt = true;
                                }
                                client.numberOfTrades++;
                                client.tradesmade.add(trade);
                                //tradescopy.remove(clientid);
                                //tradeFilled = true;

                                clients.set(clientid, client);
                                //System.out.println("Number of trades filled: "+ ++x+" not filled: "+y);
                                if(clientid == 4269) {
                                    System.out.println(client);
                                }
                                break;
                            }else{
                                // skip to the next client
                                clientid = getClientID(randomint.next());
                                //System.out.println(clientid);
                                client = clients.get(clientid);
                                //System.out.println("Number of trades filled: "+x+" not filled: "+ ++y);
                            }
                        }else{
                            // skip to the next client
                            clientid = getClientID(randomint.next());
                            //System.out.println(clientid);
                            client = clients.get(clientid);
                            //System.out.println("Number of trades filled: "+x+" not filled: "+ ++y);
                        }
                    }
                    if(clientid == 4269) {
                        System.out.println(client);
                    }
                    //clientid = getClientID(randomint.next());
                    //System.out.println(clientid);
                    //client = clients.get(clientid);
                    //break;
                }
            }else{
                if(trade.tradeType){
                    if(client.netWorth < transactioncost){
                        //System.out.println("Trade is of type TRADE and not enough money.");
                    }else{
                        //System.out.println("Price Time Priority, the market price has changed, order cannot be filled.");
                    }
                }else{
                    //System.out.println("Price Time Priority, the market price has changed, order cannot be filled.");
                }
            }

        }
    }

    public static void setClients(ArrayList<Population.Client> clients) {
        int i = -1;
        while(i++ < 6270){
            clients.add(Population.createClient());
            //System.out.println(clients.get(i));
        }

    }
    public static void main (String [] args){
        Brokerage b = new Brokerage();
        //setClients(b.clients);

        /*int count = 0, count1 = 0, count2 = 0;
        int a = 0;
        int i = -1;

        while(i++ < 6270){
            if (b.clients.get(i).getNetworth() > 25000.0){
                count++;
                a+=b.clients.get(i).getNetworth();
            }else{
                count1++;
            }
            //if(b.clients.get(i).getNetworth() < 151.00){
            //    System.out.println(++a);
            //}

            count2++;
        }
        System.out.println("\nMore than 25k "+count +" Less than 25k "+count1+ " Total number of clients "+ count2);
        System.out.println(a/count);*/
        readTrades(b.trades);
        b.assignTrade();
        //System.out.println(b.clients);
        //System.out.println(Math.max(b.clients.));
        //assignTrade();
    }
}
