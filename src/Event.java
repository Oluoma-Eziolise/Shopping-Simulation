public class Event implements Comparable<Event>{
    private Customer customer;
    private double time;
    public Event(){
    }
    public Event(Customer customer){
        this.customer = customer;
    }
    public Customer getCustomer(){
        return customer;
    }
    public double getTime(){
        return time;
    }
    public void setTime(double time){
        this.time = time;
    }public String toString(){
        return customer.toString();
    }
    public int compareTo(Event other){
        return (int) (getTime()-other.getTime());
    }
}
