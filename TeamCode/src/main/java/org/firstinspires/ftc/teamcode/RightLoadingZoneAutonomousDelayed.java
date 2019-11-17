package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;


@Autonomous


public class RightLoadingZoneAutonomousDelayed extends BaseRobot {
    private int stage;

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

            case 0 :
                if (timer.seconds() >= 14) {
                    if (auto_drive(0.6, 35)) {
                        setArmClampMotor(1);
                        reset_drive_encoders();
                        timer.reset();
                        stage++;
                    }
                }
                break;

            case 1:
                if (timer.seconds() > 1) {
                    if (auto_drive(-0.6, 31)) {
                        setArmClampMotor(-1);
                        reset_drive_encoders();
                        timer.reset();
                        stage++;
                    }
                }
                break;

            case 2:
                if (timer.seconds() > 3) {
                    if (auto_mecanum(0.5, 60)) {
                        setArmClampMotor(0);
                        reset_drive_encoders();
                        stage++;
                    }
                }

                break;

        }
    }
}