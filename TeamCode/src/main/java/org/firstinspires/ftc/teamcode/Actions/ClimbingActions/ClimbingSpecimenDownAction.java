package org.firstinspires.ftc.teamcode.Actions.ClimbingActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Actions.BaseAction;
import org.firstinspires.ftc.teamcode.Subsystems.ClimbingSubsystem;

public class ClimbingSpecimenDownAction extends BaseAction {
    private final ClimbingSubsystem climbingSubsystemModule;
    private final int targetTicks;
    private final double power;

    public ClimbingSpecimenDownAction(
            ClimbingSubsystem climbingSubsystemModule,
            int targetTicks,
            double power,
            Telemetry telemetry
    ) {
        super(telemetry);
        this.climbingSubsystemModule = climbingSubsystemModule;
        this.targetTicks = targetTicks;
        this.power = power;
    }

    @Override
    public boolean run(@NonNull TelemetryPacket packet) {
        if (!initialized) {
            climbingSubsystemModule.moveClimbingMotorTicks(targetTicks, power);
            initialized = true;
        }

        double currentPos = climbingSubsystemModule.climbingMotorTicks();
        packet.put("climbingMotorPos", currentPos);
        logTelemetry("Climbing Pos", currentPos);

        if (Math.abs(currentPos - targetTicks) <= 5) {
            climbingSubsystemModule.stopClimbingMotor();
            return false;
        }
        return true;
    }
}
