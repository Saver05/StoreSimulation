/**
 * Store Simulation Project
 * This file controls the flow of the store simulation.
 */
package storesimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author Katie Timmerman
 */
public class StoreSimulation {

    private static final int NUMBER_STANDARD_CHECKOUT = 5; // number of cashier registers
    private static final int NUMBER_SELF_CHECKOUTS = 6; // number of self-scan registers
    private static final double SELF_SCAN_TIME = .03;
    private static final double SELF_PAY_TIME = 3.5;
    private static final double REG_PAY_TIME = 2.0;
    private static final double REG_SCAN_TIME = .015;

    private static double simClock = 0; // elapsed time (minutes)
    private static EventHeap events = new EventHeap(); // events that occur in the store
    private static ArrayList<Register> registers = new ArrayList(); // registers used in the store

    public static void main(String[] args) {
        testHeap();
        testRegister();
        startSimulation(); //starts the simulation
    }

    public static void testHeap() {
        System.out.println("MAKE SURE YOU INCLUDE YOUR OWN TESTING!");
        EventHeap h = new EventHeap();
        Event e1 = new Event(null, 23.3, EventType.ARRIVAL);
        Event e2 = new Event(null, 11.65, EventType.ARRIVAL);

        h.add(e1);
        h.add(e2);
        System.out.println(h.peek()+ " Should return an event");
        System.out.println(h.size()+" Should be two");
        h.remove();
        System.out.println(h.peek()+ " Should be different then above");
        System.out.println(h.size()+" Should be one");
    }

    /**
     * very basic testing of class register just to make sure everything is working correctly
     */
    public static void testRegister() {
        System.out.println("MAKE SURE YOU INCLUDE YOUR OWN TESTING!");
        Register r = new Register(REG_SCAN_TIME, REG_PAY_TIME);
        Customer test = new Customer(5,5,15);
        r.add(test);
        System.out.println(r.isEmpty()+ " Should be false");
        System.out.println(r.peek() + " Should return Customer test");
        System.out.println(r.getLineLength() + " Should be 1");
        r.remove();
        System.out.println(r.isEmpty()+ " Should be true");
    }

    public static void startSimulation() {

        loadRegisters(); //load registers
        loadCustomerData();

        // Events are stored in a priority queue, so they will always be returned in order.
        while (events.size() > 0) {
            Event e = events.remove();
            simClock = e.getEventTime(); // Always set the clock to the time of the new event.
            if (e.getEventType() == EventType.ARRIVAL) {
                handleArrival(e);
            } else if (e.getEventType() == EventType.END_SHOPPING) {
                handleEndShopping(e);
            } else {
                handleEndCheckout(e);
            }
        }// end while

        printCollectedStatistics();

    }

    /**
     * The arguments for the Register class are (canTime, payTime) They may need
     * to be updated.
     */
    private static void loadRegisters() {
        //A loop for creating standard checkouts
        for (int i = 0; i < NUMBER_STANDARD_CHECKOUT; i++) {
            Register r = new Register(REG_SCAN_TIME, REG_PAY_TIME); //creates register
            registers.add(r);//adds register to registers arrayList
        }
        //A loop for creating self-checkouts
        for (int i = 0; i < NUMBER_SELF_CHECKOUTS; i++) {
            Register r = new Register(SELF_SCAN_TIME, SELF_PAY_TIME); //Creates the same register as before but with different pay and scan times
            registers.add(r); //adds register to SAME arraylist
        }
        //registers is an arraylist that has all the standard checkouts then all the self-checkouts.
    }

    private static void loadCustomerData() {
        double arriveTime, avgSelectionTime;
        int items;

        try {
            File myFile = new File("arrival.txt"); //Finds the file
            Scanner inputFile = new Scanner(myFile); //connects to the file
            while (inputFile.hasNext()) { //while something to read
                arriveTime = inputFile.nextDouble();
                items = inputFile.nextInt();
                avgSelectionTime = inputFile.nextDouble();
                Customer customer = new Customer(arriveTime, items, avgSelectionTime);
                Event event = new Event(customer, arriveTime, EventType.ARRIVAL);
                events.add(event); // add event into heap
            }//end while
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File was not found");
            System.exit(0);
        }
    }

    private static void handleArrival(Event e) {
        Customer c = e.getCustomer();
        double endShoppingTime = c.getArriveTime() + c.getNumItems() * c.getAvgSelectionTime();
        Event endShopping = new Event(c, endShoppingTime, EventType.END_SHOPPING);
        events.add(endShopping);
    }

