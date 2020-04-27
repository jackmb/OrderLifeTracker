
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;


public class Brokerage {


  public ArrayList<Population.Client> clients = new ArrayList<>();
  private static ArrayList<Trade> trades = new ArrayList<>();
  private static RiskManager manager = new RiskManager();
  public static int tradenbcount;
  public int pdtLosers = 0;
  public int lowNetWorths = 0;
  public Data time = new Data();
  public int highestRisk = 0;
  public Data[] timePerRisk = {new Data(), new Data(), new Data(), new Data(), new Data(), new Data(), new Data(), new Data(), new Data(), new Data(), new Data(),new Data(),new Data(),new Data(),new Data(),new Data(),new Data(),new Data(),new Data(),new Data()};
  public int[] clientsPerRisk = {0, 0, 0, 0, 0, 0, 0, 0, 0 , 0, 0, 0, 0, 0,0,0,0,0,0,0,0,0,0,0,0};

  public Brokerage(){
    readTrades(trades);
    setClients(clients, (tradenbcount/4));
    System.out.println(tradenbcount + " "+tradenbcount/4);
  }
  public static void incNBCount() {
    tradenbcount++;
  }
  public static void readTrades(ArrayList<Trade> trades){
    String csvFile = "C:\\Users\\Ash\\OneDrive\\GMU\\Fourth Semester\\OR  335\\or335projectdata\\IBMTradesOnly.csv"; // client size 10
    //String csvFile = "C:\\Users\\Ash\\OneDrive\\GMU\\Fourth Semester\\OR  335\\or335projectdata\\SPYTradesOnly.csv"; // client size 20
    //String csvFile = "C:\\Users\\Ash\\OneDrive\\GMU\\Fourth Semester\\OR  335\\or335projectdata\\VXXTradesOnly.csv"; // client size 20
    String line = "";
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
      while ((line = br.readLine()) != null) {
        // use comma as separator
        if(line.contains("TRADE NB") || line.contains("TRADE")){
          Trade t = new Trade(line);
          //manager.checkRisk(t);
          trades.add(t);
          //System.out.println(line);
          String []a = line.replace(" ", "").split(",");
          //System.out.println(Arrays.toString(a));
          if(a[1].contains("TRADENB")){
            incNBCount();
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  public static void loop(){
    for (int x = 0; x < 10; x++){
      x =2;
    }
  }
  public void assignTrade(int populationSize) throws InterruptedException {
    Rand randomint = new Rand();
    Random nextclient = new Random();
    double mean = clients.size() * .28;
    double stddev = clients.size() * .18;
    for(int i = 0; i < trades.size(); i++){

      Trade trade = trades.get(i);

      double tradeDirection = (trade.tradeType)? -1.0: 1.0;
      double commisioncost = Math.max(trade.shares / 10, 10);
      double transactioncost = ((trade.price*trade.shares)+commisioncost) * tradeDirection;

      if(trade.tradenb){

        int multiplier = 1;
        //System.out.println(trade.toString());
        Population.Client [] clientarray = new Population.Client[10];
        //Rand r = new Rand();
        for(int x = 0; x < clientarray.length; x++){ // create x number of clients
          clientarray[x] = clients.get(Math.abs((int)((nextclient.nextGaussian() *mean+stddev) % populationSize)));
        }
        Arrays.sort(clientarray, new Comparator<Population.Client>() {
          @Override
          public int compare(Population.Client o1, Population.Client o2) {
            return Integer.compare(o1.risk.riskLevel, o2.risk.riskLevel);
          }
        });
        //loop();
        boolean tradeResolved = false;
        trade.startTimer();
        while(!tradeResolved) {

          for (Population.Client client : clientarray) { // loop through each client
            if (client.getID() > (int)(Brokerage.tradenbcount *0.01) && client.getID() <= (int)(Brokerage.tradenbcount *0.57)) {
              //Thread.sleep(0, 2);
            } else if (client.getID() > (int)(Brokerage.tradenbcount *0.57)) {
              //Thread.sleep(0, 4);
            }
            int clientid = client.getID();
            if (!client.pdt) {
              if (Math.abs(transactioncost) < client.netWorth) {
                client.netWorth = client.netWorth - transactioncost;
                if (client.netWorth < 25000.0) {
                  client.pdt = true;
                } else {
                  client.pdt = false;
                }
                client.numberOfTrades++;
                trade.endTimer(tradenbcount / 4, client);
                //System.out.println(trade.timestamp);
                client.tradesmade.add(trade);
                clients.set(clientid, client);
                trade.client = client;
                trades.set(i, trade);
                tradeResolved = true;
                this.time.addData((double) trade.timeelapsed);
                this.timePerRisk[client.risk.riskLevel].addData((double) trade.timeelapsed);
                break;
              } else {
                lowNetWorths++;
                // skip to the next client
                client.risk.increaseRisk();
                //client.tradesfailed.add(trade);
                clients.set(client.getID(), client);
              }
            } else {
              if (client.numberOfTrades < 3) {
                if (Math.abs(transactioncost) < client.netWorth) {
                  client.netWorth = client.netWorth - transactioncost;
                  //client.risk.decreaseRisk();
                  if (client.netWorth > 25000.0) {
                    client.pdt = false;
                  } else {
                    client.pdt = true;
                  }
                  client.numberOfTrades++;
                  trade.endTimer(tradenbcount / 4, client);
                  //System.out.println(trade.timestamp);
                  client.tradesmade.add(trade);
                  clients.set(clientid, client);
                  trade.client = client;
                  trades.set(i, trade);
                  tradeResolved = true;
                  this.time.addData((double) trade.timeelapsed);
                  this.timePerRisk[client.risk.riskLevel].addData((double) trade.timeelapsed);
                  break;
                } else {
                  lowNetWorths++;
                  // skip to the next client
                  //client.tradesfailed.add(trade);
                  client.risk.increaseRisk();
                  clients.set(client.getID(), client);
                }
              } else {
                // skip to the next client
                pdtLosers++;
                client.risk.increaseRisk();
                //client.tradesfailed.add(trade);
                clients.set(client.getID(), client);
              }
            }

            if (client.risk.riskLevel > this.highestRisk) {
              this.highestRisk = client.risk.riskLevel;
            }
          }
          for (int x = 0; x < clientarray.length; x++) { // create x number of clients
            //clientarray[x] = clients.get( getClientID(randomint.next()));
            clientarray[x] = clients.get(Math.abs((int)((nextclient.nextGaussian() *mean+stddev) % populationSize)));
            //clientarray[x].risk.setRisk(r.next());
          }
        }
        multiplier++;
        }else{
          if(trade.tradeType){
              //System.out.println("Price Time Priority, the market price has changed, order cannot be filled.");
          }else{
            //System.out.println("Price Time Priority, the market price has changed, order cannot be filled.");
          }
        }

    }
    for(int i = 0; i < clients.size(); i++) {
        clientsPerRisk[clients.get(i).risk.riskLevel]++;
    }
    time.recordMetric("Avg time for all clients:\t", time.mean());
    for(int i = 0; i < timePerRisk.length; i++) {
        timePerRisk[i].recordMetric("Avg time (ns) for clients of risk " + i + ":\t", timePerRisk[i].mean());
    }
    for(int i = 0; i < clientsPerRisk.length; i++) {
        System.out.println(clientsPerRisk[i] + " clients of risk " + i);
    }
      for(int i = 0; i < timePerRisk.length; i++) {
              System.out.println(timePerRisk[i]);
      }
    System.out.println(time + "\nHighest Risk Level:\t" + highestRisk + "\nTrades missed due to PDT:\t" + pdtLosers + "\nTrades missed due to low net worth:\t" + lowNetWorths);
  }


  private static double[] CIinterval(double[] array) {
    double sum = 0.0;
    for (double val : array) {
      sum += val;
    }
    double mean = sum / array.length;
    double x = 0.0;
    for (double val : array) {
      x += (val - mean) * (val - mean);
    }
    double var = x / array.length;
    double temp = 1.96 * Math.sqrt(var)/ Math.sqrt(array.length);
    double [] result = new double[2];
    result[0] = mean - temp;
    result[1] = mean + temp;
    return result;
  }

  public static void setClients(ArrayList<Population.Client> clients, int popSize) {
    int i = -1;
    while(i++ < popSize){
      clients.add(Population.createClient());
      //System.out.println(clients.get(i));
    }

  }
  public static void main (String [] args) throws InterruptedException {
    Brokerage b = new Brokerage();
    //setClients(b.clients);
        /*int count = 0, count1 = 0, count2 = 0;
        int a = 0;
        int i = -1;
        while(i++ < 6270){
            if (b.clients.get(i).getNetworth() > 25000.0){
                count++;
                a+=b.clients.get(i).getNetworth();
            }else{
                count1++;
            }
            //if(b.clients.get(i).getNetworth() < 151.00){
            //    System.out.println(++a);
            //}
            count2++;
        }
        System.out.println("\nMore than 25k "+count +" Less than 25k "+count1+ " Total number of clients "+ count2);
        System.out.println(a/count);*/
    Thread.sleep(20000, 0);
    b.assignTrade(b.tradenbcount/4);
    //double [] x = .toArray();
    double[] target = new double[b.timePerRisk[0].data.size()];
    for (int i = 0; i < target.length; i++) {
      target[i] = b.timePerRisk[0].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target1 = new double[b.timePerRisk[1].data.size()];
    for (int i = 0; i < target1.length; i++) {
      target1[i] = b.timePerRisk[1].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target2 = new double[b.timePerRisk[2].data.size()];
    for (int i = 0; i < target2.length; i++) {
      target2[i] = b.timePerRisk[2].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target3 = new double[b.timePerRisk[3].data.size()];
    for (int i = 0; i < target3.length; i++) {
      target3[i] = b.timePerRisk[3].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target4 = new double[b.timePerRisk[4].data.size()];
    for (int i = 0; i < target4.length; i++) {
      target4[i] = b.timePerRisk[4].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target5 = new double[b.timePerRisk[5].data.size()];
    for (int i = 0; i < target5.length; i++) {
      target5[i] = b.timePerRisk[5].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target6 = new double[b.timePerRisk[6].data.size()];
    for (int i = 0; i < target6.length; i++) {
      target6[i] = b.timePerRisk[6].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target7 = new double[b.timePerRisk[7].data.size()];
    for (int i = 0; i < target7.length; i++) {
      target7[i] = b.timePerRisk[7].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target8 = new double[b.timePerRisk[8].data.size()];
    for (int i = 0; i < target8.length; i++) {
      target8[i] = b.timePerRisk[8].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target9 = new double[b.timePerRisk[9].data.size()];
    for (int i = 0; i < target9.length; i++) {
      target9[i] = b.timePerRisk[9].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target10 = new double[b.timePerRisk[10].data.size()];
    for (int i = 0; i < target10.length; i++) {
      target10[i] = b.timePerRisk[10].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target11 = new double[b.timePerRisk[11].data.size()];
    for (int i = 0; i < target11.length; i++) {
      target11[i] = b.timePerRisk[11].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target12 = new double[b.timePerRisk[12].data.size()];
    for (int i = 0; i < target12.length; i++) {
      target12[i] = b.timePerRisk[12].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target13 = new double[b.timePerRisk[13].data.size()];
    for (int i = 0; i < target13.length; i++) {
      target13[i] = b.timePerRisk[13].data.get(i);                // java 1.5+ style (outboxing)
    }double[] target14 = new double[b.timePerRisk[14].data.size()];
    for (int i = 0; i < target14.length; i++) {
      target14[i] = b.timePerRisk[14].data.get(i);                // java 1.5+ style (outboxing)
    }double[] target15 = new double[b.timePerRisk[15].data.size()];
    for (int i = 0; i < target15.length; i++) {
      target15[i] = b.timePerRisk[15].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target16 = new double[b.timePerRisk[16].data.size()];
    for (int i = 0; i < target16.length; i++) {
      target16[i] = b.timePerRisk[16].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target17 = new double[b.timePerRisk[17].data.size()];
    for (int i = 0; i < target17.length; i++) {
      target17[i] = b.timePerRisk[17].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target18 = new double[b.timePerRisk[18].data.size()];
    for (int i = 0; i < target18.length; i++) {
      target18[i] = b.timePerRisk[18].data.get(i);                // java 1.5+ style (outboxing)
    }
    double[] target19 = new double[b.timePerRisk[19].data.size()];
    for (int i = 0; i < target19.length; i++) {
      target19[i] = b.timePerRisk[19].data.get(i);                // java 1.5+ style (outboxing)
    }
    System.out.println("95% CI for risk 0: "+ Arrays.toString(CIinterval(target)));
    System.out.println("95% CI for risk 1: "+Arrays.toString(CIinterval(target1)));
    System.out.println("95% CI for risk 2: "+Arrays.toString(CIinterval(target2)));
    System.out.println("95% CI for risk 3: "+Arrays.toString(CIinterval(target3)));
    System.out.println("95% CI for risk 4: "+Arrays.toString(CIinterval(target4)));
    System.out.println("95% CI for risk 5: "+Arrays.toString(CIinterval(target5)));
    System.out.println("95% CI for risk 6: "+Arrays.toString(CIinterval(target6)));
    System.out.println("95% CI for risk 7: "+Arrays.toString(CIinterval(target7)));
    System.out.println("95% CI for risk 8: "+Arrays.toString(CIinterval(target8)));
    System.out.println("95% CI for risk 9: "+Arrays.toString(CIinterval(target9)));
    System.out.println("95% CI for risk 10: "+Arrays.toString(CIinterval(target10)));
    System.out.println("95% CI for risk 11: "+Arrays.toString(CIinterval(target11)));
    System.out.println("95% CI for risk 12: "+Arrays.toString(CIinterval(target12)));
    System.out.println("95% CI for risk 13: "+Arrays.toString(CIinterval(target13)));
    System.out.println("95% CI for risk 14: "+Arrays.toString(CIinterval(target14)));
    System.out.println("95% CI for risk 15: "+Arrays.toString(CIinterval(target15)));
    System.out.println("95% CI for risk 16: "+Arrays.toString(CIinterval(target16)));
    System.out.println("95% CI for risk 17: "+Arrays.toString(CIinterval(target17)));
    System.out.println("95% CI for risk 18: "+Arrays.toString(CIinterval(target18)));
    System.out.println("95% CI for risk 19: "+Arrays.toString(CIinterval(target19)));



    //System.out.println(trades.toString());
   //System.out.println(b.clients);
    //System.out.println(Math.max(b.clients.));
    //assignTrade();

  }
}