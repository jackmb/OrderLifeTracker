import java.util.ArrayList;
import java.util.Random;

public class Population {

    public static class Client {
        private int id;
        public int numberOfTrades = 0;
        public boolean pdt;
        public double netWorth;
        public ArrayList<Trade> tradesmade;

        private Client(int _id) {
            id = _id;
            netWorth = randomNetworth(id);
            pdt = netWorth <= 25000.00;
            tradesmade = new ArrayList<>();
        }

        public static double randomNetworth(int id){
            Random r = new Random();
            if (id >= 0 && id < 3574){ // top 57% , unlimited trades
                double x = 1;
                while (x < 25000.0) x = Math.abs(r.nextGaussian() * 37500.0 + 4166.33);
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
            return "ID: " + id + " PDT: "+ pdt + " net-worth: "+ netWorth;
        }

    }

    static int clientID = 0;
    public static Client createClient() {
        return new Client(clientID++);
    }
}