import java.io.IOException;

public class Trade {
    int timestamp;
    String symbol;
    double price;
    int shares;
    String exchange;

    public Trade() {
        timestamp = 0;
        symbol = "ABC";
        price = 0.0;
        shares = 0;
        exchange = "NYSE";
    }

    public Trade(String line) throws IOException {
        String[] data = line.split(", ");
        for(int i = 0; i < data.length; i++) {
            if(data[i].equals("")) {
                throw new IOException("improper row passed as Trade parameter\n");
            }
        }
        timestamp = Integer.parseInt(data[0].replaceAll(":", "").replace(".", ""));
        symbol = data[2];
        price = Double.parseDouble(data[3]);
        shares = Integer.parseInt(data[4]);
        exchange = data[5];
    }
}