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
import org.photonvision.EstimatedRobotPose;
import org.photonvision.PhotonCamera;
import org.photonvision.PhotonPoseEstimator;
import org.photonvision.PhotonPoseEstimator.PoseStrategy;
import org.photonvision.PhotonUtils;
import org.photonvision.targeting.PhotonPipelineResult;
import org.photonvision.targeting.PhotonTrackedTarget;



public class Vision_Subsystem extends SubsystemBase {
   
    private final PIDController spinPIDController = new PIDController(VisionConstants.V_Kp, VisionConstants.V_Ki, VisionConstants.V_Kd);
    
    
    Transform3d robotToCam = VisionConstants.CAMERA_OFFSET;

AprilTagFieldLayout AprilTagFieldLayout = 
Constants.APRIL_TAG_FIELD_LAYOUT;

    PhotonCamera camera;
    PhotonPoseEstimator photonPoseEstimator;
    PhotonPipelineResult result; 

     public Vision_Subsystem() {
        camera = new PhotonCamera(VisionConstants.TARGET_CAMERA);
        photonPoseEstimator =
                new PhotonPoseEstimator(
                        AprilTagFieldLayout,
                        PoseStrategy.CLOSEST_TO_REFERENCE_POSE,
                        
                        robotToCam);
        result = camera.getLatestResult();
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

    @Override
    public void periodic() {
        result = camera.getLatestResult();
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
    