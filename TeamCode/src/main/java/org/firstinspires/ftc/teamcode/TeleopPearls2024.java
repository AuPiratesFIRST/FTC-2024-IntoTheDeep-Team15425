package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;


@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

@Config
public class TeleopPearls2024 extends Base { // extends base instead of linearopmode 

    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1555;
        //double climbingPressurePos = Constants.GROUND_POS;
        int climbingSpecimenHangPos = 1125;
        double doubleRestingPos = (double)climbingUpPos * 0.35;
        int intRestingPos = (int)doubleRestingPos;
        int doRestingPos = 0;
        int doDownPos = 0;
        ElapsedTime timer = new ElapsedTime();
        boolean timerIsActivated = false;

        boolean hasToggledSpeed = false;
        boolean toggledSpeed = false;

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
        boolean isInit = false;

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        //matchTime.reset();

        while (opModeIsActive()) {
            if (!isInit) {
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                isInit = true;
            }

            double time = timer.seconds();

            //climbing
           if(gamepad2.dpad_up) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos != 0) {
                    doDownPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingUpPos, Constants.climbingPower);
            }
            else if(gamepad2.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if(doDownPos == 0) {
                    doDownPos = 1;
                    climbingModule.moveClimbingMotorTicks(startPos, Constants.climbingPower);
                }
                else if(doDownPos == 2) {
                    doDownPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }
            else if(gamepad2.dpad_right) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingSpecimenHangPos, Constants.climbingPower);
                if (climbingModule.climbingMotorTicks() < climbingSpecimenHangPos + 20) {
                    climbingModule.setLeftServo(Constants.climbingLeftServoOpenPos);
                    climbingModule.setRightServo(Constants.climbingRightServoOpenPos);
                }
            }
            else if(gamepad2.dpad_left) {
                if(doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, Constants.climbingPower);
                }
                if(doRestingPos == 2) {
                    doRestingPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }

            //TJ wants climbing control
            else if(gamepad1.dpad_up) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos != 0) {
                    doDownPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingUpPos, Constants.climbingPower);
            }
            else if(gamepad1.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if(doDownPos == 0) {
                    doDownPos = 1;
                    climbingModule.moveClimbingMotorTicks(startPos, Constants.climbingPower);
                }
                else if(doDownPos == 2) {
                    doDownPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }
            else if(gamepad1.dpad_right) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingSpecimenHangPos, Constants.climbingPower);
            }
            else if(gamepad1.dpad_left) {
                if(doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, Constants.climbingPower);
                }
                if(doRestingPos == 2) {
                    doRestingPos = 3;
                    climbingModule.stopClimbingMotor();
                }
            }
            else{
                if(doRestingPos == 3) {
                    doRestingPos = 0;
                }
                else if (doRestingPos == 1) {
                    doRestingPos = 2;
                }

                if(doDownPos == 3) {
                    doDownPos = 0;
                }
                else if (doDownPos == 1) {
                    doDownPos = 2;
                }
                if (doRestingPos == 2) {
                    climbingModule.moveClimbingMotorTicks(intRestingPos, Constants.climbingPower);
                }
                else if (doDownPos == 2) {
                    climbingModule.moveClimbingMotorTicks(startPos, Constants.climbingPower);
                }
                else{
                    climbingModule.stopClimbingMotor();
                }
            }

            //bumpers
            if (gamepad2.left_bumper) {
                climbingModule.setRightServo(Constants.climbingRightServoOpenPos);
                climbingModule.setLeftServo(Constants.climbingLeftServoOpenPos);
            }
            else if (gamepad2.right_bumper) {
                climbingModule.setRightServo(Constants.climbingRightServoClosePos);
                climbingModule.setLeftServo(Constants.climbingLeftServoClosePos);
            }
            else {
                climbingModule.servosFreeze();
            }

            if (gamepad1.back) {
                climbingModule.resetMotor(Constants.motorPower);
            }
            else if (gamepad2.back) {
                climbingModule.resetMotor(Constants.motorPower);
            }

            //automated snatching of sample
            if (gamepad2.back && snatchStage == 0 && climbingModule.climbingMotorTicks() >= Math.abs(intRestingPos) - intakeDeadBand) {
                snatchStage = 1;
            }
            else if (snatchStage == 1) {
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, Constants.subMotorPower);
                intakeSubmersibleModule.moveSubCRServo(Constants.GROUND_POS_UNEXTENDED);
                currentPivotPos = "Ground";
                if (Math.abs(intakeSubmersibleModule.subMotorTicks()) > Math.abs(subMotorStowPos)) {
                    snatchStage = 2;
                }
            }
            else if (snatchStage == 2) {
                intakeSubmersibleModule.setServos(subClawServosOpenPos);
                if (intakeSubmersibleModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand) && intakeSubmersibleModule.subMotorTicks() > subExtendedPos - intakeDeadBand && intakeSubmersibleModule.subMotorTicks() < subExtendedPos + intakeDeadBand) {
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
                intakeSubmersibleModule.moveSubMotorTicks(subMotorStowPos, Constants.subMotorPower);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                if (intakeSubmersibleModule.subMotorTicks() > subMotorStowPos - intakeDeadBand && intakeSubmersibleModule.subMotorTicks() < subMotorStowPos + intakeDeadBand) {
                    snatchStage = 0;
                }
            }
            //automated reeling in
            else if (gamepad2.a && climbingModule.climbingMotorTicks() > 200) {
                intakeSubmersibleModule.setServos(subClawServosClosePos);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                intakeSubmersibleModule.moveSubMotorTicks(subMotorStowPos, Constants.subMotorPower);
            }
            //automated casting out
            else if (gamepad2.y && climbingModule.climbingMotorTicks() > 200) {
                intakeSubmersibleModule.moveSubCRServo(Constants.GROUND_POS);
                currentPivotPos = "Ground";
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, Constants.subMotorPower);
            }
            else if (gamepad2.x && climbingModule.climbingMotorTicks() > 200) {
                if (!timerIsActivated) {
                    timerIsActivated = true;
                    timer.reset();
                }
                else if (time <= Constants.waitToGround) {
                    intakeSubmersibleModule.moveSubCRServo(Constants.GROUND_POS_UNEXTENDED);
                    currentPivotPos = "Ground";
                }
                else if (time <= Constants.waitToRelease) {
                    intakeSubmersibleModule.setServos(subClawServosOpenPos);
                }
                else if (time <= Constants.waitToClose) {
                    intakeSubmersibleModule.setServos(subClawServosClosePos);
                }
                else if (time <= Constants.waitToStow) {
                    intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                    currentPivotPos = "Stow";
                }

            }
            else if (gamepad2.right_stick_y > 0.2) {
                intakeSubmersibleModule.setServos(subClawServosClosePos);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
            }
            else if (gamepad2.right_stick_y < -0.2 && climbingModule.climbingMotorTicks() > 200) {
                intakeSubmersibleModule.moveSubCRServo(Constants.GROUND_POS_UNEXTENDED);
                currentPivotPos = "Ground";
            }
            //claw
            else if (currentPivotPos.equals("Ground")) {
                if(gamepad2.left_trigger > deadBand) {
                    intakeSubmersibleModule.setServos(subClawServosOpenPos);
                }
                else if(gamepad2.right_trigger > deadBand) {
                    intakeSubmersibleModule.setServos(subClawServosClosePos);
                }
                else{
                    intakeSubmersibleModule.servosFreeze();
                }
            }
            else {
                intakeSubmersibleModule.servosFreeze();
            }

            if (!gamepad2.x) {
                timerIsActivated = false;
            }

            //driving
            if (gamepad1.a && !hasToggledSpeed) {
                hasToggledSpeed = true;
                if (toggledSpeed) {
                    toggledSpeed = false;
                }
                else {
                    toggledSpeed = true;
                }
            }
            else {
                hasToggledSpeed = false;
            }
            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, toggledSpeed);
            if (gamepad1.b) {
                drivingModule.resetAngle();
            }
            double adjustedHeading = drivingModule.adjustHeading();

            //telemetry
            telemetry.addData("Climbing Motor Position: ", climbingModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", intakeSubmersibleModule.subMotorTicks());
            //telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);
            //telemetry.addData("Right Servo Sub Pos", intakeSubmersibleModule.rightServo.getPosition());
            //telemetry.addData("Left Servo Sub Pos", intakeSubmersibleModule.leftServo.getPosition());
           // telemetry.addData("right trigger", gamepad2.right_trigger);
            //telemetry.addData("left trigger", gamepad2.left_trigger);
            telemetry.addData("restPos", doRestingPos);
            telemetry.addData("dPad left", gamepad1.dpad_left);
            telemetry.addData("currentPivotPos", currentPivotPos);
            telemetry.addData("subMotorPos", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("snatch stage", snatchStage);
            telemetry.addData("servoIsOpen", intakeSubmersibleModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand));
            telemetry.addData("servoIsClosed", intakeSubmersibleModule.servosAtPosition(subClawServosClosePos, subServosDeadBand));
            telemetry.addData("motorIsExtended", intakeSubmersibleModule.subMotorTicks() > subExtendedPos - intakeDeadBand && intakeSubmersibleModule.subMotorTicks() < subExtendedPos + intakeDeadBand);
//            telemetry.addData("time", time);
//            telemetry.addData("timerIsActivated", timerIsActivated);
//            telemetry.addData("x", gamepad2.x);
            telemetry.addData("doDownPos", doDownPos);

            telemetry.update();
        }
    }
}
