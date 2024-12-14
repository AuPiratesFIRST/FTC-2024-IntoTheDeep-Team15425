package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Actions.ClimbingActions.ClimbingDownAction;

@TeleOp(name = "CompetitionTeleop", group = "IntoTheDeep2024")

@Config
public class TeleopPearls2024 extends BaseConfig { // extends base instead of linearopmode

    @Override
    public void runOpMode() throws InterruptedException {

        //init variables
        int climbingUpPos = 1555;
        //double climbingPressurePos = Constants.GROUND_POS;
        int climbingSpecimenHangPos = 1125;
        double doubleRestingPos = (double) climbingUpPos * 0.35;
        int intRestingPos = (int) doubleRestingPos;
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

        /************************************************
         * ACTIONS
         */
        Action climbingClose = climbingSubsystemModule.climbingClose();
        Action climbingUp = climbingSubsystemModule.climbingMotorUp();
        ClimbingDownAction climbingDown = climbingSubsystemModule.climbingMotorDown();
        Action climbingSpecimenDown = climbingSubsystemModule.climbingMotorSpecimenDown();
        Action climbingRelease = climbingSubsystemModule.climbingRelease();

        initHardware(); // inits hardware
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        //matchTime.reset();

        while (opModeIsActive()) {
            if (!isInit) {
                submersibleIntakeSubsystemModule.moveSubCRServo(subCRServoStowPos);
                isInit = true;
            }

            double time = timer.seconds();

            //climbing
            if (gamepad2.dpad_up) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos != 0) {
                    doDownPos = 0;
                }
                // climbingSubsystemModule.moveClimbingMotorTicks(climbingUpPos, Constants.Climbing.CLIMBING_POWER);
            } else if (gamepad2.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos == 0) {
                    doDownPos = 1;
                    climbingDown.setTargetTicks(startPos);
                    climbingDown.setPower(Constants.Climbing.CLIMBING_POWER);
                    climbingDown.run(null);
                    // climbingSubsystemModule.moveClimbingMotorTicks(startPos, Constants.Climbing.CLIMBING_POWER);
                } else if (doDownPos == 2) {
                    doDownPos = 3;
                    climbingSubsystemModule.stopClimbingMotor();
                }
            } else if (gamepad2.dpad_right) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }

                climbingSubsystemModule.moveClimbingMotorTicks(climbingSpecimenHangPos, Constants.Climbing.CLIMBING_POWER);
                if (climbingSubsystemModule.climbingMotorTicks() < climbingSpecimenHangPos + 20) {
                    climbingSubsystemModule.setLeftServo(Constants.Climbing.CLIMBING_LEFT_SERVO_OPEN_POSITION);
                    climbingSubsystemModule.setRightServo(Constants.Climbing.CLIMBING_RIGHT_SERVO_OPEN_POSITION);
                }
            } else if (gamepad2.dpad_left) {
                if (doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingSubsystemModule.moveClimbingMotorTicks(intRestingPos, Constants.Climbing.CLIMBING_POWER);
                }
                if (doRestingPos == 2) {
                    doRestingPos = 3;
                    climbingSubsystemModule.stopClimbingMotor();
                }
            }

            //TJ wants climbing control
            else if (gamepad1.dpad_up) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos != 0) {
                    doDownPos = 0;
                }
                climbingSubsystemModule.moveClimbingMotorTicks(climbingUpPos, Constants.Climbing.CLIMBING_POWER);
            } else if (gamepad1.dpad_down) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                if (doDownPos == 0) {
                    doDownPos = 1;
                    climbingSubsystemModule.moveClimbingMotorTicks(startPos, Constants.Climbing.CLIMBING_POWER);
                } else if (doDownPos == 2) {
                    doDownPos = 3;
                    climbingSubsystemModule.stopClimbingMotor();
                }
            } else if (gamepad1.dpad_right) {
                if (doRestingPos != 0) {
                    doRestingPos = 0;
                }
                climbingSubsystemModule.moveClimbingMotorTicks(climbingSpecimenHangPos, Constants.Climbing.CLIMBING_POWER);
            } else if (gamepad1.dpad_left) {
                if (doRestingPos == 0) {
                    doRestingPos = 1;
                    climbingSubsystemModule.moveClimbingMotorTicks(intRestingPos, Constants.Climbing.CLIMBING_POWER);
                }
                if (doRestingPos == 2) {
                    doRestingPos = 3;
                    climbingSubsystemModule.stopClimbingMotor();
                }
            } else {
                if (doRestingPos == 3) {
                    doRestingPos = 0;
                } else if (doRestingPos == 1) {
                    doRestingPos = 2;
                }

                if (doDownPos == 3) {
                    doDownPos = 0;
                } else if (doDownPos == 1) {
                    doDownPos = 2;
                }
                if (doRestingPos == 2) {
                    climbingSubsystemModule.moveClimbingMotorTicks(intRestingPos, Constants.Climbing.CLIMBING_POWER);
                } else if (doDownPos == 2) {
                    climbingSubsystemModule.moveClimbingMotorTicks(startPos, Constants.Climbing.CLIMBING_POWER);
                } else {
                    climbingSubsystemModule.stopClimbingMotor();
                }
            }

            //bumpers
            if (gamepad2.left_bumper) {
                climbingSubsystemModule.setRightServo(Constants.Climbing.CLIMBING_RIGHT_SERVO_OPEN_POSITION);
                climbingSubsystemModule.setLeftServo(Constants.Climbing.CLIMBING_LEFT_SERVO_OPEN_POSITION);
            } else if (gamepad2.right_bumper) {
                climbingSubsystemModule.setRightServo(Constants.Climbing.CLIMBING_RIGHT_SERVO_CLOSED_POSITION);
                climbingSubsystemModule.setLeftServo(Constants.Climbing.CLIMBING_LEFT_SERVO_CLOSED_POSITION);
            } else {
                climbingSubsystemModule.servosFreeze();
            }

            if (gamepad1.back) {
                climbingSubsystemModule.resetMotor(Constants.Climbing.CLIMBING_POWER);
            } else if (gamepad2.back) {
                climbingSubsystemModule.resetMotor(Constants.Climbing.CLIMBING_POWER);
            }

            //automated snatching of sample
            if (gamepad2.back && snatchStage == 0 && climbingSubsystemModule.climbingMotorTicks() >= Math.abs(intRestingPos) - intakeDeadBand) {
                snatchStage = 1;
            } else if (snatchStage == 1) {
                submersibleIntakeSubsystemModule.moveSubMotorTicks(subExtendedPos, Constants.SubmersibleIntake.SUBMERSIBLE_POWER);
                submersibleIntakeSubsystemModule.moveSubCRServo(Constants.SubmersibleIntake.SUBMERSIBLE_GROUND_POS_UNEXTENDED);
                currentPivotPos = "Ground";
                if (Math.abs(submersibleIntakeSubsystemModule.subMotorTicks()) > Math.abs(subMotorStowPos)) {
                    snatchStage = 2;
                }
            } else if (snatchStage == 2) {
                submersibleIntakeSubsystemModule.setServos(subClawServosOpenPos);
                if (submersibleIntakeSubsystemModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand) && submersibleIntakeSubsystemModule.subMotorTicks() > subExtendedPos - intakeDeadBand && submersibleIntakeSubsystemModule.subMotorTicks() < subExtendedPos + intakeDeadBand) {
                    snatchStage = 3;
                }
            } else if (snatchStage == 3) {
                submersibleIntakeSubsystemModule.setServos(subClawServosClosePos);
                if (submersibleIntakeSubsystemModule.servosAtPosition(subClawServosClosePos, subServosDeadBand)) {
                    snatchStage = 4;
                }
            } else if (snatchStage == 4) {
                submersibleIntakeSubsystemModule.moveSubMotorTicks(subMotorStowPos, Constants.SubmersibleIntake.SUBMERSIBLE_POWER);
                submersibleIntakeSubsystemModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                if (submersibleIntakeSubsystemModule.subMotorTicks() > subMotorStowPos - intakeDeadBand && submersibleIntakeSubsystemModule.subMotorTicks() < subMotorStowPos + intakeDeadBand) {
                    snatchStage = 0;
                }
            }
            //automated reeling in
            else if (gamepad2.a && climbingSubsystemModule.climbingMotorTicks() > 200) {
                submersibleIntakeSubsystemModule.setServos(subClawServosClosePos);
                submersibleIntakeSubsystemModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
                submersibleIntakeSubsystemModule.moveSubMotorTicks(subMotorStowPos, Constants.SubmersibleIntake.SUBMERSIBLE_POWER);
            }
            //automated casting out
            else if (gamepad2.y && climbingSubsystemModule.climbingMotorTicks() > 200) {
                submersibleIntakeSubsystemModule.moveSubCRServo(Constants.SubmersibleIntake.SUBMERSIBLE_GROUND_POS_EXTENDED);
                currentPivotPos = "Ground";
                submersibleIntakeSubsystemModule.moveSubMotorTicks(subExtendedPos, Constants.SubmersibleIntake.SUBMERSIBLE_POWER);
            } else if (gamepad2.x && climbingSubsystemModule.climbingMotorTicks() > 200) {
                if (!timerIsActivated) {
                    timerIsActivated = true;
                    timer.reset();
                } else if (time <= Constants.SubmersibleIntake.SUBMERSIBLE_WAIT_TO_GROUND) {
                    submersibleIntakeSubsystemModule.moveSubCRServo(Constants.SubmersibleIntake.SUBMERSIBLE_GROUND_POS_EXTENDED);
                    currentPivotPos = "Ground";
                } else if (time <= Constants.SubmersibleIntake.SUBMERSIBLE_WAIT_TO_RELEASE) {
                    submersibleIntakeSubsystemModule.setServos(subClawServosOpenPos);
                } else if (time <= Constants.SubmersibleIntake.SUBMERSIBLE_WAIT_TO_CLOSE) {
                    submersibleIntakeSubsystemModule.setServos(subClawServosClosePos);
                } else if (time <= Constants.SubmersibleIntake.SUBMERSIBLE_WAIT_TO_STOW) {
                    submersibleIntakeSubsystemModule.moveSubCRServo(subCRServoStowPos);
                    currentPivotPos = "Stow";
                }

            } else if (gamepad2.right_stick_y > 0.2) {
                submersibleIntakeSubsystemModule.setServos(subClawServosClosePos);
                submersibleIntakeSubsystemModule.moveSubCRServo(subCRServoStowPos);
                currentPivotPos = "Stow";
            } else if (gamepad2.right_stick_y < -0.2 && climbingSubsystemModule.climbingMotorTicks() > 200) {
                submersibleIntakeSubsystemModule.moveSubCRServo(Constants.SubmersibleIntake.SUBMERSIBLE_GROUND_POS_EXTENDED);
                currentPivotPos = "Ground";
            }
            //claw
            else if (currentPivotPos.equals("Ground")) {
                if (gamepad2.left_trigger > deadBand) {
                    submersibleIntakeSubsystemModule.setServos(subClawServosOpenPos);
                } else if (gamepad2.right_trigger > deadBand) {
                    submersibleIntakeSubsystemModule.setServos(subClawServosClosePos);
                } else {
                    submersibleIntakeSubsystemModule.servosFreeze();
                }
            } else {
                submersibleIntakeSubsystemModule.servosFreeze();
            }

            if (!gamepad2.x) {
                timerIsActivated = false;
            }

            //driving
            if (gamepad1.a && !hasToggledSpeed) {
                hasToggledSpeed = true;
                if (toggledSpeed) {
                    toggledSpeed = false;
                } else {
                    toggledSpeed = true;
                }
            } else {
                hasToggledSpeed = false;
            }
            drivingModule.updateAngle();
            drivingModule.driveTheMotors(gamepad1.right_stick_x, gamepad1.right_stick_y, gamepad1.left_stick_x, gamepad1.left_stick_y, toggledSpeed);
            if (gamepad1.b) {
                drivingModule.resetAngle();
            }
            double adjustedHeading = drivingModule.adjustHeading();

            //telemetry
            telemetry.addData("Climbing Motor Position: ", climbingSubsystemModule.climbingMotorTicks());
            telemetry.addData("Sub Motor Position: ", submersibleIntakeSubsystemModule.subMotorTicks());
            //telemetry.addData("Heading: ", drivingModule.angle);
            telemetry.addData("AdjustedHeading: ", adjustedHeading);
            //telemetry.addData("Right Servo Sub Pos", intakeSubmersibleModule.rightServo.getPosition());
            //telemetry.addData("Left Servo Sub Pos", intakeSubmersibleModule.leftServo.getPosition());
            // telemetry.addData("right trigger", gamepad2.right_trigger);
            //telemetry.addData("left trigger", gamepad2.left_trigger);
            telemetry.addData("restPos", doRestingPos);
            telemetry.addData("dPad left", gamepad1.dpad_left);
            telemetry.addData("currentPivotPos", currentPivotPos);
            telemetry.addData("subMotorPos", submersibleIntakeSubsystemModule.subMotorTicks());
            telemetry.addData("snatch stage", snatchStage);
            telemetry.addData("servoIsOpen", submersibleIntakeSubsystemModule.servosAtPosition(subClawServosOpenPos, subServosDeadBand));
            telemetry.addData("servoIsClosed", submersibleIntakeSubsystemModule.servosAtPosition(subClawServosClosePos, subServosDeadBand));
            telemetry.addData("motorIsExtended", submersibleIntakeSubsystemModule.subMotorTicks() > subExtendedPos - intakeDeadBand && submersibleIntakeSubsystemModule.subMotorTicks() < subExtendedPos + intakeDeadBand);
//            telemetry.addData("time", time);
//            telemetry.addData("timerIsActivated", timerIsActivated);
//            telemetry.addData("x", gamepad2.x);
            telemetry.addData("doDownPos", doDownPos);

            telemetry.update();
        }
    }
}
