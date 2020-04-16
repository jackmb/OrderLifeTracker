import java.util.ArrayList;
import java.util.Random;

public class Population {

    public static class Client {
        private int id;
        public int numberOfTrades = 0;
        public boolean pdt;
        public double netWorth;
        public ArrayList<Trade> tradesmade;
        public double initNW = 0;
        private Client(int _id) {
            id = _id;
            netWorth = randomNetworth(id);
            pdt = netWorth <= 25000.00;
            tradesmade = new ArrayList<>();
            initNW = netWorth;
        }

        public static double randomNetworth(int id){
            Random r = new Random();
            if (id >= 0 && id < 62) {
                double x = 1;
                while (x < 5000000.0) x = Math.abs(r.nextGaussian() * 10000000.0 + 3000000.0);
                return x;
            }else if (id >= 62 && id < 3574){ // top 57% , unlimited trades
                double x = 1;
                while (x < 25000.0) x = Math.abs(r.nextGaussian() * 50000.0 + 8333.33);
                return x;
            } else {
                double x = 25000.0;
                while (x >= 25000.0) x = Math.abs(r.nextGaussian() * 12500.0 + 4166.33);
                return x;
            }
        }
        public double getNetworth(){
            return this.netWorth;
        }

        public String toString(){
            return "ID: " + id + " PDT: "+ pdt + " initial net-worth: "+initNW+" final net-worth: "+ getNetworth() + "(" +getNetworth()/initNW *100.0+"%)"+" Number of trades: "+numberOfTrades +" Trades made: "+tradesmade.toString()+"\n";
        }

    }

    static int clientID = 0;
    public static Client createClient() {
        return new Client(clientID++);
    }
}