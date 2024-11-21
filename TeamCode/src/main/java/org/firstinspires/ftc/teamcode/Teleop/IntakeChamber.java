package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;

public class IntakeChamber {
    DcMotor motor;
    Servo servo;
    // class constructor
    public IntakeChamber(DcMotor subMotor, Servo subServo){
        this.motor = subMotor; // reinit motors for use within class functions.
        this.servo = subServo;
    }

    public void moveChamMotorTicks(int ticksToBeMoved, double power){
        motor.setTargetPosition(ticksToBeMoved);  //Sets Target Tick Position
        motor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor.setPower(power);  //Sets Motor to go to position at 1 power.
    }
    public void stopChamMotor(){
        motor.setPower(0);
    }
    public double chamMotorTicks() {
        return motor.getCurrentPosition();
    }
    public void moveChamServo(double power){servo.setPosition(power);}
    public double chamServoPos() {return servo.getPosition();}
    public void freezeChamServo() {servo.setPosition(servo.getPosition());}
}
