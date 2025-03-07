import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel implements CarObserver {

    // Arrays to store car positions and images
    private Point[] carPositions;
    private BufferedImage[] carImages;
    private String statusMessage = "";

    // Workshop position and image
    private Point workshopPosition;
    private BufferedImage workshopImage;

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        try {
            workshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));
            workshopPosition = new Point(300, 300);


        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void update(Point[] carPositions, BufferedImage[] carImages, String statusMessage) {
        this.carPositions = carPositions;
        this.carImages = carImages;
        this.statusMessage = statusMessage;
        repaint();
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw the workshop

        if (carPositions != null && carImages != null) {
            for (int i = 0; i < carPositions.length; i++) {
                if (carImages[i] != null && carPositions[i] != null) {
                    g.drawImage(carImages[i], carPositions[i].x, carPositions[i].y, null);
                }
            }
        }

        if (workshopImage != null && workshopPosition != null) {
            g.drawImage(workshopImage, workshopPosition.x, workshopPosition.y, null);
        }

        if (!statusMessage.isEmpty()) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString(statusMessage, 10, 30);
        }
    }
}
