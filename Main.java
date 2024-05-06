public class Main {
    
}

class Block {
    //Fields 
    String name; //Name of block 
    String technicalName; //Technical name of block when running commands in Minecraft
    int versionAdded; //The version that this block was added to the game originally
    
    int dimension; //Primary Dimension of the block
    int[] biomes; //Biomes this block can be found in

    String tool; //What tool is needed to break and recieve the block as an item
    String stackable; //How stackable this block is in your inventory

    double hardness; //The in game hardness of the block
    double blastResistance; // In game blast resistance
    boolean waterlogable; //If this block absorbs water
    boolean renewable; //If the block is renewable nauturally
    boolean luminous; //If this block gives of light
    boolean flamableFire; //If the block is flamable from nautural fire
    boolean flamableLava; //If the block is flamable due to lava

    //Creating a block
    public Block(String name, String technicalName, int versionAdded, int dimension, int[] biomes, String tool, 
                String stackable, double hardness, double blastResistance, boolean waterlogable, boolean renewable,
                boolean luminous, boolean flamableFire, boolean flamableLava) {


                    
    }

    public String toString() {
        if flamableFire = true

        return name + " otherwise refered to in the games code as \"" + technicalName + "\" was added in Version 1." + versionAdded + 
        ". It primarily spawns in the " +dimension + " in mainly " + biomes[0] + ". Some properities are: \n" + 
        "Stackability: " + stackable + "\nHardness: "+hardness+"\nBlast Resistance: "+blastResistance+"\nWaterlogable: " + waterlogable +
        "\nRenewable: "+renewable+"add last fire code as a sentence";
    }

}
