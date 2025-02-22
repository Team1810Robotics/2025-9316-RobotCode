// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;


import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Constants.VisionConstants;
import java.util.List;
import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.math.geometry.Translation3d;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.apriltag.AprilTagFieldLayout;
import edu.wpi.first.apriltag.AprilTagFields;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotContainer;
import frc.robot.Constants.VisionConstants;
import org.photonvision.targeting.PhotonPipelineResult;




public class VisionSubsystem extends SubsystemBase {
   
    public PIDController spinPIDController = new PIDController(VisionConstants.V_Kp, VisionConstants.V_Ki, VisionConstants.V_Kd);
    public PIDController driveControllerY = new PIDController(VisionConstants.VY_Kp, VisionConstants.VY_Ki, VisionConstants.VY_Kd);
	public PIDController driveControllerX = new PIDController(VisionConstants.VX_Kp, VisionConstants.VX_Ki, VisionConstants.VX_Kd);
    
    Transform3d robotToCam = VisionConstants.CAMERA_OFFSET;

AprilTagFieldLayout AprilTagFieldLayout = 
VisionConstants.APRIL_TAG_FIELD_LAYOUT;

    public PhotonCamera camera;
    PhotonPoseEstimator photonPoseEstimator;

    List<PhotonPipelineResult> allResults;
    public static PhotonPipelineResult result; 
    double targetYaw;
	double targetRange;
	boolean targetVisible;

    AprilTagFieldLayout aprilTagFieldLayout =
            AprilTagFields.k2025Reefscape.loadAprilTagLayoutField();
    
    public static final Transform3d CAMERA_TO_ROBOT =
                new Transform3d(new Translation3d(0.0, 0.0, 0.0), new Rotation3d(0, 0, 0));
    
    public VisionSubsystem() {
        camera = new PhotonCamera(VisionConstants.TARGET_CAMERA);
        photonPoseEstimator =
                new PhotonPoseEstimator(
                        AprilTagFieldLayout,

                        PoseStrategy.CLOSEST_TO_REFERENCE_POSE,                    
                        robotToCam);
        allResults = camera.getAllUnreadResults();

        SmartDashboard.putData("VisPID", spinPIDController);

    }
    

public Optional<Double> getRange() {
    if (hasTarget()) {
        return Optional.of(result.getBestTarget().getBestCameraToTarget().getTranslation().getNorm());
    }
    return Optional.of(1000.0);
}

public Optional<Double> getXRange(){
    if (hasTarget()){
        return Optional.of(result.getBestTarget().altCameraToTarget.getX());
    } else return Optional.of(1000.0);
}

public Optional<Double> getPitch() {
    if (hasTarget()) {
        return Optional.of(result.getBestTarget().getPitch());
    } else {
        return Optional.of(1000.0);
    }
}

public Optional<Double> getRangeError(){
    if (hasTarget()) {
        return Optional.of(getRange().get() - 0.5);
    }
    else return Optional.of(1000.0);
}
      @Override
      public void periodic() {
        allResults = camera.getAllUnreadResults();
        
        // result = allResults.get(allResults.size() - 1);
        result = camera.getLatestResult();
        // This method will be called once per scheduler 
        
        //Shuffleboard.getTab("Vision").addBoolean("Has Tag", () -> result.hasTargets());
        //Shuffleboard.getTab("vision").addDouble("Yaw To Target", () -> getYaw().get());
        //SmartDashboard.putNumber("pidVis", visionTargetPIDCalc(RobotContainer.joystick.getZ(), ))
      }

//TODO: Review to ensure correctly instantiated
      PIDController rotPidController =
            new PIDController(VisionConstants.V_Kp, VisionConstants.V_Ki, VisionConstants.V_Kd);


    /**
     * @return the best target's ID
     */
    public int getTargetId() {
        return result.getBestTarget().getFiducialId();
    }

    /**
     * @returns the vision pipeline's result (all of its data)
     */
    public PhotonPipelineResult getResult() {
        return result;
    }

    public List<PhotonTrackedTarget> getTargets() {
        return result.getTargets();
    }


      /**
     * @return whether or not an AprilTag is detected
     */
    public boolean hasTarget() {
        return result.hasTargets();
    }

    public Optional<Double>getYaw() {
        if (hasTarget()) { 
            return Optional.of(result.getBestTarget().getYaw());
        } 
        else{return Optional.empty();}
    }

	public double visionDrive(double altDrive, double distance, double source, boolean driveMode, PIDController pid){
    if(getRange().isPresent() && driveMode && hasTarget()){
        return pid.calculate(source - distance);
    } else return altDrive;
}

    public double visionTargetPIDCalc(

         double altRotation, boolean visionMode) {
         boolean target = hasTarget();
         Optional<Double> yaw = getYaw();

         if (target && visionMode && yaw.isPresent()) {

             return -spinPIDController.calculate(yaw.get());

         }
         if ((visionMode == true) && !target) {
             return altRotation;
         }
          return altRotation;
      }

}

