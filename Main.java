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

    static final int FRAME_WIDTH = 1080;
    static final int FRAME_HEIGHT = 720;
    static final int FRAME_RATE = 60; // Frames per second (adjust as needed)

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenuGUI::mainMenu);
      
    }

    

    

    

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

}


