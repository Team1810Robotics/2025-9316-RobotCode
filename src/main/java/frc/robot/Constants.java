package frc.robot;

// import javax.xml.crypto.dsig.Transform;

import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Transform3d;


public class Constants {
    public static final class VisionConstants{
       
        public static final double CAMERA_HIGHT = 0.0;
        public static final double APRILTAG_RED_SHOOTER_HEIGHT = 0.0;
        public static final double CAMERA_PITCH = 0.0;
        
        public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT =
            AprilTagFields.k2025Reefscape.loadAprilTagLayoutField();
        public static final Transform3d CAMERA_OFFSET = null;
        public static String TARGET_CAMERA; 
        
        }

    public static final AprilTagFieldLayout APRIL_TAG_FIELD_LAYOUT = null;
    
    public static final class LEDConstants{ 
        public static final double Yellow[] = {245, 239, 66};
        public static final double White[] = {255, 255, 255};
        public static final double Orange[] = {255, 128, 0};
        public static final double Green[] = {60, 255, 0};
        }
    
}



