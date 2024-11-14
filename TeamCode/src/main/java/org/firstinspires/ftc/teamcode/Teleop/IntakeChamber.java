package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class IntakeChamber {
    DcMotor motor;
    CRServo CRservo;
    // class constructor
    public IntakeChamber(DcMotor subMotor, CRServo subCRServo){
        this.motor = subMotor; // reinit motors for use within class functions.
        this.CRservo = subCRServo;
    }

    public void moveChamMotorTicks(int ticksToBeMoved, double power){
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }
    public void stopChamMotor(){
        motor.setPower(0);
    }
    public double ChamMotorTicks() {
        return motor.getCurrentPosition();
    }
    public void moveChamServo(double power){
        CRservo.setPower(power);
    }
}
