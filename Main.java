import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class Main {

    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;
    private static final int FRAME_RATE = 60; // Frames per second (adjust as needed)

    private static boolean isOverlayTopSelected = false;
    private static boolean isOverlayMiddleSelected = false;
    private static boolean isOverlayBottomSelected = false;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Minecraft Block Repository");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);

        // Create a JPanel to hold the canvas for drawing
        JPanel canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Load background image
                BufferedImage backgroundImage = loadImage("MenuAssets\\TempBg.jpg");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
                }

                // Load overlay image based on selection
                if (isOverlayTopSelected) {
                    BufferedImage overlayImage = loadImage("MenuAssets\\ButtonsTopSelected.png");
                    if (overlayImage != null) {
                        g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), this);
                    }
                } else if (isOverlayMiddleSelected) {
                    BufferedImage overlayImage = loadImage("MenuAssets\\ButtonsMiddleSelected.png");
                    if (overlayImage != null) {
                        g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), this);
                    }
                } else if (isOverlayBottomSelected) {
                    BufferedImage overlayImage = loadImage("MenuAssets\\ButtonsBottomSelected.png");
                    if (overlayImage != null) {
                        g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), this);
                    }
                } else {
                    BufferedImage overlayImage = loadImage("MenuAssets\\ButtonsNoneSelected.png");
                    if (overlayImage != null) {
                        g.drawImage(overlayImage, 0, 0, getWidth(), getHeight(), this);
                    }
                }
            }
        };

        // Set the preferred size of the canvas panel
        canvasPanel.setPreferredSize(new Dimension(FRAME_WIDTH, FRAME_HEIGHT));

        // Add mouse listener to canvas panel to track mouse movements
        canvasPanel.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                updateOverlaySelection(e.getX(), e.getY());
            }
        });

        // Add the canvas panel to the frame
        frame.getContentPane().add(canvasPanel);

        // Display the window
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Set up a timer to update and repaint the canvas at a fixed interval
        Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            canvasPanel.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }

    private static void updateOverlaySelection(int mouseX, int mouseY) {
        isOverlayTopSelected = isWithinButtonRange(mouseX, mouseY, 70, 518, 204, 294);
        isOverlayMiddleSelected = isWithinButtonRange(mouseX, mouseY, 70, 518, 315, 405);
        isOverlayBottomSelected = isWithinButtonRange(mouseX, mouseY, 70, 518, 425, 515);
    }

    private static boolean isWithinButtonRange(int x, int y, int minX, int maxX, int minY, int maxY) {
        return (x >= minX && x <= maxX && y >= minY && y <= maxY);
    }

    // Load an image from file (replace this with your image loading logic)
    private static BufferedImage loadImage(String filename) {
        try {
            return ImageIO.read(new File(filename));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    static class Block {
        // Fields
        String name; // Name of block
        String technicalName; // Technical name of block when running commands in Minecraft
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
        public Block(String name, String technicalName, int versionAdded, int dimension, int[] biomes, String tool,
                String stackable, double hardness, double blastResistance, boolean waterlogable, boolean renewable,
                boolean luminous, boolean flammableFire, boolean flammableLava, String model, String sfx) {
            this.name = name;
            this.technicalName = technicalName;
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

            return name + " otherwise referred to in the game's code as \"" + technicalName + "\" was added in Version 1."
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

        
    }
}
