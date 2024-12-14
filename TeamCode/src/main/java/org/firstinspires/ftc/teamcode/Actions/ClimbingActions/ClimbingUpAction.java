package org.firstinspires.ftc.teamcode.Actions.ClimbingActions;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Actions.BaseAction;
import org.firstinspires.ftc.teamcode.Subsystems.ClimbingSubsystem;

public class ClimbingUpAction extends BaseAction {
    private final ClimbingSubsystem climbingSubsystemModule;
    private int targetTicks;
    private double power;

    public ClimbingUpAction(
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

    // Setters for targetTicks and power
    public void setTargetTicks(int targetTicks) {
        this.targetTicks = targetTicks;
    }

    public void setPower(double power) {
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

        return currentPos < targetTicks - 10; // Adjust tolerance as needed
    }
}
