import java.awt.*;


// Main CarTransport class using composition
public class CarTransport extends Car implements RampInterface{
    private final Ramp ramp;
    private static final double MAX_CAR_LENGTH = 5.5;
    private static final double MAX_CAR_WIDTH = 2.0;
    private final CarLoader<Car> carLoader;

    public CarTransport(int maxLoad) {
        super(2, 80, Color.gray, "TheLongtrader", 10, 2.5); // Initialize Car's attributes
        this.ramp = new Ramp();
        this.carLoader = new CarLoader<>(maxLoad);
    }


    public void loadCar(Car car) {
        if (!ramp.isRampDown()) {
            throw new IllegalStateException("Ramp must be lowered to load a car!");
        }
        if (car instanceof CarTransport) {
            throw new IllegalArgumentException("Cannot load another car transport");
        }

        if (car.getLength() > MAX_CAR_LENGTH || car.getWidth() > MAX_CAR_WIDTH) {
            throw new IllegalArgumentException("Car is too large to be transported");
        }
        carLoader.loadCar(car, getX(), getY());
    }

    public void unloadCar() {
        if (!ramp.isRampDown()) {
            throw new IllegalStateException("Ramp must be lowered to unload a car");
        }
        carLoader.unloadCar(getX(), getY());
    }

    @Override
    public void move() {
        if (ramp.isRampDown()) {
            throw new IllegalArgumentException("Ramp cannot be down when moving");
        }

        super.move();

        // Update the position of loaded cars
        for (Car car : carLoader.getLoadedCars()) {
            car.setX(getX());
            car.setY(getY());
        }
    }

    @Override
    protected double speedFactor() {
        return getEnginePower() * 0.01;
    }

    @Override
    public void raise() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving!");
        }
        ramp.raise();
    }

    @Override
    public void lower() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving!");
        }
        ramp.lower();
    }

    @Override
    public void gas(double amount) {
        if (ramp.isRampDown()) {
            throw new IllegalArgumentException("Ramp cannot be down when moving");
        }
        super.gas(amount);
    }
}