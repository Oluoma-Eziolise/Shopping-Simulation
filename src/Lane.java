import java.util.ArrayList;
import java.util.LinkedList;
public class Lane extends LinkedList<Customer> implements Comparable<Lane>{
    public Lane(){
    }
    public Lane(ArrayList<Customer> customers){
        for(int i=0; i< customers.size();i++){
            if(customers.get(i).inExpressLane()){
                i++;
            }else{
                offer(customers.get(i));
            }
        }
    }
    public boolean isEmpty(){
        if(size() >0){
            return false;
        }
        return true;
    }
    public int compareTo(Lane other){
        return size()-other.size();
    }
    //returns total checkoutTime for all customers in the queue except for the last person
    public double totalWaitTime(){
        double sum =0;
        for(int i =0; i< size(); i++){
            sum += 2 + (get(i).getOrderSize()*0.05);
        }
        return sum;
    }
    public String toString(){
        return "This lane has " + size() + " customers in it.";
    }
}