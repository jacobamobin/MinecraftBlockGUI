import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.applet.MainFrame;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.universe.PlatformGeometry;
import com.sun.j3d.utils.universe.SimpleUniverse;

import javax.swing.*;
import javax.vecmath.Color3f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileReader;

import com.sun.j3d.utils.universe.ViewingPlatform;
import javax.media.j3d.*;

import javax.swing.*;
import java.awt.*;
import com.sun.j3d.utils.universe.*;
import com.sun.j3d.utils.geometry.ColorCube;
import javax.media.j3d.*;
import javax.vecmath.*;

public class BlockView3dGUI {

    public static void blockView3dGUI() {
        JFrame frame = new JFrame("3D Canvas Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        // Create panels
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        JPanel rightPanel = new JPanel();
        JPanel leftPanel = new JPanel();
        JPanel centerPanel = new JPanel(new BorderLayout()); // For 3D content

        // Set background colors
        topPanel.setBackground(Color.RED);
        bottomPanel.setBackground(Color.GREEN);
        rightPanel.setBackground(Color.BLUE);
        leftPanel.setBackground(Color.YELLOW);

        // Set layout manager (BorderLayout)
        frame.setLayout(new BorderLayout());

        // Adjust the sizes of the side panels
        topPanel.setPreferredSize(new Dimension(1080, 185));
        bottomPanel.setPreferredSize(new Dimension(1080, 185));
        rightPanel.setPreferredSize(new Dimension(315, 500));
        leftPanel.setPreferredSize(new Dimension(315, 500));

        // Set size for center panel
        centerPanel.setPreferredSize(new Dimension(450, 350));

        // Load the object file
        Scene scene = null;
        try {
            ObjectFile objFile = new ObjectFile(ObjectFile.RESIZE);
            scene = objFile.load(new FileReader("SpaceShip.obj"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        // Create a new simple universe
        SimpleUniverse universe = new SimpleUniverse();

        // Create a BranchGroup to hold the scene
        BranchGroup sceneBG = new BranchGroup();
        sceneBG.addChild(scene.getSceneGroup());

        // Add the loaded object to the scene
        universe.addBranchGraph(sceneBG);

        // Get the Canvas3D from the universe
        Canvas3D canvas = new Canvas3D(SimpleUniverse.getPreferredConfiguration());

        // Enable mouse rotation behavior
        ViewingPlatform viewingPlatform = universe.getViewingPlatform();
        OrbitBehavior orbit = new OrbitBehavior(canvas, OrbitBehavior.REVERSE_ROTATE);
        orbit.setSchedulingBounds(new BoundingSphere(new Point3d(), Double.MAX_VALUE));
        viewingPlatform.setViewPlatformBehavior(orbit);

        // Add the Canvas3D to the center panel
        centerPanel.add(canvas, BorderLayout.CENTER);

        // Add panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(bottomPanel, BorderLayout.SOUTH);
        frame.add(rightPanel, BorderLayout.EAST);
        frame.add(leftPanel, BorderLayout.WEST);
        frame.add(centerPanel, BorderLayout.CENTER);

        // Show the frame
        frame.setVisible(true);
    }
}