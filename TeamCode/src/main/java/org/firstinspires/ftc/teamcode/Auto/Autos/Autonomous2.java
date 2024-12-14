package org.firstinspires.ftc.teamcode.Auto.Autos;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Auto.MecanumDrive;
import org.firstinspires.ftc.teamcode.BaseConfig;
import org.firstinspires.ftc.teamcode.Constants;

@Config
@Autonomous(name = "AllLeftAuto(Ascent)", group = "Autonomous")
public class Autonomous2 extends BaseConfig {
    public static double offset = 1;
    public static double offset2 = -15;

    public class Climbing {

        public class ClimbingUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    climbingSubsystemModule.moveClimbingMotorTicks(1550, 0.6);
                    initialized = true;
                }

                double pos = climbingSubsystemModule.climbingMotorTicks();
                packet.put("climbingMotorPos", pos);
                telemetry.addData("Climbing Pos", pos);
                telemetry.update();
                if (pos < 1540) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        public Action climbingMotorUp() {
            return new ClimbingUp();
        }


        public class ClimbingDown implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    climbingSubsystemModule.moveClimbingMotorTicks(0, 0.6);
                    initialized = true;
                }

                double pos = climbingSubsystemModule.climbingMotorTicks();
                packet.put("climbingMotorPos", pos);
                telemetry.addData("Climbing Pos", pos);
                telemetry.update();
                if (!(pos > -10 && pos < 5)) {
                    return true;
                } else {
                    return false;
                }
            }
        }

        public Action climbingMotorDown() {
            return new ClimbingDown();
        }

        public class ClimbingSpecimenDown implements Action {
            private boolean initialized = false;
            private int climbingSpecimenHangPos = 1125;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    climbingSubsystemModule.moveClimbingMotorTicks(climbingSpecimenHangPos, 0.6);
                    initialized = true;
                }

                double pos = climbingSubsystemModule.climbingMotorTicks();
                packet.put("climbingMotorPos", pos);
                telemetry.addData("Climbing Pos", pos);
                telemetry.update();
                if (!(pos < climbingSpecimenHangPos + 5 && pos > climbingSpecimenHangPos - 5)) {
                    return true;
                } else {
                    climbingSubsystemModule.stopClimbingMotor();
                    return false;
                }
            }
        }

        public Action climbingMotorSpecimenDown() {
            return new ClimbingSpecimenDown();
        }

        public class ClimbingRelease implements Action {

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                climbingSubsystemModule.setRightServo(Constants.Climbing.CLIMBING_RIGHT_SERVO_OPEN_POSITION);
                climbingSubsystemModule.setLeftServo(Constants.Climbing.CLIMBING_LEFT_SERVO_OPEN_POSITION);
                return false;
            }
        }

        public Action climbingRelease() {
            return new ClimbingRelease();
        }

        public class ClimbingClose implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                climbingSubsystemModule.setRightServo(Constants.Climbing.CLIMBING_RIGHT_SERVO_OPEN_POSITION);
                climbingSubsystemModule.setLeftServo(Constants.Climbing.CLIMBING_LEFT_SERVO_OPEN_POSITION);
                return false;
            }
        }

        public Action climbingClose() {
            return new ClimbingClose();
        }


    }


//    public class Claw {
//        private Servo claw;
//
//        public Claw(HardwareMap hardwareMap) {
//            claw = hardwareMap.get(Servo.class, "claw");
//        }
//
//        public class CloseClaw implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                claw.setPosition(0.55);
//                return false;
//            }
//        }
//        public Action closeClaw() {
//            return new CloseClaw();
//        }
//
//        public class OpenClaw implements Action {
//            @Override
//            public boolean run(@NonNull TelemetryPacket packet) {
//                claw.setPosition(1.0);
//                return false;
//            }
//        }
//        public Action openClaw() {
//            return new OpenClaw();
//        }
//    }

    @Override
    public void runOpMode() {
        Pose2d initialPose = new Pose2d(-60, 36, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        double chamberLength = 27.5;
        double robotWidth = 12.375 / 2;
        double ascentZoneLength = 42.75;

//        TrajectoryActionBuilder driveAction = drive.actionBuilder(initialPose)
//                .splineToConstantHeading(new Vector2d(ascentZoneLength/2 + robotWidth, chamberLength/4), Math.PI)
//                .setTangent(-Math.PI/4)
//                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
//                .splineToLinearHeading(new Pose2d(ascentZoneLength/2 + robotWidth, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI)
//                .splineToLinearHeading(new Pose2d(ascentZoneLength/4, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI);
        TrajectoryActionBuilder driveAction = drive.actionBuilder(new Pose2d(-60, 36, 0))
                .splineTo(new Vector2d(-(ascentZoneLength / 2 + robotWidth - 5) + offset, -(chamberLength / 4)), -Math.PI / 2);

        TrajectoryActionBuilder driveAction2 = drive.actionBuilder(new Pose2d(-(ascentZoneLength / 2 + robotWidth - 5) + offset, -(chamberLength / 4), -Math.PI / 2))
                //.setTangent(-Math.PI/4)
                .setTangent(Math.PI)

                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
                .splineToConstantHeading(new Vector2d(-(ascentZoneLength / 2 + robotWidth - 5), 26), Math.PI / 2)
                .splineToConstantHeading(new Vector2d(ascentZoneLength / 2 - 6, 26), 0)
                .splineToConstantHeading(new Vector2d(ascentZoneLength / 2 - 6, -(-14.5 - 12.9 / 2) + offset2), -Math.PI / 2);
//                .build());
        //.build();)

//        Action trajectoryActionCloseOut = driveAction.fresh()//New action!
//                //.strafeTo(new Vector2d(48, 12))//go to that position
//                .build();
        //Sorry I marked this up- please delete if you want

        // actions that need to happen on init; for instance, a claw tightening.
        //Actions.runBlocking(claw.closeClaw());
        Actions.runBlocking(climbingSubsystemModule.climbingClose());


//        while (!isStopRequested() && !opModeIsActive()) {
//            int position = visionOutputPosition;
//            telemetry.addData("Position during Init", position);
//            telemetry.update();
//        }

//        int startPosition = visionOutputPosition;
//        telemetry.addData("Starting Position", startPosition);
//        telemetry.update();

        waitForStart();

        if (isStopRequested()) return;

        Actions.runBlocking(
                new SequentialAction(
                        climbingSubsystemModule.climbingClose(),
                        new ParallelAction(climbingSubsystemModule.climbingMotorUp(),
                                driveAction.build()),
                        climbingSubsystemModule.climbingMotorSpecimenDown(),
                        climbingSubsystemModule.climbingRelease(),
                        //climbing.climbingMotorDown()
                        //driveAction2.build(),
                        new ParallelAction(climbingSubsystemModule.climbingMotorUp(),
                                driveAction2.build())
                )
        );
    }
}