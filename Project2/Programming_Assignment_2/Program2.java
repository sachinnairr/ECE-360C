/*
 * Name: Sachin Nair
 * EID: svn343
 */

// Implement your algorithms here
// Methods may be added to this file, but don't remove anything
// Include this file in your final submission

import java.util.ArrayList;

public class Program2 implements ProgramTwoInterface {
    
    
    // additional constructor fields may be added, but don't delete or modify anything already here


    /**
     * findMinimumRouteDistance(Problem problem)
     *
     *  @param problem - the problem to solve.
     * 
     *  @param problem  - contains the cities, start, dest, and heap.
     * 
     * @return the minimum distance possible to get from start to dest.
     * Assume the given graph is always connected.
     */
    public int findMinimumRouteDistance(Problem problem) {

        // Some code to get you started
        City start = problem.getStart();
        City dest = problem.getDest();
        start.setMinDist(0);

        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());        // build the heap
        // runtime o(mlogn)
        // TODO: implement this function
        
        while (heap.toArrayList().size() > 0) {
            City city = heap.extractMin();
            //go through each edge and node o(n + m)
            for (int i = 0; i < city.getNeighbors().size(); i++) {
                // check if element is in the heap o(1)
                if (city.getNeighbors().get(i).getIndex() == -1) {
                    continue;
                }
                int distance = city.getMinDist() + city.getWeights().get(i);
                //if neighbor minDistance > distance, change neighbormindistance to distance o(logn)
                if (city.getNeighbors().get(i).getMinDist() > distance ) {
                    heap.changeKey(city.getNeighbors().get(i), distance);
                }
            }
          }
          
          return dest.getMinDist();
    }

    /**
     * findMinimumLength()
     *
     * @return The minimum total optical line length required to connect (span) each city on the given graph.
     * Assume the given graph is always connected.
     */
    public int findMinimumLength(Problem problem) {

        // Some code to get you started
        City start = problem.getCities().get(0);
        start.setMinDist(0);
    
        HeapInterface heap = problem.getHeap();     // get the heap
        heap.buildHeap(problem.getCities());        // build the heap
    
        // o(mlogn)

        //add all cities to a temp arrayList o(n)
        ArrayList<City> temp = new ArrayList<City>();          
        for (int i = 0; i < heap.toArrayList().size(); i++ ) {
          temp.add(heap.toArrayList().get(i));
        }

        while (!heap.toArrayList().isEmpty()) {
          City city = heap.extractMin();
          //go through every vertex and edge o(m)

          for (int i = 0; i < city.getNeighbors().size(); i++ ) {
            //check if in heap
            if (city.getNeighbors().get(i).getIndex() ==-1) {
              continue;
            }
            int distance = city.getWeights().get(i);
            //worst case log(n) to change every key
            if (city.getNeighbors().get(i).getMinDist() > distance) {
              heap.changeKey(city.getNeighbors().get(i), distance);
            }
          }
        }
        int len = 0;
        for (int i = 0; i < temp.size(); i++ ) {
          len += temp.get(i).getMinDist();
        }
        
        return len;
      }

    //returns edges and weights in a string.
    public String toString(Problem problem){ 
        String o = "";
        for (City v : problem.getCities()) {
            boolean first = true;
            o += "City ";
            o += v.getName();
            o += " has neighbors ";
            ArrayList<City> ngbr = v.getNeighbors();
            for (City n : ngbr) {
                o += first ? n.getName() : ", " + n.getName();
                first = false;
            }
            first = true;
            o += " with distances ";
            ArrayList<int> wght = v.getWeights();
            for (int i : wght) {
                o += first ? i : ", " + i;
                first = false;
            }
            o += System.getProperty("line.separator");

        }

        return o;
    }

}
