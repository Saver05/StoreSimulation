/**
 * This class is used for both types of checkout.
 * You can set payTime and scanTime to match the type of checkout.
 * 
*/

package storesimulation;

import java.util.ArrayList;

/**
 *
 * @author timmermank1
 */
class Register { 

    private double scanTime, payTime;

    private Customer[] queue;
    private int size;
    private int capacity;
    private int start;
    private int end;

    private int max;
    private ArrayList<Integer> numItems = new ArrayList<Integer>();
    private int numCust;
    
    public Register(double scanTime, double payTime)
    {
        size =0;
        capacity=30;
        start=0;
        end=0;
        queue = new Customer[capacity-1];
        numCust =0;
        max =0;
    }

    /**
     * returns the current length of the line
     * @return size
     */
    public int getLineLength() {
        return size;
    }

    /**
     * adds a customer to the end of the queue
     * @param customer
     */
    public void add(Customer customer)
    {
        end++;
        if (end==29)
        {
            end = 0;
        }
        queue[end] = customer;
        size++;
        numCust++;
        numItems.add(customer.getNumItems());
        if (getLineLength()>max)
        {
            max = getLineLength();
        }
    }

    /**
     * Removes the first item from the queue and returns it
     * @return removed Customer
     */
    public Customer remove()
    {
        Customer r = queue[start];
        queue[start] =null;
        start++;
        if (start==29)
        {
            start =0;
        }
        size--;
        return r;
    }

    /**
     * if the line is empty returns true
     * @return boolean
     */
    public boolean isEmpty()
    {
        if (size>0)
        {
            return false;
        }
        return true;
    }

    /**
     * returns the first item in the queue
     * @return First customer in queue
     */
    public Customer peek()
    {
       return queue[start];
    }

    public double getScanTime()
    {
        return this.scanTime;
    }

    public double getPayTime()
    {
        return this.payTime;
    }

    public int getNumCust()
    {
        return this.numCust;
    }

    public double getAverageTime()
    {
        double average =0;
        int counter = numItems.size();
        int add =0;
        for (int i : numItems)
        {
            add = add+i;
        }
        if (counter != 0) {
            average = add / counter;
        }
        return average;
    }

    public int getMaxSize()
    {
        return this.max;
    }
}
