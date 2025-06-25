package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import static org.firstinspires.ftc.teamcode.Constants.SampleClawConstants.*;

public class SampleClawSubsystem extends SubsystemBase {
    /**
     * Creates a new SpecimenClawSubsystem.
     */
    private final ServoImplEx sampleClaw;
    private final ServoImplEx sampleWrist;
    public SampleClawSubsystem(HardwareMap hMap) {
        sampleClaw = hMap.get(ServoImplEx.class, SAMPLE_CLAW_SERVO_NAME);
        sampleWrist = hMap.get(ServoImplEx.class, SAMPLE_WRIST_SERVO_NAME);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void openClaw() {
        sampleClaw.setPosition(CLAW_OPEN_CLAW_POS);
    }
    public void closeClaw() {
        sampleClaw.setPosition(CLAW_CLOSED_CLAW_POS);
    }
    public void setWrist(double pos) {
        sampleWrist.setPosition(pos);}

}