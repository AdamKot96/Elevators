package system;

import enums.Direction;
import exceptions.InvalidInputException;
import interfaces.ElevatorSystemInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class ElevatorSystem implements ElevatorSystemInterface {
    private static final int MAX_ELEVATORS = 16;
    private int numOfElevators;
    private int numOfFloors;
    private final List<Elevator> elevators;
    private final Map<Integer, Direction> pickupCalls;

    public ElevatorSystem(int numOfElevators, int numOfFloors) throws InvalidInputException {
        //check input
        if (numOfElevators < 1 || numOfElevators > MAX_ELEVATORS)
            throw new InvalidInputException("number of Elevators must be between 1 and 16");
        if (numOfFloors < 1) throw new InvalidInputException("number of floors must be higher than 1");
        this.numOfElevators = numOfElevators;
        this.numOfFloors = numOfFloors;
        this.elevators = new ArrayList<>();
        IntStream.range(0, numOfElevators).forEach(id -> this.elevators.add(id, new Elevator(numOfFloors)));

        pickupCalls = new HashMap<>();
    }

    @Override
    public void addDestination(int elevatorID, int destinationFloor) throws InvalidInputException {
        //check input
        if (elevatorID < 0 || elevatorID > numOfElevators)
            throw new InvalidInputException("not existing elevator passed as argument");
        if (destinationFloor < 0 || destinationFloor > numOfFloors)
            throw new InvalidInputException("not existing floor passed as argument");
        elevators.get(elevatorID).setDestination(destinationFloor);
    }

    @Override
    public void pickup(int callFloor, Direction direction) throws InvalidInputException {
        //check input
        if (callFloor < 0 || callFloor > numOfFloors)
            throw new InvalidInputException("not existing floor passed as argument");
        pickupCalls.put(callFloor, direction);
    }

    private void setOptimalElevators() {
        try {
            for (Map.Entry<Integer, Direction> entry :
                    pickupCalls.entrySet()) {
                int suitableElevator = 0;
                int i = 0;
                int suitabilityFactor = 0;
                //find the most suitable elevator
                for (Elevator e :
                        elevators) {
                    int tmp;
                    // check if elevator is heading towards the call
                    if ((entry.getKey() > e.getCurrentFloor() && e.getCurrentDirection() == Direction.UP) || entry.getKey() < e.getCurrentFloor() && e.getCurrentDirection() == Direction.DOWN) {
                        //check if you are heading in the same direction as the elevator
                        if (entry.getValue() == e.getCurrentDirection()) {
                            tmp = numOfFloors + 2 - (Math.abs(entry.getKey() - e.getCurrentFloor()));
                        } else {
                            tmp = numOfFloors + 1 - (Math.abs(entry.getKey() - e.getCurrentFloor()));
                        }
                    }   //check if elevator is not moving
                    else if (e.getCurrentDirection() == Direction.NONE)
                        tmp = numOfFloors - (Math.abs(entry.getKey() - e.getCurrentFloor()));
                        //if it is moving in opposite direction
                    else
                        tmp = 1;
                    if (tmp > suitabilityFactor) {
                        suitabilityFactor = tmp;
                        suitableElevator = i;
                    }
                    i++;
                }
                elevators.get(suitableElevator).setDestination(entry.getKey());
            }
        } catch (InvalidInputException invalidInputException) {
            invalidInputException.printStackTrace();
        }
    }

    public void step() {
        try {
            setOptimalElevators();
            for (Elevator e :
                    elevators) {
                e.setDestinationDirection();
                //moving elevators towards directions
                switch (e.getCurrentDirection()) {
                    case UP:
                        e.moveUp();
                        break;
                    case DOWN:
                        e.moveDown();
                        break;
                    case NONE:
                        break;
                }
                //canceling other orders to this floor after arrival of any other elevator
                pickupCalls.remove(e.getCurrentFloor());
                for (Elevator ele :
                        elevators) {
                    ele.cancelDestination(ele.getCurrentFloor());
                }
            }
        } catch (InvalidInputException invalidInputException) {
            invalidInputException.printStackTrace();
        }
    }

    @Override
    public void inlineStatus() {
        for (int i = 0; i < numOfElevators; i++) {
            System.out.printf("%d\t", i);
        }
        System.out.print("ID \n");
        for (Elevator e :
                elevators) {
            System.out.printf("%d\t", e.getCurrentFloor());
        }
        System.out.print("Current Floor \n");
        for (Elevator e :
                elevators) {
            switch (e.getCurrentDirection()) {
                case UP:
                    System.out.print("^\t");
                    break;
                case DOWN:
                    System.out.print("v\t");
                    break;
                case NONE:
                    System.out.print("-\t");
                    break;
            }
        }
        System.out.print("Direction \n\n");
    }

    public List<Elevator> getElevators() {
        return elevators;
    }

    public List<Integer> getCurrentElevatorPositions() {
        List<Integer> list = new ArrayList<>();
        for (Elevator e :
                elevators) {
            list.add(e.getCurrentFloor());
        }
        return list;
    }

    public void setElevatorPosition(int elevatorID, int floor) throws InvalidInputException {
        //check input
        if (elevatorID < 0 || elevatorID > numOfElevators)
            throw new InvalidInputException("not existing elevator passed as argument");
        if (floor < 0 || floor > numOfFloors) throw new InvalidInputException("not existing floor passed as argument");
        elevators.get(elevatorID).setCurrentFloor(floor);
    }
}

