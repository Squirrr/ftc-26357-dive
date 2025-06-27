package org.firstinspires.ftc.teamcode.opModes;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.seattlesolvers.solverslib.command.CommandOpMode;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.DeferredCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.PerpetualCommand;
import com.seattlesolvers.solverslib.command.RunCommand;
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
import org.firstinspires.ftc.teamcode.util.BotState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    private Button bHome,
            bBuckets,
            bChambers,
            bSpecimenPickup,
            bClimb,
            bClawsControl;
    private Trigger bIntake;

    private DefaultDrive m_driveCommand;


    @Override
    public void initialize() {
        arm = new ArmSubsystem(hardwareMap);
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
        bHome = new GamepadButton(driver, GamepadKeys.Button.CROSS)
                .whenPressed(arm.setGoalCommand(BotState.HOME));

        bIntake = new Trigger(() -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) > 0.1)
                .whileActiveContinuous(new DeferredCommand(() -> new ConditionalCommand(
                        new ConditionalCommand(
                                arm.setGoalCommand(BotState.INTAKE_CLOSE),
                                arm.setGoalCommand(BotState.INTAKE_FAR),
                                () -> driver.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER) < 0.7),
                        arm.setGoalCommand(BotState.PICKUP_SPEC),
                        () -> !driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                ),
                        new ArrayList<>(Collections.singletonList(arm))
                ))
                .toggleWhenActive(new DeferredCommand(
                        () -> new ConditionalCommand(
                                new InstantCommand(specimenClaw::openClaw),
                                new InstantCommand(sampleClaw::openClaw),
                                () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)

                        ), new ArrayList<>(Arrays.asList(sampleClaw, specimenClaw))
                ));

        bClawsControl = new GamepadButton(driver, GamepadKeys.Button.RIGHT_BUMPER)
                .toggleWhenPressed(new DeferredCommand(
                        () -> new ConditionalCommand(
                                new InstantCommand(specimenClaw::openClaw),
                                new InstantCommand(sampleClaw::openClaw),
                                        () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                        ), new ArrayList<>(Arrays.asList(specimenClaw, sampleClaw))),

                        new DeferredCommand(
                                () -> new ConditionalCommand(
                                        new InstantCommand(specimenClaw::closeClaw),
                                        new InstantCommand(sampleClaw::closeClaw),
                                        () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                                ), new ArrayList<>(Arrays.asList(specimenClaw, sampleClaw))));



        bBuckets = new GamepadButton(driver, GamepadKeys.Button.SQUARE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(BotState.LOWBUCKET),
                        arm.setGoalCommand(BotState.HIGHBUCKET),
                        () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));

        bChambers = new GamepadButton(driver, GamepadKeys.Button.CIRCLE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(BotState.LOWCHAMBER),
                        arm.setGoalCommand(BotState.HIGHCHAMBER),
                        () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));

        bClimb = new GamepadButton(driver, GamepadKeys.Button.TRIANGLE)
                .whenPressed(new DeferredCommand(() -> new ConditionalCommand(
                        arm.setGoalCommand(BotState.CLIMB),
                        arm.setGoalCommand(BotState.PREP_CLIMB),
                        () -> driver.getButton(GamepadKeys.Button.LEFT_BUMPER)
                ), new ArrayList<>(Collections.singletonList(arm))));
    }
}
