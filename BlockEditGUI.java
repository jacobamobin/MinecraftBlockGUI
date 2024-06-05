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
    private static String backgroundType = "HighRes/Background 1.png";

    public static void blockEditGUI() {

        JFrame newFrame = new JFrame("Edit Blocks");
        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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
                    case "Fire":
                        buttonFilePath = "ViewPannelAssets\\Flammable.png";
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
                BufferedImage backgroundImage = loadImage(backgroundType);
                if (backgroundImage != null) {
                    g.drawImage(backgroundImage, 0, -2000 + 50 + (scrollDist) / 3, 1080, 10000, this);
                }

                parserAndReadin parser = new parserAndReadin(); // initiallize new parser
                performSortingMethods sorter = new performSortingMethods(); // initialize new sortingmethods
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt"); // read in our blocks data file
                blocksArray = sorter.sortBlockParameter("name", "asc"); // Initial sorting when gui open                
                Font font = new Font("Arial", Font.PLAIN, 60);
                int numberOfObjects = 0;
                int sizeOfFrontPng = 0;


                for (int i = 0; i < blocksArray.length; i++) {
                    int y = (scrollDist) + (i * 100) + 100 + 20; // Adjust Y position based on loop iteration (for drwaing name and desc)
                    Block block = parser.getBlockByName(blocks, blocksArray[i]);
                    if (isWithinButtonRange(mouseX, mouseY, 5, 950, (scrollDist) + (i * 100) + 50, (scrollDist) + (i * 100) + 150)) {
                        BufferedImage highlighted = loadImage("ViewPannelAssets\\BackGroundOfBlockSelected.png");
                        if (highlighted != null) {
                            g.drawImage(highlighted, 0 - 5 , (scrollDist) + (i * 100) - 5, 1080 + 10, 720 + 10, this);
                            g.setColor(Color.WHITE);
                            font = new Font("Arial", Font.PLAIN, 45);
                            sizeOfFrontPng = 70;
                        }
                        BufferedImage blockFront = loadImage("object\\" + block.getName() + "\\" + "front.jpg.jpg"); // add front png of block to block
                        if (blockFront != null) {
                            g.drawImage(blockFront, 70, y-60, sizeOfFrontPng, sizeOfFrontPng, this);
                        } else {
                            blockFront = loadImage("object\\" + "front.jpg.jpg");
                            g.drawImage(blockFront, 70, y-50, sizeOfFrontPng, sizeOfFrontPng, this);
                        }

                    } else {
                        BufferedImage unselected = loadImage("ViewPannelAssets\\BackGroundOfBlockUnselected.png");
                        if (unselected != null) {
                            g.drawImage(unselected, 0 , (scrollDist) + (i * 100), 1080, 720, this);
                            g.setColor(Color.LIGHT_GRAY);
                            font = new Font("Arial", Font.PLAIN, 40);
                            sizeOfFrontPng = 60;
                        }
                        BufferedImage blockFront = loadImage("object\\" + block.getName() + "\\" + "front.jpg.jpg"); // add front png of block to block
                        if (blockFront != null) {
                            g.drawImage(blockFront, 80, y-50, sizeOfFrontPng, sizeOfFrontPng, this);
                        } else {
                            blockFront = loadImage("object\\" + "front.jpg.jpg");
                            g.drawImage(blockFront, 80, y-50, sizeOfFrontPng, sizeOfFrontPng, this);
                        }
                    }

                    g.setFont(font);
                    y = (scrollDist) + (i * 100) + 100 + 20 - 10; // Adjust Y position based on loop iteration
                    String text = String.valueOf(i + 1);
                    g.drawString(text, 15, y);
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
        canvas3.setBackground(Color.LIGHT_GRAY); // Set background color for demonstration

        // Add buttons to canvas3
        canvas3.setLayout(new GridLayout(3, 1)); // 3 rows, 1 column
        JButton addButton = new JButton();
        JButton removeButton = new JButton();
        JButton editButton = new JButton();

        // Load images for the buttons' states
        ImageIcon addButtonIconON = new ImageIcon("ViewPannelAssets\\addOn.png");
        ImageIcon addButtonIconOFF = new ImageIcon("ViewPannelAssets\\addOff.png");
        ImageIcon removeButtonIconON = new ImageIcon("ViewPannelAssets\\removeOn.png");
        ImageIcon removeButtonIconOFF = new ImageIcon("ViewPannelAssets\\removeOff.png");
        ImageIcon editButtonIconON = new ImageIcon("ViewPannelAssets\\editOn.png");
        ImageIcon editButtonIconOFF = new ImageIcon("ViewPannelAssets\\editOff.png");

        // Set icons to the buttons
        addButton.setIcon(addButtonIconOFF);
        addButton.setPressedIcon(addButtonIconON);
        removeButton.setIcon(removeButtonIconOFF);
        removeButton.setPressedIcon(removeButtonIconON);
        editButton.setIcon(editButtonIconOFF);
        editButton.setPressedIcon(editButtonIconON);

        // Add action listeners to buttons
        addButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                // Initialize variables
                String blockName;
                boolean blockRenewability = false; // default value
                String blockStackability;
                double blockBlastRes;
                double blockHardness;
                double blockLuminous;
                boolean blockFlammability = false; // default value
                String blockDimension;

                parserAndReadin parser = new parserAndReadin();
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                System.out.println("Button 1 clicked");

                // Get Block Name
                blockName = JOptionPane.showInputDialog(null, "Enter the name:");
                if (blockName != null && !blockName.trim().isEmpty()) 
                {
                    System.out.println("You entered: " + blockName);
                    
                    // Get Renewability
                    int isRenewable = JOptionPane.showConfirmDialog(null, "Is the block renewable?", "Renewability", JOptionPane.YES_NO_OPTION);
                    if (isRenewable == JOptionPane.YES_OPTION) 
                    {
                        System.out.println("User chose Yes.");
                        blockRenewability = true;
                    } 
                    else if (isRenewable == JOptionPane.NO_OPTION) 
                    {
                        System.out.println("User chose No.");
                        blockRenewability = false;
                    } 
                    else 
                    {
                        System.out.println("User cancelled the operation.");
                        return;
                    }

                    // Get Stackability
                    blockStackability = JOptionPane.showInputDialog(null, "Enter the stackability:");
                    if (blockStackability == null || blockStackability.trim().isEmpty()) 
                    {
                        System.out.println("Input dialog was cancelled.");
                        return;
                    }

                    // Get Blast Resistance
                    try 
                    {
                        blockBlastRes = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the blast resistance:"));
                    } 
                    catch (NumberFormatException ex) 
                    {
                        System.out.println("Invalid input for blast resistance.");
                        JOptionPane.showMessageDialog(null, "Invalid input for blast resistance. Please put in a numeric value next time!");
                        return;
                    }

                    // Get Hardness
                    try 
                    {
                        blockHardness = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the hardness:"));
                    } 
                    catch (NumberFormatException ex) 
                    {
                        System.out.println("Invalid input for hardness.");
                        JOptionPane.showMessageDialog(null, "Invalid input for blast hardness. Please put in a numeric value next time!");
                        return;
                    }

                    // Get Luminous
                    try 
                    {
                        blockLuminous = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the luminous:"));
                    } 
                    catch (NumberFormatException ex) 
                    {
                        System.out.println("Invalid input for luminous.");
                        JOptionPane.showMessageDialog(null, "Invalid input for luminous. Please put in a numeric value next time!");
                        return;
                    }

                    // Get Flammability
                    int isFlammable = JOptionPane.showConfirmDialog(null, "Is the block flammable?", "Flammability", JOptionPane.YES_NO_OPTION);
                    if (isFlammable == JOptionPane.YES_OPTION) 
                    {
                        System.out.println("User chose Yes.");
                        blockFlammability = true;
                    } 
                    else if (isFlammable == JOptionPane.NO_OPTION) 
                    {
                        System.out.println("User chose No.");
                        blockFlammability = false;
                    } 
                    else 
                    {
                        System.out.println("User cancelled the operation.");
                        return;
                    }

                    // Get Dimension
                    blockDimension = JOptionPane.showInputDialog(null, "Enter the dimension:");
                    if (blockDimension == null || blockDimension.trim().isEmpty()) 
                    {
                        System.out.println("Input dialog was cancelled.");
                        return;
                    }

                    // Create a new Block and add it to the list
                    Block newBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                    if (parser.addEntry(blocks, newBlock, "data//blocks.txt"))           
                        System.out.println("New block added: " + newBlock);
                    else
                        JOptionPane.showMessageDialog(null, "Block of the same name already exists!");
                } 
                else 
                    System.out.println("Input dialog was cancelled or empty.");
            }
        });
        
        removeButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String blockName;
                
                parserAndReadin parser = new parserAndReadin();
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                System.out.println("Button 2 clicked");
                blockName = JOptionPane.showInputDialog(null, "Enter the name:");
                if (blockName != null && !blockName.trim().isEmpty())
                {
                    System.out.println("You're trying to remove: " + blockName);
                    if (parser.removeEntry(blocks, blockName, "data//blocks.txt")) 
                        JOptionPane.showMessageDialog(null, "Successfully removed " + blockName);
                    else 
                        JOptionPane.showMessageDialog(null, "Hm, can't find "+blockName+", are you sure you spelled it right?");
                }
                else              
                    System.out.println("dialog closed.");
            }
        });

        editButton.addActionListener(new ActionListener() 
        {
            @Override
            public void actionPerformed(ActionEvent e) 
            {
                String blockName;
                boolean blockRenewability = false; // default value
                String blockStackability;
                double blockBlastRes;
                double blockHardness;
                double blockLuminous;
                boolean blockFlammability = false; // default value
                String blockDimension;                
        
                System.out.println("Button 3 clicked");
                parserAndReadin parser = new parserAndReadin();
                ArrayList<Block> blocks = parser.readInData("data/Blocks.txt");
                String blockChoice = JOptionPane.showInputDialog(null, "Enter the name:");
                if (blockChoice != null && !blockChoice.trim().isEmpty()) 
                {
                    System.out.println("You entered: " + blockChoice);
                    try 
                    {
                        Block preEditBlock = parser.getBlockByName(blocks, blockChoice);
                        if (preEditBlock == null) 
                        {
                            throw new Exception("Block not found");
                        }
                        blockName = preEditBlock.getName();
                        blockRenewability = preEditBlock.getRenewability();
                        blockStackability = preEditBlock.getStackability();
                        blockBlastRes = preEditBlock.getBlastres();
                        blockHardness = preEditBlock.getHardness();
                        blockLuminous = preEditBlock.getLuminous();
                        blockFlammability = preEditBlock.getFlammable();
                        blockDimension = preEditBlock.getDimension();
                        Block updatedBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                        Object[] options = {"Name", "Renewability", "Stackability", "Blastres", "Hardness", "Luminousity", "Flammability", "Dimension"};
                        int choice = JOptionPane.showOptionDialog(null, 
                                                                "What attribute would you like to change?", 
                                                                "Block Edit Panel", 
                                                                JOptionPane.YES_NO_CANCEL_OPTION, 
                                                                JOptionPane.QUESTION_MESSAGE, 
                                                                null, 
                                                                options, 
                                                                options[0]);
                        if (choice != -1) 
                        {
                            System.out.println("User chose: " + options[choice]);
                            switch (choice) 
                            {
                                case 0: // Name
                                    String newName = JOptionPane.showInputDialog(null, "Enter the new name:");
                                    if (newName != null && !newName.trim().isEmpty()) 
                                    {
                                        updatedBlock = new Block(newName, blockRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                            blockName = newName; // Update blockName if the name changes
                                        } 
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    }
                                    break;
                                case 1: // Renewability
                                    boolean newRenewability;
                                    int renewabilityChoice = JOptionPane.showConfirmDialog(null, "Is the block renewable?", "Renewability", JOptionPane.YES_NO_OPTION);
                                    if (renewabilityChoice == JOptionPane.YES_OPTION) 
                                    {
                                        System.out.println("User chose Yes.");
                                        newRenewability = true;
                                    } 
                                    else if (renewabilityChoice == JOptionPane.NO_OPTION) 
                                    {
                                        System.out.println("User chose No.");
                                        newRenewability = false;
                                    } 
                                    else 
                                    {
                                        System.out.println("User cancelled the operation.");
                                        return;
                                    }
                                    updatedBlock = new Block(blockName, newRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                                    if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                    {
                                        JOptionPane.showMessageDialog(null, "Success!");
                                    } 
                                    else 
                                    {
                                        JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                    }
                                    break;
                                case 2: // Stackability
                                    String newStackability = JOptionPane.showInputDialog(null, "Enter the new stackability:");
                                    if (newStackability != null && !newStackability.trim().isEmpty()) 
                                    {
                                        updatedBlock = new Block(blockName, blockRenewability, newStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                        } 
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    }
                                    break;
                                case 3: // Blast resistance
                                    try 
                                    {
                                        double newBlastRes = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the new blast resistance:"));
                                        updatedBlock = new Block(blockName, blockRenewability, blockStackability, newBlastRes, blockHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                        } 
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    } 
                                    catch (NumberFormatException ex) 
                                    {
                                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value!");
                                    }
                                    break;
                                case 4: // Hardness
                                    try 
                                    {
                                        double newHardness = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the new hardness:"));
                                        updatedBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, newHardness, blockLuminous, blockFlammability, blockDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                        } 
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    } 
                                    catch (NumberFormatException ex) 
                                    {
                                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value!");
                                    }
                                    break;
                                case 5: // Luminosity
                                    try 
                                    {
                                        double newLuminous = Double.parseDouble(JOptionPane.showInputDialog(null, "Enter the new luminosity:"));
                                        updatedBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, blockHardness, newLuminous, blockFlammability, blockDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                        } 
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    } 
                                    catch (NumberFormatException ex) 
                                    {
                                        JOptionPane.showMessageDialog(null, "Invalid input. Please enter a numeric value!");
                                    }
                                    break;
                                case 6: // Flammability
                                    boolean newFlammability;
                                    int flammChoice = JOptionPane.showConfirmDialog(null, "Is it flammable?", "Flammability", JOptionPane.YES_NO_OPTION);
                                    if (flammChoice == JOptionPane.YES_OPTION) 
                                    {
                                        System.out.println("User chose Yes.");
                                        newFlammability = true;
                                    } 
                                    else if (flammChoice == JOptionPane.NO_OPTION) 
                                    {
                                        System.out.println("User chose No.");
                                        newFlammability = false;
                                    } 
                                    else 
                                    {
                                        System.out.println("User cancelled the operation.");
                                        return;
                                    }
                                    updatedBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, newFlammability, blockDimension, false, "null");
                                    if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                    {
                                        JOptionPane.showMessageDialog(null, "Success!");
                                    } 
                                    else 
                                    {
                                        JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                    }
                                    break;
                                case 7: // Dimension
                                    String newDimension = JOptionPane.showInputDialog(null, "Where does the block spawn?");
                                    if (newDimension != null && !newDimension.trim().isEmpty()) 
                                    {
                                        updatedBlock = new Block(blockName, blockRenewability, blockStackability, blockBlastRes, blockHardness, blockLuminous, blockFlammability, newDimension, false, "null");
                                        if (parser.editEntry(blocks, blockChoice, updatedBlock, "data/Blocks.txt")) 
                                        {
                                            JOptionPane.showMessageDialog(null, "Success!");
                                        }
                                        else 
                                        {
                                            JOptionPane.showMessageDialog(null, "Error occurred. Try again!");
                                        }
                                    }
                                    break;
                                default:
                                    System.out.println("I don't know what happened???");
                                    break;
                            }
                        } 
                        else 
                        {
                            System.out.println("User cancelled the operation.");
                        }
                    } 
                    catch (Exception ex) 
                    {
                        JOptionPane.showMessageDialog(null, "Block not found: " + blockChoice);
                        System.out.println("Block not found: " + blockChoice);
                    }
                }            
            }
        });
                   
        // Add buttons to canvas3
        canvas3.add(addButton);
        canvas3.add(removeButton);
        canvas3.add(editButton);

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
                sortType = "Stackable";
                backgroundType = "HighRes/Background 2.png";
            } else if (sortType == "Stackable") {
                    sortType = "Dimension";
                backgroundType = "HighRes/Background 3.png";
            } else if (sortType == "Dimension") {
                sortType = "Hardness";
                backgroundType = "HighRes/Background 4.png";
            } else if (sortType == "Hardness") {
                sortType = "BlastRes";
                backgroundType = "HighRes/Background 5.png";
            } else if (sortType == "BlastRes") {
                sortType = "Luminous";
                backgroundType = "HighRes/Background 6.png";
            } else if (sortType == "Luminous") {
                sortType = "Renewable";
                backgroundType = "HighRes/Background 7.png";
            } else if (sortType == "Renewable") {
                sortType = "Fire";
                backgroundType = "HighRes/Background 8.png";
            } else if (sortType == "Fire") {
                sortType = "Name";
                backgroundType = "HighRes/Background 1.png";
            } else {
                sortType = "Name";
                backgroundType = "HighRes/Background 1.png";
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
                //e.printStackTrace();
                return null;
            }
        }
    }
}