import java.util.Random;

public class Population {

    public static class Client {
        private int id, numberOfTrades = 0;
        private boolean pdt;
        private double netWorth;

        private Client(int _id) {
            id = _id;
            netWorth = randomNetworth(id);
            pdt = (netWorth <= 25000.00) ? true : false;
        }

        public static double randomNetworth(int id){
            Rand rand = new Rand();
            Random r = new Random((long)rand.next());
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
