import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class BlockEditGUI {

    private static boolean firstButton = true;
    private static String sortType = "Name";
    protected static int mouseY;
    protected static int mouseX;
    private static int scrollDist = 0;
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    private static String [] blocksArray;

    public static void blockEditGUI() {

        JFrame newFrame = new JFrame("Edit Blocks");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        newFrame.setResizable(false);

        // Create the main panel to hold the canvases
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        // Create Canvas 1 at the top
        JPanel canvas1 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                Font font = new Font("Arial", Font.PLAIN, 60);

                BufferedImage textBar = loadImage("ViewPannelAssets\\TopTypeBar.png");
                if (textBar != null) {
                    g.drawImage(textBar, 0, 0, 1080, 720, this);
                }
                BufferedImage quantityBG = loadImage("ViewPannelAssets\\Spacer.png");
                if (quantityBG != null) {
                    g.drawImage(quantityBG, 0, 0, 1080, 720, this);
                }

                // Draw Sort Type
                String buttonFilePath = ""; // File path for dropdown button
                String buttonFilePathON = ""; // File path for top
                String buttonFilePathOFF = ""; // File path for bottom
                switch (sortType) {
                    case "Name":
                        buttonFilePath = "ViewPannelAssets\\Name.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\A-ZON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\A-ZOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\Z-AON.png";
                        }
                        break;
                    case "Stackable":
                        buttonFilePath = "ViewPannelAssets\\Stack.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\StackedON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\StackedOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\NotON.png";
                        }
                        break;
                    case "Dimension":
                        buttonFilePath = "ViewPannelAssets\\Dimension.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TopON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TopOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\BottomON.png";
                        }
                        break;
                    case "Hardness":
                        buttonFilePath = "ViewPannelAssets\\Hardness.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\HardestON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\HardestOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\SoftestON.png";
                        }
                        break;
                    case "BlastRes":
                        buttonFilePath = "ViewPannelAssets\\BlastRes.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\MostON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\MostOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\LeastON.png";
                        }
                        break;
                    case "Renewable":
                        buttonFilePath = "ViewPannelAssets\\Renewable.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Luminous":
                        buttonFilePath = "ViewPannelAssets\\Luminous.png";
                        if (firstButton) {
                            buttonFilePathON = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathON = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathOFF = "ViewPannelAssets\\FalseON.png";
                        }
                        break;
                    case "Fire":
                        buttonFilePath = "ViewPannelAssets\\Fire.png";
                        if (firstButton) {
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

            }
        };

        canvas1.setPreferredSize(new Dimension(1080, 50));
        canvas1.setBackground(Color.BLUE); // Set background color for demonstration

        canvas1.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleClickEventSort(e.getX(), e.getY());
            }
        });

        // Add mouse listener to canvas panel to track mouse movements
        canvas1.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseX = (e.getX());
                mouseY = (e.getY());
            }
        });
        

        // Create Canvas 2 on the left (60% height of the bottom space)
    JPanel canvas2 = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                BufferedImage backgroundImage = loadImage("ViewPannelAssets\\Background.png");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, -2000 + 50 + (scrollDist) / 4, 1080, 10000, this);
                }

                parserAndReadin parser = new parserAndReadin(); // initiallize new parser
                performSortingMethods sorter = new performSortingMethods(); // initialize new sortingmethods
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt"); // read in our blocks data file
                blocksArray = sorter.sortBlockParameter("name", "asc"); // Initial sorting when gui open                
                Font font = new Font("Arial", Font.PLAIN, 60);
                int numberOfObjects = 0;

                for (int i = 0; i < blocksArray.length; i++) {
                    if (isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150)) {
                        BufferedImage highlighted = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                        if (highlighted != null) {
                            g.drawImage(highlighted, 0 - 5, (scrollDist) + (i * 100) - 5, getWidth() + 10, getHeight() + 10, this);
                            g.setColor(Color.WHITE);
                            font = new Font("Arial", Font.PLAIN, 70);
                        }

                    } else {
                        BufferedImage unselected = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                        if (unselected != null) {
                            g.drawImage(unselected, 0, (scrollDist) + (i * 100), getWidth(), getHeight(), this);
                            g.setColor(Color.LIGHT_GRAY);
                            font = new Font("Arial", Font.PLAIN, 60);
                        }
                    }

                    g.setFont(font);
                    int y = (scrollDist) + (i * 100) + 100 + 20 - 10; // Adjust Y position based on loop iteration
                    String text = String.valueOf(i + 1);
                    g.drawString(text, 30, y);
                    text = blocksArray[i];
                    g.drawString(text, 150, y);

                    numberOfObjects += 1;
                }
            }
        };
        canvas2.setPreferredSize(new Dimension(500, 670)); // Adjust width as needed
        canvas2.setBackground(Color.RED); // Set background color for demonstration

        canvas2.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                handleClickEventSort(e.getX(), e.getY());
            }
        });

        // Add mouse listener to canvas panel to track mouse movements
        canvas2.addMouseMotionListener(new MouseInputAdapter() {
            @Override
            public void mouseMoved(java.awt.event.MouseEvent e) {
                mouseX = (e.getX());
                mouseY = (e.getY());
            }
        });

        canvas2.addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int scrollAmount = e.getWheelRotation();
                if (scrollAmount < 0) {
                    if(scrollDist > 0) {

                    } else {
                        scrollDist += 20;
                    }
                } else if (scrollAmount > 0) {
                    scrollDist -= 20;
                }
                canvas2.repaint();
            }
        });

        // Create Canvas 3 on the right of Canvas 2 (40% height of the bottom space)
        JPanel canvas3 = new JPanel();
        canvas3.setPreferredSize(new Dimension(220, 670)); // Adjust width as needed
        canvas3.setBackground(Color.GREEN); // Set background color for demonstration

        // Add buttons to canvas3
        canvas3.setLayout(new GridLayout(3, 1)); // 3 rows, 1 column
        JButton button1 = new JButton();
        JButton button2 = new JButton();
        JButton button3 = new JButton();

        // Load images for the buttons' states
        ImageIcon button1IconON = new ImageIcon("ViewPannelAssets\\addOn.png");
        ImageIcon button1IconOFF = new ImageIcon("ViewPannelAssets\\addOff.png");
        ImageIcon button2IconON = new ImageIcon("ViewPannelAssets\\removeOn.png");
        ImageIcon button2IconOFF = new ImageIcon("ViewPannelAssets\\removeOff.png");
        ImageIcon button3IconON = new ImageIcon("ViewPannelAssets\\editOn.png");
        ImageIcon button3IconOFF = new ImageIcon("ViewPannelAssets\\editOff.png");

        // Set icons to the buttons
        button1.setIcon(button1IconOFF);
        button1.setPressedIcon(button1IconON);
        button2.setIcon(button2IconOFF);
        button2.setPressedIcon(button2IconON);
        button3.setIcon(button3IconOFF);
        button3.setPressedIcon(button3IconON);

        // Add action listeners to buttons
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for Button 1 here
                System.out.println("Button 1 clicked");
            }
        });

        button2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for Button 2 here
                System.out.println("Button 2 clicked");
            }
        });

        button3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Add functionality for Button 3 here
                System.out.println("Button 3 clicked");
            }
        });

        // Add buttons to canvas3
        canvas3.add(button1);
        canvas3.add(button2);
        canvas3.add(button3);

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
         Timer timer = new Timer(1000 / Main.FRAME_RATE, e -> {
            mainPanel.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }
    private static void handleClickEventSort(int x, int y) {
        if (isWithinButtonRange(x, y, 544, 819, 0, 50)) {
            if (sortType == "Name") {
                sortType = "Stack";
            } else if (sortType == "Stack") {
                    sortType = "Dimension";
            } else if (sortType == "Dimension") {
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
                sortType = "Name";
            } else {
                sortType = "Name";
            }
        } else if (isWithinButtonRange(x, y, 820, 950, 0, 50)) {
            firstButton = true;
        } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
            firstButton = false;
        }
    }

    private static boolean isWithinButtonRange(int mouseX, int mouseY, int buttonX, int buttonWidth, int buttonY, int buttonHeight) {
        return mouseX > buttonX && mouseX < buttonWidth && mouseY > buttonY && mouseY < buttonHeight;
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