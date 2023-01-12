public class ArrivalEvent extends Event{
    public ArrivalEvent(){
        super();
    }
    //set time of customer Arrival
    public ArrivalEvent(Customer customer){
        super(customer);
        setTime(super.getCustomer().getArrivalTime());
    }
    public String toString(){
        return String.format("Customer " + getCustomer().getCustomerID() + " arrived %.2f"  
        + " minute(s) after the store opened." + "\n", getTime());
    }
    
    
}
