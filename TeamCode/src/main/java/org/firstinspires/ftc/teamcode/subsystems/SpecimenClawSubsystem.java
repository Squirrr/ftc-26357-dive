package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.seattlesolvers.solverslib.command.SubsystemBase;

import static org.firstinspires.ftc.teamcode.Constants.SpecimenClawConstants.*;

public class SpecimenClawSubsystem extends SubsystemBase {

    private ServoImplEx specClaw;
    public SpecimenClawSubsystem(HardwareMap hMap) {
        specClaw = hMap.get(ServoImplEx.class, SPEC_CLAW_SERVO_NAME);
    }

    @Override
    public void periodic() {
        // This method will be called once per scheduler run
    }

    public void openClaw() {
        specClaw.setPosition(SPEC_CLAW_OPEN_POS);
    }
    public void closeClaw() {
        specClaw.setPosition(SPEC_CLAW_CLOSED_POS);
    }
}
