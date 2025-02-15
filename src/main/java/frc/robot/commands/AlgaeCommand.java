package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.subsystems.AlgaeSubsystem;

public class AlgaeCommand extends Command {
    private AlgaeSubsystem algaeSubsystem;

    public AlgaeCommand(AlgaeSubsystem algaeSubsystem){
        this.algaeSubsystem = algaeSubsystem;

        addRequirements(algaeSubsystem);
    }
 
    @Override
    public void initialize() {
        //algaeSubsystem.distanceSensor.setAutomaticMode(true);
    }

    @Override
    public void execute() {
        algaeSubsystem.setSpeed(1);
    }

    // @Override
    // public boolean isFinished() {
    //     double distance = algaeSubsystem.getDistanceSensor();
    //     if (distance <= 0 && distance > 0) {
    //         //TODO: tune first value may be 7cm 
    //         return true;
    //     } else if(distance < 0){
    //         CommandScheduler.getInstance().schedule(Commands.print("Invalid range"));
    //         return true;
    //     }

    //     return false;
    // }

    @Override
    public void end(boolean interrupted) {
        algaeSubsystem.stop();
    }
}
