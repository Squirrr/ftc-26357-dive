package org.firstinspires.ftc.teamcode.util;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.seattlesolvers.solverslib.command.button.GamepadButton;
import com.seattlesolvers.solverslib.gamepad.GamepadKeys;
import com.seattlesolvers.solverslib.hardware.motors.Motor;
import com.seattlesolvers.solverslib.hardware.motors.MotorEx;

import java.util.function.BooleanSupplier;

public class Util {

    public static MotorEx initializeMotor(
            HardwareMap hMap,
            String name,
            @Nullable Motor.ZeroPowerBehavior zeroPowerBehavior,
            @Nullable BooleanSupplier isInverted,
            @Nullable Motor.RunMode runMode,
            @Nullable double[] vK
            ) {
        MotorEx m = new MotorEx(hMap, name);
        m.setZeroPowerBehavior(zeroPowerBehavior != null ? zeroPowerBehavior : Motor.ZeroPowerBehavior.FLOAT);
        m.setInverted(isInverted != null && isInverted.getAsBoolean());
        m.setRunMode(runMode != null ? runMode : Motor.RunMode.RawPower);
        boolean veloNull = vK != null;
        m.setVeloCoefficients(veloNull ? vK[0] : 0.0, veloNull ? vK[1] : 0.0, veloNull ? vK[2] : 0.0);

        return m;
    }

    public static double average(double[] nums) {
        double a = 0;
        for (double num : nums) {
            a += num;
        }
        return a/nums.length;
    }

    public static int clamp(int value, int min, int max) {
        return Math.max(min, Math.min(max, value));
    }

    public static final class LoopUtil {
        private static long lastTime = 0;

        /**
         * @return The nano time between each function call
         */
        public static long getLoopTime() {
            long currentTime = System.nanoTime();
            long delta = currentTime - lastTime;
            lastTime = currentTime;
            return delta;
        }

        public static double getLoopTimeInSeconds() {
            return getLoopTime() / 1000000000.0;
        }

        public static double getLoopTimeInHertz() {
            return 1 / getLoopTimeInSeconds();
        }
    }


}
