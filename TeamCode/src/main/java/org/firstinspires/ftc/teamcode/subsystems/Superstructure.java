//package org.firstinspires.ftc.teamcode.subsystems;
//
//import com.seattlesolvers.solverslib.command.SubsystemBase;
//
//public class Superstructure extends StateSubsystem {
//    public static enum RobotState {
//        HOME,
//        PREP_INTAKE,
//        INTAKE,
//        PREP_LOWBUCKET,
//        LOWBUCKET,
//        PREP_HIGHBUCKET,
//        HIGHBUCKET,
//        PREP_LOWCHAMBER,
//        LOWCHAMBER,
//        PREP_HIGHCHAMBER,
//        HIGHCHAMBER,
//        PREP_CLIMB,
//        CLIMB
//    }
//    public RobotState previousState;
//    public RobotState currentState = RobotState.HOME;
//    public RobotState wantedState = RobotState.HOME;
//    private ExtensionSubsystem extensionSubsystem;
//    private PivotSubsystem pivotSubsystem;
//    private IntakeSubsystem intakeSubsystem;
//
//    public Superstructure(PivotSubsystem pivot,
//                          ExtensionSubsystem extension,
//                          IntakeSubsystem intake) {
//        extensionSubsystem = extension;
//        pivotSubsystem = pivot;
//        intakeSubsystem = intake;
//    }
//
//    @Override
//    public void periodic() {
//
//    }
//
//    @Override
//    public void applyStates() {
//        switch (currentState) {
//            case HOME:
//                home();
//                break;
//            case PREP_INTAKE:
//            case INTAKE:
//                handleIntake();
//                break;
//            case PREP_LOWBUCKET:
//            case LOWBUCKET:
//                handleLowBucket();
//                break;
//            case PREP_HIGHBUCKET:
//            case HIGHBUCKET:
//                handleHighBucket();
//                break;
//            case PREP_HIGHCHAMBER:
//            case HIGHCHAMBER:
//                handleHighChamber();
//                break;
//            case PREP_LOWCHAMBER:
//            case LOWCHAMBER:
//                handleLowChamber();
//                break;
//            case PREP_CLIMB:
//            case CLIMB:
//                handleClimb();
//        }
//    }
//
//
//
//    @Override
//    public void handleStateTransitions() {
//        previousState = currentState;
//        switch (wantedState) {
//            case HOME:
//                currentState = RobotState.HOME;
//                break;
//            case INTAKE:
//                switch (currentState) {
//                    case PREP_INTAKE:
//                    case HOME:
//                        currentState = RobotState.INTAKE;
//                        break;
//                    default:
//                        currentState = RobotState.PREP_INTAKE;
//                        break;
//                }
//            case LOWBUCKET:
//                switch (currentState) {
//                    case HOME:
//                    case PREP_LOWBUCKET:
//                        currentState = RobotState.LOWBUCKET;
//                        break;
//                    default:
//                        currentState = RobotState.PREP_LOWBUCKET;
//                        break;
//                }
//            case HIGHBUCKET:
//                switch (currentState) {
//                    case HOME:
//                    case PREP_HIGHBUCKET:
//                        currentState = RobotState.HIGHBUCKET;
//                        break;
//                    default:
//                        currentState = RobotState.PREP_HIGHBUCKET;
//                        break;
//                }
//            case LOWCHAMBER:
//                switch (currentState) {
//                    case HOME:
//                    case PREP_LOWCHAMBER:
//                        currentState = RobotState.LOWCHAMBER;
//                        break;
//                    default:
//                        currentState = RobotState.HIGHCHAMBER;
//                        break;
//                }
//            case HIGHCHAMBER:
//                switch (currentState) {
//                    case HOME:
//                    case PREP_HIGHCHAMBER:
//                        currentState = RobotState.HIGHCHAMBER;
//                        break;
//                    default:
//                        currentState = RobotState.PREP_HIGHCHAMBER;
//                        break;
//                }
//            case CLIMB:
//                switch (currentState) {
//                    case HOME:
//                    case PREP_CLIMB:
//                        currentState = RobotState.CLIMB;
//                        break;
//                    default:
//                        currentState = RobotState.PREP_CLIMB;
//                        break;
//                }
//        }
//    }
//
//    /*****Checks*****/
//
//    /*****Basic States*****/
//    private void handleIntake() {
//    }
//
//    private void home() {
//
//    }
//
//    /*****Sample States*****/
//
//    private void handleHighBucket() {
//    }
//
//    private void handleLowBucket() {
//    }
//
//    /*****Specimen States*****/
//
//    private void handleLowChamber() {
//    }
//
//    private void handleHighChamber() {
//    }
//
//    /*****Climb States*****/
//
//    private void handleClimb() {
//    }
//
//    /*****Utility States*****/
//
//
//    public void setWantedState(RobotState wantedState) {
//        this.wantedState = wantedState;
//    }
//
//}
