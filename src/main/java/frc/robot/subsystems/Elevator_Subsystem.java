package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

public class Elevator_Subsystem extends SubsystemBase {
    private WPI_TalonSRX elevatorMotor;
    private Encoder elevatorEncoder;
    private PIDController elevatorPID;
    

    public void ElevatorSubsystem() {
        elevatorMotor = new WPI_TalonSRX(5);
        elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        elevatorPID = new PIDController(1.0, 0.0, 0.0);
        elevatorPID.setSetpoint(0);  // Starting setpoint
        elevatorPID.setTolerance(1.0);  // Set tolerance for how close we need to get to the target (in encoder counts)
    }

    public void moveElevator(double speed) {
        elevatorMotor.set(speed);
    }

    public void moveToPosition(double setpoint) {
        elevatorPID.setSetpoint(setpoint);
        double output = elevatorPID.calculate(elevatorEncoder.getDistance());
        elevatorMotor.set(output);
    }
}

