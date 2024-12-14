package org.firstinspires.ftc.teamcode.Subsystems;

import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingCloseAction;
import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingDownAction;
import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingReleaseAction;
import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingSpecimenDownAction;
import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingUpAction;
import org.firstinspires.ftc.teamcode.Constants;

public class ClimbingSubsystem {

    DcMotor motor;
    Servo rightServo;
    Servo leftServo;
    Telemetry telemetry;

    // class constructor
    public ClimbingSubsystem(
            DcMotor climbingMotor,
            Servo rightServo,
            Servo leftServo,
            Telemetry telemetry
    ) {
        this.motor = climbingMotor; // reinit motors for use within class functions.
        this.rightServo = rightServo;
        this.leftServo = leftServo;
        this.telemetry = telemetry;
    }

    public double climbingMotorTicks() {
        return motor.getCurrentPosition();
    }

    public void moveClimbingMotorTicks(int ticksToBeMoved, double power) {
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }

    public void resetMotor(double motorPower) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setPower(motorPower);
    }

    public void servosFreeze() {
        rightServo.setPosition(rightServo.getPosition());
        leftServo.setPosition(leftServo.getPosition());
    }

    public void setLeftServo(double pos) {
        leftServo.setPosition(pos);
    }

    public void setRightServo(double pos) {
        rightServo.setPosition(pos);
    }

    public void stopClimbingMotor() {
        motor.setPower(0);
    }
    
    public Action climbingMotorUp() {
        return new ClimbingUpAction(this, 1550, 0.6, telemetry);
    }

    public ClimbingDownAction climbingMotorDown() {
        return new ClimbingDownAction(this, 0, 0.6, telemetry);
    }

    public Action climbingMotorSpecimenDown() {
        return new ClimbingSpecimenDownAction(this, 1125, 0.6, telemetry);
    }

    public Action climbingRelease() {
        return new ClimbingReleaseAction(
                this,
                Constants.Climbing.CLIMBING_RIGHT_SERVO_OPEN_POSITION,
                Constants.Climbing.CLIMBING_LEFT_SERVO_OPEN_POSITION,
                telemetry
        );
    }

    public Action climbingClose() {
        return new ClimbingCloseAction(this, telemetry);
    }
}
