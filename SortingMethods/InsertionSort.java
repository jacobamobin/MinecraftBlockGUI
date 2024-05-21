package SortingMethods;

import java.util.ArrayList;

public class InsertionSort {
    public static void insertionSort(ArrayList<Student> myStudents)
    {
        // cycle through each student starting at the second student and ending at the last student 
        for (int i = 1; i < myStudents.size(); i++)
        {

            // get the index of the sudent directly to the left of student 1
            int x = i-1;
            // store the value of student 1 to a variable
            Student sortObj = myStudents.get(i);
            String name1 = sortObj.firstName;

            // go through each student until you find a student that comes before student 1
            // shift everything down 1 each time
            while (x >= 0 && name1.compareTo(myStudents.get(x).firstName)<0)
            {
                myStudents.set(x+1, myStudents.get(x));
                x--; 
            }

            // set the value of student 1 to the index below the student that comes before student 1
            myStudents.set(x+1, sortObj);
        }
    }
}
