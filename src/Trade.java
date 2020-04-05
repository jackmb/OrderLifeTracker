import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Trade {
    int timestamp;
    String symbol;
    double price;
    int shares;
    String exchange;

    public Trade() {
        timestamp = 0;
        symbol = "ABC";
        price = 10.00;
        shares = 10;
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