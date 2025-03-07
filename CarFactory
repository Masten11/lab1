import java.util.Random;
public class CarFactory {
    private static final Random random = new Random();

    public static Car createRandomCar() {
        int randomNumber = random.nextInt(3);
        switch (randomNumber) {
            case 0:
                return new Volvo240();
            case 1:
                return new Saab95();
            case 2:
                return new Scania();
            default:
                throw new IllegalStateException("Unexpected value: " + randomNumber);
        }
    }
}