    private static void handleEndShopping(Event e) {
        Customer customer = e.getCustomer();
        int shortest = getShortestLine(customer.getNumItems()); //shortest is an index of which register in the registers variable.
        customer.setCheckoutLine(shortest); // Customer will always enter shortest checkout line.
        registers.get(shortest).add(customer); // Even if line is empty, customer must be enqueued and dequeued so that the customer gets included in the stats for the register
        if (registers.get(shortest).getLineLength() == 1) { // If new customer is the only one in line, begin checkout.
            startCheckout(customer);
        }
    }

    private static void handleEndCheckout(Event e) {
        int line = e.getCustomer().getCheckoutLine();
        Customer c = registers.get(line).remove();
        if (registers.get(line).isEmpty()) {
            return;
        } else {
            Customer customer = registers.get(line).peek();
            startCheckout(customer);
        }
    }
    private static void startCheckout(Customer customer) {
        if (customer != null) { //there is one single customer as far as I can tell that is null so this just ignores him
            int line = customer.getCheckoutLine();
            double checkoutLength = customer.getNumItems() * registers.get(line).getScanTime() + registers.get(line).getPayTime();
            Event endCheckout = new Event(customer, checkoutLength + simClock, EventType.END_CHECKOUT);
            events.add(endCheckout);
        }
    }

    private static int getShortestLine(int items)
    {
        int shortestLength = 30;
        int r =0;
        for (int i =0;i<registers.size();i++)
        {
            if(items> 49)
            {
                if (registers.get(i).getScanTime() != SELF_SCAN_TIME)
                {
                    if (registers.get(i).isEmpty())
                    {
                        return i;
                    }
                    if(registers.get(i).getLineLength()<shortestLength)
                    {
                        r =i;
                        shortestLength = registers.get(i).getLineLength();
                    }
                }
            }
            else
            {
                if (registers.get(i).isEmpty())
                {
                    return i;
                }
                if(registers.get(i).getLineLength()<shortestLength)
                {
                    r =i;
                    shortestLength = registers.get(i).getLineLength();
                }
            }
        }
        return r;
    }

    private static void printCollectedStatistics()
    {
        for (int i =0;i<50;i++)
        {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("Regular Registers: ");
        for (int i =0;i<NUMBER_STANDARD_CHECKOUT;i++)
        {
            i++;
            System.out.println("    Register "+i);
            i--;
            System.out.println("        Average wait time: " + registers.get(i).getAverageTime());
            System.out.println("        Customers Served: " + registers.get(i).getNumCust());
            System.out.println("        Longest Line: "+ registers.get(i).getMaxSize());

        }
        for (int i =0;i<50;i++)
        {
            System.out.print("*");
        }
        System.out.println();
        System.out.println("Self Checkout: ");
        for (int i =NUMBER_STANDARD_CHECKOUT;i<NUMBER_STANDARD_CHECKOUT+NUMBER_SELF_CHECKOUTS;i++)
        {
            i++;
            System.out.println("    Self Checkout "+i);
            i--;
            System.out.println("        Average wait time: "+ registers.get(i).getAverageTime());
            System.out.println("        Customers Served: " + registers.get(i).getNumCust());
            System.out.println("        Longest Line: " + registers.get(i).getMaxSize());
        }
        for (int i =0;i<50;i++)
        {
            System.out.print("*");
        }
        int totalCustomers =0;
        System.out.println();
        for (Register i : registers)
        {
            totalCustomers = i.getNumCust() + totalCustomers;
        }
        System.out.println("Over All: ");
        System.out.println("Number of total customers: " +totalCustomers);
        int max =0;
        for (Register i : registers)
        {
            if (max<i.getMaxSize())
            {
                max=i.getMaxSize();
            }
        }
        System.out.println("    Average wait time: ");
        System.out.println("    Average wait time per regular register: ");
        System.out.println("    Average wait time per self-checkout: ");
        System.out.println("    Maximum line length: "+max);
        System.out.println("    Average wait time: ");
        System.out.println("    Average wait time per regular register: ");
        System.out.println("    Average wait time per self-checkout: ");
        System.out.println("    Maximum line length: ");
        System.out.println("    Percentage of customers who waited: ");
        System.out.println("        Two or more minutes: ");
        System.out.println("        Three or more minutes: ");
        System.out.println("        Five or more minutes: ");
        System.out.println("        Ten or more minutes: ");
        for (int i =0;i<50;i++)
        {
            System.out.print("*");
        }
    }

}
