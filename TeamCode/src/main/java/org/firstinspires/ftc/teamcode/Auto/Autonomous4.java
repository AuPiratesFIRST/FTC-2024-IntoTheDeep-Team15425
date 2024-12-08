package org.firstinspires.ftc.teamcode.Auto;
import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@Autonomous(name = "AllRightAuto(Ascent)", group = "Autonomous")
public class Autonomous4 extends LinearOpMode {
    public static double offset = -2.4;
    public static double offset2 = -15;
    public class Climbing {
        private DcMotorEx climbingMotor;
        private Servo rightClimbingServo;
        private Servo leftClimbingServo;

        public Climbing(HardwareMap hardwareMap) {
            climbingMotor = hardwareMap.get(DcMotorEx.class, "Climbing Motor");
            climbingMotor.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
            climbingMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
            rightClimbingServo = hardwareMap.get(Servo.class, "Climbing Right Servo");
            leftClimbingServo = hardwareMap.get(Servo.class, "Climbing Left Servo");
        }

        public class ClimbingUp implements Action {
            private boolean initialized = false;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    climbingMotor.setTargetPosition(1550);
                    climbingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    climbingMotor.setPower(0.6);
                    initialized = true;
                }

                double pos = climbingMotor.getCurrentPosition();
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
                    climbingMotor.setTargetPosition(0);
                    climbingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    climbingMotor.setPower(0.6);
                    initialized = true;
                }

                double pos = climbingMotor.getCurrentPosition();
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
        public Action climbingMotorDown(){
            return new ClimbingDown();
        }

        public class ClimbingSpecimenDown implements Action {
            private boolean initialized = false;
            private int climbingSpecimenHangPos = 1125;

            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if (!initialized) {
                    climbingMotor.setTargetPosition(climbingSpecimenHangPos);
                    climbingMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                    climbingMotor.setPower(0.6);
                    initialized = true;
                }

                double pos = climbingMotor.getCurrentPosition();
                packet.put("climbingMotorPos", pos);
                telemetry.addData("Climbing Pos", pos);
                telemetry.update();
                if (!(pos < climbingSpecimenHangPos + 5 && pos > climbingSpecimenHangPos - 5)) {
                    return true;
                } else {
                    climbingMotor.setPower(0);
                    climbingMotor.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
                    return false;
                }
            }
        }
        public Action climbingMotorSpecimenDown(){
            return new ClimbingSpecimenDown();
        }

        public class ClimbingRelease implements Action {
            private double climbingRightServoOpenPos = 0.35;
            private double climbingRightServoClosePos = 0.18;
            private double climbingLeftServoOpenPos = 0.4;
            private double climbingLeftServoClosePos = 0.48;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightClimbingServo.setPosition(climbingRightServoOpenPos);
                leftClimbingServo.setPosition(climbingLeftServoOpenPos);
                return false;
            }
        }

        public Action climbingRelease(){
            return new ClimbingRelease();
        }

        public class ClimbingClose implements Action {
            private double climbingRightServoOpenPos = 0.35;
            private double climbingRightServoClosePos = 0.18;
            private double climbingLeftServoOpenPos = 0.4;
            private double climbingLeftServoClosePos = 0.48;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                rightClimbingServo.setPosition(climbingRightServoClosePos);
                leftClimbingServo.setPosition(climbingLeftServoClosePos);
                return false;
            }
        }
        public Action climbingClose(){
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
        Pose2d initialPose = new Pose2d(-60, -24, 0);
        MecanumDrive drive = new MecanumDrive(hardwareMap, initialPose);
        double chamberLength = 27.5;
        double robotWidth = 12.375/2;
        double ascentZoneLength = 42.75;
        Climbing climbing = new Climbing(hardwareMap);


//        TrajectoryActionBuilder driveAction = drive.actionBuilder(initialPose)
//                .splineToConstantHeading(new Vector2d(ascentZoneLength/2 + robotWidth, chamberLength/4), Math.PI)
//                .setTangent(-Math.PI/4)
//                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
//                .splineToLinearHeading(new Pose2d(ascentZoneLength/2 + robotWidth, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI)
//                .splineToLinearHeading(new Pose2d(ascentZoneLength/4, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI);
        TrajectoryActionBuilder driveAction = drive.actionBuilder(new Pose2d(-60, -24, 0))
                .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(-(ascentZoneLength/2 + robotWidth - 5) - offset, -(chamberLength/4), -Math.PI/2), 0);
        TrajectoryActionBuilder driveAction2 = drive.actionBuilder(new Pose2d(-(ascentZoneLength/2 + robotWidth + 2), -(chamberLength/4), -Math.PI/2))
                //.setTangent(-Math.PI/4)
                .setTangent(Math.PI)

                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
                .splineToConstantHeading(new Vector2d(-(ascentZoneLength/2 + robotWidth - 5), 26), Math.PI/2)
                .splineToConstantHeading(new Vector2d(ascentZoneLength/2 -6, 26), 0)
                .splineToConstantHeading(new Vector2d(ascentZoneLength/2 -6, -(-14.5 - 12.9/2) + offset2), -Math.PI/2);
//                .build());
        //.build();)

//        Action trajectoryActionCloseOut = driveAction.fresh()//New action!
//                //.strafeTo(new Vector2d(48, 12))//go to that position
//                .build();
        //Sorry I marked this up- please delete if you want

        // actions that need to happen on init; for instance, a claw tightening.
        Actions.runBlocking(climbing.climbingClose());


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
                        climbing.climbingClose(),
                        new ParallelAction(climbing.climbingMotorUp(),
                                driveAction.build()),
                        climbing.climbingMotorSpecimenDown(),
                        climbing.climbingRelease(),
                        new SleepAction(0.5),
                        //climbing.climbingMotorDown()
                        //driveAction2.build(),
                        new ParallelAction(climbing.climbingMotorUp(),
                                driveAction2.build())
                )
        );
    }
}