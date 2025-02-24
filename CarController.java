import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;


/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController implements CarControllerInterface {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 45;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
    ArrayList<Car> cars = new ArrayList<>();

    private static CarWorkshop<Volvo240> volvo240CarWorkshop = new CarWorkshop<>(2);;

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();
        Volvo240 volvo = new Volvo240();
        Saab95 saab = new Saab95();
        Scania scania = new Scania();
        Volvo240 volvo2 = new Volvo240();

        saab.setX(0);
        saab.setY(100);

        scania.setX(0);
        scania.setY(200);

        volvo2.setX(0);
        volvo2.setY(300);

        volvo240CarWorkshop.setX(300);
        volvo240CarWorkshop.setY(300);

        cc.cars.add(volvo);
        cc.cars.add(saab);
        cc.cars.add(scania);
        cc.cars.add(volvo2);

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();

    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int screenwidth = frame.getWidth();
            for (int i = 0; i < cars.size(); i++) {
                Car car = cars.get(i);
                try {
                    car.move();
                } catch (IllegalArgumentException ex) {
                    continue;
                }
                int x = (int) Math.round(car.getX());
                int y = (int) Math.round(car.getY());

                if (x >= screenwidth - 100 || x <= 0) {
                    car.setDirection(car.getDirection() + 180); // Turn around
                }

                frame.drawPanel.moveit(i, x, y); // Use the car index to move the correct car

                //if car is a volvo
                if (car instanceof Volvo240) {
                    double workshopX = volvo240CarWorkshop.getX();
                    double workshopY = volvo240CarWorkshop.getY();

                    //if it collides with workshop
                    if (x >= workshopX && x <= workshopX + 100 &&
                            y >= workshopY && y <= workshopY + 100) {
                        // Collision detected, load volvo2 into the workshop
                        volvo240CarWorkshop.leaveCar((Volvo240) car);
                        cars.remove(car); // Remove volvo2 from the list of cars
                        frame.drawPanel.removeCar(car); // Update the draw panel to remove volvo2
                        // status message
                        frame.drawPanel.setStatusMessage("Volvo loaded into the workshop!");
                    }

                }
            }
        }
    }

    // Calls the gas method for each car once

    public void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Car car : cars
                ) {
            try {
                car.gas(gas);
            }
            catch (IllegalArgumentException ex){
                continue;
            }

        }
    }


    public void brake(int amount) {
        double brakeForce = ((double) amount) / 100;
        for (Car car : cars) {
            car.brake(brakeForce);
        }
    }

    // Enables turbo mode for Saab95
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
    }

    // Lifts the truck bed for Scania
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
    }

    // Lowers the truck bed for Scania
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
    }

    // Starts all cars
    public void startAllCars() {
        for (Car car : cars) {
            car.startEngine();
        }
    }

    // Stops all cars
    public void stopAllCars() {
        for (Car car : cars) {
            car.stopEngine();
        }
    }

}
