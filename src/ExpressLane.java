import java.util.ArrayList;
public class ExpressLane extends Lane{
    public ExpressLane(){
        super();   
    }
    public ExpressLane(ArrayList<Customer> customers){
        for(int i =0; i < customers.size(); i++){
            if(customers.get(i).inExpressLane()){
                offer(customers.get(i));
            }else{
                i++;
            }
        }
    }
    public double totalWaitTime(){
        double sum =0;
        for(int i =0; i< size(); i++){
            sum += 1 + (get(i).getOrderSize()*0.1);
        }
        return sum;
    }
}