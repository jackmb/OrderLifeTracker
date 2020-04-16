import java.io.IOException;
import java.util.Random;

public class Trade {
    int timestamp;
    String symbol;
    double price;
    int shares;
    String exchange;
    boolean tradenb;
    Population.Client client;
    boolean tradeType;

    public Trade() {
        timestamp = 0;
        symbol = "ABC";
        price = 10.00;
        shares = 10;
        exchange = "NYSE";
        tradenb = false;
        tradeType = false;
        //TRUE = BUY
        //FALSE = SELL
    }
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
        return "Time : "+ timestamp+" Symbol: "+symbol+" Price: "+price+" Shares: "+shares+" Exchange: "+exchange+ " TRADE NB: " + tradenb + " Trade type: "+(!tradeType?"Buy":"Sell")+"\n";
    }

}