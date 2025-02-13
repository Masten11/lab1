import java.awt.*;

public class Scania extends Car {
    private final Ramp ramp;

    // Constructor to initialize Scania attributes
    public Scania() {
        super(2, 90, Color.white, "Scania", 7.0, 2.2); // Initialize Car's attributes
        this.ramp = new Ramp(70);
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }


    public void raisePlatform(double amount) {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.raise(amount);
    }

    // Lower the ramp by a specific amount
    public void lowerPlatform(double amount) {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.lower(amount);
    }

    @Override
    public void move() {
        if (ramp.getAngle() < 70) {
            throw new IllegalArgumentException("Angle cannot be greater than 0");
        }
        super.move();
    }

    @Override
    public void gas(double amount){
        if (ramp.getAngle() < 70) {
            throw new IllegalArgumentException("Angle cannot be greater than 0");
        }
        super.gas(amount);
    }

}