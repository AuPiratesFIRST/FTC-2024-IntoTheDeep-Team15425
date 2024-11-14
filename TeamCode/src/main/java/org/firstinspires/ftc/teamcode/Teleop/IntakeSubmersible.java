package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.CRServo;

public class IntakeSubmersible {
    DcMotor motor;
    CRServo CRservo;
    // class constructor
    public IntakeSubmersible(DcMotor subMotor, CRServo subCRServo){
        this.motor = subMotor; // reinit motors for use within class functions.
        this.CRservo = subCRServo;
    }

    public void moveSubMotorTicks(int ticksToBeMoved, double power){
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }
    public double subMotorTicks() {
        return motor.getCurrentPosition();
    }
    public void moveSubServo(double power){
        CRservo.setPower(power);
    }
}
