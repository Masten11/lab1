import java.awt.*;

public class Saab95 extends PassengerCar {
    private boolean turboOn; // Specific to Saab95 (private)

    public Saab95() {
        super(2, 125, Color.red, "Saab95", 5, 2); // Initialize Car's attributes
        this.turboOn = false;
    }

    // Public methods to control turbo
    public void setTurboOn() {
        this.turboOn = true;
    }

    public void setTurboOff() {
        this.turboOn = false;
    }

    // Calculate speed factor (specific to Saab95)
    @Override
    protected double speedFactor() {
        double turbo = 1;
        if(turboOn) turbo = 1.3;
        return getEnginePower() * 0.01 * turbo;
    }

}
