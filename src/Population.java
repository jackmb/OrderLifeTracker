import java.util.Random;

public class Population {

    public static class Client {
        private int id;
        private boolean pdt;
        //private double netWorth; //Scrapped idea - maybe implement later

        private Client(int _id) {
            id = _id;
            if(id < 3574) {
                pdt = false;
            } else {
                pdt = true;
            }
            //netWorth = getNetWorth();
        }

        /*public double getNetWorth() {
            Random r = new Random();
            if (id >= 0 && id < 3574){ // top 57% , unlimited trades
                return (double)((int)(((r.nextGaussian() * 5000.0) + 30000.0) * 100)) / 100;
            } else {
                return (double) ((int) (((r.nextGaussian() * 5000.0) + 19999.0) * 100)) / 100;
            }
        }*/
        public String toString(){
            return "id: " + id + "\tpdt: $"+ pdt;
        }

    }

    static int clientID = 0;
    public static Client createClient() {
        return new Client(clientID++);
    }
}
