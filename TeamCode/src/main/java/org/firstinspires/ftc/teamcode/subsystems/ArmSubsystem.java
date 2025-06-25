package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.ExtensionConstants.*;
import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.ShoulderConstants.*;
import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.*;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;
import com.seattlesolvers.solverslib.hardware.motors.MotorGroup;

import org.firstinspires.ftc.teamcode.util.BotState;
import org.firstinspires.ftc.teamcode.util.Util;

public class ArmSubsystem extends SubsystemBase {
    private BotState currentState;
    //Extension objects
    private final MotorEx lExtend, rExtend;
    private final MotorGroup extension;

    //Shoulder objects
    private final MotorEx shoulder;
    private boolean shoulderFirst;

    //Arm objects
    private final ServoImplEx elbow;

    public ArmSubsystem(HardwareMap hMap) {
        lExtend = Util.initializeMotor(hMap,
                LEFT_EXTEND_NAME,
                LEFT_EXTEND_ZPB,
                () -> LEFT_EXTEND_IS_INVERTED,
                LEFT_EXTEND_RUN_MODE,
                new double[] {EXTENSION_kP, EXTENSION_kI, EXTENSION_kD});
        rExtend = Util.initializeMotor(hMap,
                RIGHT_EXTEND_MOTOR_NAME,
                RIGHT_EXTEND_ZPB,
                () -> RIGHT_EXTEND_IS_INVERTED,
                RIGHT_EXTEND_RUN_MODE,
                new double[] {EXTENSION_kP, EXTENSION_kI, EXTENSION_kD});

        extension = new MotorGroup(lExtend, rExtend);

        shoulder = Util.initializeMotor(hMap,
                SHOULDER_MOTOR_NAME,
                SHOULDER_ZPB,
                () -> SHOULDER_IS_INVERTED,
                SHOULDER_RUN_MODE,
                new double[] {SHOULDER_kP, SHOULDER_kI, SHOULDER_kD}
        );

        elbow = hMap.get(ServoImplEx.class, ELBOW_SERVO_NAME);
    }

    @Override
    public void periodic() {
        if (shoulderFirst) {
            shoulder.setTargetPosition(currentState.getShoulderSetpoint());
            elbow.setPosition(currentState.getElbowSetpoint());

            if (shoulderIsAtSetpoint()) {
                extension.setTargetPosition(currentState.getExtensionSetpoint());
            }
        } else {
            extension.setTargetPosition(currentState.getExtensionSetpoint());

            if (extensionIsAtSetpoint()) {
                shoulder.setTargetPosition(currentState.getShoulderSetpoint());
                elbow.setPosition(currentState.getElbowSetpoint());
            }
        }
    }

    public boolean setShoulderFirst(boolean shoulderFirst) {
        this.shoulderFirst = shoulderFirst;
        return this.shoulderFirst;
    }

    public double getShoulerError() {
        return shoulder.getCurrentPosition() - currentState.getShoulderSetpoint();
    }
    public boolean shoulderIsAtSetpoint() {
        return shoulder.atTargetPosition();
    }

    public double getError() {
        return Util.average(new double[]
                {lExtend.getCurrentPosition() - currentState.getExtensionSetpoint(),
                        rExtend.getCurrentPosition() - currentState.getExtensionSetpoint()
                });
    }
    public boolean extensionIsAtSetpoint() {
        return lExtend.atTargetPosition() && rExtend.atTargetPosition();
    }
    public void setGoal(BotState desiredState) {
        this.shoulderFirst = currentState.getExtensionSetpoint() < desiredState.getExtensionSetpoint();
        currentState = desiredState;
    }
    public Command setGoalCommand(BotState desiredState) {
        return new InstantCommand(() -> {
            setGoal(desiredState);
        });
    }
    public BotState getCurrentState() {
        return currentState;
    }
}
