# Elevator Controler

------

This project contains simple elevator control system that reflects the most common elevator system.

- On every floor there is one panel to call an elevator with two buttons for each direction.
- There is a panel with number of buttons equal to number of floors in the building inside every elevator.

### Algorithm design

------

I decided to use other algorithm than FCFS because it is more efficient for the elevator users. It looks for the closest  elevator, however elevators that are already moving towards the call are sligthly favored

- Eveytime someone pushes call button, program is looking for the most optimal elevator using following rules
  - Suitability factor is defined. Call floor is added to destination list of an elevator with the highest suitability factor
  - Let's say N is a number of floors and D is a difference betwwen call floor and floor where the elevator currently is
  - Suitability factor equals N+2-D when the elevator is heading towards the call and the direction button of the call is same as current elevator direction
  - Suitability factor equals N+1-D when the elevator is heading towards the call and the direction button of the call is in oposite direction than the curent elevator direction
  - Suitability factor equals N-D when the elevator is not moving
  - Suitability factor equals 1 when the elevator is moving away from the call
- Everytime someone pushes the button in an elevator it adds that floor to the destination list
- Whenever elevator arrives to the floor, the floor is removed from destination list of every elevator
- Elevator continues to move in one direction as long as there are destinations in that direction, it may change direction if there is no destination ahead in current direction
- If elevator is not moving it prefers calls that make it move up over the ones that make it move down

Algorithm may require more computing power than FCFS algorithm but it serves users in more logical order than order in which calls were made. It might be improved by changing the system to destination dispatch system used in large modern buildings. User would choose destination floor before the elevator is choosen but it would not be "classic" and most commonly used elevator system. Also users might need time before they learn how to use it properly. Also program could alternate starting point of iteration to avoid overusing of a single elevator and damaging it over time. Number added to suitability factor for already moving elevators could be increased in non-peek hours to avoid using multiple elevators to reduce energy consumption.

### Class design

------

###### Enum Direction 

```java

```

```java
public enum Direction {
    UP,
    DOWN,
    NONE
}
```

###### Exception InvalidInput

For throwing exceptions for invalid input such as number of elevators exceeding given maximum or negative floor(floors under ground should be represented as negative using graphics on hardware, not as negative numbers in code)

###### Interface ElevatorSystemInterface

```java
public interface ElevatorSystemInterface {
    void pickup(int callFloor, Direction direction) throws InvalidInput;
    void addDestination(int elevatorID, int destinationFloor)throws InvalidInput;
    void step();
    void inlineStatus();
}
```

Contains abstract methods used in elevator systems

###### Class Elevator

Constructors:

```java
Elevator(int numOfFloors) - creates Elevator with number of floors passed in argument
```

Methods:

```Java
void setDestination(int floor) - adds floor passed as argument to destinations list
```

```java
void cancelDestination(int floor) - removes floor passed as argument from destinations list

void moveUp() - increments current floor of the elevator

void moveDown() - decrements current floor of the elevator

void setDestinationDirection() - sets current direction based on destinasions list

Direction findDestinationDirection() - returns direction the elevator should follow based on destinations list
```

```java
Direction getCurrentDirection() - returns current direction

void getCurrentFloor() - returns current floor

void setCurrentFloor(int floor) - sets current floor to the floor passed in argument

```

###### Class ElevatorSystem

Constructors:

```
ElevatorSystem(int numOfElevators, int numOfFloors) - creates ElevatorSystem with number of elevators and floors passed as arguments
```

Methods:

```java
List<Elevator> getElevators() - returns list of elevators

List<Integer> getCurrentElevatorPositions() - returns list of current elevator positions 

void setElevatorPosition(int elevatorID, int floor) - moves the elevator of ID passed in first argument to the floor passed in second argument
```

```java
void addDestination(int elevatorID, int destinationFloor) - adds destination floor passed in second argument to destination list of elevator passed in first argument

void pickup(int callFloor, Direction direction) - puts floor and Direction passed in arguments into pickupCalls map

void setOptimalElevators() - iterates through map of pickup calls and puts the calls to the most suitable elevators

void step() - performs step of simulation, moves every elevator by one floor in proper direction and removes the floor from destinations lists and pickupCalls map upon elevator arrival to that floor 

void inlineStatus() - prints system status in following format:
					  line 1: elevators IDs
					  line 2: current floor
					  line 3: direction visualized by '^' 'V' '-' signs  

```

###### Tests

Project contains simple tests for Elevator and ElevatorSystem classes

### Running the project

------

If you open the project in your IDE, besides elevator system classes, you can find simple simulation of system usage using randomly generated calls, new destinations and random elevator starting position.

Pass parameters of simulation to randomSimulation function called in main function

```java
static void randomSimulation(int numOfElevators, int numOfFloors,int numOfSteps, int numOfStepsBetweenCalls, int numOfStepsBetweenDestinationPick, int SleepTimeEachStep) 
    - performs simulation, arguments are: number of elevators in the system, number of floors in the buildsin, number of steps/iterations, number which defines steps interval between pick up calls, number which defines interval between picking new destination, sleep time between steps in miliseconds.
```

Function is already called in main function in Main class. You can change parameters of simulation there

everytime someone calls an elevator, program is looking for the most optimal elevator using following rules and adds the call floor to the list of destinations

- Suitability factor is defined. Pick up call is assigned to the elevator with the highest suitability factor
- Let's say that N is number of  floors and D is difference between current elevator position and call floor
- Suitability factor is equal to N + 2 - D if elevator is heading towards call and pushed direction button is same as current elevator direction
- N + 1 - D if the elevator is heading towards call and pushed direction button is opposite to current elevator direction
- N - D if the elevator is not moving
- Equals 1 if elevator is moving away from the call 

everytime someone pushes the floor button in the elevator, this floor is added to the destination list

whenever elevator arrives to the floor, this floor is removed from all of the destination lists



