package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

@Config
public class TeleopPearls2024 extends Base { // extends base instead of linearopmode 
    public static double GROUND_POS = -0.8;
    public static double climbingPower = 0.6;
    public static double subMotorPower = 0.4;
    public static double climbingPressurePower = 0.3;
    public static double climbingRightServoOpenPos = 0.5;
    public static double climbingRightServoClosePos = 0.4;
    public static double climbingLeftServoOpenPos = 1-0.5;
    public static double climbingLeftServoClosePos = 0.4;


    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1500;
        int climbingPressurePos = 500;
        double doubleRestingPos = (double)climbingUpPos * 0.35;
        int intRestingPos = (int)doubleRestingPos;
        int doRestingPos = 0;

        int startPos = 0;

        int subExtendedPos = -1800;
        int subMotorStowPos = -210;
        int snatchStage = 0;
        double subServosDeadBand = 0.05;



        double intakeDeadBand = 5;

        double subClawServosClosePos = 0;
        double subClawServosOpenPos = 0.5;

        double subCRServoStowPos = 0.5;

        String currentPivotPos = "Stow";

        double deadBand = 0.05;

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);

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

            //bumpers
            if (gamepad2.left_bumper) {
                climbingModule.setRightServo(climbingRightServoClosePos);
                climbingModule.setLeftServo(climbingLeftServoClosePos);
            }
            else if (gamepad2.right_bumper) {
                climbingModule.setRightServo(climbingRightServoOpenPos);
                climbingModule.setLeftServo(climbingLeftServoOpenPos);
            }
            else {
                climbingModule.servosFreeze();
            }

            

            //automated snatching of sample
            if (gamepad2.back && snatchStage == 0 && climbingModule.climbingMotorTicks() >= Math.abs(intRestingPos) - intakeDeadBand) {
                snatchStage = 1;
            }
            else if (snatchStage == 1) {
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, subMotorPower);
                intakeSubmersibleModule.moveSubCRServo(GROUND_POS);
                currentPivotPos = "Ground";
                if (Math.abs(intakeSubmersibleModule.subMotorTicks()) > Math.abs(subMotorStowPos)) {
                    snatchStage = 2;
                }
            }
            else if (snatchStage == 2) {
                intakeSubmersibleModule.setServos(subClawServosOpenPos);
                if (intakeSubmersibleModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand) && intakeSubmersibleModule.motor.getCurrentPosition() > subExtendedPos - intakeDeadBand && intakeSubmersibleModule.motor.getCurrentPosition() < subExtendedPos + intakeDeadBand) {
                    snatchStage = 3;
                }
            }
            else if (snatchStage == 3) {
                intakeSubmersibleModule.setServos(subClawServosClosePos);
                if (intakeSubmersibleModule.servosAtPosition(subClawServosClosePos, subServosDeadBand)) {
                    snatchStage = 4;
                }
            }
            else if (snatchStage == 4) {
                intakeSubmersibleModule.moveSubMotorTicks(subMotorStowPos, subMotorPower);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                if (intakeSubmersibleModule.motor.getCurrentPosition() > subMotorStowPos - intakeDeadBand && intakeSubmersibleModule.motor.getCurrentPosition() < subMotorStowPos + intakeDeadBand) {
                    snatchStage = 0;
                }
            }
            //automated casting out
            else if (gamepad2.a) {
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                intakeSubmersibleModule.moveSubMotorTicks(subMotorStowPos, subMotorPower);
            }
            //automated reeling in
            else if (gamepad2.y) {
                intakeSubmersibleModule.moveSubCRServo(GROUND_POS);
                currentPivotPos = "Ground";
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, subMotorPower);
            }
            //claw
            else if (currentPivotPos.equals("Ground")) {
                if(gamepad2.left_trigger > deadBand){
                    intakeSubmersibleModule.setServos(subClawServosOpenPos);
                }
                else if(gamepad2.right_trigger > deadBand){
                    intakeSubmersibleModule.setServos(subClawServosClosePos);
                }
                else{
                    intakeSubmersibleModule.servosFreeze();
                }
            }
            else {
                intakeSubmersibleModule.servosFreeze();
            }
            



            //driving
            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);
            double adjustedHeading = drivingModule.adjustHeading();

            //telemetry
            telemetry.addData("Climbing Motor Position: ", climbingModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);
            telemetry.addData("Right Servo Sub Pos", intakeSubmersibleModule.rightServo.getPosition());
            telemetry.addData("Left Servo Sub Pos", intakeSubmersibleModule.leftServo.getPosition());
            telemetry.addData("right trigger", gamepad2.right_trigger);
            telemetry.addData("left trigger", gamepad2.left_trigger);
            telemetry.addData("restPos", doRestingPos);
            telemetry.addData("dPad left", gamepad1.dpad_left);
            telemetry.addData("currentPivotPos", currentPivotPos);
            telemetry.addData("subMotorPos", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("snatch stage", snatchStage);
            telemetry.addData("servoIsOpen", intakeSubmersibleModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand));
            telemetry.addData("servoIsClosed", intakeSubmersibleModule.servosAtPosition(subClawServosClosePos, subServosDeadBand));
            telemetry.addData("motorIsExtended", intakeSubmersibleModule.motor.getCurrentPosition() > subExtendedPos - intakeDeadBand && intakeSubmersibleModule.motor.getCurrentPosition() < subExtendedPos + intakeDeadBand);

            telemetry.update();
        }
    }
}
