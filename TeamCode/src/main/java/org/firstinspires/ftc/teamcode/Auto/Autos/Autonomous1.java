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

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.Auto.MecanumDrive;
import org.firstinspires.ftc.teamcode.BaseConfig;
import org.firstinspires.ftc.teamcode.Constants;

@Config
@Autonomous(name = "LeftChamber/Start, Observation", group = "Autonomous")
public class Autonomous1 extends BaseConfig {
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
                .splineTo(new Vector2d(-(ascentZoneLength / 2 + robotWidth - 5), -(chamberLength / 4)), -Math.PI / 2);

        TrajectoryActionBuilder driveAction2 = drive.actionBuilder(new Pose2d(-(ascentZoneLength / 2 + robotWidth + 2), -(chamberLength / 4), -Math.PI / 2))
                //.setTangent(-Math.PI/4)
                .setTangent(Math.PI)
                .splineToConstantHeading(new Vector2d(-58, -58), -Math.PI / 2);
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
                        //climbingModule.climbingMotorDown()
                        //driveAction2.build(),

                        driveAction2.build()
                )

        );
    }
}