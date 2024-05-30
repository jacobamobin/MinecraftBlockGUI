import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class parserAndReadin {
    public ArrayList<Block> readInData(String fileName) {
        ArrayList<Block> blocks = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;

            while ((line = reader.readLine()) != null) {
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
                    String SFX = data[8];
                    String texture = data[9];

                    Block block = new Block(name, renewability, stackability, blastres, hardness, luminous, flammable, dimension, SFX, texture);
                    blocks.add(block);
                }
            }
        } catch (IOException iox) {
            System.out.println("Problem Reading " + fileName);
        }

        return blocks;
    }
    //edit entry function
    public void editEntry(ArrayList<Block> blocks, String blockName, Block updatedBlock) {
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block.getName().equals(blockName)) {
                blocks.set(i, updatedBlock);
                break;
            }
        }
    }
    
    //Remove entry function
    public void removeEntry(ArrayList<Block> blocks, String blockName) {
        blocks.removeIf(block -> block.getName().equals(blockName));
    }
    
    //Add entry function
    public boolean addEntry(ArrayList<Block> blocks, Block newBlock) {
        for (Block block : blocks) {
            if (block.getName().equals(newBlock.getName())) {
                return false; // Block with the same name already exists
            }
        }
        blocks.add(newBlock);
        return true; // Block added successfully
    }
    
    // Gets and sends a list of a certain given parameter.
    public Object getBlockParameterList(ArrayList<Block> blocks, String parameterType) {
        Object[] parameterList = new Object[blocks.size()];

        try {
            Method method = Block.class.getMethod("get" + parameterType);
            Class<?> returnType = method.getReturnType();

            if (returnType == String.class) {
                String[] result = new String[blocks.size()];
                for (int i = 0; i < blocks.size(); i++) {
                    result[i] = (String) method.invoke(blocks.get(i));
                }
                return result;
            } else if (returnType == boolean.class) {
                boolean[] result = new boolean[blocks.size()];
                for (int i = 0; i < blocks.size(); i++) {
                    result[i] = (boolean) method.invoke(blocks.get(i));
                }
                return result;
            } else if (returnType == double.class) {
                double[] result = new double[blocks.size()];
                for (int i = 0; i < blocks.size(); i++) {
                    result[i] = (double) method.invoke(blocks.get(i));
                }
                return result;
            } else {
                for (int i = 0; i < blocks.size(); i++) {
                    parameterList[i] = method.invoke(blocks.get(i));
                }
                return parameterList;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return parameterList;
    }
    
    public Object getBlockParameter(Block block, String parameterType) {
        try {
            Method method = Block.class.getMethod("get" + parameterType);
            return method.invoke(block);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public Block getBlockByName(ArrayList<Block> blocks, String blockName) {
        for (Block block : blocks) {
            if (block.getName().equalsIgnoreCase(blockName)) {
                return block;
            }
        }
        return null;
    }   
}
