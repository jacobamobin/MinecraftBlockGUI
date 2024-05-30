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
    double luminous; // If this block gives off light
    boolean flammable; // If the block is flammable from natural fire
    String dimension; // Primary Dimension of the block
    String texture; // texture file path that we will put on the block model
    String sfx; // SFX path

    // Creating a block
    public Block(String name, boolean renewable, String stackable, double blastRes, double hardness, double luminous,boolean flammable, String dimension, String texture, String sfx) 
    {
        this.name = name;
        this.renewable = renewable;
        this.stackable = stackable;
        this.hardness = hardness;
        this.blastRes = blastRes;
        this.luminous = luminous;
        this.flammable = flammable;
        this.dimension = dimension;
        this.texture = texture;
        this.sfx = sfx;
    }
    
    public String toString() {
        String flammableInfo = "";
        if (flammable)
            flammableInfo = "This block also catches fire.";
        else
            flammableInfo = "This block is not flammable.";

        return "It spawns in the " + dimension + ".  " + "Stackability: " + stackable + "  Hardness: " + hardness
                + "\nBlast Resistance: " + blastRes + "  Is Renewable: "
                + renewable + ". " + flammableInfo;
    }

    public String toStringA() {
        String flammableInfo = "";
        if (flammable)
            flammableInfo = "This block also catches fire.";
        else
            flammableInfo = "This block is not flammable.";

        return "It spawns in the " + dimension +".  " + "Stackability: " + stackable + "  Hardness: " + hardness
                + "\nBlast Resistance: " + blastRes + "  Is Renewable: "
                + renewable + ". " + flammableInfo;
    }


    //Accessor and Mutator methods (IDK how to put this anywhere else. This'll have to do)
    public String getName() 
    {
        return name;
    }

    public void setName(String name) 
    {
        this.name = name;
    }

    public boolean getRenewability() 
    {
        return renewable;
    }

    public void setRenewability(boolean renewable) 
    {
        this.renewable = renewable;
    }

    public String getStackability() 
    {
        return stackable;
    }

    public void setStackability(String stackability) 
    {
        this.stackable = stackable;
    }

    public double getBlastres() 
    {
        return blastRes;
    }

    public void setBlastres(double blastRes) 
    {
        this.blastRes = blastRes;
    }

    public double getHardness() 
    {
        return hardness;
    }

    public void setHardness(double hardness) 
    {
        this.hardness = hardness;
    }

    public double getLuminous() 
    {
        return luminous;
    }

    public void setLuminous(double luminous) 
    {
        this.luminous = luminous;
    }

    public boolean getFlammable() {
        return flammable;
    }

    public void setFlammable(boolean flammable) 
    {
        this.flammable = flammable;
    }

    public String getDimension() 
    {
        return dimension;
    }

    public void setDimension(String dimension) 
    {
        this.dimension = dimension;
    }

    public String getSFX() 
    {
        return sfx;
    }

    public void setSFX(String sfx) 
    {
        this.sfx = sfx;
    }

    public String getTexture() 
    {
        return texture;
    }

    public void setTexture(String texture) 
    {
        this.texture = texture;
    }
    //End of setters and getters

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
