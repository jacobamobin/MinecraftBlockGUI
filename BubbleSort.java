import java.util.ArrayList;

public class BubbleSort
{
   public void BubbleSort(ArrayList<Block> myBlock)
   {
      int j = 0;
      while(j < myBlock.size()+1)
      {
         j++;
         
         for(int i = 0 ; i < myBlock.size()-1 ; i++)
         {
            String left = myBlock.get(i).name;
            String right = myBlock.get(i+1).name;
            
            if (left.compareTo(right) > 0)
               swap(myBlock, i,i+1);
         }
      }
   }
   
   public static void swap(ArrayList<Block> blocks, int leftIndex, int rightIndex)
   {
      Block tempBlock = blocks.get(leftIndex); //save to a temporary storage spot
      blocks.set(leftIndex, blocks.get(rightIndex)); // overwrite block on the left with the one on the right
      blocks.set(rightIndex, tempBlock); // overwrites the block on the right with the one in the temporary storage spot
   }

}