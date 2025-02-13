import java.util.ArrayList;
import java.util.List;

public class CarLoader<T extends Car> {
    private final List<T> loadedCars;
    private final int maxLoad;
    private static final double MAX_LOAD_DISTANCE = 2.0;

    public CarLoader(int maxLoad) {
        this.loadedCars = new ArrayList<>();
        this.maxLoad = maxLoad;
    }

    //Car Transport
    public void loadCar(T car, double transportX, double transportY) {
        if (loadedCars.size() >= maxLoad) {
            throw new IllegalStateException("Car loader is full!");
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

        Car car = loadedCars.removeLast();
        car.setX(transportX);
        car.setY(transportY + MAX_LOAD_DISTANCE);
        return car;
    }

    public List<T> getLoadedCars() {
        return loadedCars;
    }
}