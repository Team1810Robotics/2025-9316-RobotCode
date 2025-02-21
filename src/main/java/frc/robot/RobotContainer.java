package frc.robot;

import static edu.wpi.first.units.Units.*;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.commands.AlgaeCommand;



import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.generated.TunerConstants;
import frc.robot.subsystems.AlgaeSubsystem;
import frc.robot.subsystems.AutoSubsystem;
import frc.robot.subsystems.CommandSwerveDrivetrain;
import frc.robot.subsystems.CoralHandlerSubsystem;
import frc.robot.subsystems.ElevatorSubsystem;
import frc.robot.subsystems.VisionSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.AutoSubsystem;


public class RobotContainer {
    private double MaxSpeed = TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
    private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
            .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1)
            .withDriveRequestType(DriveRequestType.OpenLoopVoltage);
    private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
    private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

    private final Telemetry logger = new Telemetry(MaxSpeed);
    private final CommandXboxController xbox = new CommandXboxController(1);
    private final CommandXboxController joystick = new CommandXboxController(0);
    public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
    public final ElevatorSubsystem elevatorSubsystem = new ElevatorSubsystem(); // Initialize Elevator Subsystem
    public final VisionSubsystem visionSubsystem = new VisionSubsystem();


    private final CoralHandlerSubsystem coralHandlerSubsystem = new CoralHandlerSubsystem();
    public final AlgaeSubsystem algaeSubsystem = new AlgaeSubsystem();
    private final SendableChooser<Command> autoChooser = new SendableChooser<>();
   

   
    public RobotContainer() {
        algaeSubsystem.setDefaultCommand(new AlgaeCommand(algaeSubsystem, false));
        
        configureBindings();
        configureAutoChooser();
    }


    private void offLineAuto(){
       // return driveSubsystem.drive(-.5,-.5).withTimeout(2);
    }

    private void configureBindings() {
        drivetrain.setDefaultCommand(
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-joystick.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-joystick.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-visionSubsystem.visionTargetPIDCalc(joystick.getRightX(), joystick.a().getAsBoolean()) * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );
        
        if(xbox.x().getAsBoolean()){
            elevatorSubsystem.setPower(0.1);
            System.out.println("High");
        } else {
            //elevatorSubsystem.setPower(0);
        }
        if(xbox.y().getAsBoolean()){
            elevatorSubsystem.setPower(-0.1);
        } else {
            //elevatorSubsystem.setPower(0);
        }

        xbox.leftBumper().whileTrue(new AlgaeCommand(algaeSubsystem, true));


        joystick.a().whileTrue(drivetrain.applyRequest(() -> brake));
        joystick.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-joystick.getLeftY(), -joystick.getLeftX()))
        ));

        // Bind Xbox controller buttons to elevator control
        joystick.rightBumper().whileTrue(new InstantCommand(() -> elevatorSubsystem.controlElevator(0.3))); // Raise elevator
        joystick.leftBumper().whileTrue(new InstantCommand(() -> elevatorSubsystem.controlElevator(-0.3))); // Lower elevator

        drivetrain.registerTelemetry(logger::telemeterize);

        // joystick.a().whileTrue(new ShooterCommand(shooterSubsystem, MaxAngularRate));

    }

    private void configureAutoChooser() {
        // Set default option
        autoChooser.setDefaultOption("No Auto", new InstantCommand(() -> AutoSubsystem.getAutoCommand("NoPath")));

        // Add PathPlanner paths
        autoChooser.addOption("2 Left Auto", AutoSubsystem.getAutoCommand("2LeftAuto"));
        autoChooser.addOption("2 Right Auto", AutoSubsystem.getAutoCommand("2RightAuto"));
        autoChooser.addOption("Left Auto", AutoSubsystem.getAutoCommand("LeftAuto"));
        autoChooser.addOption("Right Auto", AutoSubsystem.getAutoCommand("RightAuto"));
        autoChooser.addOption("Middle Auto", AutoSubsystem.getAutoCommand("MiddleAuto"));

        // Display on SmartDashboard
        SmartDashboard.putData("Auto choices", autoChooser);
    }

    public Command getAutonomousCommand() {
        if (autoChooser.getSelected() != null){
            return autoChooser.getSelected();
        } else {
            return Commands.print("No autonomous command configured, if a path was chosen, this is an error.");
        }
    }



}
