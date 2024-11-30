package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class DrivingSystem {
    DcMotor rightFront;
    DcMotor leftFront;
    DcMotor rightBack;
    DcMotor leftBack;
    BNO055IMU imu;
    double heading;
    double offsetHeading = 0;
    double angle;


    // class constructor
    public DrivingSystem(BNO055IMU imu, DcMotor rightFront, DcMotor leftFront, DcMotor rightBack, DcMotor leftBack) {
        this.leftFront = leftFront; // reinit motors for use within class functions. =
        this.rightFront = rightFront;
        this.rightBack = rightBack;
        this.leftBack = leftBack;
        this.imu = imu;
    }

    public void driveTheMotors(float right_stick_x, float right_stick_y, float left_stick_x, float left_stick_y) {


        //this can be divided by any number but we chose 4
        double protate = right_stick_x / 4;

        //accounts for protate when limiting magnitude to be less than 1
        double processedProtate = Math.sqrt( Math.pow( 1 - Math.abs( protate ), 2 ) / 2 );
        double stickX = left_stick_x * processedProtate;
        double stickY = left_stick_y * processedProtate;


        double cValue   = 0;
        double theta    = 0;
        double pX       = 0;
        double pY       = 0;

        // 1/4 of the circle or 90 degree sections
        double halfPi = Math.PI / 2;

        // 1/8 of the circle or 45 degree sections
        double quarterPi = Math.PI / 4;

        //converts gyroAngle into radians
        double gyroAngle = heading * Math.PI / 180;


        if ( gyroAngle <= 0 ) {
            gyroAngle = gyroAngle + halfPi;
        } else if ( 0 < gyroAngle  && gyroAngle < halfPi ) {
            gyroAngle = gyroAngle + halfPi;
        } else if ( halfPi <= gyroAngle ) {
            gyroAngle = gyroAngle - (3 * halfPi);
        }

        gyroAngle = -1 * gyroAngle;
        //Disables gyro, sets to -Math.PI/2 so front is defined correctly


        theta = Math.atan2(stickY, stickX) - gyroAngle - halfPi;
        cValue = Math.sqrt(Math.pow(stickX, 2) + Math.pow(stickY, 2));

        pX = cValue * (Math.sin(theta + quarterPi));
        pY = cValue * (Math.sin(theta - quarterPi));

        leftFront.setPower((pY - protate) * 1.5);
        leftBack.setPower((pX - protate) * 1.5);
        rightFront.setPower((pX + protate) * 1.5);
        rightBack.setPower((pY + protate) * 1.5);

    }


    

    /**
     * Used to set the gyroscope starting angle.
     * the initial starting angle is based on when the robot is turned on
     * if the robot is moved after it is on, it needs to have a starting position
     *
     * EXTRA: maybe this can be automatically
     */
    public void resetAngle() {
        offsetHeading = getHeading();
    }

    private double getHeading() {
        double currentHeading = angle;
        if ( currentHeading < -180 ) {
            currentHeading = currentHeading + 360;
        } else if ( currentHeading > 180 ) {
            currentHeading = currentHeading - 360;
        }
        return currentHeading;
    }

    public double adjustHeading(){
        heading = getHeading() - offsetHeading;
        return heading;
    }
    public void updateAngle(){
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        angle = angles.firstAngle;
    }


}

