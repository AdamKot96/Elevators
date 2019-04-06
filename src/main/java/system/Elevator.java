package system;

import enums.Direction;
import exceptions.InvalidInputException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Elevator {
    private int currentFloor;
    private Direction currentDirection;
    private final List<Boolean> destinations;

    public Elevator(int numOfFloors) {
        destinations = new ArrayList<>(Collections.nCopies(numOfFloors, false));
        this.currentDirection = Direction.NONE;
        this.currentFloor = 0;
    }

    public void setDestination(int floor) throws InvalidInputException {
        if (floor < 0 || floor > destinations.size())
            throw new InvalidInputException("not existing floor passed as argument");
        this.destinations.set(floor, true);
    }

    public void cancelDestination(int floor) throws InvalidInputException {
        if (floor < 0 || floor > destinations.size())
            throw new InvalidInputException("not existing floor passed as argument");
        this.destinations.set(floor, false);
    }

    void moveUp() {
        currentFloor++;
    }

    void moveDown() {
        currentFloor--;
    }

    void setDestinationDirection() {
        this.currentDirection = findDestinationDirection();
    }

    public Direction findDestinationDirection() {
        switch (currentDirection) {
            case UP:
                for (int i = currentFloor; i < destinations.size() - 1; i++) {
                    if (destinations.get(i + 1))
                        return Direction.UP;
                }
                for (int i = currentFloor; i > 0; i--) {
                    if (destinations.get(i - 1)) {
                        return Direction.DOWN;
                    }
                }
                return Direction.NONE;

            case DOWN:
                for (int i = currentFloor; i > 0; i--) {
                    if (destinations.get(i - 1))
                        return Direction.DOWN;
                }
                for (int i = currentFloor; i < destinations.size() - 1; i++) {
                    if (destinations.get(i + 1)) {
                        return Direction.UP;
                    }
                }
                return Direction.NONE;
            case NONE:
                for (int i = currentFloor; i < destinations.size() - 1; i++) {
                    if (destinations.get(i + 1)) {
                        return Direction.UP;
                    }
                }
                for (int i = currentFloor; i > 0; i--) {
                    if (destinations.get(i - 1)) {
                        return Direction.DOWN;
                    }
                }
                return Direction.NONE;
        }
        return Direction.NONE;
    }

    Direction getCurrentDirection() {
        return currentDirection;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int floor) throws InvalidInputException {
        if (floor < 0 || floor > destinations.size())
            throw new InvalidInputException("not existing floor passed as argument");
        this.currentFloor = floor;
    }
}


