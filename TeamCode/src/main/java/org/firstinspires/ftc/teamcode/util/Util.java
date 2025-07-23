package org.firstinspires.ftc.teamcode.util;

import com.seattlesolvers.solverslib.command.Command;
import com.seattlesolvers.solverslib.command.ConditionalCommand;
import com.seattlesolvers.solverslib.command.DeferredCommand;
import com.seattlesolvers.solverslib.command.InstantCommand;
import com.seattlesolvers.solverslib.command.Subsystem;
import com.seattlesolvers.solverslib.gamepad.GamepadEx;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;

import java.util.ArrayList;
import java.util.Arrays;

public final class Util {

    public static final class CommandUtil {
        public static Command ShiftCommand(
                Runnable trueRunnable,
                Runnable falseRunnable,
                GamepadEx gp,
                ArrayList<Subsystem> requirements) {
          return new DeferredCommand(() -> new ConditionalCommand(
                          new InstantCommand(trueRunnable),
                          new InstantCommand(falseRunnable),
                          () -> gp.getButton(GamepadKeys.Button.LEFT_BUMPER)
                  ), requirements);
        };

        public static Command ShiftCommand(
                Command trueCommand,
                Command falseCommand,
                GamepadEx gp,
                ArrayList<Subsystem> requirements) {
            return new DeferredCommand(() -> new ConditionalCommand(
                    trueCommand,
                    falseCommand,
                    () -> gp.getButton(GamepadKeys.Button.LEFT_BUMPER)
            ), requirements);
        };

        public static Command ShiftCommand(
                Command trueCommand,
                Command falseCommand,
                GamepadEx gp,
                Subsystem... requirements) {
            return new DeferredCommand(() -> new ConditionalCommand(
                    trueCommand,
                    falseCommand,
                    () -> gp.getButton(GamepadKeys.Button.LEFT_BUMPER)
            ), new ArrayList<>(Arrays.asList(requirements)));
        };
    }
}
