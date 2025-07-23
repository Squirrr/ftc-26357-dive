package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.ExtensionConstants.*;
import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.ShoulderConstants.*;
import static org.firstinspires.ftc.teamcode.Constants.ArmConstants.*;
import static org.firstinspires.ftc.teamcode.Constants.STALL_CURRENT_AMPS;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.SubsystemBase;
import com.seattlesolvers.solverslib.hardware.ServoEx;
import com.seattlesolvers.solverslib.hardware.SimpleServo;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.util.BotState;
import org.firstinspires.ftc.teamcode.util.MathUtil;
import org.firstinspires.ftc.teamcode.util.MotorUtil;

public class ArmSubsystem extends SubsystemBase {
    private BotState currentState = BotState.HOME;
    //Extension objects
    private final MotorEx lExtend, rExtend;
    private final DcMotorEx dcLExtend, dcRExtend;
    //Shoulder objects
    private final MotorEx shoulder;
    private boolean shoulderFirst;

    //Arm objects
    private final ServoEx elbow;

    //FTCDash
    private MultipleTelemetry mTelemetry = null;
    private FtcDashboard dashboard = null;
    private TelemetryPacket packet = null;
    private final ElapsedTime resetSlideTimer = new ElapsedTime();

    public ArmSubsystem(HardwareMap hMap, Telemetry telemetry) {

        dcLExtend = hMap.get(DcMotorEx.class, LEFT_EXTEND_NAME);
        dcRExtend = hMap.get(DcMotorEx.class, RIGHT_EXTEND_MOTOR_NAME);

        initializeDashboard(telemetry);

        lExtend = MotorUtil.initializeMotor(hMap,
                LEFT_EXTEND_NAME,
                LEFT_EXTEND_ZPB,
                () -> LEFT_EXTEND_IS_INVERTED,
                LEFT_EXTEND_RUN_MODE,
                new double[] {EXTENSION_kP, EXTENSION_kI, EXTENSION_kD});
        lExtend.setPositionTolerance(500);
        rExtend = MotorUtil.initializeMotor(hMap,
                RIGHT_EXTEND_MOTOR_NAME,
                RIGHT_EXTEND_ZPB,
                () -> RIGHT_EXTEND_IS_INVERTED,
                RIGHT_EXTEND_RUN_MODE,
                new double[] {EXTENSION_kP, EXTENSION_kI, EXTENSION_kD});
        rExtend.setPositionTolerance(500);
        shoulder = MotorUtil.initializeMotor(hMap,
                SHOULDER_MOTOR_NAME,
                SHOULDER_ZPB,
                () -> SHOULDER_IS_INVERTED,
                SHOULDER_RUN_MODE,
                new double[] {SHOULDER_kP, SHOULDER_kI, SHOULDER_kD}
        );
        shoulder.setPositionTolerance(10);
        elbow = new SimpleServo(hMap, ELBOW_SERVO_NAME, 0, 180);
    }

    @Override
    public void periodic() {

        sendAllTelemetry();
        mTelemetry.update();
        elbow.setPosition(currentState.getElbowSetpoint());


        if (shoulderFirst) {
            shoulder.setTargetPosition(currentState.getShoulderSetpoint());
            if (shoulderIsAtSetpoint()) {
                lExtend.setTargetPosition(currentState.getExtensionSetpoint());
                rExtend.setTargetPosition(currentState.getExtensionSetpoint());
            }
        } else {
            lExtend.setTargetPosition(currentState.getExtensionSetpoint());
            rExtend.setTargetPosition(currentState.getExtensionSetpoint());

            if (extensionIsAtSetpoint()) {
                shoulder.setTargetPosition(currentState.getShoulderSetpoint());
            }
        }

        MotorUtil.setPositionPower(shoulder);
        MotorUtil.setPositionPower(lExtend);
        MotorUtil.setPositionPower(rExtend);

    }

    private void sendAllTelemetry() {

        if (lExtend != null) {
            packet.put("lift Pos: ", lExtend.getCurrentPosition());
            packet.put("lift at Target: ", lExtend.atTargetPosition());
        }

        if (shoulder != null) {
            packet.put("pivot Pos: ", shoulder.getCurrentPosition());
            packet.put("pivot at Target: ", shoulder.atTargetPosition());
        }

        dashboard.sendTelemetryPacket(packet);
    }

    private void initializeDashboard(Telemetry telemetry) {
        dashboard = FtcDashboard.getInstance();
        mTelemetry = new MultipleTelemetry(telemetry, dashboard.getTelemetry());
        packet = new TelemetryPacket();
    }

    public boolean setShoulderFirst(boolean shoulderFirst) {
        this.shoulderFirst = shoulderFirst;
        return this.shoulderFirst;
    }

    public double getShoulderError() {
        return shoulder.getCurrentPosition() - currentState.getShoulderSetpoint();
    }
    public boolean shoulderIsAtSetpoint() {
        return shoulder.atTargetPosition();
    }

    public double getError() {
        return MathUtil.average(new double[]
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

    public void resetSlides() {
        lExtend.setRunMode(Motor.RunMode.RawPower);
        rExtend.setRunMode(Motor.RunMode.RawPower);
        while (dcLExtend.getCurrent(CurrentUnit.AMPS) < STALL_CURRENT_AMPS
                && dcRExtend.getCurrent(CurrentUnit.AMPS) < STALL_CURRENT_AMPS) {
            lExtend.set(-1);
            rExtend.set(-1);
        }
        lExtend.set(0);
        rExtend.set(0);
        lExtend.setRunMode(Motor.RunMode.PositionControl);
        rExtend.setRunMode(Motor.RunMode.PositionControl);
    }
}
