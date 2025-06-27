package org.firstinspires.ftc.teamcode.subsystems;

import com.pedropathing.follower.Follower;
import com.pedropathing.localization.Pose;
import com.pedropathing.util.Constants;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.FunctionalCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.drivebase.MecanumDrive;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.geometry.Rotation2d;
import com.seattlesolvers.solverslib.hardware.RevIMU;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.teamcode.pedroPathing.constants.FConstants;
import org.firstinspires.ftc.teamcode.pedroPathing.constants.LConstants;

import java.util.function.DoubleSupplier;

public class DriveSubsystem extends SubsystemBase {
    private MotorEx fL, fR, bL, bR;
    private RevIMU imu;
    private double imuOffset;
    private MecanumDrive drive;
    private double x, y, rot;
    private GamepadEx gp = new GamepadEx(new Gamepad());

    private Follower follower;
    private final Pose startPose = new Pose(0,0,0);


    public DriveSubsystem(HardwareMap hMap) {
        Constants.setConstants(FConstants.class, LConstants.class);
        follower = new Follower(hMap);
        follower.setStartingPose(startPose);

        MotorEx fl = new MotorEx(hMap, "fl");
        fl.setInverted(true);

        MotorEx fr = new MotorEx(hMap, "fr");
        fr.setInverted(false);
        drive = new MecanumDrive(
                false,
                fl,
                fr,
                new MotorEx(hMap, "bl"),
                new MotorEx(hMap, "br")
        );
    }

    @Override
    public void periodic() {
        //Looped code goes here
        follower.setTeleOpMovementVectors(-gp.getLeftY(), -gp.getLeftX(), -gp.getRightX(), true);
        follower.update();
    }

    public Pose getFollowerPose() {
        return follower.getPose();
    }

    public double getFollowerHeading() {
        return follower.getPose().getHeading();
    }

    public void driveFieldCentric
            (double x,
             double y,
             double rot) {
        drive.driveFieldCentric(x, y, rot, getOffsetHeading());
    }

    public void driveMDrive(
            double x,
            double y,
            double rotX,
            double rotY) {
        drive.driveFieldCentric(x,
                y,
                Math.toDegrees(Math.atan2(x, y)) - 90,
                getOffsetHeading());
    }

    public void driveRobotRelative
            (double strafe,
             double forward,
             double turn
             ) {
        drive.driveRobotCentric(strafe, forward, turn);
    }

    public void resetIMU() {
        imu.reset();
    }
    public void addOffset(double offset) {
        imuOffset = offset;
    }

    public double getImuOffset() {
        return imuOffset;
    }

    public double getHeadingRaw() {
        return imu.getHeading();
    }
    public double getOffsetHeading() {
        return imu.getHeading() + imuOffset;
    }

}
