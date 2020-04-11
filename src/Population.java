import java.util.Random;

public class Population {

    public static class Client {
        private int id;
        private boolean PDT;
        private double networth;
        private int numberOfTrades;
        public Client (int id){
            this.id = id;
        }
        public Client(int id, double _networth){
            this.id = id;
            this.networth = _networth;
            if(this.networth < 25000.0){
                PDT = true;
            }
            numberOfTrades = 0;
        }
        public double getNetworth(){
            return this.networth;
        }

        public String toString(){
            return "ID: " + id + " PDT: "+ PDT + " net-worth: "+networth;
        }

    }

    static int clientID = 0;
    public static Client createClient() {
        return new Client(clientID++);
    }
}
