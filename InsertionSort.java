import java.util.ArrayList;

public class InsertionSort {
    public static void insertionSort(ArrayList<Block> myBlock)
    {
        // cycle through each student starting at the second student and ending at the last student 
        for (int i = 1; i < myBlock.size(); i++)
        {

            // get the index of the sudent directly to the left of student 1
            int x = i-1;
            // store the value of student 1 to a variable
            Block sortObj = myBlock.get(i);
            String name1 = sortObj.name;

            // go through each student until you find a student that comes before student 1
            // shift everything down 1 each time
            while (x >= 0 && name1.compareTo(myBlock.get(x).name)<0)
            {
                myBlock.set(x+1, myBlock.get(x));
                x--; 
            }

            // set the value of student 1 to the index below the student that comes before student 1
            myBlock.set(x+1, sortObj);
        }
    }
}
