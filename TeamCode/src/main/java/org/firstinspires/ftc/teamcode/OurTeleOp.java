package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/*
Created by Chun on 1/26/19 for 10023. Edited by Ben on 10/14/19 for 13981
*/

@TeleOp

public class OurTeleOp extends BaseRobot {

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
        //keep marker_servo up
        set_marker_servo(ConstantVariables.K_MARKER_SERVO_UP);

        //drive train
        tankanum_drive(gamepad1.right_stick_y, gamepad1.left_stick_y, gamepad1.right_stick_x);

        //climber or raise motor
        if(gamepad1.dpad_up) {
            climb(-1);
        } else if (gamepad1.dpad_down) {
            climb(1);
        } else {
            climb(0);
        }

        //slide using gamepad button a and button b
        if(gamepad1.a || gamepad1.dpad_right) {
            slide(-1);
        } else if (gamepad1.b) {
            slide(1);
        } else {
            slide(0);
        }

        //left arm motor servo
        if(gamepad1.left_bumper) {
            set_armLeft_servo(ConstantVariables.K_ARMLEFT_SERVO_IN);
        } else if (gamepad1.right_bumper){
            set_armLeft_servo(ConstantVariables.K_ARMLEFT_SERVO_OUT);
        }

        //gamepad right or left is no longer used. save for future use
        if(gamepad1.dpad_right) {
        } else if (gamepad1.dpad_left) {
        }

        //gamepad x and y is no longer used. save for future use
        if(gamepad1.x) {
        } else if (gamepad1.y) {
        }
    }
}
