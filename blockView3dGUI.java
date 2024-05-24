import com.sun.j3d.loaders.Scene;
import com.sun.j3d.loaders.objectfile.ObjectFile;
import com.sun.j3d.utils.behaviors.vp.OrbitBehavior;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;
import com.sun.j3d.utils.universe.ViewingPlatform;
import com.sun.j3d.utils.geometry.Text2D;

import javax.media.j3d.*;
import javax.swing.*;
import javax.vecmath.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BlockView3dGUI {

    private static TransformGroup objTransformGroup;
    private static Transform3D transform3D = new Transform3D();
    private static double zoom = 1.0;

    public static void blockView3dGUI() {
        JFrame frame = new JFrame("3D Canvas Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1080, 720);

        // Create panels
        JPanel centerPanel = new JPanel(new BorderLayout()); // For 3D content

        // Set layout manager (BorderLayout)
        frame.setLayout(new BorderLayout());

        // Set size for center panel
        centerPanel.setPreferredSize(new Dimension(1080, 620));

        // Add panels to the frame
        frame.add(centerPanel, BorderLayout.CENTER);

        try {
            // Preprocess the OBJ file
            String preprocessedFilename = "preprocessed_dick.obj";
            preprocessOBJFile("dick.obj", preprocessedFilename);

            // Initialize Java 3D canvas and universe
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            if (config == null) {
                throw new RuntimeException("Preferred graphics configuration is null.");
            }

            Canvas3D canvas3D = new Canvas3D(config);
            centerPanel.add(canvas3D, BorderLayout.CENTER);

            SimpleUniverse universe = new SimpleUniverse(canvas3D);

            // Load the 3D model
            BranchGroup scene = load3DModel(preprocessedFilename);
            if (scene != null) {
                // Set up scaling and position
                objTransformGroup = new TransformGroup();
                objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
                objTransformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
                Transform3D scaleTransform = new Transform3D();
                scaleTransform.setScale(1); // Adjust scale as needed
                objTransformGroup.setTransform(scaleTransform);
                objTransformGroup.addChild(scene);

                // Set up viewing transform
                ViewingPlatform viewingPlatform = universe.getViewingPlatform();
                TransformGroup viewTransform = viewingPlatform.getViewPlatformTransform();
                Transform3D viewMatrix = new Transform3D();
                viewMatrix.lookAt(new Point3d(0, 0, 10), new Point3d(0, 0, 0), new Vector3d(0, 1, 0)); // Adjust camera position
                viewMatrix.invert();
                viewTransform.setTransform(viewMatrix);

                // Add mouse listeners for rotating the block and text
                canvas3D.addMouseMotionListener(new MouseMotionAdapter() {
                    private int lastX, lastY;

                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int x = e.getX();
                        int y = e.getY();
                        int dx = x - lastX;
                        int dy = y - lastY;

                        Transform3D rotationX = new Transform3D();
                        rotationX.rotX(Math.toRadians(dy) / 5.0);

                        Transform3D rotationY = new Transform3D();
                        rotationY.rotY(Math.toRadians(dx) / -5.0);

                        transform3D.mul(rotationX);
                        transform3D.mul(rotationY);

                        transform3D.setScale(zoom);
                        objTransformGroup.setTransform(transform3D);

                        lastX = x;
                        lastY = y;
                    }

                    @Override
                    public void mouseMoved(MouseEvent e) {
                        lastX = e.getX();
                        lastY = e.getY();
                    }
                });

                // Add mouse listener for zooming
                canvas3D.addMouseWheelListener(e -> {
                    int rotation = e.getWheelRotation();
                    zoom *= (rotation > 0) ? 1.1 : 0.9;

                    transform3D.setScale(zoom);
                    objTransformGroup.setTransform(transform3D);
                });

                // Create a white directional light
                Color3f lightColor = new Color3f(1.0f, 1.0f, 1.0f); // White light
                Vector3f lightDirection = new Vector3f(-1.0f, -1.0f, -1.0f); // Directional light from top-left
                DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
                light.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));

                // Create a plane for the floor
                Appearance floorApp = new Appearance();
                floorApp.setMaterial(new Material());
                floorApp.setColoringAttributes(new ColoringAttributes(0.3f, 0.3f, 0.3f, ColoringAttributes.NICEST));
                QuadArray floorGeometry = new QuadArray(4, QuadArray.COORDINATES);
                floorGeometry.setCoordinate(0, new Point3f(-10.0f, -2.0f, 10.0f));
                floorGeometry.setCoordinate(1, new Point3f(10.0f, -2.0f, 10.0f));
                floorGeometry.setCoordinate(2, new Point3f(10.0f, -2.0f, -10.0f));
                floorGeometry.setCoordinate(3, new Point3f(-10.0f, -2.0f, -10.0f));
                Shape3D floor = new Shape3D(floorGeometry, floorApp);

                // Add the background and floor to the scene
                BranchGroup root = new BranchGroup();
                root.addChild(light);
                root.addChild(floor);
                root.addChild(objTransformGroup);

                root.compile();
                universe.addBranchGraph(root);
            } else {
                System.err.println("Failed to load the 3D model.");
            }

            // Show the frame
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while initializing the 3D canvas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static BranchGroup load3DModel(String filename) {
        ObjectFile loader = new ObjectFile(ObjectFile.RESIZE);
        Scene scene = null;
        try {
            scene = loader.load(new FileReader(filename));
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + filename);
        } catch (Exception e) {
            System.err.println("Error loading model: " + e.getMessage());
            e.printStackTrace();
        }

        if (scene != null) {
            return scene.getSceneGroup();
        } else {
            return new BranchGroup(); // Return an empty group in case of failure
        }
    }

    private static void preprocessOBJFile(String inputFilename, String outputFilename) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(inputFilename));
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilename));

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("v ") || line.startsWith("f ") || line.startsWith("vn ") || line.startsWith("vt ")) {
                writer.write(line);
                writer.newLine();
            }
            // Ignore other lines like 'o', 'mtllib', etc.
        }
        reader.close();
        writer.close();
    }

}
