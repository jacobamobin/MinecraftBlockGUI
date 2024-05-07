import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        // Create and set up the window
        JFrame frame = new JFrame("Minecraft Block Repository");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        // Create a JPanel to hold the canvas for drawing
        JPanel canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw the image across the entire canvas
                BufferedImage image = loadImage(); // Load your image here
                if (image != null) {
                    g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };

        // Set the preferred size of the canvas panel
        canvasPanel.setPreferredSize(new Dimension(1080, 720));

        // Add the canvas panel to the frame
        frame.getContentPane().add(canvasPanel);

        // Display the window
        frame.pack(); // Adjusts frame size based on content's preferred size
        frame.setLocationRelativeTo(null); // Center the frame on screen
        frame.setVisible(true);
    }

    // Load an image from file (replace this with your image loading logic)
    private static BufferedImage loadImage() {
        try {
            // Example: Load an image from a file
            return ImageIO.read(new File("MenuBackground.png"));
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
    }
}
