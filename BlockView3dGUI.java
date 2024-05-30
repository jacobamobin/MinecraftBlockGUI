//This file is by Jacob Mobin
import javax.media.j3d.*;
import javax.vecmath.*;

import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Text2D;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
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

            // Add floor and wall
            addFloor(root);
            addBackgroundWall(root);


            // Add mouse rotate behavior
            MouseRotate mouseRotate = new MouseRotate();
            mouseRotate.setTransformGroup(cubeTransformGroup);
            mouseRotate.setSchedulingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
            root.addChild(mouseRotate);

            // Add additional smaller cubes in a grid pattern
            addGridBackgroundCubes(root, 10, 6, 0.45f); // 5x5 grid with 2.0f spacing

            addOverlay(root);

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

    /*
    * CUBE LOGIC METHODS BELOW
    * Rendering main cube, side panel ovelay cubes and background cubes
    * */
    private static TransformGroup createTexturedCube() {
        // Create a transform group for the cube
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);

        // Create textured cube
        Box box = new Box(0.25f, 0.25f, 0.25f, Box.GENERATE_TEXTURE_COORDS, new Appearance());

        // Apply textures to each side
        setTexture(box, "Crafting Table");

        // Add the cube to the transform group
        transformGroup.addChild(box);

        return transformGroup;
    }

    // New method to create specific overlay cubes with different textures
    private static TransformGroup createSpecificOverlayCube(String name) {
        // Create a transform group for the overlay cube
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Create a smaller textured cube for the overlay
        Box box = new Box(0.05f, 0.05f, 0.05f, Box.GENERATE_TEXTURE_COORDS, new Appearance());

        // Apply different textures to each side
        setTexture(box, name);

        // Add the cube to the transform group
        transformGroup.addChild(box);

        return transformGroup;
    }

    //Add a grid of background cubes
    private static void addGridBackgroundCubes(BranchGroup root, int rows, int cols, float spacing) {
        String[] avalableTextures = {
                "Stone",
                        "Cherry Log",
                        "Dirt",
                        "Birch Log",
                        "Diorite",
                        "Oak Log",
                        "Deepslate Tiles",
                        "Prismarine",
                        "Cobblestone",
                        "Piston",
                        "Bedrock",
                        "Sculk",
                        "Sponge",
                        "End stone",
                        "Obsidian",
                        "Polished Andesite",
                        "Wool",
                        "Concrete",
                        "Leaves",
                        "Basalt",
                        "Granite",
                        "Shulker box",
                        "Hay bale",
                        "Redstone Lamp",
                        "Sea lantern",
                        "Soul Sand",
                        "Calcite",
                        "Command block",
                        "Lodestone",
                        "Magma block",
                        "Crafting Table",
                        "Juke Box",
                        "Note Block",
                        "Observer",
                        "Block of Diamond",
                        "Block of Gold",
                        "Block of Emerald",
                        "Block of Iron",
                        "Iron ore",
                        "Diamond ore",
                        "Gold ore",
                        "Emerald ore",
                        "Bricks",
                        "Bookshelf",
                        "Furnace",
                        "Ice",
                        "Packed Ice",
                        "Redstone ore",
                        "Block of Redstone",
                        "Acacia plank",
                        "Barrel",
                        "Cactus",
                        "Dark Prismarine",
                        "Gilded Blackstone",
                        "Fire Coral Block",
                        "Grass Block",
                        "Jack o'Lantern",
                        "Shroomlight",
                        "Slime Block",
                        "Podzol",
                        "Dried Kelp Block",
                        "TNT",
                        "Pumpkin",
                        "Farmland",
                        "Gravel",
                        "Blast Furnace",
                        "Rooted Dirt",
                        "Horn Coral Block",
                        "Coarse Dirt",
                        "Infested Cobblestone",
                        "Sand",
                        "Sandstone",
                        "Smithing Table",
                        "Smoker",
                        "Coal Ore",
                        "Purpur Block",
                        "Cracked Stone Brick",
                        "Terracotta",
                        "Honeycomb Block",
                        "Reinforced Deepslate",
                        "Smooth Red Sandstone",
                        "Brown Mushroom Block",
                        "Red Mushroom Block",
                        "Stem Mushroom Block",
                        "Concrete Powder",
                        "Tuff",
                        "Target",
                        "Nylium",
                        "Mud",
                        "Mycelium",
                        "Muddy Mangrove Roots",
                        "Nether Bricks",
                        "Mud Bricks",
                        "Nether Gold Ore",
                        "Nether Wart Block",
                        "Warped Wart Block",
                        "Waxed Block of Copper",
                        "Packed Mud",
                        "Pearlescent",
                        "Dropper"
        };

        String[] avalableTextures2 = {"TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT",
                "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT",
                "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT", "TNT"};

        int x = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.println(avalableTextures[x]);
                TransformGroup cubeTransformGroup = createRotatingBackgroundCube(avalableTextures[x]); //scrapped becuase it was too much for school pc to handle as is
                Transform3D transform = new Transform3D();
                // Position the cubes in a grid pattern
                transform.setTranslation(new Vector3f(i * spacing - (rows - 1) * spacing / 6 - 1.3f, j * spacing - (cols - 1) * spacing / 6 -1f, -2.7f));
                cubeTransformGroup.setTransform(transform);
                root.addChild(cubeTransformGroup);
                x++;
            }

        }
    }

    //This method creates the background cubes, these are intended to slowly rotate (If time permits)
    private static TransformGroup createRotatingBackgroundCube(String texture) {
        // Create a transform group for the background cube
        TransformGroup transformGroup = new TransformGroup();
        transformGroup.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

        // Create smaller textured cube
        Box box = new Box(0.1f, 0.1f, 0.1f, Box.GENERATE_TEXTURE_COORDS, new Appearance());

        // Apply textures to each side
        setTexture(box, texture);

        // Add the cube to the transform group
        transformGroup.addChild(box);

        return transformGroup;
    }

    //This method rotates the front back left and right images to line up with the 3d cube
    private static BufferedImage rotateImage(BufferedImage originalImage, double angle) {
        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        BufferedImage rotatedImage = new BufferedImage(height, width, originalImage.getType());
        AffineTransform transform = new AffineTransform();
        transform.translate((height - width) / 2, (width - height) / 2);
        transform.rotate(Math.toRadians(angle), width / 2, height / 2);

        AffineTransformOp op = new AffineTransformOp(transform, AffineTransformOp.TYPE_BILINEAR);
        op.filter(originalImage, rotatedImage);

        return rotatedImage;
    }

    //This method rotates the front back left and right images to line up with the 3d cube
    private static Texture loadAndRotateTexture(String imagePath, double angle) {
        TextureLoader loader = new TextureLoader(imagePath, null);
        ImageComponent2D originalImage = loader.getImage();
        BufferedImage rotatedImage = rotateImage(originalImage.getImage(), angle);
        return new TextureLoader(rotatedImage, (String) null).getTexture();
    }

    //This method sets the textures on each face of the cube, and uses the rotate image method to fix texture orientation
    private static void setTexture(Box box, String name) {
        // Load textures for each side
        Texture textureFront = loadAndRotateTexture("object/" + name + "/front.jpg.jpg", 90);
        Texture textureBack = loadAndRotateTexture("object/" + name + "/back.jpg.jpg", 270);
        Texture textureBottom = new TextureLoader("object/" + name + "/bottom.jpg.jpg", null).getTexture(); // No rotation needed
        Texture textureTop = new TextureLoader("object/" + name + "/top.jpg.jpg", null).getTexture(); // No rotation needed
        Texture textureLeft = loadAndRotateTexture("object/" + name + "/left.jpg.jpg", 90);
        Texture textureRight = loadAndRotateTexture("object/" + name + "/right.jpg.jpg", 90);
        //System.out.println(name);

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




    /*
    * SCENE METHODS
    * These methods build the actually scene
    * */

    //use Colors and Vectors to create a directional light with a range of 100f for the scenes lighting
    private static void addLight(BranchGroup root) {
        Color3f lightColor = new Color3f(1.0f, 1.0f, 1.0f); // White light
        Vector3f lightDirection = new Vector3f(-1.0f, -1.0f, -1.0f); // Directional light from top-left
        DirectionalLight light = new DirectionalLight(lightColor, lightDirection);
        light.setInfluencingBounds(new BoundingSphere(new Point3d(0, 0, 0), 100));
        root.addChild(light);
    }

    //Add a floor to the scene using floor and texture co-ordinates
    private static void addFloor(BranchGroup root) {
        // Load the texture image
        TextureLoader loader = new TextureLoader("object/ground.jpg", null);
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

    //Add a background wall to the scene using the same logic and texture as the ground method
    private static void addBackgroundWall(BranchGroup root) {
        // Load the texture image for the wall
        TextureLoader loader = new TextureLoader("object/ground.jpg", null);
        Texture wallTexture = loader.getTexture();

        // Create a textured appearance for the wall
        Appearance wallApp = new Appearance();
        wallApp.setTexture(wallTexture);

        // Create texture attributes and set the texture mode to modulate
        TextureAttributes texAttr = new TextureAttributes();
        texAttr.setTextureMode(TextureAttributes.MODULATE);
        wallApp.setTextureAttributes(texAttr);

        // Create the geometry for the wall
        QuadArray wallGeometry = new QuadArray(4, QuadArray.COORDINATES | QuadArray.TEXTURE_COORDINATE_2);
        wallGeometry.setCoordinate(0, new Point3f(-10.0f, -1.5f, -15.0f)); // Bottom-left
        wallGeometry.setCoordinate(1, new Point3f(10.0f, -1.5f, -15.0f));  // Bottom-right
        wallGeometry.setCoordinate(2, new Point3f(10.0f, 10f, -15.0f));   // Top-right
        wallGeometry.setCoordinate(3, new Point3f(-10.0f, 10f, -15.0f));  // Top-left

        // Set the texture coordinates for the wall
        wallGeometry.setTextureCoordinate(0, 0, new TexCoord2f(0.0f, 0.0f));
        wallGeometry.setTextureCoordinate(0, 1, new TexCoord2f(1.0f, 0.0f));
        wallGeometry.setTextureCoordinate(0, 2, new TexCoord2f(1.0f, 1.0f));
        wallGeometry.setTextureCoordinate(0, 3, new TexCoord2f(0.0f, 1.0f));

        // Create the wall shape and set its appearance
        Shape3D wall = new Shape3D(wallGeometry, wallApp);

        // Add the wall to the root branch group
        root.addChild(wall);
    }

    /*
    * OVERLAY METHODS
    * methods that controll construction of the overlay
    * */

    //The overlay controller that runs the other function, made this way so its easier for CHU
    private static void addOverlay(BranchGroup root) {
        addSpecificOverlayCubes(root,  0.13f); // add sidebar cube icons
        addOverlayRectFar(root);
        addOverlayRectTop(root);
        addOverlayRect(root); // add layer for 3d cubes and text.
        addText3D(root, "Block Name", -0.9f, 0.42f, 0.2f,40 ); //String text, float x, float y, float z, float fontSize
        addText3D(root, "BlastRes: ", -0.75f, 0.25f, 0f, 25 );
        addText3D(root, "Hardness: ", -0.75f, 0.12f, 0f, 25 );
        addText3D(root, "Renewable: ", -0.75f, 0.25f, 0f, 25 );
        addText3D(root, "Flammable: ", -0.75f, 0.25f, 0f, 25 );
        addText3D(root, "Dimension: ", -0.75f, 0.25f, 0f, 25 );
        addText3D(root, "Craftable: ", -0.75f, 0.25f, 0f, 25 );
        addText3D(root, "Luminious: ", -0.75f, 0.25f, 0f, 25 );

        // add sidebar LEFT text
    }

    //Add a 3d text to the scene with root x y z and font size (Font size is absouloute and is not modified (note to self by jacob)
    private static void addText3D(BranchGroup root, String text, float x, float y, float z, float fontSize) {
        // Create a Text2D object with the specified text and font size
        Text2D text2D = new Text2D(text, new Color3f(0f, 0f, 0f), "Arial", (int) (fontSize), Font.PLAIN); //111 is white

        // Create a Transform3D to position the text
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(x, y, z+0.01f));

        // Create a TransformGroup and add the Transform3D to it
        TransformGroup textTransformGroup = new TransformGroup(transform);
        textTransformGroup.addChild(text2D);

        // Add the text TransformGroup to the root BranchGroup
        root.addChild(textTransformGroup);
    }

    // adds specific 3d cubes tot he 2d overlay on the 3d scene
    private static void addSpecificOverlayCubes(BranchGroup root, float spacing) {
        String[] name = {"TNT", "Bedrock", "Leaves", "Lava", "Dimension", "Crafting Table", "Glowstone"};
        TransformGroup overlayTransformGroup = new TransformGroup();
        for (int i = 0; i < name.length; i++) {
            TransformGroup cubeTransformGroup = createSpecificOverlayCube(name[i]);
            Transform3D transform = new Transform3D();
            transform.setTranslation(new Vector3f(-0.82f, 0.3f - i * spacing, 0.1f)); // Position the cubes vertically
            cubeTransformGroup.setTransform(transform);
            overlayTransformGroup.addChild(cubeTransformGroup);
        }
        root.addChild(overlayTransformGroup);
    }

    // same logic as the wall and ground methods however has no texture and the blending attributes give it transparecy
    //same with all methods below this
    public static void addOverlayRect(BranchGroup root) {
        // Create an appearance with semi-transparent grey color
        Appearance overlayAppearance = new Appearance();
        ColoringAttributes coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(new Color3f(0.5f, 0.5f, 0.5f));
        overlayAppearance.setColoringAttributes(coloringAttributes);

        TransparencyAttributes transparencyAttributes = new TransparencyAttributes();
        transparencyAttributes.setTransparencyMode(TransparencyAttributes.NONE);
        transparencyAttributes.setTransparency(0.3f); // For example
        overlayAppearance.setTransparencyAttributes(transparencyAttributes);

        // Create the geometry for the rectangle
        QuadArray overlayGeometry = new QuadArray(4, QuadArray.COORDINATES);
        overlayGeometry.setCoordinate(0, new Point3f(-0.95f, -0.6f, 0f));
        overlayGeometry.setCoordinate(1, new Point3f(-0.3f, -0.6f, 0f));
        overlayGeometry.setCoordinate(2, new Point3f(-0.3f, 0.4f, 0f));
        overlayGeometry.setCoordinate(3, new Point3f(-0.95f, 0.4f, 0f));

        // Create the shape with the appearance
        Shape3D overlay = new Shape3D(overlayGeometry, overlayAppearance);

        // Create a transform group for positioning the rectangle
        TransformGroup overlayTransformGroup = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(0.0f, 0.0f, -0.1f)); // Position the rectangle
        overlayTransformGroup.setTransform(transform);

        // Add the rectangle to the transform group and the root
        overlayTransformGroup.addChild(overlay);
        root.addChild(overlayTransformGroup);
    }

    public static void addOverlayRectTop(BranchGroup root) {
        // Create an appearance with semi-transparent grey color
        Appearance overlayAppearance = new Appearance();
        ColoringAttributes coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(new Color3f(0.5f, 0.5f, 0.5f));
        overlayAppearance.setColoringAttributes(coloringAttributes);

        TransparencyAttributes transparencyAttributes = new TransparencyAttributes(TransparencyAttributes.NONE, 0.05f);
        overlayAppearance.setTransparencyAttributes(transparencyAttributes);

        // Create the geometry for the rectangle
        QuadArray overlayGeometry = new QuadArray(4, QuadArray.COORDINATES);
        overlayGeometry.setCoordinate(0, new Point3f(-1f, 0.47f, 0f));
        overlayGeometry.setCoordinate(1, new Point3f(1f, 0.47f, 0f));
        overlayGeometry.setCoordinate(2, new Point3f(1f, 2f, 0f));
        overlayGeometry.setCoordinate(3, new Point3f(-1f, 2f, 0f));

        // Create the shape with the appearance
        Shape3D overlay = new Shape3D(overlayGeometry, overlayAppearance);

        // Create a transform group for positioning the rectangle
        TransformGroup overlayTransformGroup = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(0.0f, 0.0f, 0.0f)); // Position the rectangle
        overlayTransformGroup.setTransform(transform);

        // Add the rectangle to the transform group and the root
        overlayTransformGroup.addChild(overlay);
        root.addChild(overlayTransformGroup);
    }

    public static void addOverlayRectFar(BranchGroup root) {
        // Create an appearance with semi-transparent grey color
        Appearance overlayAppearance = new Appearance();
        ColoringAttributes coloringAttributes = new ColoringAttributes();
        coloringAttributes.setColor(new Color3f(0.5f, 0.5f, 0.5f));
        overlayAppearance.setColoringAttributes(coloringAttributes);

        TransparencyAttributes transparencyAttributes = new TransparencyAttributes(TransparencyAttributes.BLENDED, 0.5f);
        overlayAppearance.setTransparencyAttributes(transparencyAttributes);

        // Create the geometry for the rectangle
        QuadArray overlayGeometry = new QuadArray(4, QuadArray.COORDINATES);
        overlayGeometry.setCoordinate(0, new Point3f(-10.0f, -10f, -2.0f)); // Bottom-left
        overlayGeometry.setCoordinate(1, new Point3f(10.0f, -10f, -2.0f));  // Bottom-right
        overlayGeometry.setCoordinate(2, new Point3f(10.0f, 10f, -2.0f));   // Top-right
        overlayGeometry.setCoordinate(3, new Point3f(-10.0f, 10f, -2.0f));  // Top-left

        // Create the shape with the appearance
        Shape3D overlay = new Shape3D(overlayGeometry, overlayAppearance);

        // Create a transform group for positioning the rectangle
        TransformGroup overlayTransformGroup = new TransformGroup();
        Transform3D transform = new Transform3D();
        transform.setTranslation(new Vector3f(0.0f, 0.0f, 0f)); // Position the rectangle
        overlayTransformGroup.setTransform(transform);

        // Add the rectangle to the transform group and the root
        overlayTransformGroup.addChild(overlay);
        root.addChild(overlayTransformGroup);
    }

}
