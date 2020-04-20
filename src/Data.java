import java.util.ArrayList;

public class Data {
    private ArrayList<Double> data = new ArrayList<Double>();

    public void setData(ArrayList<Double> newData) {
        this.data = newData;
    }

    public void addData(ArrayList<Double> newData) {
        for(int i = 0; i < newData.size(); i++) {
            this.data.add(newData.get(i));
        }
    }

    public void addData(double newData) {
        this.data.add(newData);
    }

    public double mean() {
        double sum = 0.0;
        for(int i = 0; i < this.data.size(); i++) {
            sum += this.data.get(i);
        }
        return sum / this.data.size();
    }

    public double stddev() {
        ArrayList<Double> temp = new ArrayList<>();
        double m = this.mean();
        for(int i = 0; i < this.data.size(); i++) {
            double meanDiff = Math.abs(this.data.get(i) - m);
            temp.add(meanDiff * meanDiff);
        }
        double sum = 0.0;
        for(int i = 0; i < temp.size(); i++) {
            sum += temp.get(i);
        }
        sum /= temp.size();
        return Math.sqrt(sum);
    }
}
