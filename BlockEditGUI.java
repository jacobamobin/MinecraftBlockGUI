import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BlockEditGUI {
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
         Timer timer = new Timer(1000 / Main.FRAME_RATE, e -> {
            mainPanel.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }
}
