import java.util.ArrayList;
import java.util.PriorityQueue;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.FileAlreadyExistsException;
import java.util.Scanner;
import java.util.Collections;
public class Store {
    private static PrintWriter printWriter;
    public static ArrayList<Customer> createCustomers(){//creates all customers and stores them in a array List
        ArrayList<Customer> customers = new ArrayList<>();
        try{
            int customerCount=0;
            File file = new File("src\\arrival.txt");
            Scanner in = new Scanner(file);
            while(in.hasNextLine()){
                String[] data = in.nextLine().strip().split("    ");
                customerCount++;
                Customer customer = new Customer(customerCount, Float.parseFloat(data[0]), Integer.parseInt(data[1]), Float.parseFloat(data[2]));
                customers.add(customer);
            }
            in.close();
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
        return customers;
    }
    //creates arrival events and offers them to the eventList Priority Queue
    public static PriorityQueue<Event> createArrivalEvents(ArrayList<Customer> customers){
        PriorityQueue<Event> eventList = new PriorityQueue<>();
        for(int i =0; i < customers.size(); i++){
            Event arrivalEvent = new ArrivalEvent(customers.get(i));
            eventList.offer(arrivalEvent);
        }
        return eventList;
    }
    //logs event data in a file
    public static void log(Event event, File file){
        printWriter.println(event.toString());
        return;
    }
    public static void closePrintWriter(PrintWriter pw){
        pw.close();
    }


    public static void main(String[] args) throws FileAlreadyExistsException {
        double simClock =0.0;
        ArrayList<Lane> regularLanes = new ArrayList<>();
        ArrayList<Lane> expressLanes = new ArrayList<>();
        ArrayList<Customer> customers = createCustomers();

        double sumWaitTime = 0.0;
        int numOfCustomers = customers.size();

        PriorityQueue<Event> eventList = createArrivalEvents(customers);
        File logFile = new File("logFile");
        try{
            printWriter = new PrintWriter(logFile);
        }catch(FileNotFoundException fnfe){
            System.out.println("File not found");
        }
        //create regular and express lanes
        for(int i =0; i < 9 ; i++){
            Lane lane = new Lane();
            regularLanes.add(lane);
        }
        for(int i =0; i < 9; i++){
            Lane express = new ExpressLane();
            expressLanes.add(express);
        }
        if(regularLanes.size()==0 && expressLanes.size() ==0){
            System.out.println("There are no lanes open.");
            return;
        }
        while(!eventList.isEmpty()){
            if(eventList.peek() instanceof ArrivalEvent){
                simClock = eventList.peek().getTime();
                Customer customer = eventList.peek().getCustomer();
                //updates "simCLock" with the time of the event
                log(eventList.peek(), logFile);//log event 
                Event event = new EndShoppingEvent(customer);
                eventList.poll();
                //remove arrival event
                eventList.offer(event);
                //offer end shopping event to the queue
                
                
            }else if(eventList.peek() instanceof EndShoppingEvent){
                Customer customer = eventList.peek().getCustomer();
                simClock = eventList.peek().getTime();
                //add the customer to the shortest expresslane or regularlane 
                if(customer.inExpressLane()){
                    
                    Collections.sort(expressLanes);
                    double waitTimeForLane = expressLanes.get(0).totalWaitTime();
                    customer.setWaitTime(waitTimeForLane);
                    sumWaitTime +=customer.getWaitTime();
                    //setting the wait time of the customer before they can checkout.
                    Lane lane = expressLanes.get(0);
                    lane.offer(customer);
                    customer.setLane(lane);
                    
                }else{
                    //get the shortest lane
                    Collections.sort(regularLanes);
                    Lane lane = regularLanes.get(0);
                    //get lanes wait time and set  it to be the customer's wait time
                    double waitTimeForLane = regularLanes.get(0).totalWaitTime();
                    customer.setWaitTime(waitTimeForLane);
                    sumWaitTime += customer.getWaitTime();
                    //add customer to the lane and set that lanes as cutomer's lane
                    lane.offer(customer);
                    customer.setLane(lane);
                }
                log(eventList.peek(), logFile);

                Event event = new EndCheckoutEvent(eventList.peek().getCustomer());
                eventList.poll();
                //removing endshopping event
                eventList.offer(event);
                //offer endCheckoutEvent to the queue
            }else if(eventList.peek() instanceof EndCheckoutEvent){
                simClock = eventList.peek().getTime();
                log(eventList.peek(), logFile);
                Customer customer = eventList.peek().getCustomer();
                customer.getLane().poll();//remove customer form the lane they are in
                eventList.poll();//remove endChecoutEvent form the queue
                
            }
        }
        //compute avergae wait time and print it to the console
        double avgWaitTime = sumWaitTime/ numOfCustomers;
        System.out.printf("The customers waited %.3f minute(s) in a checkout lane on average", avgWaitTime);
        printWriter.close();
    }
    

}
