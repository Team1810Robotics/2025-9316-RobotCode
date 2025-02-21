package frc.robot.subsystems;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.ElevatorConstants;

public class ElevatorSubsystem extends SubsystemBase {
    private SparkMax elevatorMotor;
    private SparkMax elevatorMotor2;
    private Encoder elevatorEncoder;
    private PIDController elevatorPID;
    private DigitalInput upperLimitSwitch;
    private DigitalInput lowerLimitSwitch;

    private static final double MAX_HEIGHT = 72.0; // Maximum height in encoder units
    private static final double MIN_HEIGHT = 0.0;  // Minimum height in encoder units

    public static final double INTAKE_POSITION = 0.0;
    public static final double L2_POSITION = 10.0;
    public static final double L3_POSITION = 20.0;
    public static final double ALGAE_SCORE_POSITION = 30.0;

    private double targetPosition = INTAKE_POSITION;

    public ElevatorSubsystem() {
        elevatorMotor = new SparkMax(Constants.ElevatorConstants.ELEVATOR_MOTOR_1_ID, MotorType.kBrushless);
        elevatorMotor2 = new SparkMax(Constants.ElevatorConstants.ELEVATOR_MOTOR_2_ID, MotorType.kBrushless);
        
        SparkMaxConfig config_ = new SparkMaxConfig();
        SparkMaxConfig config_2 = new SparkMaxConfig();
        
        config_.idleMode(SparkBaseConfig.IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.smartCurrentLimit);
        config_2.idleMode(SparkBaseConfig.IdleMode.kBrake).smartCurrentLimit(ElevatorConstants.smartCurrentLimit).follow(Constants.ElevatorConstants.ELEVATOR_MOTOR_1_ID, true);
        
        elevatorMotor.configure(config_, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        elevatorMotor2.configure(config_2, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        elevatorEncoder = new Encoder(0, 1, false, Encoder.EncodingType.k4X); // Correct Encoder instantiation
        elevatorEncoder.setDistancePerPulse(1.0); // Calibrate this value!

        elevatorPID = new PIDController(1.0, 0.0, 0.0); // PIDController instantiation
        elevatorPID.setTolerance(1.0); // Set tolerance for PID control

        // Initialize limit switches (replace 2 and 3 with your actual pin numbers)
        upperLimitSwitch = new DigitalInput(2);
        lowerLimitSwitch = new DigitalInput(3);

        SmartDashboard.putNumber("Motor Power", elevatorMotor.getOutputCurrent());
    }

    /**
     * Sets the target elevator position.
     */
    public void setElevatorPosition(double position) {
        targetPosition = position;
    }

    /**
     * Controls the elevator based on the speed input.
     */
    public void controlElevator(double speed) {
        // Check limits before moving
        if ((speed > 0 && !upperLimitSwitch.get()) || (speed < 0 && !lowerLimitSwitch.get())) { // Active low
            // Check software limits if hardware limits are not triggered
            if ((speed > 0 && elevatorEncoder.getDistance() < MAX_HEIGHT) || (speed < 0 && elevatorEncoder.getDistance() > MIN_HEIGHT)) {
                elevatorMotor.set(speed);
                elevatorMotor2.set(speed);
            } else {
                stopElevator();
            }
        } else {
            stopElevator(); // Stop if limit switch is active
        }
    }

    public void setPower(double power) {
        elevatorMotor.set(power);
        elevatorMotor2.set(power);
    }

    public void stopElevator() {
        elevatorMotor.set(0);
        elevatorMotor2.set(0);
    }

    @Override
    public void periodic() {
        double currentPosition = elevatorEncoder.getDistance();
        
        // PID control logic can be added here if needed
        double power = elevatorPID.calculate(currentPosition, targetPosition);
        elevatorMotor.set(power); // Apply PID control
        elevatorMotor2.set(power); // Apply PID control to follower motor

        SmartDashboard.putNumber("Elevator Position", currentPosition);
        SmartDashboard.putNumber("Elevator Power", power);
    }
}