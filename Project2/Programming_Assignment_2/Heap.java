/*
 * Name: Sachin Nair
 * EID: svn343
 */

// Implement your heap here
// Private methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Heap implements HeapInterface {
    private ArrayList<City> minHeap;

    public int size(){
        return minHeap.size();
    }
    public boolean isEmpty(){
        return minHeap.size() == 0;
    }

    public Heap() {
        minHeap = new ArrayList<City>();
    }

    /**
     * buildHeap(ArrayList<City> cities)
     * Given an ArrayList of Cities, build a min-heap keyed on each City's minDist
     * Time Complexity - O(nlog(n)) or O(n)
     * 
     * Should assign cities to the minHeap instance variable
     *
     *
     * @param cities
     */
    public void buildHeap(ArrayList<City> cities) {
        minHeap = cities;
        if (minHeap.isEmpty()){
            minHeap = new ArrayList<City>();
        }

        for(int i = (minHeap.size()/2)-1; i >= 0; i--){ //o(n)
            heapifyDown(i); // o(log(n))
        }
    }
    public void heapifyDown(int index){
        int largest = index;
        int left = 2*index + 1;
        int right = 2*index + 2;
        if (left < minHeap.size() && minHeap.get(left).getMinDist() < minHeap.get(largest).getMinDist()){
            largest = left;
        }
        if (right < minHeap.size() && minHeap.get(right).getMinDist() < minHeap.get(largest).getMinDist()){
            largest = right;
        }
        
        if (largest != index){
            swap(index, largest);
            heapifyDown(largest);
        }
    }
    public void heapifyUp(int index){
        int parent = (index-1)/2;
        while(index !=0 && minHeap.get(parent).getMinDist() > minHeap.get(index).getMinDist()){
                swap(index, parent);
                index = parent;
                parent = (index-1)/2;
            }
        } 
    public void swap(int city1, int city2) {
        City temp = minHeap.get(city1);
        minHeap.set(city1, minHeap.get(city2));
        minHeap.set(city2, temp);

        minHeap.get(city1).setIndex(city1);
        minHeap.get(city2).setIndex(city2);
    }  

    /**
     * insertNode(City in)
     * Insert a City into the heap.
     * Time Complexity - O(log(n))
     *
     * @param in - the City to insert.
     */
    public void insertNode(City in) {
        minHeap.add(in);
        in.setIndex(minHeap.size()-1);
        heapifyUp(minHeap.size()-1);
    }

    /**
     * findMin()
     * Time Complexity - O(1)
     *
     * @return the minimum element of the heap.
     */
    public City findMin() {
        // TODO: implement this method
        if(!(minHeap.isEmpty()))
            return minHeap.get(0);
        return null;
    }

    /**
     * extractMin()
     * Time Complexity - O(log(n))
     *
     * @return the minimum element of the heap, AND removes the element from said heap.
     */
    public City extractMin() {
        // TODO: implement this method
        //find min city
        if(minHeap.size() == 0){
            return null;
        }
        City minCity = minHeap.get(0);

        if(minHeap.size() == 1){
            minHeap.remove(0);
            
        }
        else{
            swap(0, minHeap.size()-1);
            minHeap.remove(minHeap.size()-1);
            heapifyDown(0);
        }
        return minCity;
    }

    /**
     * delete(int index)
     * Deletes an element in the min-heap given an index to delete at.
     * Time Complexity - O(log(n))
     *
     * @param index - the index of the item to be deleted in the min-heap.
     */
    public void delete(int index) {
        // TODO: implement this method
        swap(index, minHeap.size()-1);
        minHeap.remove(minHeap.size()-1);
        heapifyDown(index);

    }

    /**
     * changeKey(City r, int newDist)
     * Changes minDist of City s to newDist and updates the heap.
     * Time Complexity - O(log(n))
     *
     * @param r       - the City in the heap that needs to be updated.
     * @param newDist - the new cost of City r in the heap (note that the heap is keyed on the values of minDist)
     */ 
    public void changeKey(City r, int newDist) {
        int index = r.getIndex();
        if(index != -1){
            minHeap.get(index).setMinDist(newDist);
            heapifyUp(index);
        }
    }
    public String toString() {
        String output = "";
        for (int i = 0; i < minHeap.size(); i++) {
            output += minHeap.get(i).getName() + " ";
        }
        return output;
    }

///////////////////////////////////////////////////////////////////////////////
//                           DANGER ZONE                                     //
//                everything below is used for grading                       //
//                      please do not change :)                              //
///////////////////////////////////////////////////////////////////////////////

    public ArrayList<City> toArrayList() {
        return minHeap;
    }
}
