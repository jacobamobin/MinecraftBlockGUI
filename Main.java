import java.awt.Component;
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

public class Main {

    private static final int FRAME_WIDTH = 1080;
    private static final int FRAME_HEIGHT = 720;
    private static final int FRAME_RATE = 60; // Frames per second (adjust as needed)

    private static boolean isOverlayTopSelected = false;
    private static boolean isOverlayMiddleSelected = false;
    private static boolean isOverlayBottomSelected = false;

    private static JPanel canvasPanel;
    public static String sortType = "Name"; //Name, Version, Stackable,  Tool, Biomes, Dimension, Water,  Hardness, BlastRes,  Luminious, Renewable,  Fire, Lava, 
    public static Boolean firstButton = true;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::createAndShowGUI);
      
      //read block list block list
      //ArrayList<Block> blocks = readInData("Blocks.txt");
      
      // use built in forEach method to print all blocks (unsorted
      //System.out.println("Unaltered List: \n\n");
      //for(Block myBlock : blocks)
         //System.out.println(myBlock);
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
        if (isOverlayTopSelected = isWithinButtonRange(x, y, 70, 518, 204, 294)) {
            // Close the current JFrame
            //JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(canvasPanel);
            //currentFrame.dispose();
            viewPanel();
        } else if (isOverlayMiddleSelected = isWithinButtonRange(x, y, 70, 518, 315, 405)) {
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

                //Draw Sort Type
                //Name, Version,   Tool, Stackable, Biomes, Dimension, Water,  Hardness, BlastRes,  Luminious, Renewable,  Fire, Lava, 
                String buttonFilePath = ""; //File path for dropdown button
                String buttonFilePathON = ""; //File path for top 
                String buttonFilePathOFF = ""; //file path for bottom
                switch(sortType) {
                    case "Name":
                        buttonFilePath = "ViewPannelAssets\\Name.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\A-ZON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\A-ZOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AON.png";
                        }
                        break; 
                    case "Version":
                        buttonFilePath = "ViewPannelAssets\\Version.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\NewestON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\OldestOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\NewestOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\OldestON.png";
                        }
                        break;
                    case "Tool":
                        buttonFilePath = "ViewPannelAssets\\Tool.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\BestON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\WorstOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\BestOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\WorstON.png";
                        }
                        break;
                    case "Stackable":
                        buttonFilePath = "ViewPannelAssets\\Stack.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\StackedON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotOFF.png.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\StackedOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotON.png";
                        }
                        break;
                    case "Dimension":
                        buttonFilePath = "ViewPannelAssets\\Dimension.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TopON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TopOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomON.png";
                        }
                        break;
                    case "Hardness":
                        buttonFilePath = "ViewPannelAssets\\Hardness.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\HardestON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\HardestpOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestON.png";
                        }
                        break;
                    case "BlastRes":
                        buttonFilePath = "ViewPannelAssets\\BlastRes.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\MostON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\MostOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastON.png";
                        }
                        break;
                    case "Water":
                        buttonFilePath = "ViewPannelAssets\\Water.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Renewable":
                        buttonFilePath = "ViewPannelAssets\\Renewable.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Luminous":
                        buttonFilePath = "ViewPannelAssets\\Luminous.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Fire":
                        buttonFilePath = "ViewPannelAssets\\Fire.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Lava":
                        buttonFilePath = "ViewPannelAssets\\Lava.png";
                        if(firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    
                }
                BufferedImage activeSortMethod = loadImage(buttonFilePath);
                if (activeSortMethod != null) {
                    g.drawImage(activeSortMethod, 543, 0, 276, 50, this);
                }
                BufferedImage sortOption1 = loadImage(buttonFilePathON);
                if (sortOption1 != null) {
                    g.drawImage(sortOption1, 820, 0, 130, 50, this);
                }
                BufferedImage sortOption2 = loadImage(buttonFilePathOFF);
                if (sortOption2 != null) {
                    g.drawImage(sortOption2, 950, 0, 130, 50, this);
                }
                

                BufferedImage sideBar = loadImage("ViewPannelAssets\\Sidebar.png");
                if (sideBar != null) {
                    g.drawImage(sideBar, 0, 0, getWidth(), getHeight(), this);
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


        blankCanvasPanel.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleClickEventSort(e.getX(), e.getY());
            }
        });

        // Add mouse listener to canvas panel to track mouse movements
        blankCanvasPanel.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                //ignore for now
            }
        });


        blankCanvasPanel.setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));
        newFrame.getContentPane().add(blankCanvasPanel);

        newFrame.pack();
        newFrame.setLocationRelativeTo(null);
        newFrame.setVisible(true);

        // Set up a timer to update and repaint the canvas at a fixed interval
        Timer timerNew = new Timer(1000 / FRAME_RATE, e -> {
            blankCanvasPanel.repaint(); // Repaint the canvas every frame
        });
        timerNew.start(); // Start the timer

        
    }

    private static void handleClickEventSort(int x, int y) {
        if(isWithinButtonRange(x, y, 544, 819, 0, 50)) {
            if(sortType == "Name") {
                sortType = "Version";
            } else if (sortType == "Version") {
                sortType = "Stackable";
            } else if (sortType == "Stackable") {
                sortType = "Tool";
            } else if (sortType == "Tool") {
                sortType = "Dimension";
            } else if (sortType == "Dimension") {
                sortType = "Water";
            } else if (sortType == "Water") {
                sortType = "Hardness";
            } else if (sortType == "Hardness") {
                sortType = "BlastRes";
            } else if (sortType == "BlastRes") {
                sortType = "Luminous";
            } else if (sortType == "Luminous") {
                sortType = "Renewable";
            } else if (sortType == "Renewable") {
                sortType = "Fire";
            } else if (sortType == "Fire") {
                sortType = "Lava";
            } else if (sortType == "Lava") {
                sortType = "Name";
            } else {
                sortType = "Name";
            }
                
            if(isWithinButtonRange(x, y, 820, 950, 0, 50)) {
                firstButton = true;
            } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
                firstButton = false;
            }
                
        }

    }

    
     //reads blocks.text and makes an array list *(this is copy pasted so might be shitty)*
     /* 
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
            //Block myBlock = new Block();
            
            // convert the student number String object to an Integer object
            // -->Integer myKey = Integer.valueOf(data[0]);
            
            //add student to the list
            //blocks.add(myBlock);
         }
      }
      catch ( IOException iox )
      {
         System.out.println("Problem Reading "+ fileName);
      }
      
      return blocks;
   }
   */

}


