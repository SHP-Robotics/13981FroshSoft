package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;


// Template created by Chun on 1/26/19 for 10023. Made by Team 13981 on 10/11/19


/*
    Other team: will be moving loading zone
    TODO: - declare variable, set variable to encoder value of motor in order to track distance
          - do 0.2 power for auto drive/mecanum so it will line up properly (it just works with it)

    One camera for blocks (color) [ could also be angled to see ground for loading zone ]
    One camera for what you are holding (is holding / is not holding)

    If no block in hand: (or in stage 0)
        look for black or yellow (block)
    else if block in hand: (or in latter stage)
        look for blue or red (loading zone)

    Stage 0: try to find black block, if cant find, find yellow block and use that
        Action: move forward, then sideways to right (keep track of movement)
        End condition: see block of black/yellow spectrum (stop there)
        - add movement distance to distance from start to calculate distance to loading zone
        Retry condition: if fails to see block, go back to left and pick up yellow block
                         acting like you can't find them AND you have already gotten the two black blocks

    Stage 1:
        Action: Align with block
        End condition: properly aligned (need to test this out)
        Retry condition: keep trying to align

    Stage 2:
        Action: lower the rack and pinion, clamp, raise the rack and pinion
        end condition: (1) rack is up and (2) block is successfully in hand. if not, retry stage 2
            - block should be seen in hand by color sensor
        retry condition: reset rack and try again

    Stage 3: move back and sideways to left back to starting point, then to loading zone
             OR move back and towards computed distance toward loading zone
        Action: movement dependent on loading zone
        End condition: at loading zone (color sensor angled down sees this)
        Retry condition: go back to origin and look from there according to hard coded locations
                         OR back track through steps and try again

    Stage 4:
        Action: drop block in loading zone
        End condition: block no longer in hand

    Stage 5: go back to origin and start again from stage 0 (set stage == 0)
 */
/* bros idea of how it should work:
    mecanum.set_power(1);
    mecanum.go_distance(20);
       if( mecaum.reached_destination) {reset drivers and power of mecanum};

    this is the starting stage:
    set power to 1, distance to 20, and this will loop until it has reached distance
    once reached, internal function sets motor power to 0, and we reset encoders, and we move to next stage
*/
@Autonomous

public class LeftBlocksAutonomous extends BaseRobot {
    private int stage = 0;
    private boolean toggle = false;



    @Override
    public void init() {
        super.init();
    }

    @Override
    public void start() {
        super.start();
    }

    @Override
    public void loop() {
        super.loop();
        switch (stage) {

            // Drive forward from the corner and stop
            case 0:
                if (auto_drive(1, 27)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;

            case 1:
                if (checkBlackColor(colorBlock.red(), colorBlock.blue())) {
                    reset_drive_encoders();
                    stage++;
                }
                else auto_mecanum(-0.3, 47);

                break;
            case 2:
                if (auto_mecanum(-0.3, 3)) {
                    reset_drive_encoders();
                    stage++;
                }

            case 3:
                // adjust the robot to the block
                timer.reset();
                if (auto_drive(0.2, 2)) {
                    setArmClampMotor(1);
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;

            case 4:
                if (timer.seconds() > 1) {
                    if (auto_drive(-0.4, 10)) {
                        reset_drive_encoders();
                        stage++;
                    }
                }
                break;

            case 5:
                if (auto_turn(1, 90)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;

            case 6:
                if (auto_drive(1, 55)) {
                    setArmClampMotor(-1);
                    reset_drive_encoders();
                    timer.reset();
                    stage++;
                }
                break;

            case 7:
                if (timer.seconds() > 2) {
                    if (auto_drive(-1, 15)) {
                        setArmClampMotor(0);
                        reset_drive_encoders();
                        reset_armClampMotor_encoders();
                        reset_armLftMotor_encoders();
                        reset_armLiftMotor2_encoders();
                        stage++;
                    }
                }
                break;
                    default:
                        break;
                }
    }
}
//move sideways and scan
            /*case 1:
                if (auto_turn(0.6, 360)) {
                    reset_drive_encoders();
                    stage++;
                }
                break;*/