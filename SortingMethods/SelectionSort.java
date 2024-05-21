package SortingMethods;
import java.util.ArrayList;

public class SelectionSort {
    public static void selectionSort(ArrayList<Student> myStudents)
    {
        // cycle through every student until the second last student
        for (int i = 0; i < myStudents.size()-1; i++)
        {
            int swapIndex = i; 
            // go through every student from the first to last student 
            for (int x = i + 1; x < myStudents.size(); x++ )
            {
                int num1 = myStudents.get(i).studentNum;
                int num2 = myStudents.get(x).studentNum;

                // if the first student num is greater than the second one then don't switch them 
                // if the first student num is smaller than the second one and the second student num is less than the previous one then swap them
                if (num1 > num2 && num2 < myStudents.get(swapIndex).studentNum) // works bc if swapIndex remains i then its just the same thing twice 
                    swapIndex = x; 
            }

            if(!(swapIndex == i))
                swap(myStudents, i,swapIndex);
        }
    }

    public static void swap(ArrayList<Student> students, int leftIndex, int rightIndex) {
        Student tempStudent = students.get(leftIndex);
        students.set(leftIndex, students.get(rightIndex));
        students.set(rightIndex, tempStudent);
    }
}