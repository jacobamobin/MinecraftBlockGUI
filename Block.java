import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

class Block {
    // Fields
    String name; // Name of block
    boolean renewable; // If the block is renewable naturally
    String stackable; // How stackable this block is in your inventory
    double blastRes; // In-game blast resistance
    double hardness; // The in-game hardness of the block
    String luminous; // If this block gives off light
    boolean flammable; // If the block is flammable from natural fire
    String dimension; // Primary Dimension of the block

    String texture; // texture file path that we will put on the block model
    String sfx; // SFX path

    // Creating a block
    public Block(String name, boolean renewable, String stackable, double blastRes, double hardness, String luminous,boolean flammable, String dimenstion, String texture, String sfx) {
        this.name = name;
        this.dimension = dimension;
        this.stackable = stackable;
        this.hardness = hardness;
        this.blastRes = blastRes;
        this.renewable = renewable;
        this.luminous = luminous;
        this.flammable = flammable;
        this.texture = texture;
        this.sfx = sfx;
    }

    public String toString() {
        String flammableInfo = "";
        if (flammable)
            flammableInfo = "This block also catches fire.";
        else
            flammableInfo = "This block is not flammable.";
        
        return name + ". It spawns in the " + dimension +". Properties: \n" + "Stackability: " + stackable + "\nHardness: " + hardness
                    + "\nBlast Resistance: " + blastRes + "\nIs Renewable: "
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
    }
        