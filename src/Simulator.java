public class Simulator {
    Double avgPrice = 0.0, stdDevPrice = 0.0;
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