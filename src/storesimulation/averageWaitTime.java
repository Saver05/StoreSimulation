package storesimulation;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * finds and returns the average wait time
 */
public class averageWaitTime
{
    Register register;
    int lineLength;
    ArrayList<Customer> customerArrayList = new ArrayList<>();

    /**
     * constructor
     * @param r the register being used
     * @param q the queue of the customers at the register
     * @param c the current customer we are looking at
     */
    public averageWaitTime(Register r, Customer[] q, Customer c)
    {
        register = r;
        for (Customer a : q) //adds all the customers in the queue to a usable list
        {
            if (a!=null && a!= c) {
                customerArrayList.add(a);
            }
        }
    }

    /**
     * Returns the current register
     * @return register
     */
    public Register getRegister()
    {
        return this.register;
    }

    /**
     * returns the wait of the customers
     * @return
     */
    public double getWait()
    {
        int numItems =0;
        double timeInCheckout =0;
        double totalTime =0;
        for(Customer c : customerArrayList)
        {
            numItems = c.getNumItems();
            timeInCheckout = numItems * register.getScanTime();
            timeInCheckout = timeInCheckout + register.getPayTime();
            totalTime = timeInCheckout + totalTime;
        }
        return totalTime;
    }

    public ArrayList<Double> getEachWait()
    {
        int numItems =0;
        double timeInCheckout =0;
        ArrayList<Double> r = new ArrayList<Double>();
        double totalTime =0;
        for (Customer c : customerArrayList)
        {
            numItems = c.getNumItems();
            timeInCheckout = numItems * register.getScanTime();
            timeInCheckout = timeInCheckout + register.getPayTime();
            totalTime = timeInCheckout + totalTime;
            r.add(totalTime);
        }
        return r;
    }
}
