import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.event.MouseInputAdapter;

public class MainMenuGUI {

    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;
    private static final int FRAME_RATE = 60; // Frames per second (adjust as needed)

    private static boolean isOverlayTopSelected = false;
    private static boolean isOverlayMiddleSelected = false;
    private static boolean isOverlayBottomSelected = false;

    private static JPanel canvasPanel;
    private static Map<String, BufferedImage> imageCache = new HashMap<>();

    public static void mainMenu() {
        // Create and set up the window
        JFrame frame = new JFrame("Minecraft Block Repository");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(FRAME_WIDTH, FRAME_HEIGHT);
        frame.setResizable(false);

        // Create a JPanel to hold the canvas for drawing
        canvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Load background image
                BufferedImage backgroundImage = loadImage("MenuAssets\\TempBg.png");
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

        canvasPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleClickEvent(e.getX(), e.getY());
            }
        });

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

    private static void handleClickEvent(int x, int y) {
        if (isOverlayTopSelected = isWithinButtonRange(x, y, 665, 909, 244, 310)) {
            SwingUtilities.invokeLater(BlockListGUI::blockListGUI);
        } else if (isOverlayMiddleSelected = isWithinButtonRange(x, y, 665, 909, 329, 393)) {
            SwingUtilities.invokeLater(BlockEditGUI::blockEditGUI);
        } else if (isOverlayBottomSelected = isWithinButtonRange(x, y, 665, 909, 409, 476)) {
            SwingUtilities.invokeLater(BlockView3dGUI::blockView3dGUI);
        }
    }

    private static void updateOverlaySelection(int mouseX, int mouseY) {
        isOverlayTopSelected = isWithinButtonRange(mouseX, mouseY, 665, 909, 244, 310);
        isOverlayMiddleSelected = isWithinButtonRange(mouseX, mouseY, 665, 909, 329, 393);
        isOverlayBottomSelected = isWithinButtonRange(mouseX, mouseY, 665, 909, 409, 476);
    }

    private static boolean isWithinButtonRange(int x, int y, int minX, int maxX, int minY, int maxY) {
        return (x >= minX && x <= maxX && y >= minY && y <= maxY);
    }

    private static BufferedImage loadImage(String filename) {
        if (imageCache.containsKey(filename)) {
            return imageCache.get(filename);
        } else {
            try {
                BufferedImage image = ImageIO.read(new File(filename));
                imageCache.put(filename, image);
                return image;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }
}