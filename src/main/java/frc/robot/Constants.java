// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot;


import edu.wpi.first.wpilibj.I2C.Port;
import javax.xml.crypto.dsig.Transform;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.XboxController;


public class Constants {

    public static final class ElevatorConstants{
        // for more info:
    // https://docs.google.com/spreadsheets/d/1y8MNmf4Ztvmj5xiLHOoS9DUJrCoks60W2GWf0nozFus/edit?gid=2021393044#gid=2021393044
    public static final double ELEVATOR_UP_POSITION = 0.25;
    public static final double ELEVATOR_DOWN_POSITION = 0;
    public static final int elevatorMotor1 = 10;
    public static final int elevatorMotor2 = 9;
    public static final int LIMIT_SWITCH = 0; // Unknown Port
    public static final int ELEVATOR_ENCODER = 49; 
    }


  public static final class ShooterConstants {

        public static final int MOTOR_ID = 11; // Coral Intake
        /*

         See Wiring Spreadsheet for more details:

        https://docs.google.com/spreadsheets/d/1y8MNmf4Ztvmj5xiLHOoS9DUJrCoks60W2GWf0nozFus/edit?gid=2021393044#gid=2021393044
         
        */
        public static final int BEAM1_ID = 40; // Hopper Beam Break
        public static final int BEAM2_ID = 41; // Inside Intake Beam Break
        public static final int BEAM3_ID = 42; // Outside Intake Beam Break

        //TODO: dont know yet
        public static final int ELEVATOR_BEAM_ID = 0;
        public static final int HOPPER_BEAM_ID = 0;
        public static final int SHOOTER_BEAM_ID = 0;
        
    }

    public class AlgaeConstants {
        public static final int MOTOR_ID = 0;
        public static final Port DISTANCE_SENSOR_PORT = edu.wpi.first.wpilibj.I2C.Port.kOnboard;

    }
    public class IntakeConstants {
        public static int INTAKE_MOTOR = 25;
    }
    public static final class VisionConstants {

        public static final String TARGET_CAMERA = "Arducam_OV9281_USB_Camera";

        public static final double CAMERA_HIGHT = 0.0;
        public static final double APRILTAG_RED_SHOOTER_HEIGHT = 0.0;
        public static final double CAMERA_PITCH = 0.0;
        
        public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT =
            AprilTagFields.k2025Reefscape.loadAprilTagLayoutField();
        public static final Transform3d CAMERA_OFFSET = null;
               

        //PID constants and will need to be changed for the robot 
        public static final double V_Kp = 0.05;
        public static final double V_Ki = 0.0;
        public static final double V_Kd = 0.0;

       }

    
    


    public static final class LEDConstants{ 
        public static final double Yellow[] = {255, 255, };
        public static final double White[] = {255, 255, 255};
        public static final double Orange[] = {255, 128, 0};
        public static final double Green[] = {0, 255, 0};
        public static final int CANdleID = 35;
        public static final int MaxBrightnessAngle = 90;
        public static final int MidBrightnessAngle = 180;
        public static final int ZeroBrightnessAngle = 270;
        public static final int VbatButton = XboxController.Button.kA.value;
        public static final int V5Button = XboxController.Button.kB.value;
        public static final int CurrentButton = XboxController.Button.kX.value;
        public static final int TemperatureButton = XboxController.Button.kY.value;

        }





   
}
