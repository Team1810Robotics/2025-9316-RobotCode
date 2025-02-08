package frc.robot;


// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


import javax.xml.crypto.dsig.Transform;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.wpilibj.XboxController;



public class Constants {

  public static final class ShooterConstants {
        //dont know yet
        public static final int MOTOR_ID = 0;
        public static final int BEAM1_ID = 0;
        public static final int BEAM2_ID = 0;
        public static final int BEAM3_ID = 0;
    }
    public class IntakeConstants {
        public static int INTAKE_MOTOR = 25;
    }
    public static final class VisionConstants{

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

    public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = null;
    


    public static final class LEDConstants{ 
        public static final double Yellow[] = {245, 239, 66};
        public static final double White[] = {255, 255, 255};
        public static final double Orange[] = {255, 128, 0};
        public static final double Green[] = {60, 255, 0};
        }
    public final class constants{
         public static final int CANdleID = 1;
         public static final int MaxBrightnessAngle = 90;
         public static final int MidBrightnessAngle = 180;
         public static final int ZeroBrightnessAngle = 270;
         public static final int VbatButton = XboxController.Button.kA.value;
         public static final int V5Button = XboxController.Button.kB.value;
         public static final int CurrentButton = XboxController.Button.kX.value;
         public static final int TemperatureButton = XboxController.Button.kY.value;
}

}



