package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

public class TeleopPearls2024 extends Base { // extends base instead of linearopmode

    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1500;
        int climbingPressurePos = 500;
        double doubleRestingPos = (double)climbingUpPos * 0.35;
        int intRestingPos = (int)doubleRestingPos;
        int doRestingPos = 0;

        int startPos = 0;

        int chamUpPos = 1500;

        int subExtendedPos = 1500;
        int subStowPos = 300;


        double climbingPower = 0.6;
        double chamPower = 0.5;
        double subMotorPower = 0.4;
        double chamServoOpenPos = 0.5;
        double chamServoClosePos = 0;
        double chamDeadBand = 5;
        double climbingPressurePower = 0.3;

        double subPivotClosePos = 0;
        double subPivotOpenPos = 0.5;

        double stowPos = 0.5;
        double groundPos = -0.8;

        String currentPivotPos = "Stow";

        double deadBand = 0.05;

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        intakeSubmersibleModule.moveSubCRServo(stowPos);

        waitForStart();
        //matchTime.reset();

        while (opModeIsActive()) {
            //climbing
            if(gamepad2.dpad_up){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad2.dpad_down){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
            }
            else if(gamepad2.dpad_right){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingPressurePos, climbingPressurePower);
            }
            else if(gamepad2.dpad_left){
                if(doRestingPos == 0){
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
                }
                if(doRestingPos == 2){
                    doRestingPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }


            //TJ wants climbing control
            else if(gamepad1.dpad_up){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad1.dpad_down){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
            }
            else if(gamepad1.dpad_right){
                if (doRestingPos != 0){
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingPressurePos, climbingPressurePower);
            }
            else if(gamepad1.dpad_left){
                if(doRestingPos == 0){
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
                }
                if(doRestingPos == 2){
                    doRestingPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }
            else{
                if(doRestingPos == 3){
                    doRestingPos = 0;
                }
                else if (doRestingPos == 1){
                    doRestingPos = 2;
                }
                if (doRestingPos == 2){
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
                }
                else{
                    climbingModule.stopClimbingMotor();
                }
            }

            //cham motor
            if(gamepad2.x) {
                intakeChamberModule.moveChamMotorTicks(chamUpPos, chamPower);
                if (intakeChamberModule.motor.getCurrentPosition() > chamUpPos - chamDeadBand && intakeChamberModule.motor.getCurrentPosition() < chamUpPos + chamDeadBand) {
                    intakeChamberModule.moveChamServo(chamServoOpenPos);
                }
            }
            else if(gamepad2.b) {
                intakeChamberModule.moveChamMotorTicks(startPos, chamPower);
            }
            else{
                intakeChamberModule.stopChamMotor();
            }
                //bumpers
            if (gamepad2.left_bumper) {
                intakeChamberModule.moveChamServo(chamServoClosePos);
            }
            else if (gamepad2.right_bumper) {
                intakeChamberModule.moveChamServo(chamServoOpenPos);
            }
            else {
                intakeChamberModule.freezeChamServo();
            }

            //claw
            if (currentPivotPos == "Ground") {
                if(gamepad2.left_trigger > deadBand){
                    intakeSubmersibleModule.setServos(subPivotOpenPos);
                }
                else if(gamepad2.right_trigger > deadBand){
                    intakeSubmersibleModule.setServos(subPivotClosePos);
                }
                else{
                    intakeSubmersibleModule.servosFreeze();
                }
            }
            else {
                intakeSubmersibleModule.servosFreeze();
            }

            //pivot servo
            if (gamepad2.a) {
                intakeSubmersibleModule.moveSubCRServo(stowPos);
                currentPivotPos = "Stow";
                intakeSubmersibleModule.moveSubMotorTicks(subStowPos, subMotorPower);
            }
            else if (gamepad2.y) {
                intakeSubmersibleModule.moveSubCRServo(groundPos);
                currentPivotPos = "Ground";
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, subMotorPower);
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
            telemetry.addData("right trigger", gamepad2.right_trigger);
            telemetry.addData("left trigger", gamepad2.left_trigger);
            telemetry.addData("restPos", doRestingPos);
            telemetry.addData("dPad left", gamepad1.dpad_left);
            telemetry.addData("currentPivotPos", currentPivotPos);
            telemetry.addData("subMotorPos", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("chamMotorPos", intakeChamberModule.chamMotorTicks());
            telemetry.addData("chamServo", intakeChamberModule.chamServoPos());



            telemetry.update();
        }
    }
}
