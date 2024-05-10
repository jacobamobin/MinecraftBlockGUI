import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.Timer;

//stuff for sorting shit
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;

public class Main {

    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;
    private static final int FRAME_RATE = 30; // Frames per second (adjust as needed)

    private static boolean isOverlayTopSelected = false;
    private static boolean isOverlayMiddleSelected = false;
    private static boolean isOverlayBottomSelected = false;

    private static JPanel canvasPanel;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
      
      //read block list block list
      ArrayList<Block> blocks = readInData("Blocks.txt");
      
      // use built in forEach method to print all blocks (unsorted
      System.out.println("Unaltered List: \n\n");
      for(Block myBlock : blocks)
         System.out.println(myBlock);
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
        System.out.println("Pressed");
        if (isOverlayTopSelected = isWithinButtonRange(x, y, 70, 518, 204, 294)) {
            // Close the current JFrame
            //JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(canvasPanel);
            //currentFrame.dispose();
            viewPanel();
        } else if (isOverlayMiddleSelected = isWithinButtonRange(x, y, 70, 518, 315, 405)) {
            //HARRY CHU AND KAEMON THIS IS WEHRE YOU WORK OFF OF FOR THE GUI TMROW
            //HARRY CHU AND KAEMON THIS IS WEHRE YOU WORK OFF OF FOR THE GUI TMROW
            //HARRY CHU AND KAEMON THIS IS WEHRE YOU WORK OFF OF FOR THE GUI TMROW
            //HARRY CHU AND KAEMON THIS IS WEHRE YOU WORK OFF OF FOR THE GUI TMROW
            //HARRY CHU AND KAEMON THIS IS WEHRE YOU WORK OFF OF FOR THE GUI TMROW
        } else if (isOverlayBottomSelected = isWithinButtonRange(x, y, 70, 518, 425, 515)) {
            System.exit(0);
        }

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

    public static void viewPanel() {
        // Open a new JFrame with a blank canvas
        JFrame newFrame = new JFrame("View Blocks");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        newFrame.setResizable(false);

        JPanel blankCanvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Draw shit

                BufferedImage backgroundImage = loadImage("ViewPannelAssets\\Background.png");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, 50, 1080, 10000, this);
                }
                BufferedImage textBar = loadImage("ViewPannelAssets\\TopTypeBar.png");
                if (textBar != null) {
                    g.drawImage(textBar, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage quanityBG = loadImage("ViewPannelAssets\\Spacer.png");
                if (quanityBG != null) {
                    g.drawImage(quanityBG, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage activeSortMethod = loadImage("ViewPannelAssets\\Dimension.png");
                if (activeSortMethod != null) {
                    g.drawImage(activeSortMethod, 543, 0, 276, 50, this);
                }
                BufferedImage sideBar = loadImage("ViewPannelAssets\\Sidebar.png");
                if (sideBar != null) {
                    g.drawImage(sideBar, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage sortOption1 = loadImage("ViewPannelAssets\\A-ZON.png");
                if (sortOption1 != null) {
                    g.drawImage(sortOption1, 820, 0, 130, 50, this);
                }
                BufferedImage sortOption2 = loadImage("ViewPannelAssets\\Z-AOFF.png");
                if (sortOption2 != null) {
                    g.drawImage(sortOption2, 950, 0, 130, 50, this);
                }
                BufferedImage block1 = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                if (block1 != null) {
                    g.drawImage(block1, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage block2 = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                if (block2 != null) {
                    g.drawImage(block2, 0, (0 + 100), getWidth(), getHeight(), this);
                }

            }
        };

        blankCanvasPanel.setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        newFrame.getContentPane().add(blankCanvasPanel);

        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);


        // Set up a timer to update and repaint the canvas at a fixed interval
        Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            blankCanvasPanel.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer

    }
    
     //reads blocks.text and makes an array list *(this is copy pasted so might be shitty)*
     public static ArrayList<Block> readInData(String fileName)
   {
      ArrayList<Block> blocks = new ArrayList<Block>();
      
      try
      {
         System.getProperty("user.dir");
         BufferedReader reader = new BufferedReader( new FileReader(fileName));
         String line;
         
         while ((line = reader.readLine())!=null)
         {
            // split the line into an array (String.split())
            String[] data = line.split(",");
            
            // build blocks array
            double[] marks = new double[8];
            for(int i = 0; i<marks.length; i++)
               if (!data[i+3].equals("") && !data[i+3].equals(null))
                  marks[i] = Double.valueOf(data[i+3]);
               else
                  marks[i] = 0.0;
         
            // instantiate a block object with the data
            Block myBlock = new Block();
            
            // convert the student number String object to an Integer object
            // -->Integer myKey = Integer.valueOf(data[0]);
            
            //add student to the list
            blocks.add(myBlock);
         }
      }
      catch ( IOException iox )
      {
         System.out.println("Problem Reading "+ fileName);
      }
      
      return blocks;
   }

}
