public class Simulator {
    Double meanPrice = 0.0;
    int maxSharesAllowed = 0;
    RiskManager rm = new RiskManager();
    Brokerage brkrg = new Brokerage();

    public Simulator() {}

    public void Initialization() {

    }

    public static double exponential(Rand rng, double mean) {
        return -mean * Math.log(rng.next());
    }
}