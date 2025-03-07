import java.awt.*;

public abstract class Truck extends Car implements RampInterface {
    private final Ramp ramp;

    protected Truck(int nrDoors, double enginePower, Color color, String modelName, double length, double width) {
        super(nrDoors, enginePower, color, modelName, length, width); // Initialize Car's attributes
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

    //ifall ingen vinkel anges sätts vinkeln till max
    @Override
    public void lower() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving");
        }
        ramp.lower(70);
    }

    protected Ramp getRamp() {
        return ramp;
    }

    @Override
    protected void startEngine() {
        if (ramp.getAngle()==0) {
            super.startEngine();
        }
    }

    @Override
    public void gas(double amount) {
        if (ramp.isRampDown()) {
            throw new IllegalArgumentException("Ramp cannot be down when moving");
        }
        super.gas(amount);
    }
}
