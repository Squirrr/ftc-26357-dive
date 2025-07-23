//package org.firstinspires.ftc.teamcode;
//
//import static org.firstinspires.ftc.teamcode.util.Common.mTelemetry;
//import static org.firstinspires.ftc.teamcode.util.Common.myRobot;
//
//import com.acmerobotics.dashboard.config.Config;
//import com.qualcomm.robotcore.hardware.HardwareMap;
//
//import org.firstinspires.ftc.teamcode.util.BotState;
//import org.firstinspires.ftc.teamcode.util.BulkReader;
//import org.firstinspires.ftc.teamcode.util.LoopUtil;
//import org.firstinspires.ftc.teamcode.subsystems.ArmSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.SpecimenClawSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.SampleClawSubsystem;
//
//@Config
//public class MyRobot extends com.seattlesolvers.solverslib.command.Robot {
//    public final DriveSubsystem drivetrain;
//    public final ArmSubsystem arm;
//    public final SpecimenClawSubsystem intake;
//    public final SampleClawSubsystem claw;
//    public final BulkReader bulkReader;
//
//    BotState currentState = BotState.HOME;
//
////    /**
////     * Constructor used in teleOp classes that makes the current pose2d, 0
////     * @param hardwareMap A constant map that holds all the parts for config in code
////     */
////    public Robot(HardwareMap hardwareMap) {
////        this(hardwareMap);
////    }
//
//    /**
//     * Constructor for instantiating a new 'robot' class
//     * @param hardwareMap: A constant map that holds all the parts for config in code
//     */
//    public MyRobot(HardwareMap hardwareMap) {
////        Limelight3A limelight3A = hardwareMap.get(Limelight3A.class, "limelight");
//
//        drivetrain = new DriveSubsystem(hardwareMap);
//        arm = new ArmSubsystem(hardwareMap, this);
//        bulkReader = new BulkReader(hardwareMap);
//        intake = new SpecimenClawSubsystem(hardwareMap);
//        claw = new SampleClawSubsystem(hardwareMap);
////        autoAligner = new AutoAligner(hardwareMap);
//
////        limelight3A.start();
//    }
//
//    // Reads all the necessary sensors (including battery volt.) in one bulk read
//    public void readSensors() {
//        bulkReader.bulkRead();
//    }
//
//    // Runs all the necessary mechanisms
//
//
//    // Prints data on the driver hub for debugging and other uses
//    public void printTelemetry() {
//        mTelemetry.addData("Robot State", myRobot.currentState.getName());
//        mTelemetry.addData("Loop time (hertz)", LoopUtil.getLoopTimeInHertz());
////        extendo.printTelemetry();
////        lift.printTelemetry();
////        arm.printTelemetry();
////        intake.printTelemetry();
////        autoAligner.printTelemetry();
//        mTelemetry.update();
//    }
//
//    public BotState getCurrentState() {
//        return currentState;
//    }
//    public void setCurrentState(BotState state) {
//        this.currentState = state;
//    }
//}
