public class EndShoppingEvent extends Event {
    public EndShoppingEvent(){
        super();
    }
    public EndShoppingEvent(Customer customer){
        super(customer);
        double timeSpentShopping = customer.getAvgSelectionTime() * customer.getOrderSize();//compute tome spent shopping
        double endShoppingTime =timeSpentShopping + customer.getArrivalTime();
        customer.setEndShoppingTime(endShoppingTime);//set Customer's endShopping time time
        setTime(endShoppingTime);//set event's time
    }
    public String toString(){
        return String.format("Customer " + getCustomer().getCustomerID() + " finished shopping %.2f" +
        " minute(s) after the store opened.\n", getTime());
    }
}
