/**
 * This class is used for both types of checkout.
 * You can set payTime and scanTime to match the type of checkout.
 * 
*/

package storesimulation;

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
    
    public Register(double scanTime, double payTime)
    {
        size =0;
        capacity=30;
        start=0;
        end=0;
        queue = new Customer[capacity-1];
    }

    /**
     * returns the current length of the line
     * @return size
     */
    public int getLineLength() {
        return size;
    }

    public void add(Customer customer)
    {
        //putting the customer at the end of the line
    }

    public Customer remove()
    {
        Customer r = queue[start];
        queue[start] =null;
        start++;
        if (start==30)
        {
            start =0;
        }
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
    
   
}
