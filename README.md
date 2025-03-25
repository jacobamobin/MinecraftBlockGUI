# Minecraft Block Viewer 3D GUI

A Java-based 3D visualization tool for Minecraft blocks, allowing users to explore block properties and appearances in an interactive 3D environment.

## Features

- Interactive 3D block visualization
- Real-time block rotation and manipulation
- Detailed block information display
- Crafting recipe visualization
- Background grid of various Minecraft blocks
- Transparent overlay UI with block properties
- Mouse-controlled camera rotation
- Textured environment (floor and walls)

## Prerequisites

- Java Development Kit (JDK) 8 or higher
- Java 3D API libraries
- Maven (for dependency management)

## Setup Instructions

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

1. Run the application
2. Select a Minecraft block to view
3. Use mouse controls to rotate and examine the block
4. View block properties in the overlay panel
5. Examine crafting recipes (if available)

## Controls

- Left Mouse Button: Rotate block
- Right Mouse Button: Pan view
- Mouse Wheel: Zoom in/out

## Block Properties Displayed

- Blast Resistance
- Hardness
- Renewable Status
- Flammability
- Dimension
- Craftability
- Luminosity

## Screenshots

### Main Interface
[Insert screenshot of the main 3D viewer interface showing a block with the overlay panel]

### Block Rotation
[Insert screenshot demonstrating block rotation and different viewing angles]

### Property Panel
[Insert screenshot of the property panel showing block information]

### Crafting Recipe View
[Insert screenshot of the crafting recipe display]

### Background Grid
[Insert screenshot showing the background grid of various Minecraft blocks]

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

[Add your license information here]

## Acknowledgments

- Java 3D API team
- Minecraft community
- [Add any other acknowledgments]

## Contact

[Add your contact information]

## Version History

- v1.0.0
  - Initial release
  - Basic 3D block visualization
  - Property display panel
  - Crafting recipe viewer

[Add more version history as needed]


## Manager

- [@jacobamobin](https://www.github.com/jacobamobin) || Programming 

## Contributors

- [@KMurph889](https://github.com/KMurph889) || Assets
- [@awesomesaucehv](https://github.com/awesomesaucehv) || Assets

## Installation

You need J3D and VecMath to run this project.

    
