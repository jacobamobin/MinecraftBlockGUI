import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;

public class BlockListGUI {

    private static boolean isDropDownOpen = false;
    private static String sortOrder = "asc";
    private static boolean firstButton = true;
    private static String sortType = "Name";
    protected static int mouseY;
    protected static int mouseX;
    private static int scrollDist = 0;
    private static Map<String, BufferedImage> imageCache = new HashMap<>();
    private static String[] blocksArray;
    private static JPanel blankCanvasPanel;
     
    public static void blockListGUI() {
        parserAndReadin parser = new parserAndReadin();
        performSortingMethods sorter = new performSortingMethods();
        ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
        blocksArray = sorter.sortBlockParameter("name", "asc"); // Initial sorting
        JFrame newFrame = new JFrame("View Blocks");
        newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Change default close operation

        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT);
        newFrame.setResizable(false);

        blankCanvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                BufferedImage backgroundImage = loadImage("ViewPannelAssets\\Background.png");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, -2000 + 50 + (scrollDist) / 4, 1080, 10000, this);
                }

                BufferedImage overlay = loadImage("ViewPannelAssets\\Sidebar.png");
                if (overlay != null) {
                    g.drawImage(overlay, 0, 0, getWidth(), getHeight(), this);
                }

                Font font = new Font("Arial", Font.PLAIN, 60);
                int numberOfObjects = 0;
                int sizeOfFrontPng = 0;

                for (int i = 0; i < blocksArray.length; i++) {
                    if (isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150) && !isDropDownOpen) {
                        BufferedImage highlighted = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                        if (highlighted != null) {
                            g.drawImage(highlighted, 0 - 5, (scrollDist) + (i * 100) - 5, getWidth() + 10, getHeight() + 10, this);
                            g.setColor(Color.WHITE);
                            font = new Font("Arial", Font.PLAIN, 60);
                            sizeOfFrontPng = 70;
                        }

                    } else {
                        BufferedImage unselected = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                        if (unselected != null) {
                            g.drawImage(unselected, 0, (scrollDist) + (i * 100), getWidth(), getHeight(), this);
                            g.setColor(Color.LIGHT_GRAY);
                            font = new Font("Arial", Font.PLAIN, 50);
                            sizeOfFrontPng = 60;
                        }
                    }

                    g.setFont(font);
                    int y = (scrollDist) + (i * 100) + 100 + 20; // Adjust Y position based on loop iteration
                    String text = String.valueOf(i + 1);
                    g.drawString(text, 990, y);
                    text = blocksArray[i];
                    g.drawString(text, 110, y);
                    parserAndReadin parser = new parserAndReadin();
                    ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                    Block block = parser.getBlockByName(blocks, blocksArray[i]);
                    font = new Font("Arial", Font.PLAIN, 20); //font for details
                    g.setFont(font);
                    String flammableInfo = ""; //get flammable info first
                    if (block.getFlammable())
                        flammableInfo = "This block also catches fire.";
                    else
                        flammableInfo = "This block is not flammable.";
                    g.drawString("It spawns in the " + block.getDimension() + ". Stackability: " + block.getStackability() +"." , 450, y-40);
                    g.drawString("Hardness: " + block.getHardness() + ". Blast Resistance: " + block.getBlastres() + "." , 450, y+25-40);
                    g.drawString("Is Renewable: " + block.getRenewability() + ". " + flammableInfo, 450, y+50-40); //bild da strings

                    BufferedImage blockFront = loadImage("object\\" + block.getName() + "\\" + "front.jpg.jpg"); // add front png of block to block
                    if (blockFront != null) {
                        g.drawImage(blockFront, 30, y-50, sizeOfFrontPng, sizeOfFrontPng, this);
                    }

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
                    case "Stackability":
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
                    case "Renewability":
                        buttonFilePath = "ViewPannelAssets\\Renewability.png";
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
                    case "Flammable":
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
                    String sortOption[] = {"Name", "Stack", "Dimension", "Hardness", "BlastRes", "Renewability", "Luminous", "Flammable"}; //IGNORE CHU
                    int dropdownY = 50;
                    for (String option : sortOption) {
                        BufferedImage sortMethod = loadImage("ViewPannelAssets\\" + option + ".png");
                        if (sortMethod != null) {
                            g.drawImage(sortMethod, 544, dropdownY, 276, 50, this);
                        }
                        dropdownY += 50;
                    }
                }

                BufferedImage tooltip = loadImage("ViewPannelAssets\\Tooltip.png");
                if (tooltip != null) {
                    g.drawImage(tooltip, 0, 0, 1080, 720, this);
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
                    if(scrollDist > 0) {

                    } else {
                        scrollDist += 20;
                    }
                } else if (scrollAmount > 0) {
                    scrollDist -= 20;
                }
                blankCanvasPanel.repaint();
            }
        });

        newFrame.addWindowListener(new WindowAdapter() { //when the window closes dont close everything, just go back to the main menu
            @Override
            public void windowClosing(WindowEvent e) {
                // Action to perform when the window is closing
                SwingUtilities.invokeLater(MainMenuGUI::mainMenu); // Opens the main menu again
                newFrame.dispose(); // Dispose of the current frame
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
        if (!isDropDownOpen) {
            if (isWithinButtonRange(x, y, 544, 819, 0, 50)) {
                isDropDownOpen = true;
            } else if (isWithinButtonRange(x, y, 820, 950, 0, 50)) {
                firstButton = true;
                performSortAction(sortType);
            } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
                firstButton = false;
                performSortAction(sortType);
            }


            for (int i = 0; i < blocksArray.length; i++) {
                parserAndReadin parser = new parserAndReadin();
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                if(isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150) && !isDropDownOpen) {
                    System.out.println(blocksArray[i]);
                    Block block = parser.getBlockByName(blocks, blocksArray[i]);
                    SwingUtilities.invokeLater(() -> {
                        BlockView3dGUI view = new BlockView3dGUI();
                        view.blockView3dGUI(block);
                    });
                }
            }

        } else {
            if (isWithinButtonRange(x, y, 820, 950, 0, 50)) {
                firstButton = true;
                performSortAction(sortType);
            } else if (isWithinButtonRange(x, y, 950, 1080, 0, 50)) {
                firstButton = false;
                performSortAction(sortType);
            } else if (isWithinButtonRange(x, y, 544, 819, 50, 100)) { 
                sortType = "Name";
                isDropDownOpen = false;
                performSortAction("Name");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 100, 150)) {
                sortType = "Stackability";
                isDropDownOpen = false;
                performSortAction("Stackability");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 150, 200)) {
                sortType = "Dimension";
                isDropDownOpen = false;
                performSortAction("Dimension");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 200, 250)) {
                sortType = "Hardness";
                isDropDownOpen = false;
                performSortAction("Hardness");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 250, 300)) {
                sortType = "BlastRes";
                isDropDownOpen = false;
                performSortAction("BlastRes");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 300, 350)) {
                sortType = "Renewability";
                isDropDownOpen = false;
                performSortAction("Renewability");
                scrollDist = 0;
            } else if (isWithinButtonRange(x, y, 544, 819, 350, 400)) {
                sortType = "Luminous";
                isDropDownOpen = false;
                performSortAction("Luminous");
            } else if (isWithinButtonRange(x, y, 544, 819, 400, 450)) {
                sortType = "Flammable";
                isDropDownOpen = false;
                performSortAction("Flammable");
                scrollDist = 0;
            } else {
                isDropDownOpen = false;
                scrollDist = 0;
            }
        }
        blankCanvasPanel.repaint();
    }

    // Add the action to perform when a sort type is selected
    private static void performSortAction(String sortType) {
        performSortingMethods sorter = new performSortingMethods();
        if (firstButton == false) {
            sortOrder = "desc";
        } else {
            sortOrder = "asc";
        }
        blocksArray = sorter.sortBlockParameter(sortType, sortOrder);
    }
    
    // Add logic for handling selection
    private static void handleBlockSelection(int x, int y) {
        // Add logic for handling selection
        System.out.println("selection at: " + x + ", " + y);
        // Determine the block based on x, y coordinates and handle accordingly
    }
}
