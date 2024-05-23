import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class parserAndReadin 
{
    public static void main(String[] args) 
    {
        // Read block list from the file
        ArrayList<Block> blocks = readInData("Blocks.txt");

        // Print all blocks (unsorted)
        System.out.println("Unaltered List: \n\n");
        for (Block myBlock : blocks) {
            System.out.println(myBlock);
        }
    }

    public static ArrayList<Block> readInData(String fileName) {
        ArrayList<Block> blocks = new ArrayList<Block>();

        try 
        {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                if (data.length == 10) 
                {
                    String name = data[0];
                    boolean renewability = Boolean.parseBoolean(data[1]);
                    String stackability = data[2];
                    double blastres = Double.parseDouble(data[3]);
                    double hardness = Double.parseDouble(data[4]);
                    String luminous = data[5];
                    boolean flammable = Boolean.parseBoolean(data[6]);
                    String dimension = data[7];
                    String SFX = data[8];
                    String texture = data[9];

                    Block block = new Block(name, renewability, stackability, blastres, hardness, luminous, flammable, dimension, SFX, texture);
                    blocks.add(block);
                }
            }

            reader.close();
        } 
        catch (IOException iox) 
        {
            System.out.println("Problem Reading " + fileName);
        }

        return blocks;
    }
    
    public static void removeEntry(ArrayList<Block> blocks, String blockName)
    {
        blocks.removeIf(block -> block.getName().equals(blockName));
    }

    public static void addEntry(ArrayList<Block> blocks, Block newBlock) 
    {
        blocks.add(newBlock);
    }
}
