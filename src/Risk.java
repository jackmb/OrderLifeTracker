public class Risk {

    public int riskLevel;
    public int client;

    public Risk(int id){
        riskLevel = 0;
        client = id;
    }
    public int getID(){
        return client;
    }
    public void increaseRisk(){
        riskLevel = Math.min(100, riskLevel+1);
    }
    public void decreaseRisk(){
        riskLevel = Math.max(0, riskLevel-1);
    }
}