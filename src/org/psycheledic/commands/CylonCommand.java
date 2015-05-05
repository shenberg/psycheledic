package org.psycheledic.commands;

import org.psycheledic.Color;

/**
 * Created by shenberg on 5/5/15.
 */
public class CylonCommand {
    public CylonCommand() {
        double[] shape = {-1.001, -0.25, -0.04, 0, 0.04, 0.25, 1.001};
        Color[] colors = {
                new Color(0,0,0),
                new Color(0,0,0),
                new Color(127,0,0),
                new Color(127,127,127),
                new Color(127,0,0),
                new Color(0,0,0),
                new Color(0,0,0)
        };


        byte[] column = new byte[ImmediateColumnCommand.columnSize()];

        for (int i = 0; i < 600; i++) {
            double positionBase = - Math.abs(1.f - i / 300.f);
            for (int pixel = 0; pixel < 90; pixel++) {
                double position = positionBase + pixel/90.f;
                // find segment and position in segment
                int endIndex = 0;
                //TODO: error checking
                while (shape[endIndex] < position) {
                    endIndex++;
                }
                double segmentLength = shape[endIndex] - shape[endIndex-1];
                float t = (float)((position - shape[endIndex-1]) / segmentLength);
                Color color = colors[endIndex-1].lerp(colors[endIndex], t);

                column[pixel*3] = (byte)color.r;
                column[pixel*3 + 1] = (byte)color.g;
                column[pixel*3 + 2] = (byte)color.b;
            }
            new ImmediateColumnCommand(column).start();
        }
    }
}
