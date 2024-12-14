package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Actions.ClawActions.CloseClawAction;
import org.firstinspires.ftc.teamcode.Actions.ClawActions.OpenClawAction;

public class ClawSubsystem {
    private final Servo claw;

    public ClawSubsystem(Servo claw) {
        this.claw = claw;
    }

    public void setPosition(double position) {
        claw.setPosition(position);
    }

    public Action createCloseClawAction(Telemetry telemetry) {
        return new CloseClawAction(this, telemetry);
    }

    public Action createOpenClawAction(Telemetry telemetry) {
        return new OpenClawAction(this, telemetry);
    }
}
