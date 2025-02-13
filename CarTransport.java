import java.util.ArrayList;
import java.util.List;
import java.awt.*;


// Class to manage the loading and unloading of cars
class CarLoader {
    private final List<Car> loadedCars;
    private final int maxLoad;
    private static final double MAX_CAR_LENGTH = 5.5;
    private static final double MAX_CAR_WIDTH = 2.0;
    private static final double MAX_LOAD_DISTANCE = 2.0;

    public CarLoader(int maxLoad) {
        this.loadedCars = new ArrayList<>();
        this.maxLoad = maxLoad;
    }

    public void loadCar(Car car, double transportX, double transportY) {
        if (loadedCars.size() >= maxLoad) {
            throw new IllegalStateException("Car transport is full!");
        }
        if (car.getLength() > MAX_CAR_LENGTH || car.getWidth() > MAX_CAR_WIDTH) {
            throw new IllegalArgumentException("Car is too large to be transported");
        }
        if (car instanceof CarTransport) {
            throw new IllegalArgumentException("Cannot load another car transport");
        }
        if (Math.abs(car.getY() - transportY) > MAX_LOAD_DISTANCE || Math.abs(car.getX() - transportX) > MAX_LOAD_DISTANCE) {
            throw new IllegalArgumentException("Car needs to be closer to transport car");
        }

        loadedCars.add(car);
    }

    public Car unloadCar(double transportX, double transportY) {
        if (loadedCars.isEmpty()) {
            throw new IllegalStateException("No cars to unload");
        }

        Car car = loadedCars.remove(loadedCars.size() - 1);
        car.setX(transportX);
        car.setY(transportY + MAX_LOAD_DISTANCE);
        return car;
    }

    public boolean isEmpty() {
        return loadedCars.isEmpty();
    }

    public List<Car> getLoadedCars() {
        return loadedCars;
    }
}

// Main CarTransport class using composition
public class CarTransport extends Car {
    private final Ramp ramp;
    private final CarLoader carLoader;

    public CarTransport(int maxLoad) {
        super(2, 80, Color.gray, "TheLongtrader", 10, 2.5); // Initialize Car's attributes
        this.ramp = new Ramp();
        this.carLoader = new CarLoader(maxLoad);
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

    public void raisePlatform() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving!");
        }
        ramp.raise();
    }

    public void lowerPlatform() {
        if (getCurrentSpeed() > 0) {
            throw new IllegalArgumentException("The truck cannot be moving!");
        }
        ramp.lower();
    }

    public void loadCar(Car car) {
        if (!ramp.isRampDown()) {
            throw new IllegalStateException("Ramp must be lowered to load a car!");
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
    public void gas(double amount) {
        if (ramp.isRampDown()) {
            throw new IllegalArgumentException("Ramp cannot be down when moving");
        }
        super.gas(amount);
    }
}