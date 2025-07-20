package org.firstinspires.ftc.teamcode;

import com.seattlesolvers.solverslib.hardware.motors.Motor;

public class Constants {

    public static class DriveConstants {}

    public static class ArmConstants {
        public static final String ELBOW_SERVO_NAME = "intakePivot";

        public static class ShoulderConstants {
            public static final String SHOULDER_MOTOR_NAME = "pivot";
            public static final boolean SHOULDER_IS_INVERTED = false;
            public static final Motor.RunMode SHOULDER_RUN_MODE = Motor.RunMode.PositionControl;
            public static final Motor.ZeroPowerBehavior SHOULDER_ZPB = Motor.ZeroPowerBehavior.BRAKE;

            public static final double SHOULDER_kP = 0.01;
            public static final double SHOULDER_kI = 0.0;
            public static final double SHOULDER_kD = 0.0;
        }

        public static class ExtensionConstants {
            public static final String LEFT_EXTEND_NAME = "leftLift";
            public static final boolean LEFT_EXTEND_IS_INVERTED = false;
            public static final Motor.RunMode LEFT_EXTEND_RUN_MODE = Motor.RunMode.PositionControl;
            public static final Motor.ZeroPowerBehavior LEFT_EXTEND_ZPB = Motor.ZeroPowerBehavior.BRAKE;

            public static final String RIGHT_EXTEND_MOTOR_NAME = "rightLift";
            public static final boolean RIGHT_EXTEND_IS_INVERTED = true;
            public static final Motor.RunMode RIGHT_EXTEND_RUN_MODE = Motor.RunMode.PositionControl;
            public static final Motor.ZeroPowerBehavior RIGHT_EXTEND_ZPB = Motor.ZeroPowerBehavior.BRAKE;

            public static final double EXTENSION_kP = 0.0075;
            public static final double EXTENSION_kI = 0.0;
            public static final double EXTENSION_kD = 0.0;
            public static final double EXTENSION_kFF = 0.0;
        }
    }


    public static class SpecimenClawConstants {
        public static final String SPEC_CLAW_SERVO_NAME = "clawSpec";
        public static final double SPEC_CLAW_OPEN_POS = 1.0;
        public static final double SPEC_CLAW_CLOSED_POS = 0.0;
    }

    public static class SampleClawConstants {
        public static final String SAMPLE_CLAW_SERVO_NAME = "intake";
        public static final double CLAW_OPEN_CLAW_POS = 1.0;
        public static final double CLAW_CLOSED_CLAW_POS = 0.0;
        public static final String SAMPLE_WRIST_SERVO_NAME = "intakeRotate";
    }
}
