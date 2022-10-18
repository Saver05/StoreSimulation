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
    
    
    public Register(double scanTime, double payTime) {
        
    }

    public int getLineLength() {
        return -1;
    }

    public void add(Customer customer) {
        //putting the customer at the end of the line
    }

    public Customer remove() {
        //remove customer at the start of the line.
        return null;
    }

    public boolean isEmpty() {
        return false;
    }

    public Customer peek() {
       return null;
    }

    public double getScanTime() {
        return this.scanTime;
    }

    public double getPayTime() {
        return this.payTime;
    }
    
   
}
