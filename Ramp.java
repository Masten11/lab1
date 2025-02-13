public class Ramp {
    private double angle; // Represents the angle of the ramp (for Scania)
    private boolean isRampDown; // Represents the state of the ramp (for CarTransport)
    private final double maxAngle; // Maximum angle for the ramp (for Scania)

    public Ramp(double maxAngle) {
        this.angle = 0;
        this.maxAngle = maxAngle;
    }

    public Ramp() {
        this(0);
        this.isRampDown = false;// Default maxAngle of 0 (binary state only)
    }

    // For CarTransport: binary state (up/down)
    public void raise() {
        this.isRampDown = false;
    }

    public void lower() {
        this.isRampDown = true;
    }

    public boolean isRampDown() {
        return isRampDown;
    }

    // For Scania: angular state
    public void raise(double amount) {
        this.angle = Math.max(this.angle - amount, 0);
    }

    public void lower(double amount) {
        this.angle = Math.min(this.angle + amount, maxAngle);
    }

    public double getAngle() {
        return angle;
    }

}