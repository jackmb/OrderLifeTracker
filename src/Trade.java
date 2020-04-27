import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Trade {
    int timestamp;
    String symbol;
    double price;
    int shares;
    String exchange;
    boolean tradenb;
    Population.Client client;
    boolean tradeType;
    long time;
    long timeelapsed;

    public Trade() {
        timestamp = 0;
        symbol = "ABC";
        price = 10.00;
        shares = 10;
        exchange = "NYSE";
        tradenb = false;
        tradeType = false;
        client = null;
        time = 0;
        //TRUE = BUY
        //FALSE = SELL

    }
    public void startTimer(){
        time = System.nanoTime();
    }
    public void endTimer(){
        //long endtime = System.nanoTime();
        //timeelapsed = endtime - time;
    }
    public void endTimer(int popSize, Population.Client client) {
        long endtime = System.nanoTime(), offset;
        if(client.getID() < (double)popSize * 0.01) {
            offset = 500;
        }else {
            offset = 1000;
        }
        timeelapsed = endtime - time + (offset);
    }
    //public void executionTime(long t){
    //    timeelapsed = t;
    //}
    private boolean buyorsell(){
        Random r = new Random();
        return r.nextGaussian() < 0;
    }
    public Trade(String line) throws IOException {
        String[] data = line.replace(" ", "").split(",");
        for(int i = 0; i < data.length; i++) {
            if(data[i].equals("")) {
                throw new IOException("improper row passed as Trade parameter\n");
            }
        }
        timestamp = Integer.parseInt(data[0].replaceAll(":", "").replace(".", ""));
        //System.out.println(timestamp);
        String temp = data[1];
        //System.out.println(temp);
        tradenb = temp.contains("TRADENB");
        //System.out.println(tradenb);
        symbol = data[2];
        //System.out.println(symbol);
        price = Double.parseDouble(data[3]);
        //System.out.println(price);
        shares = Integer.parseInt(data[4]);
        //System.out.println(shares);
        exchange = data[5];
        //System.out.println(exchange);
        tradeType = buyorsell();
        //System.out.println(tradeType);
    }
    public String toString(){
        if(client != null)
            return "Time : "+ timestamp+" Symbol: "+symbol+" Price: "+price+" Shares: "+shares+" Exchange: "+exchange+ " TRADE NB: " + tradenb
                    + " Trade type: "+(!tradeType?"Buy":"Sell")+ "  Client ID: "+client.getID()+" Time Elapsed: "+ timeelapsed+"\n";
        else
            //return "This trades cost: " + (shares*price);
            return "null";
    }

}