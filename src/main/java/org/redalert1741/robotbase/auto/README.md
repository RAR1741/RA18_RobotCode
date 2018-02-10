# Creating AutoMove Components

## AutoMoveMove
First, create a new class that implements `AutoMoveMove`:
```java
public class TalonMove implements AutoMoveMove {
    
}
```
Create a constructor that takes and stores the objects that are used:
```java
public class TalonMove implements AutoMoveMove {
    TalonSrxWrapper talon;

    public TalonMove(TalonSrxWrapper talon) {
        this.talon = talon;
    }
}
```
The `setArgs` function takes a map that describes how a move may vary.

In this example, we just want a power to set the talon to.

Implement the `setArgs` function:
```java
public class TalonMove implements AutoMoveMove {
    ...
    private double power;

    @Override
    public void setArgs(Map<String, String> args) {
        power = (double) args.get("power");
    }
}
```
The three parts of running a move are `start`, `run`, and `stop`.

`start` and `stop` are run once at the beginning and end of a move respectively.
`run` is run each code cycle, possibly directly after `start` or before `stop`.

Running a motor has no special initialization, so we'll leave it empty:
```java
public class TalonMove implements AutoMoveMove {
    ...
    @Override
    public void start {}
}
```
`run` will run the motor, and `stop` will stop it when the move ends:
```java
public class TalonMove implements AutoMoveMove {
    ...
    @Override
    public void run {
        talon.set(ControlMove.PercentOutput, power);
    }

    @Override
    public void stop {
        talon.set(ControlMove.PercentOutput, 0);
    }
}
```
And that's it, the whole file would look like this:
```java
//package and imports

public class TalonMove implements AutoMoveMove {
    TalonSrxWrapper talon;

    public TalonMove(TalonSrxWrapper talon) {
        this.talon = talon;
    }

    @Override
    public void start {}

    @Override
    public void run {
        talon.set(ControlMove.PercentOutput, power);
    }

    @Override
    public void stop {
        talon.set(ControlMove.PercentOutput, 0);
    }
}
```
To add it to the robot and control a talon called
`autoTalon`, add the following line in `Robot.java`:
```java
public class Robot extends IterativeRobot {
    ...
    @Override
    public void robotInit() {
        ...
        AutoFactory.addMoveMove("autoTalon", () -> new TalonMove(autoTalon));
        ...
    }
}
```
## AutoMoveEnd
Similar to `AutoMoveMove`, create a file and implement `AutoMoveEnd`:
```java
public class TalonRotations implements AutoMoveEnd {

}
```
Create a constructor that takes and stores the objects that are used:
```java
public class TalonRotations implements AutoMoveEnd {
    TalonSrxWrapper talon;

    public TalonRotations(TalonSrxWrapper talon) {
        this.talon = talon;
    }
}
```
The `setArgs` function takes a map that describes how an end may vary.

In this example, we just want a distance for the talon to move.

Implement the `setArgs` function:
```java
public class TalonRotations implements AutoMoveEnd {
    ...
    private int distance;

    @Override
    public void setArgs(Map<String, String> args) {
        distance = (int) args.get("distance");
    }
}
```
The two parts of an `AutoMoveEnd` are `start` and `isFinished`.

`start` should initialize the end and `isFinished` returns
whether the current move should end.

In this example, `start` will store the current position and direction of travel
and `isFinished` return whether the setpoint has been passed.
```java
public class TalonRotations implements AutoMoveEnd {
    ...
    private boolean direction;
    private int startPosition;

    @Override
    public void start() {
        startPosition = talon.getSensorCollection().getQuadraturePosition();
        direction = distance > startPosition;
    }

    @Override
    public boolean isFinished() {
        if(direction) {
            return startPosition > distance;
        else {
            return startPosition < distance;
        }
    }
}
```
The entire file will look like this:
```java
//package and imports

public class TalonRotations implements AutoMoveEnd {
    TalonSrxWrapper talon;
    private int distance;
    private boolean direction;
    private int startPosition;

    public TalonRotations(TalonSrxWrapper talon) {
        this.talon = talon;
    }

    @Override
    public void setArgs(Map<String, String> args) {
        distance = (int) args.get("distance");
    }

    @Override
    public void start() {
        startPosition = talon.getSensorCollection().getQuadraturePosition();
        direction = distance > startPosition;
    }

    @Override
    public boolean isFinished() {
        if(direction) {
            return startPosition > distance;
        else {
            return startPosition < distance;
        }
    }
}
```
To add it to the robot to measure a talon called `autoTalon`, add the following
line in `Robot.java`:
```java
public class Robot extends IterativeRobot {
    ...
    @Override
    public void robotInit() {
        ...
        AutoFactory.addMoveEnd("talonTicks", () -> new TalonRotations(autoTalon));
        ...
    }
}
```
