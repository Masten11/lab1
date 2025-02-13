import java.util.ArrayList;
import java.util.List;

// class som endast kan ta emot en viss subklass av Car
// den bestäms vid under initieringen av objektet
public class CarWorkshop<T extends Car> {
    private List<T> cars; //lista för bilar som lagras
    private final int maxCapacity;//max antal bilar som går att ta emot

    public CarWorkshop(int maxCapacity) {
        this.maxCapacity = maxCapacity;
        this.cars = new ArrayList<>();
    }

    public void leaveCar(T car) {
        //om antalet bilar överstiger max kapacitet
        if (cars.size() >= maxCapacity) {
            throw new IllegalStateException("Car workshop is full!");
        }
        cars.add(car);
    }

    public void getCar(T car) {
        // om bilen ej existerar i listan
        if (!cars.contains(car)) {
            throw new IllegalStateException("The car is not in the workshop.");
        }
        cars.remove(car);
    }

    protected boolean hasCar(T car) {
        return cars.contains(car);
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }
}