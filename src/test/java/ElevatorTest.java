import enums.Direction;
import org.junit.Before;
import org.junit.Test;
import system.Elevator;

import static org.junit.Assert.assertEquals;

public class ElevatorTest {
    private static final int NUM_OF_FLOORS = 25;
    private Elevator elevator;

    @Before
    public void start() {
        elevator = new Elevator(NUM_OF_FLOORS);
    }

    @Test
    public void findDestinationDirectionTest() throws Exception {
        int startingFloor = 5;
        int destinationFloor = 0;

        elevator.setCurrentFloor(startingFloor);
        elevator.setDestination(destinationFloor);

        assertEquals(elevator.findDestinationDirection(), Direction.DOWN);
        elevator.cancelDestination(0);
        destinationFloor = 11;
        elevator.setDestination(destinationFloor);
        assertEquals(elevator.findDestinationDirection(), Direction.UP);
    }
}