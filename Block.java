import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
static class Block {
    // Fields
    String name; // Name of block
    int versionAdded; // The version that this block was added to the game originally
    int dimension; // Primary Dimension of the block
    int[] biomes; // Biomes this block can be found in

    String tool; // What tool is needed to break and receive the block as an item
    String stackable; // How stackable this block is in your inventory

    double hardness; // The in-game hardness of the block
    double blastResistance; // In-game blast resistance
    boolean waterlogable; // If this block absorbs water
    boolean renewable; // If the block is renewable naturally
    boolean luminous; // If this block gives off light
    boolean flammableFire; // If the block is flammable from natural fire
    boolean flammableLava; // If the block is flammable due to lava

    String model; // 3D model file path
    String sfx; // SFX path

    // Creating a block
    public Block(String name, int versionAdded, int dimension, int[] biomes, String tool,
            String stackable, double hardness, double blastResistance, boolean waterlogable, boolean renewable,
            boolean luminous, boolean flammableFire, boolean flammableLava, String model, String sfx) {
        this.name = name;
        this.versionAdded = versionAdded;
        this.dimension = dimension;
        this.biomes = biomes;
        this.tool = tool;
        this.stackable = stackable;
        this.hardness = hardness;
        this.blastResistance = blastResistance;
        this.waterlogable = waterlogable;
        this.renewable = renewable;
        this.luminous = luminous;
        this.flammableFire = flammableFire;
        this.flammableLava = flammableLava;
        this.model = model;
        this.sfx = sfx;

    }

    public String toString() {
        String flammableInfo = "";
        if (flammableFire && flammableLava) {
            flammableInfo = "This block also catches fire from fire and lava sources.";
        } else if (flammableFire && !flammableLava) {
            flammableInfo = "This block catches fire due to fire but not lava.";
        } else if (!flammableFire && flammableLava) {
            flammableInfo = "This block doesn't catch fire from fire, but does from lava.";
        } else {
            flammableInfo = "This block is also not flammable.";
        }
        
        return name + " was added in Version 1."
                + versionAdded + ". It primarily spawns in the " + dimension + " in mainly " + biomes[0]
                + ". Some properties are: \n" + "Stackability: " + stackable + "\nHardness: " + hardness
                + "\nBlast Resistance: " + blastResistance + "\nWaterlogable: " + waterlogable + "\nRenewable: "
                + renewable + "\n" + flammableInfo;
     }

     /* IMPORTANT: HOW TO USE - First create a Clip object:
           Clip currentClip = playSFX("Block_Break.wav", 5f);
        When you want it to stop do the following:
           if (currentClip != null) 
              currentClip.stop(); // Stop the currently playing sound effect
        The if structure prevents the program from stopping a clip that has alread finished
     */
     // It takes .wav format files, not .mp3 files.
     public Clip playSFX(String song, float volume)
     {
        Clip clip = null;
        try
        {
           AudioInputStream audioStream = AudioSystem.getAudioInputStream(this.getClass().getResource("/res/"+song));
           clip = AudioSystem.getClip();
           clip.open(audioStream);
           FloatControl setVolume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
           setVolume.setValue(volume); // Reduces the volume by the input value
           clip.start();
        }
        catch (Exception e)
        {
           e.printStackTrace();
           System.out.println("Runtime error when adding audio file " + song);
        }
        return clip;

     }

        
