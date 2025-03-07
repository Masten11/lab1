public class CarSimulator {
    public static void main(String[] args) {
        CarModel carModel = new CarModel();
        CarView carView = new CarView();
        CarController cc = new CarController(carModel,carView );

        carModel.loadCarImages();
        carView.Initialize("CarSim 1.0", cc);
        cc.initTimer();
    }
}
