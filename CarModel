import java.awt.Point;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.List;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.*;

public class CarModel {
    private Map<Class<? extends Car>, BufferedImage> carImageMap = new HashMap<>();
    private List<Car> cars = new ArrayList<>();
    private List<CarObserver> observers = new ArrayList<>(); // List of observers
    private String statusMessage = "";

    // Add an observer
    public void addObserver(CarObserver observer) {
        observers.add(observer);
    }


    // Notify all observers
    private void notifyObservers() {
        Point[] carPositions = getCarPositions();
        BufferedImage[] imagesArray = getCarImages(); // Get images from the map
        for (CarObserver observer : observers) {
            observer.update(carPositions,imagesArray, statusMessage);
        }
    }

    // Load car images and create the mapping
    public void loadCarImages() {
        try {
            carImageMap.put(Volvo240.class, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("pics/Volvo240.jpg"))));
            carImageMap.put(Saab95.class, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("pics/Saab95.jpg"))));
            carImageMap.put(Scania.class, ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("pics/Scania.jpg"))));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public BufferedImage[] getCarImages() {
        BufferedImage[] images = new BufferedImage[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            images[i] = carImageMap.get(car.getClass()); // Get the image from the map
        }
        return images;
    }


    public void setStatusMessage(String message) {
        this.statusMessage = message;
        notifyObservers();
    }

    public void addRandomCar() {
        if (cars.size()<=8) {
            Car car = CarFactory.createRandomCar();
            addCar(car);
        }
    }


    public void addCar(Car car) {
        cars.add(car);
        setCarPositions();
    }

    public void removeCar () {
        if (!cars.isEmpty()) {
            cars.removeLast();
        }
        notifyObservers();
    }

    public void removeCar(Car car) {
        cars.remove(car);
        notifyObservers();
    }

    public Point[] getCarPositions() {
        Point[] positions = new Point[cars.size()];
        for (int i = 0; i < cars.size(); i++) {
            positions[i] = new Point((int) cars.get(i).getX(), (int) cars.get(i).getY());
        }
        return positions;
    }

    public void moveCar(int carIndex, int x, int y) {
        if (carIndex >= 0 && carIndex < cars.size()) {
            Car car = cars.get(carIndex);
            car.setX(x);
            car.setY(y);
        }
    }

    public void updateCars(int screenWidth) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            try {
                car.move();
            } catch (IllegalArgumentException ex) {
                continue;
            }

            // Get the car's position
            int x = (int) Math.round(car.getX());
            int y = (int) Math.round(car.getY());

            // Check if the car hits the screen boundaries
            if (x >= screenWidth - 100 || x <= 0) {
                car.setDirection(car.getDirection() + 180); // Turn around
            }

            // Update the car's position in the model
            moveCar(i, x, y);
        }
        notifyObservers();
    }

    public void checkCollisions(CarWorkshop<Volvo240> workshop, CarView frame) {
        for (int i = 0; i < cars.size(); i++) {
            Car car = cars.get(i);
            if (car instanceof Volvo240) {
                double workshopX = workshop.getX();
                double workshopY = workshop.getY();
                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());

                // Check for collision with the workshop
                if (x >= workshopX && x <= workshopX + 100 &&
                        y >= workshopY && y <= workshopY + 100) {
                    // Collision detected, load the Volvo into the workshop
                    workshop.leaveCar((Volvo240) car);
                    removeCar(car); // Remove the car from the model
                    setStatusMessage("Volvo loaded into the workshop!"); // Notify observers
                }
            }
        }
    }

    public void setCarPositions () {
        if (cars.size()>1) {
            cars.getLast().setY(cars.get(cars.size()-2).getY() + 60);
        }
        notifyObservers();
    }

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars) {
            try {
                car.gas(gas);
            }
            catch (IllegalArgumentException ex){
                continue;
            }

        }
        notifyObservers();
    }

    public void brake(int amount) {
        double brakeForce = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brakeForce);
        }
        notifyObservers();
    }

    public void setTurbo(boolean on) {
        for (Car car : cars) {
            if (car instanceof Saab95) {
                Saab95 saab = (Saab95) car;
                if (on) {
                    saab.setTurboOn();
                } else {
                    saab.setTurboOff();
                }
            }
        }
        notifyObservers();
    }

    public void liftBed() {
        for (Car car : cars) {
            try {
                if (car instanceof Scania) {
                    Scania scania = (Scania) car;
                    scania.raise();
                }
            }
            catch (IllegalArgumentException ex){
                continue;
            }
        }
        notifyObservers();
    }

    public void lowerBed() {
        for (Car car : cars) {
            try {
                if (car instanceof Scania) {
                    Scania scania = (Scania) car;
                    scania.lower();
                }
            }
            catch (IllegalArgumentException ex) {
                continue;
            }
        }
        notifyObservers();
    }

    public void startAllCars() {
        for (Car car : cars) {
            car.startEngine();
        }
        notifyObservers();
    }

    public void stopAllCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
        notifyObservers();
    }
}
