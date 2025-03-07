import java.awt.*;

public class Volvo240 extends PassengerCar {
    private static final double trimFactor = 1.25; // Use private for constants

    // Constructor to initialize Volvo240 attributes
    public Volvo240() {
        super(4, 100, Color.black, "Volvo240", 5, 2); // Pass attributes to the Car constructor
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01 * trimFactor;
    }

}
