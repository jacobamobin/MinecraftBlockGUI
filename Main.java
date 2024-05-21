import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.Timer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.text.html.BlockView;

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
        SwingUtilities.invokeLater(Main::mainMenu);
      
      //read block list block list
      //ArrayList<Block> blocks = readInData("Blocks.txt");
      
      // use built in forEach method to print all blocks (unsorted
      //System.out.println("Unaltered List: \n\n");
      //for(Block myBlock : blocks)
         //System.out.println(myBlock);
    }

    private static void mainMenu() {
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
            // Close the current JFrame
            //JFrame currentFrame = (JFrame) SwingUtilities.getWindowAncestor(canvasPanel);
            //currentFrame.dispose();
            blockListGUI();
        } else if (isOverlayMiddleSelected = isWithinButtonRange(x, y, 665, 909, 329, 393)) {
            blockEditGUI();
            
        } else if (isOverlayBottomSelected = isWithinButtonRange(x, y, 665, 909, 409, 476)) {
            blockView3dGUI();
            //System.exit(0);
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

    private static Map<String, BufferedImage> imageCache = new HashMap<>();

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

    // Define a class to represent each sorting type with its associated file paths
    static class SortType {
        String type;
        String buttonFilePath;
        String buttonFilePathON;
        String buttonFilePathOFF;

        public SortType(String type, String buttonFilePath, String buttonFilePathON, String buttonFilePathOFF) {
            this.type = type;
            this.buttonFilePath = buttonFilePath;
            this.buttonFilePathON = buttonFilePathON;
            this.buttonFilePathOFF = buttonFilePathOFF;
        }
    }

    static int scrollDist = 0;
    static int mouseX = 0;
    static int mouseY = 0;
    static boolean isDropDownOpen = false;

    public static void blockView3dGUI() {
        JFrame frame = new JFrame("3D Canvas Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Set your desired size

        // Create panels
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel centerPanel = new JPanel(); // For 3D content

        // Set background colors
        topPanel.setBackground(Color.RED);
        bottomPanel.setBackground(Color.GREEN);
        rightPanel.setBackground(Color.BLUE);
        leftPanel.setBackground(Color.YELLOW);

        // Set layout manager (BorderLayout)
        frame.setLayout(new BorderLayout());

        // Adjust the sizes of the side panels
        topPanel.setPreferredSize(new Dimension(800, 50));
        bottomPanel.setPreferredSize(new Dimension(800, 50));
        rightPanel.setPreferredSize(new Dimension(50, 500));
        leftPanel.setPreferredSize(new Dimension(50, 500));

        // Set size for center panel
        centerPanel.setPreferredSize(new Dimension(450, 350));

        // Add panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);

         // Set up a timer to update and repaint the canvas at a fixed interval
        Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            frame.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }

    
    public static void blockEditGUI() {
        

        JFrame newFrame = new JFrame("Edit Blocks");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        newFrame.setResizable(false);

        // Create the main panel to hold the canvases
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create Canvas 1 at the top
        JPanel canvas1 = new JPanel();
        canvas1.setPreferredSize(new Dimension(1080, 50));
        canvas1.setBackground(Color.BLUE); // Set background color for demonstration
        


        // Create Canvas 2 on the left (60% height of the bottom space)
        JPanel canvas2 = new JPanel();
        canvas2.setPreferredSize(new Dimension(300, 670)); // Adjust width as needed
        canvas2.setBackground(Color.RED); // Set background color for demonstration

        // Create Canvas 3 on the right of Canvas 2 (40% height of the bottom space)
        JPanel canvas3 = new JPanel();
        canvas3.setPreferredSize(new Dimension(300, 670)); // Adjust width as needed
        canvas3.setBackground(Color.GREEN); // Set background color for demonstration

        // Create a nested panel for Canvas 2 and Canvas 3
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BorderLayout());
        bottomPanel.add(canvas2, BorderLayout.WEST);
        bottomPanel.add(canvas3, BorderLayout.CENTER);

        // Add Canvas 1 to the top of the main panel
        mainPanel.add(canvas1, BorderLayout.NORTH);

        // Add the bottom nested panel (Canvas 2 and Canvas 3) to the center of the main panel
        mainPanel.add(bottomPanel, BorderLayout.CENTER);

        // Add the main panel to the frame
        newFrame.add(mainPanel);

        // Display the frame
        newFrame.setVisible(true);

         // Set up a timer to update and repaint the canvas at a fixed interval
         Timer timer = new Timer(1000 / FRAME_RATE, e -> {
            mainPanel.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }




    public static void blockListGUI() {
        

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
                    g.drawImage(backgroundImage, 0, - 2000 +  50+(scrollDist)/4, 1080, 10000, this);
                }



                String testArray[] = {"Grass", "Dirt", "Wood", "Sand", "Grass", "Dirt", "Wood", "Sand","Grass", "Dirt", "Wood", "Sand" };
                Font font = new Font("Arial", Font.PLAIN, 60);
                int numberOfObjects = 0;

                for (int i = 0; i < testArray.length; i++) {
                    if(isWithinButtonRange(mouseX, mouseY, 5, 950 , (scrollDist)+(i*100)+50, (scrollDist)+(i*100)+150) && isDropDownOpen == false) {
                        BufferedImage highlighted = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                        if (highlighted != null) {
                            g.drawImage(highlighted, 0-5, (scrollDist)+(i*100)-5, getWidth()+10, getHeight()+10, this);
                            g.setColor(Color.WHITE);
                            font = new Font("Arial", Font.PLAIN, 70);
                            numberOfObjects += 1;
                        }

                    } else {
                        BufferedImage unselected = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                        if (unselected != null) {
                            g.drawImage(unselected, 0, (scrollDist)+(i*100), getWidth(), getHeight(), this);
                            g.setColor(Color.LIGHT_GRAY);
                            font = new Font("Arial", Font.PLAIN, 60);
                            numberOfObjects += 1;
                        }
                    }
                    
                    
                    g.setFont(font);
                    int y = (scrollDist)+(i*100) + 100 + 20; // Adjust Y position based on loop iteration
                    String text = String.valueOf(i+1);
                    g.drawString(text, 30, y); 
                    text = testArray[i];
                    g.drawString(text, 150, y); 

                }
                
                /* 
                BufferedImage block1 = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                if (block1 != null) {
                    g.drawImage(block1, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage block2 = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                if (block2 != null) {
                    g.drawImage(block2, 0, (0 + 100), getWidth(), getHeight(), this);
                } */


                
                BufferedImage textBar = loadImage("ViewPannelAssets\\TopTypeBar.png");
                if (textBar != null) {
                    g.drawImage(textBar, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage quanityBG = loadImage("ViewPannelAssets\\Spacer.png");
                if (quanityBG != null) {
                    g.drawImage(quanityBG, 0, 0, getWidth(), getHeight(), this);
                }

                //Draw Sort Type
                //Name, Version,   Tool, Stackable,, Dimension, Water,  Hardness, BlastRes,  Luminious, Renewable,  Fire, Lava, 
                String buttonFilePath = ""; //File path for dropdown button
                String buttonFilePathON = ""; //File path for top 
                String buttonFilePathOFF = ""; //file path for bottom
                switch(sortType) {
                    case "Name":
                        buttonFilePath = "ViewPannelAssets\\Name.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\A-ZON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\A-ZOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AON.png";
                        }
                        break; 
                    case "Stackable":
                        buttonFilePath = "ViewPannelAssets\\Stack.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\StackedON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\StackedOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotON.png";
                        }
                        break;
                    case "Dimension":
                        buttonFilePath = "ViewPannelAssets\\Dimension.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\TopON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TopOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomON.png";
                        }
                        break;
                    case "Hardness":
                        buttonFilePath = "ViewPannelAssets\\Hardness.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\HardestON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\HardestOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestON.png";
                        }
                        break;
                    case "BlastRes":
                        buttonFilePath = "ViewPannelAssets\\BlastRes.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\MostON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\MostOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastON.png";
                        }
                        break;
                    case "Renewable":
                        buttonFilePath = "ViewPannelAssets\\Renewable.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Luminous":
                        buttonFilePath = "ViewPannelAssets\\Luminous.png";
                        if(firstButton == true) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Fire":
                        buttonFilePath = "ViewPannelAssets\\Fire.png";
                        if(firstButton == true) {
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
                    g.drawImage(activeSortMethod, 544, 0, 276, 50, this);
                }
                BufferedImage sortOption1 = loadImage(buttonFilePathON);
                if (sortOption1 != null) {
                    g.drawImage(sortOption1, 820, 0, 130, 50, this);
                }
                BufferedImage sortOption2 = loadImage(buttonFilePathOFF);
                if (sortOption2 != null) {
                    g.drawImage(sortOption2, 950, 0, 130, 50, this);
                }

                if(isDropDownOpen == true) { /// name, Version,   Tool, Stackable,, Dimension, Water,  Hardness, BlastRes,  Luminious, Renewable,  Fire, Lava, 
                    String sortOption[] = {"Name", "Version", "Tool", "Stack", "Dimension", "Water", "Hardness", "BlastRes", "Luminous", "Renewable", "Fire", "Lava"};
                    int y = 50;

                    for(String sort : sortOption) {
                        BufferedImage dropdownSortMethod = loadImage("ViewPannelAssets\\"+sort+".png");
                        if (dropdownSortMethod != null) {
                            g.drawImage(dropdownSortMethod, 544, y, 276, 50, this);
                        }
                        y += 50;
                    }
                } 
                

                BufferedImage sideBar = loadImage("ViewPannelAssets\\Sidebar.png");
                if (sideBar != null) {
                    g.drawImage(sideBar, 0, 0, getWidth(), getHeight(), this);
   
                }
                
                String text = String.valueOf(numberOfObjects);
                g.setColor(Color.WHITE);
                font = new Font("Arial", Font.PLAIN,10);
                g.drawString(text, 465, 45); 

                

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
                mouseX = (e.getX());
                mouseY = (e.getY());
            }
        });

        blankCanvasPanel.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int scrollAmount = e.getWheelRotation();
                if (scrollAmount < 0) {
                    System.out.println("Mouse wheel scrolled up");
                    scrollDist += 20;

                } else if (scrollAmount > 0) {
                    System.out.println("Mouse wheel scrolled down");
                    scrollDist -= 20;
                }
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
        if(isDropDownOpen == false) {
            if(isWithinButtonRange(x, y, 544, 819, 0, 50)) {
                isDropDownOpen = true;
                /* 
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
                } */
                    
            } else if(isWithinButtonRange(x, y, 820, 950, 0, 50)) {
                firstButton = true;
            } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
                firstButton = false;
            }
            // add logic for block selection
        } else if (isDropDownOpen == true) {
            if(isWithinButtonRange(x, y, 820, 950, 0, 50)) {
                firstButton = true;
            } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
                firstButton = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 50, 100)) { 
                sortType = "Name";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 100, 150)) { 
                sortType = "Version";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 150, 200)) {
                sortType = "Tool";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 200, 250)) {
                sortType = "Stackable";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 250, 300)) {
                sortType = "Dimension";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 300, 350)) {
                sortType = "Water";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 350, 400)) {
                sortType = "Hardness";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819,400, 450)) {
                sortType = "BlastRes";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 450, 500)) {
                sortType = "Luminous";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 500, 550)) {
                sortType = "Renewable";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 550, 600)) {
                sortType = "Fire";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 600, 650)) {
                sortType = "Lava";
                isDropDownOpen = false;
            } else {
                isDropDownOpen = false;
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


