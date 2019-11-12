package org.firstinspires.ftc.teamcode;
import org.firstinspires.ftc.teamcode.BaseRobot;
import org.firstinspires.ftc.teamcode.Hardware.PixyBlock;
import org.firstinspires.ftc.teamcode.Hardware.PixyBlockList;
import org.firstinspires.ftc.teamcode.Hardware.PixyCam;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Source code brought in from:
 * https://github.com/Overlake-FTC-7330-2017/ftc_app/blob/TeamCode/TeamCode/src/main/java/org/firstinspires/ftc/teamcode/testing/TestPixyCam.java
 * on 2017-10-19
 */

@Autonomous(name = "OFFICIAL_TESTING", group = "Testing")
public class OFFICIAL_TESTING extends OpMode {
    PixyCam pixyCam;
    PixyBlockList blocks1;
    ElapsedTime elapsedTime = new ElapsedTime();
    ElapsedTime elapsedTime2 = new ElapsedTime();
    boolean isCentered = false;
    boolean isCloseEnough = false;
    PixyBlock block;
    public DigitalChannel armClampOpenSensor;
    public DcMotor leftBackDriveMotor, rightBackDriveMotor, leftFrontDriveMotor, rightFrontDriveMotor, armLiftMotor, armLiftMotor2, armClampMotor;

    PrintWriter file;

    /*
     * Code to run when the op mode is first enabled goes here
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
     */
    @Override
    public void init() {
        armClampOpenSensor = hardwareMap.get(DigitalChannel.class,"armClampOpenSensor");
        leftBackDriveMotor = hardwareMap.get(DcMotor.class, "leftBackDriveMotor");
        rightBackDriveMotor = hardwareMap.get(DcMotor.class, "rightBackDriveMotor");
        leftFrontDriveMotor = hardwareMap.get(DcMotor.class, "leftFrontDriveMotor");
        rightFrontDriveMotor = hardwareMap.get(DcMotor.class, "rightFrontDriveMotor");
        armLiftMotor = hardwareMap.get(DcMotor.class, "armLiftMotor");
        armLiftMotor2 = hardwareMap.get(DcMotor.class, "armLiftMotor2");
        armClampMotor = hardwareMap.get(DcMotor.class, "armClampMotor");
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
        try {
            file = new PrintWriter("/sdcard/pixyResults.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    /*
     * This method will be called repeatedly in a loop
     * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#loop()
     */
    @Override
    public void loop() {
        // Update every tenth of a second.
        if (elapsedTime.milliseconds() > 100) {
            elapsedTime.reset();
            blocks1 = pixyCam.getBiggestBlocks(1);

            telemetry.addData("Counts", "%d/%d/%d", blocks1.totalCount);
            file.println("----------------------------");
            file.format("Elapsed: %s Counts: %d/%d\n", elapsedTime2.toString());
            for (int i = 0; i < blocks1.size(); i++) {
                PixyBlock block = blocks1.get(i);
                if (!block.isEmpty()) {
                    telemetry.addData("Block 1[" + i + "]", block.toString());
                }
            }
            telemetry.update();
        }
    }

    public void loopCode() {
        // Update every tenth of a second.
        if (elapsedTime.milliseconds() > 100) {
            elapsedTime.reset();
            blocks1 = pixyCam.getBiggestBlocks(1);
            telemetry.addData("Is Arm Open",armClampOpenSensor.getState());


            if (block.isEmpty()) {
                telemetry.addData("No Block", 0);

            }
            else {
                //moves the bot to the left.
                if (block.x > 130) {
                    tankanum_drive(0, 0, 0.2);
                }
                //moves the bot to the right
                if (block.x < 120) {
                    tankanum_drive(0, 0, -0.2);

                }
                //checks if the bot is centered
                if (block.x >= 120 && block.x <= 130) {
                    tankanum_drive(0, 0, 0);
                    telemetry.addData("Centered", 0);
                    isCentered = true;
                }

                //moves bot close enough to block. width and height TBD
                if (block.width <= 20 && block.height <= 40) { // values will be optimal with and height
                    tankanum_drive(.25, .25, 0);
                } else {
                    isCloseEnough = true;
                }


                if (isCentered && isCloseEnough) {

                    if (!isArmClampMotorOpen()) {
                        setArmClampMotor(-1);
                    } else {
                        setArmClampMotor(1);
                    }


                }
            }

        }
    }
    public void tankanum_drive(double rightPwr, double leftPwr, double lateralpwr) {
        rightPwr *= -1;

        double leftFrontPower = Range.clip(leftPwr - lateralpwr, -1.0, 1.0);
        double leftBackPower = Range.clip(leftPwr + lateralpwr, -1.0, 1.0);
        double rightFrontPower = Range.clip(rightPwr - lateralpwr, -1.0, 1.0);
        double rightBackPower = Range.clip(rightPwr + lateralpwr, -1.0, 1.0);

        leftFrontDriveMotor.setPower(leftFrontPower);
        leftBackDriveMotor.setPower(leftBackPower);
        rightFrontDriveMotor.setPower(rightFrontPower);
        rightBackDriveMotor.setPower(rightBackPower);
    }

    public boolean isArmClampMotorOpen(){
        return armClampOpenSensor.getState();
    }


    public void setArmClampMotor(double power) {
        double speed = Range.clip(power, -1, 1);
        armClampMotor.setPower(speed);

    }

    @Override
    public void stop() {
        file.close();
    }

}