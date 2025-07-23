package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.DeferredCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.button.Button;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.command.button.Trigger;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.commands.DefaultDrive;
import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SampleClawSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SpecimenClawSubsystem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.firstinspires.ftc.teamcode.util.BotState.*;
import static com.seattlesolvers.solverslib.gamepad.GamepadKeys.Button.*;


@TeleOp(group = "Learning", name = "dev-commandopmode")
public class MainCommandOpMode extends CommandOpMode {
    //Initialize subsystems here
    private ArmSubsystem arm;
    private SampleClawSubsystem sampleClaw;
    private DriveSubsystem drive;
    private SpecimenClawSubsystem specimenClaw;

    //Initialize gamepads here
    private GamepadEx driver;
    private GamepadEx operator;

    //Initialize triggers here
    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private Button bHome,
            bBuckets,
            bChambers,
            bSpecimenPickup,
            bClimb,
            bClawsControl,
            bWristControlCW,
            bWristControlCCW,
            bResetSlides;

    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private Trigger bIntake;

    @SuppressWarnings({"UnusedDeclaration", "FieldCanBeLocal"})
    private DefaultDrive m_driveCommand;


    @Override
    public void initialize() {
        arm = new ArmSubsystem(hardwareMap, this.telemetry);
        sampleClaw = new SampleClawSubsystem(hardwareMap);
        drive = new DriveSubsystem(hardwareMap);
        specimenClaw = new SpecimenClawSubsystem(hardwareMap);

        driver = new GamepadEx(gamepad1);
        operator = new GamepadEx(gamepad2);

        configureButtonBindings();

        m_driveCommand = new DefaultDrive(drive, () -> driver.getLeftY(), () -> driver.getLeftX(), () -> driver.getRightX());


        register(drive);
        drive.setDefaultCommand(m_driveCommand);
    }

    /**

                          _=====_                               _=====_
                         / _____ \                             / _____ \
                       +.-'_____'-.---------------------------.-'_____'-.+
                      /   |     |  '.        S O N Y        .'  |  _  |   \
                     / ___| /|\ |___ \                     / ___| /△\ |___ \
                    / |      |      | ;  __           _   ; |           _ | ;
                    | | <---   ---> | | |__|         |_:> | ||□|       (O)| |
                    | |___   |   ___| ;SELECT       START ; |___       ___| ;
                    |\    | \|/ |    /  _     ___      _   \    | (X) |    /|
                    | \   |_____|  .','" "', |___|  ,'" "', '.  |_____|  .' |
                    |  '-.______.-' /       \ANALOG/       \  '-._____.-'   |
                    |               |  LS   |------|  RS   |                |
                    |              /\       /      \       /\               |
                    |             /  '.___.'        '.___.'  \              |
                    |            /                            \             |
                     \          /                              \           /
                      \________/                                \_________/
                                        Controller Bindings

                        - LB: "Shift" key; used to switch between two states more easily;
                        if used in a button bindings, it will be shown as [Button]S (s for shift)
                        - RB: Open/close sample claw
                        - RBS: Open/close specimen claw
                        - RT: Intake close/far (depending on how pressed trigger is)
                        - RTS: Specimen pickup off of wall
                        - X: Home position
                        - O: High chamber
                        - OS: Low chamber
                        - △: Prep Climb
                        - △S: Climb
                        - □: High bucket
                        - □S: Low bucket
                        - LS: Translation (Robot-centric)
                        - RS: Rotation (Robot-centric)

     */
    public void configureButtonBindings() {
        bHome = new GamepadButton(driver, CROSS)
                .whenPressed(arm.setGoalCommand(HOME));

        bIntake = new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .whileActiveContinuous(new ConditionalCommand(
                        new DeferredCommand(() -> new ConditionalCommand(
                                new ConditionalCommand(
                                        arm.setGoalCommand(INTAKE_CLOSE),
                                        arm.setGoalCommand(INTAKE_FAR),
                                        () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) < 0.7),
                                arm.setGoalCommand(PICKUP_SPEC),
                                () -> !driver.getButton(LEFT_BUMPER)
                        ),
                                new ArrayList<>(Collections.singletonList(arm))),
                        arm.setGoalCommand(PRE_INTAKE),
                        () -> arm.getCurrentState() == PRE_INTAKE
                ));

        bClawsControl = new GamepadButton(driver, RIGHT_BUMPER)
                .toggleWhenPressed(new DeferredCommand(
                        () -> new ConditionalCommand(
                                new InstantCommand(specimenClaw::openClaw),
                                new InstantCommand(sampleClaw::openClaw),
                                        () -> driver.getButton(LEFT_BUMPER)
                        ), new ArrayList<>(Arrays.asList(specimenClaw, sampleClaw))),

                        new DeferredCommand(
                                () -> new ConditionalCommand(
                                        new InstantCommand(specimenClaw::closeClaw),
                                        new InstantCommand(sampleClaw::closeClaw),
                                        () -> driver.getButton(LEFT_BUMPER)
                                ), new ArrayList<>(Arrays.asList(specimenClaw, sampleClaw))));

        bBuckets = new GamepadButton(driver, SQUARE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(LOWBUCKET),
                        arm.setGoalCommand(HIGHBUCKET),
                        () -> driver.getButton(LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));

        bChambers = new GamepadButton(driver, CIRCLE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(LOWCHAMBER),
                        arm.setGoalCommand(HIGHCHAMBER),
                        () -> driver.getButton(LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));

        bClimb = new GamepadButton(driver, TRIANGLE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(CLIMB),
                        arm.setGoalCommand(PREP_CLIMB),
                        () -> driver.getButton(LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));

        bWristControlCW = new GamepadButton(driver, DPAD_LEFT)
                .whileHeld(new InstantCommand(() -> sampleClaw.stepWrist(true)));

        bWristControlCCW = new GamepadButton(driver, DPAD_RIGHT)
                .whileHeld(new InstantCommand(() -> sampleClaw.stepWrist(false)));

        bResetSlides = new GamepadButton(driver, SHARE).whileHeld(arm::resetSlides);
    }
}
