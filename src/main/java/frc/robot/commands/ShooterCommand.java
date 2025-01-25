package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSubsystem;


public class ShooterCommand extends Command {
    
    private ShooterSubsystem shooterSubsystem;
    private double motorSpeed;

    public ShooterCommand(ShooterSubsystem shooterSubsystem, double motorSpeed){
        this.shooterSubsystem = shooterSubsystem;
        this.motorSpeed = motorSpeed;
        addRequirements(shooterSubsystem);
    
    }

    @Override
    public void execute() {
        shooterSubsystem.setSpeed(motorSpeed);
    }

    @Override
    public void end(boolean interrupted) {
        shooterSubsystem.stop();
    }
}
