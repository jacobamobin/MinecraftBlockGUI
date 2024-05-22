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

    
    

}


