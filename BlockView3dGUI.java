import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import javax.swing.*;

public class BlockView3dGUI {

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
            // Initialize Java 3D canvas and universe
            GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
            if (config == null) {
                throw new RuntimeException("Preferred graphics configuration is null.");
            }

            Canvas3D canvas3D = new Canvas3D(config);
            centerPanel.add(canvas3D, BorderLayout.CENTER);

            SimpleUniverse universe = new SimpleUniverse(canvas3D);
            universe.getViewingPlatform().setNominalViewingTransform(); // Reset the viewing platform

            // Create a textured cube
            BranchGroup root = new BranchGroup();
            TransformGroup cubeTransformGroup = createTexturedCube();
            root.addChild(cubeTransformGroup);

            // Add lighting
            addLight(root);

            // Add floor
            addFloor(root);

            // Add mouse rotate behavior
            MouseRotate mouseRotate = new MouseRotate();
            mouseRotate.setTransformGroup(cubeTransformGroup);
            mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
            root.addChild(mouseRotate);

            // Add additional smaller cubes in a grid pattern
            addGridBackgroundCubes(root, 10, 6, 0.45f); // 5x5 grid with 2.0f spacing

            // Add the root branch group to the universe
            universe.addBranchGraph(root);

            // Show the frame
            frame.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(frame, "An error occurred while initializing the 3D canvas: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static TransformGroup createTexturedCube() {
        // Create a transform group for the cube
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        // Create textured cube
        Box box = new Box(0.3f, 0.3f, 0.3f, Box.GENERATE_TEXTURE_COORDS, new Appearance());

        // Apply textures to each side
        setTexture(box);

        // Add the cube to the transform group
        transformGroup.addChild(box);

        return transformGroup;
    }

    private static void setTexture(Box box) {
        // Load textures for each side
        TextureLoader loader = new TextureLoader("object/front.jpg", null);
        Texture textureFront = loader.getTexture();
        loader = new TextureLoader("object/back.jpg", null);
        Texture textureBack = loader.getTexture();
        loader = new TextureLoader("object/top.jpg", null);
        Texture textureTop = loader.getTexture();
        loader = new TextureLoader("object/bottom.jpg", null);
        Texture textureBottom = loader.getTexture();
        loader = new TextureLoader("object/left.jpg", null);
        Texture textureLeft = loader.getTexture();
        loader = new TextureLoader("object/right.jpg", null);
        Texture textureRight = loader.getTexture();

        Appearance front = new Appearance();
        front.setTexture(textureFront);
        Appearance back = new Appearance();
        back.setTexture(textureBack);
        Appearance top = new Appearance();
        top.setTexture(textureTop);
        Appearance bottom = new Appearance();
        bottom.setTexture(textureBottom);
        Appearance left = new Appearance();
        left.setTexture(textureLeft);
        Appearance right = new Appearance();
        right.setTexture(textureRight);

        // Set textures to each side
        for (int i = 0; i < box.numChildren(); i++) {
            Shape3D face = (Shape3D) box.getChild(i);
            switch (i) {
                case 0:
                    face.setAppearance(front);
                    break;
                case 1:
                    face.setAppearance(back);
                    break;
                case 2:
                    face.setAppearance(top);
                    break;
                case 3:
                    face.setAppearance(bottom);
                    break;
                case 4:
                    face.setAppearance(left);
                    break;
                case 5:
                    face.setAppearance(right);
                    break;
            }
        }
    }

    private static void addLight(BranchGroup root) {
        Color3f lightColor = new Color3f(1.0f, 1.0f, 1.0f); // White light
        Vector3f lightDirection = new Vector3f(-1.0f, -1.0f, -1.0f); // Directional light from top-left
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
        root.addChild(light);
    }

    private static void addFloor(BranchGroup root) {
        // Load the texture image
        TextureLoader loader = new TextureLoader("object/top.jpg", null);
        Texture floorTexture = loader.getTexture();

        // Create a textured appearance for the floor
        Appearance floorApp = new Appearance();
        floorApp.setTexture(floorTexture);

        // Create texture attributes and set the texture mode to modulate
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        floorApp.setTextureAttributes(texAttr);

        // Create the geometry for the floor
        QuadArray floorGeometry = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);
        floorGeometry.setCoordinate(0, new Point3f(-10.0f, -1.5f, 5.0f));
        floorGeometry.setCoordinate(1, new Point3f(10.0f, -1.5f, 5.0f));
        floorGeometry.setCoordinate(2, new Point3f(10.0f, -1.5f, -15.0f));
        floorGeometry.setCoordinate(3, new Point3f(-10.0f, -1.5f, -15.0f));

        // Set the texture coordinates for the floor
        floorGeometry.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
        floorGeometry.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));
        floorGeometry.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
        floorGeometry.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));

        // Create the floor shape and set its appearance
        Shape3D floor = new Shape3D(floorGeometry, floorApp);
        root.addChild(floor);
    }

    private static void addGridBackgroundCubes(BranchGroup root, int rows, int cols, float spacing) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TransformGroup cubeTransformGroup = createRotatingBackgroundCube();
                Transform3D transform = new Transform3D();
                // Position the cubes in a grid pattern
                transform.setTranslation(new Vector3f(i * spacing - (rows - 1) * spacing / 6 - 1.3f, j * spacing - (cols - 1) * spacing / 6 -1f, -2.5f));
                cubeTransformGroup.setTransform(transform);
                root.addChild(cubeTransformGroup);
            }
        }
    }

    private static TransformGroup createRotatingBackgroundCube() {
        // Create a transform group for the background cube
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Create smaller textured cube
        Box box = new Box(0.1f, 0.1f, 0.1f, Box.GENERATE_TEXTURE_COORDS, new Appearance());

        // Apply textures to each side
        setTexture(box);

        // Add the cube to the transform group
        transformGroup.addChild(box);

        return transformGroup;
    }

}
