import java.awt.*;

public class Scania extends Truck{

    // Constructor to initialize Scania attributes
    public Scania() {
        super(2, 90, Color.white, "Scania", 7.0, 2.2); // Initialize Car's attributes
    }


    @Override
    public void move() {
        if (getRamp().getAngle() > 0) {
            throw new IllegalArgumentException("Angle cannot be greater than 0");
        }
        super.move();
    }

    public void raise(double amount) {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        getRamp().raise(amount);
    }

    // Lower the ramp by a specific amount
    public void lower(double amount) {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        getRamp().lower(amount);
    }


}
