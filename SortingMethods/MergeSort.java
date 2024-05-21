package SortingMethods;

import java.util.ArrayList;

public class MergeSort {
    public static void mergeSort(ArrayList<Student> myStudents) {
        ArrayList<Student> first = new ArrayList<Student>();
         ArrayList<Student> second = new ArrayList<Student>();
      
        for(int i = 0 ; i < myStudents.size() ; i++)
            if(i < myStudents.size()/2)
                first.add(myStudents.get(i));
            else
                second.add(myStudents.get(i));
      
        if(first.size() > 1)
            mergeSort(first);
        if(second.size() > 1)
            mergeSort(second);
      
      
      int index1 = 0, index2 = 0;
      for(int i = 0 ; i < first.size()+second.size() && index1 < first.size() && index2 < second.size() ; i++)
      {
         String listOneStudentUni = first.get(index1).university;
         String listTwoStudentUni = second.get(index2).university;
         
         if(listOneStudentUni.compareTo(listTwoStudentUni) < 0) {
            myStudents.set(i, first.get(index1));
            myStudents.set(i+1, second.get(index2));
            index1++;     
        } else {
            myStudents.set(i+1, first.get(index1));
            myStudents.set(i, second.get(index2));
            index2++;
        }
      }
     
    }
}
    
    

