import java.util.Random;

public class Population {

    public static class Client {
        private int id, trades;
        private boolean pdt;
        private double netWorth;

        private Client(int _id) {
            id = _id;
            netWorth = randomNetworth(id);
            pdt = (netWorth <= 25000.00) ? true : false;
        }

        public static double randomNetworth(int id){
            Random r = new Random();
            if (id >= 0 && id < 3574){ // top 57% , unlimited trades
                double x = 1;
                while (x < 25000.0) x = Math.abs(r.nextGaussian() * 37500.0 + 4166.33);
                return x;
            } else{
                double x = 25000.0;
                while (x >= 25000.0) x = Math.abs(r.nextGaussian() * 12500.0 + 4166.33);
                return x;
            }
        }
        public String toString(){
            return "id: " + id + "\n\tnetWorth: $"+ netWorth + "\n\tpdt: " + pdt;
        }

    }

    static int clientID = 0;
    public static Client createClient() {
        return new Client(clientID++);
    }
}
