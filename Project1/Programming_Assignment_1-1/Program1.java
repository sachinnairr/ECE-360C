/*
 * Name: Sachin Nair
 * EID: svn343
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Your solution goes in this class.
 *
 * Please do not modify the other files we have provided for you, as we will use
 * our own versions of those files when grading your project. You are
 * responsible for ensuring that your solution works with the original version
 * of all the other files we have provided for you.
 *
 * That said, please feel free to add additional files and classes to your
 * solution, as you see fit. We will use ALL of your additional files when
 * grading your solution. However, do not add extra import statements to this file.
 */
public class Program1 extends AbstractProgram1 {

    /**
     * Determines whether a candidate Matching represents a solution to the stable matching problem.
     * Study the description of a Matching in the project documentation to help you with this.
     */
    @Override
    public boolean isStableMatching(Matching problem) { 
        for(int i = 0; i < problem.getStudentCount(); i++){
            for(int j = 0; j < problem.getUniversityCount(); j++){
                if(!(problem.getStudentPreference().get(i).get(j) == problem.getStudentMatching().get(i))){
                    int uniIndex = problem.getStudentPreference().get(i).get(j);
                    int CurrPairStudent,rankOfPairedStident,rankOfOfferingPair;
                    for(int k = 0; k < problem.getStudentMatching().size(); k++){
                        if(problem.getStudentMatching().get(k) == uniIndex){
                            CurrPairStudent = k;
                            rankOfPairedStident = problem.getUniversityPreference().get(uniIndex).indexOf(CurrPairStudent);
                            rankOfOfferingPair = problem.getUniversityPreference().get(uniIndex).indexOf(i);
                            if(rankOfPairedStident>rankOfOfferingPair){
                                return false;
                            }
                        }
                        
                    
                    }
                }
                else{
                    break;
                }
                
            }
        }

        return true;
    }

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_universityoptimal(Matching problem) {
        /* TODO implement this function */
        int[] matching = new int[problem.getStudentCount()];
		for(int i = 0; i < problem.getStudentCount(); i++) {
			matching[i] = -1;
		}
		ArrayList<int> UniPositions = problem.getUniversityPositions();
		LinkedList<int> UniList = new LinkedList<int>();
		ArrayList<int> StudentList = new ArrayList<int>();
		
		for(int i = 0; i < problem.getUniversityCount(); i++) {
			UniList.add(i);
		}
		for(int i = 0; i < problem.getStudentCount(); i++) {
			StudentList.add(i);
		}
		while(UniList.peek() != null) {
			int currUni = UniList.peek();
			int currStudent;
			ArrayList<int> currUniPreference = problem.getUniversityPreference().get(currUni);
			
			for(int i = 0; i < currUniPreference.size(); i++) {
				currStudent = currUniPreference.get(i);
				if(UniPositions.get(currUni) > 0 && StudentList.contains(currStudent)) {
					matching[currStudent] = currUni;
					UniPositions.set(currUni, UniPositions.get(currUni)-1);	
					StudentList.remove(StudentList.indexOf(currStudent));				
					if(UniPositions.get(currUni) == 0) {									
						UniList.remove();
					}
					break;
				}else{
					int currPairedUniIndex = matching[currStudent];
					int rankOfPairedUni = problem.getStudentPreference().get(currStudent).indexOf(currPairedUniIndex);
					int rankOfOfferingUni = problem.getStudentPreference().get(currStudent).indexOf(currUni);
					
					if(rankOfPairedUni > rankOfOfferingUni) {
						if(UniPositions.get(currPairedUniIndex) == 0) {	
							UniList.add(currPairedUniIndex);
						}
						UniPositions.set(currPairedUniIndex, UniPositions.get(currPairedUniIndex)+1);
						matching[currStudent] = currUni;
						UniPositions.set(currUni, UniPositions.get(currUni)-1);	
						if(UniPositions.get(currUni) == 0) {
							UniList.remove();
						}
						break;
					}
				}
			}
		}
		ArrayList<int> matching2 = new ArrayList<int>();
		for(int match : matching) {	
			matching2.add(match);
		}
		problem.setStudentMatching(matching2);
		return problem;
	}
    

    /**
     * Determines a solution to the stable matching problem from the given input set. Study the
     * project description to understand the variables which represent the input to your solution.
     *
     * @return A stable Matching.
     */
    @Override
    public Matching stableMatchingGaleShapley_studentoptimal(Matching problem) {
        /* TODO implement this function */
        
		ArrayList<int> UniPositions = problem.getUniversityPositions();
		LinkedList<int> UniList = new LinkedList<int>();
		LinkedList<int> StudentList = new LinkedList<int>();
		int[] matching = new int[problem.getStudentCount()];
		
		for(int i = 0; i < problem.getStudentCount(); i++) {
			matching[i] = -1;
			StudentList.add(i);
		}
		for(int i = 0; i < problem.getUniversityCount(); i++) {
			UniList.add(i);
		}
		
		while(StudentList.peek() != null) {
			int currUni;
			int currStudent = StudentList.peek();
			ArrayList<int> currStudentPreference = problem.getStudentPreference().get(currStudent);
			
			for(int i = 0; i < currStudentPreference.size(); i++) {
				currUni = currStudentPreference.get(i);
				if(UniPositions.get(currUni) > 0) {	
					matching[currStudent] = currUni;			
					UniPositions.set(currUni, UniPositions.get(currUni)-1);	
					StudentList.remove();				
					if(UniPositions.get(currUni) == 0) {
						UniList.remove();
					}
					break;
				}else{
					ArrayList<int> currPairedStudents = new ArrayList<int>();
					for(int j = 0; j < problem.getStudentCount(); j++) {
						if(matching[j] == currUni) {
							currPairedStudents.add(j);
						}
					}
					
					int worstStudentIndex = currPairedStudents.get(0);
					ArrayList<int> currUniPreference = problem.getUniversityPreference().get(currUni);
					for(int k = 0; k < currPairedStudents.size(); k++) {
						if(currUniPreference.indexOf(currPairedStudents.get(k)) > currUniPreference.indexOf(worstStudentIndex)) {
							worstStudentIndex = currPairedStudents.get(k);
						}
					}
					int rankOfWorstStudent = currUniPreference.indexOf(worstStudentIndex);
					int rankOfOfferingStudent = problem.getUniversityPreference().get(currUni).indexOf(currStudent);
					
					if(rankOfWorstStudent > rankOfOfferingStudent) {
						StudentList.add(worstStudentIndex);
						StudentList.remove();
						matching[currStudent] = currUni;
						matching[worstStudentIndex] = -1;
						break;
					}
				}
				if(i == currStudentPreference.size()-1) {
					StudentList.remove();
				}
			}
		}
		ArrayList<int> matching2 = new ArrayList<int>();
		for(int match : matching) {
			matching2.add(match);
		}
		problem.setStudentMatching(matching2);
		return problem;
    }
}