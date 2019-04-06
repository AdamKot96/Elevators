package interfaces;

import enums.Direction;
import exceptions.InvalidInputException;

public interface ElevatorSystemInterface {
    void pickup(int callFloor, Direction direction) throws InvalidInputException;

    void addDestination(int elevatorID, int destinationFloor) throws InvalidInputException;

    void step();

    void inlineStatus();
}
