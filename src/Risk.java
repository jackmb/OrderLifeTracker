public class Risk {

    String classtype;

    public Risk(Object o ){
        classtype = o.getClass().toString();
    }

    public double generateRisk(){
        if(classtype == "class Client"){
            return 420.0;
        }else if(classtype == "class Trade"){
            return 69.0;
        }else{
            return 420.69; //randomly generated risk
        }
    }

}
