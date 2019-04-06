import enums.Direction;
import exceptions.InvalidInputException;
import system.ElevatorSystem;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        randomSimulation(4, 20, 120, 5, 4, 1000);
    }

    private static void randomSimulation(int numOfElevators, int numOfFloors, int numOfSteps, int numOfStepsBetweenCalls, int numOfStepsBetweenDestinationPick, int SleepTimeEachStep) {
        try {
            ElevatorSystem system = new ElevatorSystem(numOfElevators, numOfFloors);
            Random random = new Random();
            //randomize starting position
            for (int i = 0; i < numOfElevators; i++)
                system.setElevatorPosition(i, random.nextInt(numOfFloors));
            System.out.println("Starting postition:");
            system.inlineStatus();
            for (int i = 0; i < numOfSteps; i++) {
                if (i % numOfStepsBetweenCalls == 0) {
                    int floor = random.nextInt(numOfFloors);
                    system.pickup(floor, Direction.random());
                    System.out.printf("pick up call on floor %d \n", floor);
                }
                if (i % numOfStepsBetweenDestinationPick == 0) {
                    int elevator = random.nextInt(numOfElevators);
                    int floor = random.nextInt(numOfFloors);
                    system.addDestination(elevator, floor);
                    System.out.printf("new destination for elevator %d to floor %d \n", elevator, floor);
                }
                system.step();
                system.inlineStatus();
                try {
                    Thread.sleep(SleepTimeEachStep);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (InvalidInputException invalidInputException) {
            invalidInputException.printStackTrace();
        }

    }

}

