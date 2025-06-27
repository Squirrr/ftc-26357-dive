package org.firstinspires.ftc.teamcode.commands;

import com.seattlesolvers.solverslib.command.CommandBase;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.DoubleSupplier;

/**
 * A command to drive the robot with joystick input (passed in as {@link DoubleSupplier}s). Written
 * explicitly for pedagogical purposes.
 */
public class DefaultDrive extends CommandBase {

    private final DriveSubsystem m_drive;
    private final DoubleSupplier m_forward;
    private final DoubleSupplier m_rotation;
    private final DoubleSupplier m_strafe;

    /**
     * Creates a new DefaultDrive.
     *
     * @param subsystem The drive subsystem this command wil run on.
     * @param x   The control input for driving forwards/backwards
     * @param rot  The control input for turning
     */
    public DefaultDrive(DriveSubsystem subsystem, DoubleSupplier x, DoubleSupplier y, DoubleSupplier rot) {
        m_drive = subsystem;
        m_forward = x;
        m_strafe = y;
        m_rotation = rot;
        addRequirements(m_drive);
    }

    @Override
    public void execute() {
        m_drive.driveRobotRelative(m_strafe.getAsDouble(), m_forward.getAsDouble(), m_rotation.getAsDouble());
    }

}
