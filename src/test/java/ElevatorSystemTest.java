import enums.Direction;
import exceptions.InvalidInputException;
import org.junit.Before;
import org.junit.Test;
import system.ElevatorSystem;

import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ElevatorSystemTest {
    private static final int NUM_OF_FLOORS = 25;
    private static final int NUM_OF_ELEVATORS = 10;
    private ElevatorSystem system;

    @Before
    public void start() throws Exception {
        system = new ElevatorSystem(NUM_OF_ELEVATORS, NUM_OF_FLOORS);
        //randomizing starting floors
        Random random = new Random();
        for (int i = 0; i < NUM_OF_ELEVATORS; i++)
            system.setElevatorPosition(i, random.nextInt(NUM_OF_FLOORS));
    }

    @Test
    public void testCallingElevator() throws Exception {
        int callFloor = 24;
        system.pickup(callFloor, Direction.UP);
        for (int i = 0; i < NUM_OF_FLOORS; i++) {
            system.step();
        }
        List<Integer> floors = system.getCurrentElevatorPositions();
        //check if any Elevator arrived on requested floor
        assertTrue(floors.contains(callFloor));

    }

    @Test
    public void testGettingToDestination() throws Exception {
        int destinationFloor = 10;
        system.addDestination(5, destinationFloor);

        for (int i = 0; i < NUM_OF_FLOORS; i++) {
            system.step();
        }
        assertEquals(system.getElevators().get(5).getCurrentFloor(), destinationFloor);
    }
    @Test
    public void testSettingElevatorPosition() throws Exception {
        int position = 10;
        int elevator = 5;
         system.setElevatorPosition(elevator,position);
        assertEquals(system.getElevators().get(elevator).getCurrentFloor(),position);
    }
    @Test(expected = InvalidInputException.class)
    public void testSetCurrentFloorInvalidInputException() throws Exception{
        system.getElevators().get(0).setCurrentFloor(-10);
    }
}