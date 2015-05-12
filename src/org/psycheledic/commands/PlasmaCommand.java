package org.psycheledic.commands;

import org.psycheledic.Color;

import static java.lang.Math.max;

/**
 * Created by shenberg on 5/5/15.
 */
public class PlasmaCommand {

    final static int[] sinetable = {
        128,131,134,137,140,143,146,149,152,156,159,162,165,168,171,174,
                176,179,182,185,188,191,193,196,199,201,204,206,209,211,213,216,
                218,220,222,224,226,228,230,232,234,236,237,239,240,242,243,245,
                246,247,248,249,250,251,252,252,253,254,254,255,255,255,255,255,
                255,255,255,255,255,255,254,254,253,252,252,251,250,249,248,247,
                246,245,243,242,240,239,237,236,234,232,230,228,226,224,222,220,
                218,216,213,211,209,206,204,201,199,196,193,191,188,185,182,179,
                176,174,171,168,165,162,159,156,152,149,146,143,140,137,134,131,
                128,124,121,118,115,112,109,106,103,99, 96, 93, 90, 87, 84, 81,
                79, 76, 73, 70, 67, 64, 62, 59, 56, 54, 51, 49, 46, 44, 42, 39,
                37, 35, 33, 31, 29, 27, 25, 23, 21, 19, 18, 16, 15, 13, 12, 10,
                9,  8,  7,  6,  5,  4,  3,  3,  2,  1,  1,  0,  0,  0,  0,  0,
                0,  0,  0,  0,  0,  0,  1,  1,  2,  3,  3,  4,  5,  6,  7,  8,
                9,  10, 12, 13, 15, 16, 18, 19, 21, 23, 25, 27, 29, 31, 33, 35,
                37, 39, 42, 44, 46, 49, 51, 54, 56, 59, 62, 64, 67, 70, 73, 76,
                79, 81, 84, 87, 90, 93, 96, 99, 103,106,109,112,115,118,121,124
    };

    public PlasmaCommand() {
        int columnIndex = 0;
        long t = 0;
        while (true) {
            //byte[] column = new byte[ImmediateColumnCommand.columnSize()];
            final int columnCount = 5;
            byte[] column = new byte[ImmediateColumnCommand.columnSize()*columnCount];

            int width = 1;
            int height = 90;
            //long t = System.currentTimeMillis();
            t += 123;
            for(int x=0; x < width; x++) {
                for(int y=0; y < height; y++) {
                    int val = (sinetable[((int) ((x * 571 + t) / 223)) & 255] + sinetable[((int) ((y * 353 + t) / 27)) & 255] - 255);
                    int val2 = 3*(sinetable[((int) ((x * 153 + t) / 43)) & 255] + sinetable[((int) ((y * 61 + t) / 27)) & 255]) >> 1;
                    //int val = 64*(sin((x*131 + currentTime)/151.f) + sin((y*71 + currentTime)/131.f));
                    //int val2 = 384 + 192*(sin((x*113 - currentTime)/131.f) + sin((y*31 + currentTime)/151.f));
                    //int val3 = 64*(sin((x*11 + getFrameCount())/27.f) + sin((y*11 - getFrameCount())/17.f));
                    //int val = 64*(sin((x*131 + t)/151.f) + sin((y*71 + t)/131.f));
                    //int val2 = 384 + 192*(sin((x*113 - t)/131.f) + sin((y*31 + t)/151.f));
                    int intensity = max(val - 15, 0);
                    int r,g,b;
                    if(val2 < 256) {
                        r = val2 * intensity / 255;
                        g = (255 - val2) * intensity / 255;
                        b = 0;
                    } else if(val2 < 512) {
                        val2 -= 256;
                        r = (255 - val2) * intensity / 255;
                        g = 0;
                        b = val2 * intensity / 255;
                    } else {
                        val2 -= 512;
                        r = 0;
                        g = val2 * intensity / 255;
                        b = (255 - val2) * intensity / 255;
                    }

                    column[columnIndex*ImmediateColumnCommand.columnSize() + y*3] = (byte)r;
                    column[columnIndex*ImmediateColumnCommand.columnSize() + y*3 + 1] = (byte)g;
                    column[columnIndex*ImmediateColumnCommand.columnSize() + y*3 + 2] = (byte)b;
                }
                columnIndex++;
                if (columnIndex == columnCount) {
                    columnIndex = 0;
                    new ImmediateColumnCommand(column, columnCount).start();
                }
            }
        }
    }

    public void start() {
        //TODO:
    }
}