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
    private AllCarsWorkShop allCarsWorkShop;

    @BeforeEach
    public void setUp() {
        carTransport = new CarTransport(2); // Max två bilar
        volvoWorkshop = new CarWorkshop<>(2);
        allCarsWorkShop = new AllCarsWorkShop(2);
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
        assertEquals("Car workshop is full!", exception.getMessage());
    }

    @Test
    void testOnlySpecificCarTypeAllowed() {
        Volvo240 volvo = new Volvo240();

        volvoWorkshop.leaveCar(volvo);

        assertTrue(volvoWorkshop.hasCar(volvo), "Only one type of car object is allowrd!");
    }

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
        assertThrows(IllegalArgumentException.class, () -> carTransport.lowerPlatform(),
                "The truck cannot be moving!");
    }

    @Test
    void testCannotLoadCarWhenRampIsUp() {
        assertThrows(IllegalStateException.class, () -> carTransport.load(volvo),
                "Ramp must be lowered to load a car!");
    }

    @Test
    void testCannotLoadCarIfFull() {
        carTransport.lowerPlatform();
        carTransport.load(volvo);
        carTransport.load(saab);
        assertThrows(IllegalStateException.class, () -> carTransport.load(new Volvo240()),
                "Car transport is full!");
    }


    @Test
    void testCannotLoadAnotherCarTransport() {
        carTransport.lowerPlatform();
        assertThrows(IllegalArgumentException.class, () -> carTransport.load(new CarTransport(1)),
                "Cannot load another car transport");
    }

    @Test
    void testCannotLoadCarTooFarAway() {
        volvo.setX(carTransport.getX() + 3);
        carTransport.lowerPlatform();
        assertThrows(IllegalArgumentException.class, () -> carTransport.load(volvo),
                "Car needs to be closer to transport car");
    }

    @Test
    void testCannotUnloadWhenRampIsUp() {
        carTransport.lowerPlatform();
        carTransport.load(volvo);
        carTransport.raisePlatform();
        assertThrows(IllegalStateException.class, () -> carTransport.unload(),
                "Ramp must be lowered to unload a car");
    }

    @Test
    void testUnloadCarPlacesItNearby() {
        carTransport.lowerPlatform();
        carTransport.load(volvo);
        carTransport.unload();
        assertEquals(carTransport.getY() + 2.0, volvo.getY(), "Should be placed nere by car");
    }

    @Test
    public void testRaisePlattform() {

        assertThrows(IllegalArgumentException.class, () -> scania.raisePlatform(50),
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
