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

    private void tooManySharesCheck(Trade t) {

    }
}