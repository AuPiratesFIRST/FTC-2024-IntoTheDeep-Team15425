package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

public class Climbing {

    DcMotor motor;
    Servo rightServo;
    Servo leftServo;
    // class constructor
    public Climbing(DcMotor climbingMotor, Servo rightServo, Servo leftServo){
        this.motor = climbingMotor; // reinit motors for use within class functions.
        this.rightServo = rightServo;
        this.leftServo = leftServo;
    }

    public void moveClimbingMotorTicks(int ticksToBeMoved, double power){
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }
    public void stopClimbingMotor(){
        motor.setPower(0);
    }
    public double climbingMotorTicks() {
        return motor.getCurrentPosition();
    }

    public void setRightServo(double pos) {
        rightServo.setPosition(pos);

    }
    public void setLeftServo(double pos) {
        leftServo.setPosition(pos);
    }

    public void servosFreeze() {
        rightServo.setPosition(rightServo.getPosition());
        leftServo.setPosition(leftServo.getPosition());
    }
    public void resetMotor(double motorPower) {
        motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor.setPower(motorPower);
    }

}
