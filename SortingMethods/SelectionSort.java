import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SelectionSort {
    public static void selectionSort(ArrayList<Block> myBlock)
    {
        // cycle through every student until the second last student
        for (int i = 0; i < myBlock.size()-1; i++)
        {
            int swapIndex = i; 
            // go through every student from the first to last student 
            for (int x = i + 1; x < myBlock.size(); x++ )
            {
                double num1 = myBlock.get(i).hardness;
                double num2 = myBlock.get(x).hardness;

                // if the first student num is greater than the second one then don't switch them 
                // if the first student num is smaller than the second one and the second student num is less than the previous one then swap them
                if (num1 > num2 && num2 < myBlock.get(swapIndex).hardness) // works bc if swapIndex remains i then its just the same thing twice 
                    swapIndex = x; 
            }

            if(!(swapIndex == i))
                swap(myBlock, i,swapIndex);
        }
    }

   public static void swap(ArrayList<Block> blocks, int leftIndex, int rightIndex)
    {
       Block tempBlock = blocks.get(leftIndex); //save to a temporary storage spot
       blocks.set(leftIndex, blocks.get(rightIndex)); // overwrite Student on the left with the one on the right
       blocks.set(rightIndex, tempBlock); // overwrites the blocvk on the right with the one in the temporary storage spot
    }
}




















//      *  *
// \           /  
//  \_________/