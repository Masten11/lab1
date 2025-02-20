import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    // Arrays to store car positions and images
    private Point[] carPositions;
    private BufferedImage[] carImages;
    private String[] carNames;
    private String statusMessage = "";

    // TODO: Make this general for all cars
    // Generalized moveit method
    public void moveit(int carIndex, int x, int y) {
        if (carIndex >= 0 && carIndex < carPositions.length) {
            carPositions[carIndex].x = x;
            carPositions[carIndex].y = y;
        }
        repaint();
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        // Initialize arrays
        carNames = new String[]{"Volvo", "Saab", "Scania", "Volvo2", "Workshop"};
        carPositions = new Point[carNames.length];
        carImages = new BufferedImage[carNames.length];

        // Load images and initialize positions
        try {
            carImages[0] = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            carImages[1] = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg"));
            carImages[2] = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg"));
            carImages[3] = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            carImages[4] = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));


            // Initialize positions
            carPositions[0] = new Point(0, 0);    // Volvo
            carPositions[1] = new Point(0, 100); // Saab
            carPositions[2] = new Point(0, 200);  // Scania
            carPositions[3] = new Point(0, 300); // Volvo2
            carPositions[4] = new Point(300, 300); // VolvoWorkshop


        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < carImages.length; i++) {
            if (carImages[i] != null && carPositions[i] != null) {
                g.drawImage(carImages[i], carPositions[i].x, carPositions[i].y, null);
            }
        }

        // Draw the status message
        if (!statusMessage.isEmpty()) {
            g.setColor(Color.BLACK); // Set text color
            g.setFont(new Font("Arial", Font.BOLD, 20)); // Set font
            g.drawString(statusMessage, 10, 30); // Draw the message at (10, 30)
        }
    }

    public void removeCar(Car car) {
        for (int i = 0; i < carNames.length; i++) {
            if (carNames[i].equals(car.getClass().getSimpleName())) {
                carPositions[i] = null; // Remove the car's position
                carImages[i] = null; // Remove the car's image
                break;
            }
        }
        repaint();
    }

    public void setStatusMessage(String message) {
        this.statusMessage = message;
        repaint(); // Trigger a repaint to update the display
    }
}
