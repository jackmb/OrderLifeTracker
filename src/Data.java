import java.util.ArrayList;
import java.util.Random;

public class Data {
    public ArrayList<Double> data = new ArrayList<Double>();
    private String metricLabel;
    private Double metric;

    public void recordMetric(String label, Double metric) {
        metricLabel = label;
        this.metric = metric;
    }

    public void recordMetric(String label, ArrayList<Double> dataSet) {
        metricLabel = label;
        this.data = dataSet;
        this.metric = this.mean();
    }

    public void setData(ArrayList<Double> newData) {
        this.data = newData;
    }

    public void addData(ArrayList<Double> newData) {
        for(int i = 0; i < newData.size(); i++) {
            this.data.add(newData.get(i));
        }
    }

    public void addData(Double newData) {
        this.data.add(newData);
    }

    public Double mean() {
        Double sum = 0.0;
        for(int i = 0; i < this.data.size(); i++) {
            sum += this.data.get(i);
        }
        return sum / this.data.size();
    }

    public Double meanPrint() {
        Double sum = 0.0;
        for(int i = 0; i < this.data.size(); i++) {
            System.out.println(this.data.get(i));
            sum += this.data.get(i);
        }
        return sum / this.data.size();
    }

    public Double stddev() {
        double sum = 0.0, standardDeviation = 0.0;
        int length = data.size();
        for(double val : data) {
            sum += val;
        }
        double mean = sum/length;
        for(double val: data) {
            standardDeviation += Math.pow(val - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }

    public static class TriDist<T> {
        public T triDist(T x, double chanceX, T y, double chanceY, T z, double chanceZ) {
            double total = chanceX + chanceY + chanceZ;
            chanceX = (chanceX / total) * 100;
            chanceY = (chanceY / total) * 100;
            int rand = new Random().nextInt(100);
            if(rand <= chanceX) {
                return x;
            } else if(rand <= chanceY) {
                return y;
            } else {
                return z;
            }
        }
    }

    public String toString() {
        return this.metricLabel + " " + this.metric + "\n\t\t(stddev:\t" + this.stddev() + ")";
    }
}
