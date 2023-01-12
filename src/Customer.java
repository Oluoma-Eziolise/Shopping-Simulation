public class Customer implements Comparable<Customer>{
    private  int customerID;
    private  double arrivalTime;
    private int orderSize;
    private double endShoppingTime;
    private  double avgSelectionTime;
    private double waitTime;
    private Lane lane;


    public Customer(int customerID, float arrivalTime, int orderSize, float avgSelectionTime){
        this.customerID = customerID;
        this.arrivalTime = arrivalTime;
        this.orderSize = orderSize;
        this.avgSelectionTime = avgSelectionTime;
    
    }
    public void setLane(Lane lane){
        this.lane = lane;
    }
    public Lane getLane(){
        return lane;
    }
    public double getEndShopping(){
        return  getArrivalTime() + (getOrderSize() * getAvgSelectionTime());
    }
    public double getEndCheckout(){//computes the time it will take this customer to checkout 
        //assuming no one is in line before them
        if(inExpressLane()){
            return getEndShopping() + 1 + (0.1*getOrderSize());
        }else{
            return 2 + (0.05 * getOrderSize());
        }
    }
    public boolean inExpressLane(){
        if(orderSize <=12){
            return true;
        }
        return false;
    }
    public String toString(){
        String customerString = "Customer "+ getCustomerID() + " arrived " + getArrivalTime() + " minutes"+ 
        " after the store opened and ordered " + getOrderSize() +" items." +"\n";
        return customerString;
    }
    
    public  int getOrderSize(){
        return orderSize;
    }
    public  double getArrivalTime(){
        return arrivalTime;
    }
    public  double getAvgSelectionTime(){
        return avgSelectionTime;
    }
    public  int getCustomerID(){
        return customerID;
    }
    public int compareTo(Customer other){
        return (int)(getArrivalTime() - other.getArrivalTime());
    }
    public void setEndShoppingTime(double endShoppingTime){
        this.endShoppingTime = endShoppingTime;
    }
    public double getEndShoppingTime(){
        return endShoppingTime;
    }
    public void setWaitTime(double waitTime){
        this.waitTime = waitTime;
    }
    public double getWaitTime(){
        return waitTime;
    }   
}
