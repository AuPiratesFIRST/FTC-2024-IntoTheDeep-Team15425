package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(600);
        double chamberLength = 27.5;
        double robotWidth = 12.375/2;
        double ascentZoneLength = 42.75;

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12.375)
                .build();
        RoadRunnerBotEntity myBot2 = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 12.375)
                .build();

        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60, 36, 0))
                //.splineTo(new Vector2d(-(ascentZoneLength/2 + robotWidth - 10), -(chamberLength/4)), -Math.PI/2)
                                .setTangent(-Math.PI/2)
                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
                .splineToLinearHeading(new Pose2d(-(ascentZoneLength/2 + robotWidth - 3), -(chamberLength/4) + 15, -Math.PI/2), 0)

                //.setTangent(-Math.PI/4)
                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
                        .setTangent(Math.PI)
                .splineToConstantHeading(new Vector2d(-(ascentZoneLength/2 + robotWidth - 5), 26), Math.PI/2)
                .splineToConstantHeading(new Vector2d(ascentZoneLength/2 + 26, -(-14.5 - 12.9/2)), -Math.PI/2)
//            .splineToConstantHeading(new Vector2d(ascentZoneLength/2 + robotWidth, chamberLength/4), Math.PI)
//            .setTangent(-Math.PI/4)
//            //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
//            .splineToLinearHeading(new Pose2d(ascentZoneLength/2 + robotWidth, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI)
//            .splineToLinearHeading(new Pose2d(ascentZoneLength/4, -chamberLength/2 - robotWidth, Math.PI/2), Math.PI)
            .build());
        myBot2.runAction(myBot.getDrive().actionBuilder(new Pose2d(-60, -24, 0))
                        .setTangent(Math.PI/2)
                .splineToLinearHeading(new Pose2d(-(ascentZoneLength/2 + robotWidth - 5) - 2, -(chamberLength/4), -Math.PI/2), 0)

                //.setTangent(-Math.PI/4)
                //.splineToLinearHeading(new Pose2d(ascentZoneLength/4, -(chamberLength/2 + robotWidth), Math.PI/2), Math.PI)
                        .setTangent(Math.PI)
                .splineToLinearHeading(new Pose2d(-35.5, -47.5, 0), 0)
                .splineToLinearHeading(new Pose2d(-35.5, -47.5, Math.PI), 0)

                        .setTangent(Math.PI)
                .splineToConstantHeading(new Vector2d(-58, -58), -Math.PI/2)

                        .build());

//        myBot.runAction(myBot.getDrive().actionBuilder(new Pose2d(0, -0, 0))
//                .splineTo(new Vector2d(0, 30), 0)
//                //.splineTo(new Vector2d(0, 60), Math.PI)
//                .build());
        meepMeep.setBackground(MeepMeep.Background.FIELD_INTO_THE_DEEP_OFFICIAL)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .addEntity(myBot2)
                .start();
    }
}
