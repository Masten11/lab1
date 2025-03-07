import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/*
* This class represents the Controller part in the MVC pattern.
* It's responsibilities is to listen to the View and responds in a appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController implements CCInterface {
    private CarModel carModel;
    private CarView frame;
    private static CarWorkshop<Volvo240> volvo240CarWorkshop = new CarWorkshop<>(2);

    private final int delay = 45;
    private Timer timer = new Timer(delay, new TimerListener());

    public CarController(CarModel carmodel, CarView carView) {
        this.carModel = carmodel;
        this.frame = carView;
        carmodel.addObserver(frame.drawPanel);

        volvo240CarWorkshop.setX(300);
        volvo240CarWorkshop.setY(300);
    }

    public void initTimer() {
        timer.start();
    }


    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            int screenWidth = frame.getWidth();
            carModel.updateCars(screenWidth); // This will notify observers
            carModel.checkCollisions(volvo240CarWorkshop, frame); // This will notify observers
        }
    }

    @Override
    public void gas(int amount) {
        carModel.gas(amount);
    }

    @Override
    public void brake(int amount) {
        carModel.brake(amount);
    }

    @Override
    public void setTurbo(boolean on) {
        carModel.setTurbo(on);
    }

    @Override
    public void liftBed() {
        carModel.liftBed();
    }

    @Override
    public void lowerBed() {
        carModel.lowerBed();
    }

    @Override
    public void startAllCars() {
        carModel.startAllCars();
    }

    @Override
    public void stopAllCars() {
        carModel.stopAllCars();
    }

    @Override
    public void removeCar() {
        carModel.removeCar();
    }

    @Override
    public void addRandomCar() {
        carModel.addRandomCar();
    }


}
