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
        frame.setResizable(false);

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
}
