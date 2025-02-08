package frc.robot.subsystems;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;

public class Elevator_Subsystem extends SubsystemBase {
    private SparkMax elevatorMotor;
    private Encoder elevatorEncoder;
    private PIDController elevatorPID;

    private SparkMax elevatorMotor2;
    
    public Elevator_Subsystem() {
        elevatorMotor = new SparkMax(Constants.ElevatorConstants.elevatorMotor1, MotorType.kBrushless);
        elevatorMotor2 = new SparkMax(9, MotorType.kBrushless);
        elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
        elevatorPID = new PIDController(1.0, 0.0, 0.0);
        elevatorPID.setSetpoint(0);  // Starting setpoint
        elevatorPID.setTolerance(1.0);  // Set tolerance for how close we need to get to the target (in encoder counts)
        SmartDashboard.putNumber("Motor Power", elevatorMotor.getOutputCurrent());
    }

    public void moveElevator(double speed) {
        elevatorMotor.set(speed);
        elevatorMotor2.set(speed);
    }

    public void setPower(double power){
        elevatorMotor.set(power);
        elevatorMotor2.set(power);
    }

    public void moveToPosition(double setpoint) {
        elevatorPID.setSetpoint(setpoint);
        double output = elevatorPID.calculate(elevatorEncoder.getDistance());
        elevatorMotor.set(output);
    }

    public void controlElevator(double speed) {
        moveElevator(speed);
    }
}
