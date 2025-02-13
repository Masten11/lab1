import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class CarTest {
    private Car volvo;
    private Car saab;
    private Scania scania;
    private CarTransport carTransport;
    private CarWorkshop<Volvo240> volvoWorkshop;
    private CarWorkshop<Car> allCarsWorkShop;
    @BeforeEach
    public void setUp() {
        carTransport = new CarTransport(2); // Max två bilar
        volvoWorkshop = new CarWorkshop<>(2);
        allCarsWorkShop = new CarWorkshop(2);
        volvo = new Volvo240();
        saab = new Saab95();
        scania = new Scania();
        volvo.startEngine();
        saab.startEngine();
        scania.startEngine();
    }

    @Test
    void testMaxCapacityEnforced() {
        Volvo240 car1 = new Volvo240();
        Volvo240 car2 = new Volvo240();
        Volvo240 car3 = new Volvo240();

        volvoWorkshop.leaveCar(car1);
        volvoWorkshop.leaveCar(car2);

        Exception exception = assertThrows(IllegalStateException.class, () -> volvoWorkshop.leaveCar(car3));
        assertEquals("Car loader is full!", exception.getMessage());
    }

    //Visa jubin sen
    /*
    @Test
    void testOnlySpecificCarTypeAllowed() {
        Volvo240 volvo = new Volvo240();

        volvoWorkshop.leaveCar(volvo);
        assertTrue(volvoWorkshop.hasCar(volvo), "Volvo240 should be allowed in volvoWorkshop!");

        // This should not be allowed and should throw an error
        assertThrows(ClassCastException.class, () -> volvoWorkshop.leaveCar(saab),
                "Saab95 should NOT be allowed in volvoWorkshop!");
    }
    */

    @Test
    void testAllCarsWorkshopAcceptsAllCarTypes() {

        allCarsWorkShop.leaveCar(volvo);
        allCarsWorkShop.leaveCar(saab);

        assertTrue(allCarsWorkShop.hasCar(volvo));
        assertTrue(allCarsWorkShop.hasCar(saab));
    }

    @Test
    void testCarRemoval() {
        Volvo240 car = new Volvo240();
        volvoWorkshop.leaveCar(car);

        assertTrue(volvoWorkshop.hasCar(car));

        volvoWorkshop.getCar(car);

        assertFalse(volvoWorkshop.hasCar(car));
    }

    @Test
    void testRampCanOnlyBeLoweredWhenStill() {
        carTransport.startEngine();
        assertThrows(IllegalArgumentException.class, () -> carTransport.lower(),
                "The truck cannot be moving!");
    }

    @Test
    void testCannotLoadCarWhenRampIsUp() {
        assertThrows(IllegalStateException.class, () -> carTransport.loadCar(volvo),
                "Ramp must be lowered to load a car!");
    }

    @Test
    void testCannotLoadCarIfFull() {
        carTransport.lower();
        carTransport.loadCar(volvo);
        carTransport.loadCar(saab);
        assertThrows(IllegalStateException.class, () -> carTransport.loadCar(new Volvo240()),
                "Car transport is full!");
    }


    @Test
    void testCannotLoadAnotherCarTransport() {
        carTransport.lower();
        assertThrows(IllegalArgumentException.class, () -> carTransport.loadCar(new CarTransport(1)),
                "Cannot load another car transport");
    }

    @Test
    void testCannotLoadCarTooFarAway() {
        volvo.setX(carTransport.getX() + 3);
        carTransport.lower();
        assertThrows(IllegalArgumentException.class, () -> carTransport.loadCar(volvo),
                "Car needs to be closer to transport car");
    }

    @Test
    void testCannotUnloadWhenRampIsUp() {
        carTransport.lower();
        carTransport.loadCar(volvo);
        carTransport.raise();
        assertThrows(IllegalStateException.class, () -> carTransport.unloadCar(),
                "Ramp must be lowered to unload a car");
    }

    @Test
    void testUnloadCarPlacesItNearby() {
        carTransport.lower();
        carTransport.loadCar(volvo);
        carTransport.unloadCar();
        assertEquals(carTransport.getY() + 2.0, volvo.getY(), "Should be placed nere by car");
    }

    @Test
    public void testRaisePlattform() {

        assertThrows(IllegalArgumentException.class, () -> scania.raise(50),
                "Det ska ej gå att höja plattform under rörelse");
    }


    @Test
    public void testGetNrDoors() {
        assertEquals(4, volvo.getNrDoors(), "Antalet dörrar i Volvon stämmer inte.");
        assertEquals(2, saab.getNrDoors(), "Antalet dörrar i Saaben stämmer inte.");
    }

    @Test
    public void testGetEnginePower() {
        assertEquals(100, volvo.getEnginePower(), "Motorstyrkan stämmer inte för volvon.");
        assertEquals(125, saab.getEnginePower(), "Motorstyrkan stämmer inte för Saaben.");
    }

    @Test
    public void testColor() {
        assertEquals(Color.BLACK, volvo.getColor(), "Färg  i Volvon stämmer inte.");
        assertEquals(Color.RED, saab.getColor(), "Färg  i Saaben stämmer inte.");
    }

    @Test
    public void testModelName() {
        assertEquals("Volvo240", volvo.getModelName(), "Model  i Volvon stämmer inte.");
        assertEquals("Saab95", saab.getModelName(), "Model  i Saaben stämmer inte.");
    }

    @Test
    public void testMove() {
        saab.turnLeft();
        saab.turnLeft();
        saab.move();
        assertEquals(saab.getX(), saab.getCurrentSpeed() * Math.cos(Math.toRadians(saab.getDirection())), "Positionen X stämmer inte efter rörelse för Saab.");
        assertEquals(saab.getY(), saab.getCurrentSpeed() * Math.sin(Math.toRadians(saab.getDirection())), "Positionen Y stämmer inte efter rörelse för Saab.");
    }

    @Test
    public void testTurnLeft() {
        double initialDirection2 = saab.getDirection();
        saab.turnLeft();  // Turn the car left (increase direction)
        assertEquals(saab.getDirection(), (initialDirection2 - 90) % 360, "Svägning till vänster funka ej för saab");
    }

    @Test
    public void testTurnRight() {
        double initialDirection2 = saab.getDirection();
        saab.turnRight();  // Turn the car left (increase direction)
        assertEquals(saab.getDirection(), (initialDirection2 + 90) % 360, "Svägning till vänster funka ej för saab");
    }

    @Test
    public void testGasInterval(){
        try {
            saab.gas(-0.1);
            fail("Gas funktion ska ej funka");
        } catch (IllegalArgumentException e) {
            // Passed
        }

        try {
            saab.gas(1.1);
            fail("Gas funktion ska ej funka");
        } catch (IllegalArgumentException e) {
            //Passed
        }
    }

    @Test
    public void testBrakeInterval(){
        try {
            saab.brake(-0.1);
            fail("Brake funktion ska ej funka");
        } catch (IllegalArgumentException e) {
            // Passed
        }

        try {
            saab.brake(1.1);
            fail("Brake funktion ska ej funka");
        } catch (IllegalArgumentException e) {
            //Passed
        }
    }


    @Test
    public void testCurrentSpeedInterval(){
        saab.brake(1);
        assertTrue(saab.getCurrentSpeed()>=0, "Speed is below 0.");
        for (int i= 0;i < 10;i++) {
            saab.gas(1);
        }
        assertTrue(saab.getCurrentSpeed() <= saab.getEnginePower(), "Speed exceeds engine power.");
    }

    @Test
    public void testBrakeIncreaseSpeed() {
        double speedbefore = saab.getCurrentSpeed();
        saab.brake(0.5);
        double speedAfterBrake = saab.getCurrentSpeed();
        assertTrue(speedAfterBrake <= speedbefore, "Brake method should not increase speed.");
    }

    @Test
    public void testGasDecreaseSpeed() {
        double speedBefore = saab.getCurrentSpeed();
        saab.gas(0.5);
        double speedAfterGas = saab.getCurrentSpeed();
        assertTrue(speedAfterGas >= speedBefore, "Gas method should not decrease speed.");
    }
}
