//The gui in this file is by Jacob Mobin, The connection to the block class and logic is Lucas Chu
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class BlockListGUI {

    private static boolean isDropDownOpen = false;
    private static boolean firstButton = true;
    private static String sortType = "Name";
    protected static int mouseY;
    protected static int mouseX;
    private static int scrollDist = 0;
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    private static ArrayList<Block> blocks;

    public static void blockListGUI() {

        JFrame newFrame = new JFrame("View Blocks");
        newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        newFrame.setResizable(false);
        JPanel blankCanvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                BufferedImage backgroundImage = loadImage("ViewPannelAssets\\Background.png");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, -2000 + 50 + (scrollDist) / 4, 1080, 10000, this);
                }

                String testArray[] = {"Grass", "Dirt", "Wood", "Sand", "Grass", "Dirt", "Wood", "Sand", "Grass", "Dirt", "Wood", "Sand"};
                Font font = new Font("Arial", Font.PLAIN, 60);
                int numberOfObjects = 0;

                for (int i = 0; i < testArray.length; i++) {
                    if (isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150) && !isDropDownOpen) {
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
                    int y = (scrollDist) + (i * 100) + 100 + 20; // Adjust Y position based on loop iteration
                    String text = String.valueOf(i + 1);
                    g.drawString(text, 30, y);
                    text = testArray[i];
                    g.drawString(text, 150, y);

                    numberOfObjects += 1;
                }

                BufferedImage textBar = loadImage("ViewPannelAssets\\TopTypeBar.png");
                if (textBar != null) {
                    g.drawImage(textBar, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage quantityBG = loadImage("ViewPannelAssets\\Spacer.png");
                if (quantityBG != null) {
                    g.drawImage(quantityBG, 0, 0, getWidth(), getHeight(), this);
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
                            buttonFilePathOFF = "ViewPannelAssets\\HardestON.png";
                            buttonFilePathON = "ViewPannelAssets\\SoftestOFF.png";
                        } else {
                            buttonFilePathOFF = "ViewPannelAssets\\HardestOFF.png";
                            buttonFilePathON = "ViewPannelAssets\\SoftestON.png";
                        }
                        break;
                    case "BlastRes":
                        buttonFilePath = "ViewPannelAssets\\BlastRes.png";
                        if (firstButton) {
                            buttonFilePathOFF = "ViewPannelAssets\\MostON.png";
                            buttonFilePathON = "ViewPannelAssets\\LeastOFF.png";
                        } else {
                            buttonFilePathOFF = "ViewPannelAssets\\MostOFF.png";
                            buttonFilePathON = "ViewPannelAssets\\LeastON.png";
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
                            buttonFilePathOFF = "ViewPannelAssets\\TrueON.png";
                            buttonFilePathON = "ViewPannelAssets\\FalseOFF.png";
                        } else {
                            buttonFilePathOFF = "ViewPannelAssets\\TrueOFF.png";
                            buttonFilePathON = "ViewPannelAssets\\FalseON.png";
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

                if (isDropDownOpen) {
                    String sortOption[] = {"Name", "Stack", "Dimension", "Hardness", "BlastRes", "Renewable", "Luminous", "Fire"};
                    int dropdownY = 50;
                    for (String option : sortOption) {
                        BufferedImage sortMethod = loadImage("ViewPannelAssets\\" + option + ".png");
                        if (sortMethod != null) {
                            g.drawImage(sortMethod, 544, dropdownY, 276, 50, this);
                        }
                        dropdownY += 50;
                    }
                }
            }
        };
        
        blankCanvasPanel.setPreferredSize(new Dimension(Main.FRAME_WIDTH, Main.FRAME_HEIGHT));

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
                    scrollDist += 20;

                } else if (scrollAmount > 0) {
                    scrollDist -= 20;
                }
            }
        });
        
        newFrame.add(blankCanvasPanel);
        newFrame.pack();
        newFrame.setVisible(true);
        
        Timer timer = new Timer(Main.FRAME_RATE, e -> blankCanvasPanel.repaint());
        timer.start();
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

    private static void handleClickEventSort(int x, int y) {
        if(isDropDownOpen == false) {
            if(isWithinButtonRange(x, y, 544, 819, 0, 50)) {
                isDropDownOpen = true;
                /* 
                if(sortType == "Name") {
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
                sortType = "Stackable";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 150, 200)) {
                sortType = "Dimension";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 200, 250)) {
                sortType = "Hardness";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819,250, 300)) {
                sortType = "BlastRes";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 300, 350)) {
                sortType = "Renewable";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 350, 400)) {
                sortType = "Luminous";
                isDropDownOpen = false;
            } else if (isWithinButtonRange(x, y, 544, 819, 450, 500)) {
                sortType = "Fire";
                isDropDownOpen = false;
            } else {
                isDropDownOpen = false;
            }
        }
    }
}
