package org.firstinspires.ftc.teamcode.Actions;

import com.acmerobotics.roadrunner.Action;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public abstract class BaseAction implements Action {
    protected boolean initialized = false;
    protected final Telemetry telemetry;

    public BaseAction(Telemetry telemetry) {
        this.telemetry = telemetry;
    }

    protected void logTelemetry(String label, double value) {
        telemetry.addData(label, value);
        telemetry.update();
    }

    protected void logTelemetry(String label, String value) {
        telemetry.addData(label, value);
        telemetry.update();
    }
}
