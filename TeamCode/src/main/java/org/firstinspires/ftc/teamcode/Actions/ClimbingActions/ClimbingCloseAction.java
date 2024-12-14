package org.firstinspires.ftc.teamcode.Actions.ClimbingActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Actions.BaseAction;
import org.firstinspires.ftc.teamcode.Subsystems.ClimbingSubsystem;

public class ClimbingCloseAction extends BaseAction {
    private final ClimbingSubsystem climbingSubsystemModule;
    private final double rightServoPos;
    private final double leftServoPos;

    public ClimbingCloseAction(
            ClimbingSubsystem climbingSubsystemModule,
            Telemetry telemetry
    ) {
        super(telemetry);
        this.climbingSubsystemModule = climbingSubsystemModule;
        this.rightServoPos = rightServoPos;
        this.leftServoPos = leftServoPos;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        climbingSubsystemModule.setRightServo(rightServoPos);
        climbingSubsystemModule.setLeftServo(leftServoPos);
        return false; // Instant action, no need to repeat
    }
}
