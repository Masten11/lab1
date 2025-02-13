public class Ramp {
    private double angle = 0; // Represents the angle of the ramp (for Scania)
    private final double maxAngle = 70; // Maximum angle for the ramp (for Scania)

    // For CarTransport (up/down)
    public void raise() {
        this.angle = 0;
    }

    public void lower() {
        this.angle = maxAngle;
    }

    public boolean isRampDown() {
        if (this.angle == maxAngle) {return true;}
        else {return false;}
    }

    // For Scania:
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