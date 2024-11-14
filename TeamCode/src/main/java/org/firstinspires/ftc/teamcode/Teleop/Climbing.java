package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.hardware.DcMotor;

public class Climbing {

    DcMotor motor;
    // class constructor
    public Climbing(DcMotor climbingMotor){
        this.motor = climbingMotor; // reinit motors for use within class functions.
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

}
