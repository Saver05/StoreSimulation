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
    public void add(Event item)
    {
        if (currentSize==0)
        {
            eventHeap[0] = item;
        }
        if (currentSize >= CAPACITY)
        {
            return;
        }
        eventHeap[++currentSize] = item;
        int pos = currentSize;

        while (eventHeap[pos].getEventTime() < eventHeap[parent(pos)].getEventTime())
        {
            swap(pos,parent(pos));
            pos = parent(pos);
        }
    }

    /**
     * returns the first item in the heap
     * @return first item of heap
     */
    public Event peek(){
        return eventHeap[0];
    }

    /**
     * Returns whether a specific position is a leaf or not
     * @param pos position of the element you wish to check
     * @return boolean
     */
    private boolean isLeaf(int pos)
    {
        if (pos > (currentSize / 2))
        {
            return true;
        }
        return false;
    }

    /**
     * returns the parent of the node in the given position
     * @param pos position of node you wish to find parent of
     * @return position of the parent node
     */
    private int parent(int pos)
    {
        return pos/2;
    }

    /**
     * swaps two nodes
     * @param pos1 position of first node
     * @param pos2 position of second node
     */
    private void swap(int pos1, int pos2)
    {
        Event tmp = eventHeap[pos1];
        eventHeap[pos1] = eventHeap[pos2];
        eventHeap[pos2] = tmp;
    }
    public String toString(){
        String s = "[";
        for (int i = 0; i < currentSize; i++) {
            s = s + eventHeap[i] + " ";
        }
        return s + "]";
    }
    
}
