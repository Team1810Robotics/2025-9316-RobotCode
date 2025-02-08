package frc.robot.subsystems;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkLowLevel.MotorType;

import static frc.robot.Constants.*;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ShooterSubsystem extends SubsystemBase {

    private final SparkMax motor;
    private final DigitalInput beam1;
    private final DigitalInput beam2;

    public ShooterSubsystem() {
        motor = new SparkMax(ShooterConstants.MOTOR_ID, MotorType.kBrushless);

        beam1 = new DigitalInput(ShooterConstants.BEAM1_ID);
        beam2 = new DigitalInput(ShooterConstants.BEAM2_ID);
    }
    public void setSpeed(double speed){
        motor.set(speed);
    }
    public void stop(){
        motor.stopMotor();
    }
}
    //TODO set constant;
    



