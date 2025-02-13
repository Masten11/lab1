

// class som endast kan ta emot en viss subklass av Car
// den bestäms vid under initieringen av objektet
public class CarWorkshop<T extends Car> {
    private final CarLoader<T> carLoader;

    public CarWorkshop(int maxCapacity) {
        this.carLoader = new CarLoader<>(maxCapacity);
    }

    public void leaveCar(T car) {
        carLoader.loadCar(car, car.getX(), car.getY()); // Use the method without distance check
    }

    public void getCar(T car) {
        if (!carLoader.getLoadedCars().contains(car)) {
            throw new IllegalStateException("The car is not in the workshop.");
        }
        carLoader.unloadCar(car.getX(), car.getY()); // Use the method without distance adjustment
    }

    protected boolean hasCar(T car) {
        return carLoader.getLoadedCars().contains(car);
    }

}