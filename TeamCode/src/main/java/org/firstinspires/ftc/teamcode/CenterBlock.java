package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.Hardware.PixyBlock;
import org.firstinspires.ftc.teamcode.Hardware.PixyBlockList;
import org.firstinspires.ftc.teamcode.Hardware.PixyCam;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Autonomous
public class CenterBlock extends BaseRobot {

    PixyCam pixyCam;
    PixyBlockList blocks1;
    ElapsedTime elapsedTime = new ElapsedTime();
    ElapsedTime elapsedTime2 = new ElapsedTime();
    boolean isCentered = false;
    boolean isCloseEnough = false;
    PixyBlock Block;
    PrintWriter file;


    @Override
    //gets bot away from wall and to starting position.
    public void init() {
        pixyCam = hardwareMap.get(PixyCam.class, "pixycam");
        try {
            file = new PrintWriter("/sdcard/pixyResults.txt", "UTF-8");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loop() {
        // Update every tenth of a second.
        if (elapsedTime.milliseconds() > 100) {
            elapsedTime.reset();
            blocks1 = pixyCam.getBiggestBlocks(1);
            telemetry.addData("Counts", "%d/%d/%d", blocks1.totalCount);
            file.println("----------------------------");
            file.format("Elapsed: %s Counts: %d/%d\n", elapsedTime2.toString());
            /*
            telemetry.addData("X position of Block", Block.x);
            telemetry.addData("Block Width", Block.width);
            telemetry.addData("Block Height", Block.height);

             */
            telemetry.addData("Is Arm Open",armClampOpenSensor.getState());


            if (Block.isEmpty()) {
                telemetry.addData("No Block", 0);

            }
            else {
                //moves the bot to the left.
                if (Block.x > 130) {
                    tankanum_drive(0, 0, 0.2);
                }
                //moves the bot to the right
                if (Block.x < 120) {
                    tankanum_drive(0, 0, -0.2);

                }
                //checks if the bot is centered
                if (Block.x >= 120 && Block.x <= 130) {
                    tankanum_drive(0, 0, 0);
                    telemetry.addData("Centered", 0);
                    isCentered = true;
                }

                //moves bot close enough to block. width and height TBD
                if (Block.width <= 20 && Block.height <= 40) { // values will be optimal with and height
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
}


