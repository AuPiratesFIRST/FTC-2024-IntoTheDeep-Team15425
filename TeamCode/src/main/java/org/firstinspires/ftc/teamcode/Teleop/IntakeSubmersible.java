package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;


public class IntakeSubmersible {
    DcMotor motor;
    CRServo pivotCRServo;
    Servo rightServo;
    Servo leftServo;

    // class constructor
    public IntakeSubmersible(DcMotor subMotor, CRServo pivotCRServo, Servo rightServo, Servo leftServo){
        this.motor = subMotor; // reinit motors for use within class functions.
        this.pivotCRServo = pivotCRServo;
        this.rightServo = rightServo;
        this.leftServo = leftServo;
    }

    public void moveSubMotorTicks(int ticksToBeMoved, double power){
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }
    public double subMotorTicks() {
        return motor.getCurrentPosition();
    }

    public void moveSubCRServo(double power)
    {
        pivotCRServo.setPower(power);
    }
    public void setServos(double pos) {
        rightServo.setPosition(pos);
        leftServo.setPosition(1 - pos);
    }
    public void servosFreeze() {
        rightServo.setPosition(rightServo.getPosition());
        leftServo.setPosition(leftServo.getPosition());
    }
    public boolean servosAtPosition(double targetPosition, double deadBand){
        if (rightServo.getPosition() > targetPosition - deadBand && rightServo.getPosition() < targetPosition + deadBand && 1 - leftServo.getPosition() > targetPosition - deadBand && 1 - leftServo.getPosition() < targetPosition + deadBand) {
            return true;
        }
        else {
            return false;
        }
    }

}
