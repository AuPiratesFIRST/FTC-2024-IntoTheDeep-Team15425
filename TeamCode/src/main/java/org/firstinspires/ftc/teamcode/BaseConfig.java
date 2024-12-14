package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Subsystems.ClawSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.ClimbingSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.DrivingSubsystem;
import org.firstinspires.ftc.teamcode.Subsystems.SubmersibleIntakeSubsystem;

import java.util.List;
import java.util.Locale;

public abstract class BaseConfig extends LinearOpMode {

    // Globally Declared Sensors


    // Module Classes
    public ClimbingSubsystem climbingSubsystemModule = null;
    public SubmersibleIntakeSubsystem submersibleIntakeSubsystemModule = null;
    public DrivingSubsystem drivingModule = null;
    public ClawSubsystem clawSubsystemModule = null;

    // Global Variables

    // Initialize Hardware Function
    public void initHardware() throws InterruptedException {
        BNO055IMU imu = hardwareMap.get(BNO055IMU .class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();

        parameters.mode             = BNO055IMU.SensorMode.IMU;
        parameters.angleUnit        = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit        = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.loggingEnabled   = false;

        imu.initialize(parameters);

        //Retrieves the first axis' value


        // Hubs
        List<LynxModule> allHubs;
        allHubs = hardwareMap.getAll(LynxModule.class);
        for (LynxModule hub : allHubs) {
            hub.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        // Motors
        DcMotor climbingMotor = hardwareMap.get(DcMotor.class, "Climbing Motor");
        climbingMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        climbingMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DcMotor subMotor = hardwareMap.get(DcMotor.class, "Sub Motor");
        subMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        subMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        DcMotor leftFront = hardwareMap.dcMotor.get("frontLeft");
        DcMotor leftBack = hardwareMap.dcMotor.get("backLeft");
        DcMotor rightFront = hardwareMap.dcMotor.get("frontRight");
        DcMotor rightBack = hardwareMap.dcMotor.get("backRight");

        rightFront.setDirection(DcMotor.Direction.REVERSE);
        rightBack.setDirection(DcMotor.Direction.REVERSE);


        CRServo pivotCRServo = hardwareMap.get(CRServo.class, "Sub Pivot CRServo");
        Servo rightSubServo = hardwareMap.get(Servo.class, "Sub Right Servo");
        Servo leftSubServo = hardwareMap.get(Servo.class, "Sub Left Servo");
        Servo rightClimbingServo = hardwareMap.get(Servo.class, "Climbing Right Servo");
        Servo leftClimbingServo = hardwareMap.get(Servo.class, "Climbing Left Servo");

        // Init Module class
        climbingSubsystemModule = new ClimbingSubsystem(climbingMotor, rightClimbingServo, leftClimbingServo, telemetry);
        submersibleIntakeSubsystemModule = new SubmersibleIntakeSubsystem(subMotor, pivotCRServo, rightSubServo, leftSubServo);
        drivingModule = new DrivingSubsystem(imu, rightFront, leftFront, rightBack, leftBack);
    }

    //Utility Functions
    public String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }


    // Allows you to connect opModes to the base class
    @Override
    public abstract void runOpMode() throws InterruptedException;
}
