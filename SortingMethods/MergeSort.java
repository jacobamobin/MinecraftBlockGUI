import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class MergeSort {
    public static void mergeSort(ArrayList<Block> myBlock) {
        ArrayList<Block> first = new ArrayList<Block>();
         ArrayList<Block> second = new ArrayList<Block>();
      
        for(int i = 0 ; i < myBlock.size() ; i++)
            if(i < myBlock.size()/2)
                first.add(myBlock.get(i));
            else
                second.add(myBlock.get(i));
      
        if(first.size() > 1)
            mergeSort(first);
        if(second.size() > 1)
            mergeSort(second);
      
      
      int index1 = 0, index2 = 0;
      for(int i = 0 ; i < first.size()+second.size() && index1 < first.size() && index2 < second.size() ; i++)
      {
         String listOneStudentUni = first.get(index1).name;
         String listTwoStudentUni = second.get(index2).name;
         
         if(listOneStudentUni.compareTo(listTwoStudentUni) < 0) {
            myBlock.set(i, first.get(index1));
            myBlock.set(i+1, second.get(index2));
            index1++;     
        } else {
            myBlock.set(i+1, first.get(index1));
            myBlock.set(i, second.get(index2));
            index2++;
        }
      }
     
    }
}
    
    

