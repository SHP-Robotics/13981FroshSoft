package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.util.TypeConversion;

import java.util.Vector;

/**
 * Created by CherryPi on 12/29/2017. Adapted by 13981 in November 2019
 */

public class PixyBlockList extends Vector<PixyBlock> {
    public int totalCount = 1;

    PixyBlockList(byte totalCount) {
        this.totalCount = TypeConversion.unsignedByteToInt(totalCount);
    }
}
