/**
 * 
 */
package storesimulation;

/**
 *
 * @author timmermank1
 */

//MIN HEAP
class EventHeap{
    //You do not need to resize this.
   
   private final int CAPACITY = 5000;
   private Event[] eventHeap =  new Event[CAPACITY];  
   private int currentSize = 0;
    

   /**
    *  Returns the current number of events in the heap
    * @return currentSize
    */
    public int size() { 
        return currentSize;
    }

    /**
     * Removes and returns the event at the top of the heap
     * @return 
     */
    public Event remove() { 
        return null;
    }

    /**
     * Insert an event into the heap. Events are ordered based on the time of the event.
     * @param item 
     */
    public void add(Event item) { 
    }
    
    public Event peek(){
        return null;
    }
    
    public String toString(){
        String s = "[";
        for (int i = 0; i < currentSize; i++) {
            s = s + eventHeap[i] + " ";
        }
        return s + "]";
    }
    
}
