package frc.robot.subsystems;
import java.util.logging.*;

import java.io.Console;

import com.revrobotics.spark.SparkMax;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
public class AutoSubsystem extends SubsystemBase{

private static final Logger logger = Logger.getLogger(AutoSubsystem.class.getName());
    public static void NoPath(){
        logger.info("No Path Selected.");
    }
    public static void Option1(){
        logger.info("Option 1 Chosen");
        // 1. TEST
        // 2. TO-DO PASTE CODE FOR ROBOT TO FOLLOW PATH
    }
    public static void Option2(){
        logger.info("Option 2 Chosen");
        // 1. TEST
        // 2. TO-DO PASTE CODE FOR ROBOT TO FOLLOW PATH
    }
}
