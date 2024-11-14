package org.firstinspires.ftc.teamcode.Teleop;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

public class TeleopPearls2024 extends Base { // extends base instead of linearopmode

    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = -1500;
        int startPos = 0;
        int chamUpPos = 500;

        double climbingPower = 0.6;
        double chamPower = 0.5;

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();


        waitForStart();
        //matchTime.reset();

        while (opModeIsActive()) {
            if(gamepad1.dpad_up){
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad1.dpad_down){
                climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
            }
            else{
                climbingModule.stopClimbingMotor();
            }

            if(gamepad1.a) {
                intakeChamberModule.moveChamMotorTicks(chamUpPos, chamPower);
            }
            else if(gamepad1.b) {
                intakeChamberModule.moveChamMotorTicks(startPos, chamPower);
            }
            else{
                intakeChamberModule.stopChamMotor();
            }

            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);
            double adjustedHeading = drivingModule.adjustHeading();
            // Display Values
            telemetry.addLine("Program is running");
            telemetry.addData("Climbing Motor Position: ", climbingModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);

            telemetry.update();
        }
    }
}
