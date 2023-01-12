public class EndCheckoutEvent extends Event {
    public EndCheckoutEvent(){
        super();
    }
    //initializes the time of checkout considering how many people are in line
    public EndCheckoutEvent(Customer c){
        super(c);
        if(c.inExpressLane()){
            final double AVERAGE_CHECKOUT_TIME_PER_ITEM = 0.1;
            final double PROCESSING_TIME = 1;
            double timeSpentInCheckout = c.getWaitTime() + (PROCESSING_TIME + (c.getOrderSize()*AVERAGE_CHECKOUT_TIME_PER_ITEM));
            setTime(c.getEndShoppingTime() + timeSpentInCheckout);//compute time customer ends checkout for express lane
        }else{
            final double AVERAGE_CHECKOUT_TIME_PER_ITEM = 0.05;
            final double PROCESSING_TIME = 2;
            double timeSpentInCheckout = c.getWaitTime() + (PROCESSING_TIME + (c.getOrderSize()*AVERAGE_CHECKOUT_TIME_PER_ITEM));
            setTime(c.getEndShoppingTime() + timeSpentInCheckout);//compute time customer ends checkout for regular lane
        }
    }
    public String toString(){
        return String.format("Customer " + getCustomer().getCustomerID() + " checked out and left the store %.2f" +
        " minute(s) after the store opened.\n ", getTime()); 
    }
}
