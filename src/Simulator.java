public class Simulator {
    public static void main(String[] args) throws InterruptedException {
        Brokerage ibm = new Brokerage("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\IBMCSV2.csv", 4);
        Brokerage spy = new Brokerage("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\SPYCSV2.csv", 40);
        Brokerage vxx = new Brokerage("C:\\Users\\Jack\\Desktop\\Desktop\\PSL\\GMU\\OR\\OR335\\VXX_Trimmed.csv", 40);
        //ibm.run();
        System.out.println("----------------------------------");
        spy.run();
        System.out.println("----------------------------------");
        //vxx.run();
    }
}