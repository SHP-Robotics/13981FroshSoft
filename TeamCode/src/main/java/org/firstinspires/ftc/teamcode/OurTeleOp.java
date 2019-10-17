package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

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

    public float getCurveY(float X){
        float listX[] = new float[] {0.0f, 0.10f, 0.20f, 0.30f, 0.4f, 0.50f, 0.60f, 0.70f, 0.80f, 0.9f, 1.0f};
        float listY[] = new float[] {0.0f, 0.15f, 0.18f, 0.25f, 0.5f, 0.70f, 0.85f, 0.90f, 0.95f, 1.0f, 1.0f};
        X = Range.clip(X, 0.0f, 1.0f);
        int i = 0;
        while (i<10){
            if (X >= listX[i] && X < listX[i + 1]) {
                float delta = listX[i +1] - listX[i];
                float coeff = (X - listX[i]) / delta;
                float Y = (coeff * (listY[i + 1] - listY[i])) + listY[i];
                return Y;
            }
            i = i +1;
        }
        return 0; // error if no movement
    }


    @Override
    public void loop() {
        super.loop();
        float outRightStickY = getCurveY(gamepad1.right_stick_y);
        float outLeftStickY = getCurveY(gamepad1.left_stick_y);
        float outRightStickX = getCurveY(gamepad1.right_stick_y);
        //drive train
        tankanum_drive(outRightStickY,outLeftStickY, outRightStickX);

        //lower or raise lift motor
        if(gamepad1.dpad_up) {
            setArmLiftMotor(1);
        } else if (gamepad1.dpad_down) {
            setArmLiftMotor(-1);
        } else {
            setArmLiftMotor(0);
        }

        //slide motor
        if(gamepad1.b || gamepad1.dpad_right) {
            slide(1);
        } else if (gamepad1.a || gamepad1.dpad_left) {
            slide(-1);
        } else {
            slide(0);
        }

        //left arm motor servo
        if(gamepad1.left_bumper) {
            set_armLeft_servo(ConstantVariables.K_ARMLEFT_SERVO_OPEN );
            set_marker_servo(ConstantVariables.K_MARKER_SERVO_OPEN);

        } else if (gamepad1.right_bumper){
            set_armLeft_servo(ConstantVariables.K_ARMLEFT_SERVO_GRAB);
            set_marker_servo(ConstantVariables.K_MARKER_SERVO_GRAB);
        }

        //emergency kill button
        if (gamepad1.start && gamepad1.back) {

        }

    }
}
