package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax motor;
    private final DigitalInput hopperBeam;
    private final DigitalInput elevatorBeam;
    private final DigitalInput shooterBeam;


    public ShooterSubsystem() {
        motor = new SparkMax(ShooterConstants.SHOOTER_MOTOR_ID, MotorType.kBrushless);
        shooterBeam = new DigitalInput(ShooterConstants.SHOOTER_BEAM_BREAK_ID);
        hopperBeam = new DigitalInput(ShooterConstants.HOPPER_BEAM_BREAK_ID);
        elevatorBeam = new DigitalInput(ShooterConstants.ELEVATOR_BEAM_BREAK_ID);
    }

    public void setSpeed(double speed){
        motor.set(speed);
    }
    
    public void stop(){
        motor.stopMotor();
    }

    public boolean getHopperBeam() {
        return !hopperBeam.get();
    }

    public boolean getElevatorBeam() {
        return !elevatorBeam.get();
    }

    public boolean getShooterBeam() {
        return !shooterBeam.get();
    }


}
    //TODO set constant;
    



