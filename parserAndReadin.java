import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class parserAndReadin 
{
    public ArrayList<Block> readInData(String fileName) 
    {
        ArrayList<Block> blocks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) 
        {
            String line;

            while ((line = reader.readLine()) != null) 
            {
                String[] data = line.split(",");
                if (data.length == 10) {
                    String name = data[0];
                    boolean renewability = Boolean.parseBoolean(data[1]);
                    String stackability = data[2];
                    double blastres = Double.parseDouble(data[3]);
                    double hardness = Double.parseDouble(data[4]);
                    double luminous = Double.parseDouble(data[5]);
                    boolean flammable = Boolean.parseBoolean(data[6]);
                    String dimension = data[7];
                    boolean craftabilty = Boolean.parseBoolean(data[8]);
                    String SFX = data[9];

                    Block block = new Block(name, renewability, stackability, blastres, hardness, luminous, flammable, dimension, craftabilty, SFX);
                    blocks.add(block);
                }
            }
        } 
        catch (IOException iox) 
        {
            System.out.println("Problem Reading " + fileName);
        }

        return blocks;
    }
    //edit entry function
    public void editEntry(ArrayList<Block> blocks, String blockName, Block updatedBlock, String fileName) 
    {
        for (int i = 0; i < blocks.size(); i++) 
        {
            Block block = blocks.get(i);
            if (block.getName().equals(blockName)) 
            {
                blocks.set(i, updatedBlock);
                saveBlocksToFile(blocks, fileName); // Save to file after editing
                break;
            }
        }
    }
    
    //Remove entry function
    public boolean removeEntry(ArrayList<Block> blocks, String blockName, String fileName) 
    {
        boolean blockRemoved = blocks.removeIf(block -> block.getName().equals(blockName));
        
        if (blockRemoved) {
            saveBlocksToFile(blocks, fileName); // Save to file after removing
        }
        
        return blockRemoved;
    }
    
    //Add entry function
    public boolean addEntry(ArrayList<Block> blocks, Block newBlock, String fileName) 
    {
        for (Block block : blocks) 
        {
            if (block.getName().equals(newBlock.getName())) 
            {
                return false; // Block with the same name already exists
            }
        }
        blocks.add(newBlock);
        saveBlocksToFile(blocks, fileName); // Save to file after adding the new block
        return true; // Block added successfully
    }
    
    public void saveBlocksToFile(ArrayList<Block> blocks, String fileName) 
    {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) 
        {
            for (Block block : blocks) 
            {
                writer.write(block.toCsvString());
                writer.newLine();
            }
        } 
        catch (IOException iox) 
        {
            System.out.println("Problem Writing " + fileName);
        }
    }    
    
    public Object getBlockParameter(Block block, String parameterType)
    {
        try 
        {
            Method method = Block.class.getMethod("get" + parameterType);
            return method.invoke(block);
        } 
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Block getBlockByName(ArrayList<Block> blocks, String blockName) 
    {
        for (Block block : blocks) 
        {
            if (block.getName().equalsIgnoreCase(blockName)) 
            {
                return block;
            }
        }
        return null;
    }   
}