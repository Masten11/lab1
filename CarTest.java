import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.awt.*;

public class CarTest {
    private Car volvo;
    private Car saab;

    @BeforeEach
    public void setUp() {
        volvo = new Volvo240();
        saab = new Saab95();
        volvo.startEngine();
        saab.startEngine();
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

    /*
    @Test
    public void testCurrentSpeedInterval(){
        saab.brake(1);
        assertTrue(saab.getCurrentSpeed()>=0, "Speed is below 0.");
        saab.setCurrentSpeed(125);
        saab.gas(1);
        assertTrue(saab.getCurrentSpeed() <= saab.getEnginePower(), "Speed exceeds engine power.");
    }
    */
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
