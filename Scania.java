import java.awt.*;

public class Scania extends Car implements RampInterface{
    private final Ramp ramp;

    // Constructor to initialize Scania attributes
    public Scania() {
        super(2, 90, Color.white, "Scania", 7.0, 2.2); // Initialize Car's attributes
        this.ramp = new Ramp();
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }


    //ifall ingen vinkel anges sätts vinkeln till min
    @Override
    public void raise() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.raise(70);
    }


    public void raise(double amount) {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.raise(amount);
    }


    //ifall ingen vinkel anges sätts vinkeln till max
    @Override
    public void lower() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.lower(70);
    }

    // Lower the ramp by a specific amount
    public void lower(double amount) {
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