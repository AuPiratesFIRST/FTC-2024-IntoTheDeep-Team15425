package org.firstinspires.ftc.teamcode.Teleop;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

@Config
public class TeleopPearls2024 extends Base { // extends base instead of linearopmode 
    public static double GROUND_POS = -0.7;
    public static double climbingPower = 0.6;
    public static double subMotorPower = 0.4;
    public static double climbingPressurePower = 0.3;
    public static double climbingRightServoOpenPos = 0.5;
    public static double climbingRightServoClosePos = 0.18;
    public static double climbingLeftServoOpenPos = 0.5;
    public static double climbingLeftServoClosePos = 0.65;
    public static double waitToGround = 0.5;
    public static double waitToRelease = 1;
    public static double waitToClose = 1.3;
    public static double waitToStow = 1.7;


    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1500;
        int climbingPressurePos = 500;
        int climbingSpecimenHangPos = 1125;
        double doubleRestingPos = (double)climbingUpPos * 0.35;
        int intRestingPos = (int)doubleRestingPos;
        int doRestingPos = 0;
        int doDownPos = 0;
        ElapsedTime timer = new ElapsedTime();
        boolean timerIsActivated = false;



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
            double time = timer.seconds();

            //climbing
           if(gamepad2.dpad_up) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos != 0) {
                    doDownPos = 0;
                }
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad2.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if(doDownPos == 0) {
                    doDownPos = 1;
                    climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
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
                climbingModule.moveClimbingMotorTicks(climbingSpecimenHangPos, climbingPower);
            }
            else if(gamepad2.dpad_left) {
                if(doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
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
                climbingModule.moveClimbingMotorTicks(climbingUpPos, climbingPower);
            }
            else if(gamepad1.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if(doDownPos == 0) {
                    doDownPos = 1;
                    climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
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
                climbingModule.moveClimbingMotorTicks(climbingSpecimenHangPos, climbingPower);
            }
            else if(gamepad1.dpad_left) {
                if(doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
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
                    climbingModule.moveClimbingMotorTicks(intRestingPos, climbingPower);
                }
                else if (doDownPos == 2) {
                    climbingModule.moveClimbingMotorTicks(startPos, climbingPower);
                }
                else{
                    climbingModule.stopClimbingMotor();
                }
            }

            //bumpers
            if (gamepad2.left_bumper) {
                climbingModule.setRightServo(climbingRightServoOpenPos);
                climbingModule.setLeftServo(climbingLeftServoOpenPos);
            }
            else if (gamepad2.right_bumper) {
                climbingModule.setRightServo(climbingRightServoClosePos);
                climbingModule.setLeftServo(climbingLeftServoClosePos);
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
            //automated reeling in
            else if (gamepad2.a && climbingModule.climbingMotorTicks() > 200) {
                intakeSubmersibleModule.setServos(subClawServosClosePos);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                intakeSubmersibleModule.moveSubMotorTicks(subMotorStowPos, subMotorPower);
            }
            //automated casting out
            else if (gamepad2.y && climbingModule.climbingMotorTicks() > 200) {
                intakeSubmersibleModule.moveSubCRServo(GROUND_POS);
                currentPivotPos = "Ground";
                intakeSubmersibleModule.moveSubMotorTicks(subExtendedPos, subMotorPower);
            }
            else if (gamepad2.x) {
                if (!timerIsActivated) {
                    timerIsActivated = true;
                    timer.reset();
                }
                else if (time <= waitToGround) {
                    intakeSubmersibleModule.moveSubCRServo(GROUND_POS);
                    currentPivotPos = "Ground";
                }
                else if (time <= waitToRelease) {
                    intakeSubmersibleModule.setServos(subClawServosOpenPos);
                }
                else if (time <= waitToClose) {
                    intakeSubmersibleModule.setServos(subClawServosClosePos);
                }
                else if (time <= waitToStow) {
                    intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                    currentPivotPos = "Stow";
                }

            }
            else if (gamepad2.b) {
                intakeSubmersibleModule.setServos(subClawServosClosePos);
                intakeSubmersibleModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
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
            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y);
            if (gamepad1.b) {
                drivingModule.resetAngle();
            }
            double adjustedHeading = drivingModule.adjustHeading();

            //telemetry
            telemetry.addData("Climbing Motor Position: ", climbingModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", intakeSubmersibleModule.subMotorTicks());
            //telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);
            telemetry.addData("Right Servo Sub Pos", intakeSubmersibleModule.rightServo.getPosition());
            telemetry.addData("Left Servo Sub Pos", intakeSubmersibleModule.leftServo.getPosition());
           // telemetry.addData("right trigger", gamepad2.right_trigger);
            //telemetry.addData("left trigger", gamepad2.left_trigger);
            telemetry.addData("restPos", doRestingPos);
            telemetry.addData("dPad left", gamepad1.dpad_left);
            telemetry.addData("currentPivotPos", currentPivotPos);
            telemetry.addData("subMotorPos", intakeSubmersibleModule.subMotorTicks());
            telemetry.addData("snatch stage", snatchStage);
            telemetry.addData("servoIsOpen", intakeSubmersibleModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand));
            telemetry.addData("servoIsClosed", intakeSubmersibleModule.servosAtPosition(subClawServosClosePos, subServosDeadBand));
            telemetry.addData("motorIsExtended", intakeSubmersibleModule.motor.getCurrentPosition() > subExtendedPos - intakeDeadBand && intakeSubmersibleModule.motor.getCurrentPosition() < subExtendedPos + intakeDeadBand);
//            telemetry.addData("time", time);
//            telemetry.addData("timerIsActivated", timerIsActivated);
//            telemetry.addData("x", gamepad2.x);
            telemetry.addData("doDownPos", doDownPos);

            telemetry.update();
        }
    }
}
