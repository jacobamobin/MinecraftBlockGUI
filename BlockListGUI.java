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

    //FIELDS
    private static boolean isDropDownOpen = false; //if the drop down is open
    private static String sortOrder = "asc"; //which order we want to show at the top of gui
    private static boolean firstButton = true; // if the first button of the true/false on the gui is pressed
    private static String sortType = "Name"; //what we are sorting by
    protected static int mouseY; // the mouse cursors y position, this is to handle hovering and clicking on the gui
    protected static int mouseX; // the mouse cursors x position, this is to handle hovering and clicking on the gui
    private static int scrollDist = 0; // the distance scrolled down by the mousewheel, this allows the list to scroll and dynamically render on the canvas
    private static Map<String, BufferedImage> imageCache = new HashMap<>(); //this optimises the images we are rendering through buffered images, so it dosent have to create a new object each time
    private static String[] blocksArray; // this is an array of all the block names, from the blocks.txt file
    private static JPanel blankCanvasPanel; // i dont know why this is here but shit breaks if its gone

    //THE GUI
    public static void blockListGUI() {

        parserAndReadin parser = new parserAndReadin(); // initiallize new parser
        performSortingMethods sorter = new performSortingMethods(); // initialize new sortingmethods
        ArrayList<Block> blocks = parser.readInData("data/Blocks.txt"); // read in our blocks data file
        blocksArray = sorter.sortBlockParameter("name", "asc"); // Initial sorting when gui open

        //CREATE THE JFRAME
        JFrame newFrame = new JFrame("View Blocks");
        newFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); // Change default close operation so it dosen exit program entirely

        newFrame.setSize(Main.FRAME_WIDTH, Main.FRAME_HEIGHT); //get dimensions of 1080x720 from main.java
        newFrame.setResizable(false); //dont allow the canvas to be resizes

        //CREATE A BLANK JPANNEL
        blankCanvasPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) { //use paint component to draw
                super.paintComponent(g);

                //BACKGROUND WITH SCROLL OFFSET
                BufferedImage backgroundImage = loadImage("ViewPannelAssets\\Background.png");
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, -2000 + 50 + (scrollDist) / 5, 1080, 5000, this);
                }

                //LOAD THE SIDEBAR ON THE RIGHT SIDE OF THE SCREEN
                BufferedImage overlay = loadImage("ViewPannelAssets\\Sidebar.png");
                if (overlay != null) {
                    g.drawImage(overlay, 0, 0, getWidth(), getHeight(), this);
                }

                //CREATE A NEW FONT
                Font font = new Font("Arial", Font.PLAIN, 60); //create a new font for later
                int numberOfObjects = 0; //unused, would display number of rendered objects in the top bar
                int sizeOfFrontPng = 0; //side of block image (it gets larger when hovered)

                //LOAD THE BLOCK LIST (SORTED)
                for (int i = 0; i < blocksArray.length; i++) { //iterate through all blocks in the sorted list
                    if (isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150) && !isDropDownOpen) { //if hovered and drop down isnt open
                        BufferedImage highlighted = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png"); //draw selected
                        if (highlighted != null) {
                            g.drawImage(highlighted, 0 - 5, (scrollDist) + (i * 100) - 5, getWidth() + 10, getHeight() + 10, this); //draw background
                            g.setColor(Color.WHITE); //set the color of the text for later, and change the size
                            font = new Font("Arial", Font.PLAIN, 50); //text when hovered is a bit bigger then not
                            sizeOfFrontPng = 70; //change side of block image
                        }

                    } else {
                        BufferedImage unselected = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png"); //draw unselected
                        if (unselected != null) {
                            g.drawImage(unselected, 0, (scrollDist) + (i * 100), getWidth(), getHeight(), this); //draw background of object (unselected)
                            g.setColor(Color.LIGHT_GRAY); //set the color of the text for later, and change the size
                            font = new Font("Arial", Font.PLAIN, 40); //text when not hovered is a bit smaller then hovered
                            sizeOfFrontPng = 60; //change side of block image
                        }
                    }

                    //DRAW THE TEXT FOR OBJECTS
                    g.setFont(font); // finish setting the done
                    int y = (scrollDist) + (i * 100) + 100 + 20; // Adjust Y position based on loop iteration (for drwaing name and desc)
                    String text = String.valueOf(i + 1); //get the index of the block
                    g.drawString(text, 990, y); // draw the index of block on right side
                    text = blocksArray[i]; //get name of block
                    g.drawString(text, 110, y); //draw the name of each block

                    //RUN PARSER AGAIN (why idk)
                    parserAndReadin parser = new parserAndReadin();
                    ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                    Block block = parser.getBlockByName(blocks, blocksArray[i]);

                    //DRAW DESCRIPTION
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

                    numberOfObjects += 1; //unused for now
                }

                BufferedImage textBar = loadImage("ViewPannelAssets\\TopTypeBar.png"); // add a demo typing bar before we add the actually one (if time permits)
                if (textBar != null) {
                    g.drawImage(textBar, 0, 0, getWidth(), getHeight(), this);
                }
                BufferedImage quantityBG = loadImage("ViewPannelAssets\\Spacer.png"); // add spacer to top bar
                if (quantityBG != null) {
                    g.drawImage(quantityBG, 0, 0, getWidth(), getHeight(), this);
                }

                String buttonFilePath = ""; // File path for dropdown button
                String buttonFilePathON = ""; // File path for top
                String buttonFilePathOFF = ""; // File path for bottom
                switch (sortType) { // case statement to get file paths of sort method buttons
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
                    for (String option : sortOption) { //when drop down open, load all the buttons
                        BufferedImage sortMethod = loadImage("ViewPannelAssets\\" + option + ".png");
                        if (sortMethod != null) {
                            g.drawImage(sortMethod, 544, dropdownY, 276, 50, this);
                        }
                        dropdownY += 50;
                    }
                }

                BufferedImage tooltip = loadImage("ViewPannelAssets\\Tooltip.png"); //load the tooltip at the bottom of the screen
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
            public void mouseWheelMoved(MouseWheelEvent e) { // when scrolling update the background and object list
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
        
        Timer timer = new Timer(Main.FRAME_RATE, e -> blankCanvasPanel.repaint()); //update the screen at 60fps
        timer.start();
    }

    private static BufferedImage loadImage(String filename) { //load image with debugging (thanks chu)
        if (imageCache.containsKey(filename)) {
            return imageCache.get(filename);
        } else {
            File file = new File(filename);
            System.out.println("Attempting to load image from: " + file.getAbsolutePath());

            if (!file.exists()) {
                System.err.println("Error: File does not exist at path: " + file.getAbsolutePath());
                return null;
            }

            if (!file.canRead()) {
                System.err.println("Error: Cannot read file at path: " + file.getAbsolutePath());
                return null;
            }

            try {
                BufferedImage image = ImageIO.read(file);
                if (image != null) {
                    imageCache.put(filename, image);
                    return image;
                } else {
                    System.err.println("Error: File format not supported or file is corrupt: " + file.getAbsolutePath());
                    return null;
                }
            } catch (IOException e) {
                System.err.println("IOException while reading the image file: " + file.getAbsolutePath());
                e.printStackTrace();
                return null;
            }
        }
    }

    private static boolean isWithinButtonRange(int mouseX, int mouseY, int buttonX, int buttonWidth, int buttonY, int buttonHeight) {
        return mouseX > buttonX && mouseX < buttonWidth && mouseY > buttonY && mouseY < buttonHeight;
    } //if the mouse cursor is in a button range

    static class SortType { //handles sort button stuff
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

    private static void handleClickEventSort(int x, int y) { //handle click event for the sorting methods
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


            for (int i = 0; i < blocksArray.length; i++) { // iterate through all the blocks and figure which one user has clicked on
                parserAndReadin parser = new parserAndReadin();
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                // if hovered over the block in iteration
                if(isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150) && !isDropDownOpen) {
                    System.out.println(blocksArray[i]);
                    Block block = parser.getBlockByName(blocks, blocksArray[i]); //get the name of block then
                    SwingUtilities.invokeLater(() -> { //run the 3d gui for that block
                        BlockView3dGUI view = new BlockView3dGUI();
                        view.blockView3dGUI(block);
                    });
                }
            }

        } else { // else if the dropdown is open, allow user to click on the other sort methods
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
