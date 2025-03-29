# Minecraft Block Viewer 3D GUI

A Java-based 3D visualization tool for Minecraft blocks, allowing users to explore block properties and appearances in an interactive 3D environment. This project provides an immersive way to learn about Minecraft blocks and their properties through an intuitive 3D interface.

### Main Menu
*Main Menu Of App, Hover and Click both work on the buttons*

<img width="538" alt="image" src="https://github.com/user-attachments/assets/48ec94b8-6a7e-4b62-b108-65a6d68738f3" />

### List View
*Displays all blocks, can be sorted by properties and name background changes with sorting method*

<img width="537" alt="image2" src="https://github.com/user-attachments/assets/90a28438-871b-4824-b1e1-a9366e5f2905" />
<img width="537" alt="image3" src="https://github.com/user-attachments/assets/359dc453-da95-489b-9f60-d5cff6c639bc" />
<img width="537" alt="image4" src="https://github.com/user-attachments/assets/07cfefc4-b2b7-43d7-b75c-03e1ae5f1f54" />


### Block Rotation
[Insert screenshot demonstrating block rotation and different viewing angles]
*Interactive 3D model that can be rotated to view all sides of the block.*

### Property Panel
[Insert screenshot of the property panel showing block information]
*Detailed information panel showing all block properties and characteristics.*

### Crafting Recipe View
[Insert screenshot of the crafting recipe display]
*Visual representation of how to craft the selected block in Minecraft.*

### Background Grid
[Insert screenshot showing the background grid of various Minecraft blocks]
*Decorative background featuring a grid of different Minecraft blocks.*

## Features

- Interactive 3D block visualization with realistic textures
- Real-time block rotation and manipulation using mouse controls
- Detailed block information display with comprehensive properties
- Crafting recipe visualization for craftable blocks
- Dynamic background grid showcasing various Minecraft blocks
- Transparent overlay UI with block properties
- Mouse-controlled camera rotation and zoom
- Textured environment with Minecraft-style floor and walls
- Support for over 100 different Minecraft blocks
- Smooth performance and responsive controls

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java 3D API libraries
- Maven (for dependency management)
- Minimum 4GB RAM recommended
- Graphics card with OpenGL support

## Installation

### 1. Install Java 3D API

1. Download the Java 3D API from Oracle's website
2. Add the following JAR files to your project's classpath:
   - `j3dcore.jar`
   - `j3dutils.jar`
   - `vecmath.jar`

### 2. Project Structure

```
McBlockGUI2/
├── src/
│   └── main/
│       └── java/
│           ├── BlockView3dGUI.java
│           └── Block.java
├── resources/
│   └── object/
│       ├── front.jpg.jpg
│       ├── back.jpg.jpg
│       ├── bottom.jpg.jpg
│       ├── top.jpg.jpg
│       ├── left.jpg.jpg
│       ├── right.jpg.jpg
│       ├── ground.jpg
│       └── [Block Name]/
│           ├── front.jpg.jpg
│           ├── back.jpg.jpg
│           ├── bottom.jpg.jpg
│           ├── top.jpg.jpg
│           ├── left.jpg.jpg
│           └── right.jpg.jpg
└── README.md
```

### 3. Dependencies

Add the following dependencies to your project:

```xml
<dependencies>
    <dependency>
        <groupId>javax.media</groupId>
        <artifactId>j3d</artifactId>
        <version>1.5.2</version>
    </dependency>
    <dependency>
        <groupId>javax.vecmath</groupId>
        <artifactId>vecmath</artifactId>
        <version>1.5.2</version>
    </dependency>
</dependencies>
```

## Usage

1. Launch the application
2. Select a Minecraft block from the available options
3. Use mouse controls to rotate and examine the block
4. View block properties in the overlay panel
5. Examine crafting recipes (if available)
6. Explore the background grid of various blocks

## Controls

- Left Mouse Button: Rotate block
- Right Mouse Button: Pan view
- Mouse Wheel: Zoom in/out
- ESC: Exit application

## Block Properties Displayed

- Blast Resistance: Block's resistance to explosions
- Hardness: Time required to break the block
- Renewable Status: Whether the block can be naturally regenerated
- Flammability: Whether the block can catch fire
- Dimension: Where the block can be found (Overworld, Nether, End)
- Craftability: Whether the block can be crafted
- Luminosity: Light level emitted by the block

## Development Team

### Manager
- [@jacobamobin](https://www.github.com/jacobamobin) || Programming & Project Lead

### Contributors
- [@KMurph889](https://github.com/KMurph889) || Assets & Textures
- [@awesomesaucehv](https://github.com/awesomesaucehv) || Assets & Textures

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Create a Pull Request

## License

MIT

## Acknowledgments

- Java 3D API team for providing the 3D graphics framework
- Minecraft community for inspiration and block designs
- [Add any other acknowledgments]

## Contact

[Add your contact information]

## Version History

- v1.0.0
  - Initial release
  - Basic 3D block visualization
  - Property display panel
  - Crafting recipe viewer
  - Background grid implementation
  - Mouse controls for interaction

[Add more version history as needed]

## Support

If you encounter any issues or have questions, please:
1. Check the existing issues
2. Create a new issue with a detailed description
3. Include system information and error messages if applicable

## Roadmap

- [ ] Add support for more Minecraft blocks
- [ ] Implement block comparison feature
- [ ] Add search and filter functionality
- [ ] Improve UI/UX design
- [ ] Add block interaction animations
- [ ] Implement block sound effects

    
