import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class BlockView3dGUI {
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
        Timer timer = new Timer(1000 / Main.FRAME_RATE, e -> {
            frame.repaint(); // Repaint the canvas every frame
        });
        timer.start(); // Start the timer
    }

}
