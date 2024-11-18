package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

public class TeleopPearls2024 extends Base { // extends base instead of linearopmode

    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1500;
        int climbingPressurePos = 500;
        int startPos = 0;
        int chamUpPos = 500;


        double climbingPower = 0.6;
        double chamPower = 0.5;
        double climbingPressurePower = 0.3;

        double subClosePos = 0;
        double subOpenPos = 0.5;

        double stowPos = 0.5;
        double groundPos = -0.8;

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        intakeSubmersibleModule.moveSubCRServo(stowPos);

        waitForStart();
        //matchTime.reset();

        while (opModeIsActive()) {
            //climbing
            if(gamepad1.dpad_up){
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad1.dpad_down){
                climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
            }
            else if(gamepad1.dpad_right){
                climbingModule.moveClimbingMotorTicks(climbingPressurePos, climbingPressurePower);
            }
            else{
                climbingModule.stopClimbingMotor();
            }

            //cham motor
            if(gamepad1.a) {
                intakeChamberModule.moveChamMotorTicks(chamUpPos, chamPower);
            }
            else if(gamepad1.b) {
                intakeChamberModule.moveChamMotorTicks(startPos, chamPower);
            }
            else{
                intakeChamberModule.stopChamMotor();
            }

            //claw
            if(gamepad1.left_trigger > 0.05){
                intakeSubmersibleModule.setServos(subOpenPos);
            }
            else if(gamepad1.right_trigger > 0.05){
                intakeSubmersibleModule.setServos(subClosePos);
            }
            else{
                intakeSubmersibleModule.servosFreeze();
            }

            if (gamepad1.a) {
                intakeSubmersibleModule.moveSubCRServo(stowPos);
                //intake motor retracts
            }
            else if (gamepad1.y) {
                intakeSubmersibleModule.moveSubCRServo(groundPos);
                //intake motor extends
            }

            //driving
            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);
            double adjustedHeading = drivingModule.adjustHeading();

            //telemetry
            telemetry.addLine("Program is running");
            telemetry.addData("Climbing Motor Position: ", climbingModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);
            telemetry.addData("Right Servo Pos", intakeSubmersibleModule.rightServo.getPosition());
            telemetry.addData("Left Servo Pos", intakeSubmersibleModule.leftServo.getPosition());
            telemetry.addData("right trigger", gamepad1.right_trigger);
            telemetry.addData("left trigger", gamepad1.left_trigger);


            telemetry.update();
        }
    }
}
