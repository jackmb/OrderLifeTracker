import java.util.Random;

public class RiskManager {
    private boolean tooRisky;
    public Population.Client client;
    public Trade trade;
    private Random r = new Random();

    public RiskManager() {}

    public void randomRisk(Trade t) {
        boolean risky = (r.nextInt(1000) == 0);
        if(risky) {
            t.tooRisky = true;
        }
    }

    private void cantAffordBuyRisk(Trade t) {
        double buyPrice = (t.price) * (double)(t.shares);
        if(t.client.getNetworth() < buyPrice) {
            t.tooRisky = true;
        }
    }

    public String findBadTradeReason(Trade t) {
        double buyPrice = (t.price) * (double)(t.shares);
        if(t.client.getNetworth() < buyPrice) {
            return "Client cannot afford order.";
        }
        else {
            return "Price-Time Priority not met.";
        }
    }

    private void tooManySharesCheck(Trade t, Simulator sim) {
        if(t.shares > sim.maxSharesAllowed) {
            t.tooRisky = true;
        }
    }

    private void weirdPriceCheck(Trade t, Simulator sim) {
        double upperBound = sim.avgPrice + (3 * sim.stdDevPrice);
        double lowerBound = sim.avgPrice - (3 * sim.stdDevPrice);
        if(t.price < lowerBound || t.price > upperBound) {
            t.tooRisky = true;
        }
    }

    public void checkRisk(Trade t, Simulator sim) {
        randomRisk(t);
        cantAffordBuyRisk(t);
        tooManySharesCheck(t, sim);
        weirdPriceCheck(t, sim);
    }
}