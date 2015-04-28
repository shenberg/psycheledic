package org.psycheledic.commands;

public class ColorFadeCommand {

    public ColorFadeCommand(int timing, int... colors) {
        int len = colors.length;
        int fromColor;
        int toColor;
        for (int i = 0; i < len-1; i++) {
            fromColor = colors[i];
            toColor = colors[i+1];

            for (int j=0; j<=100; j++) {
                double t = 0.01*j;
                int R = (int) (((fromColor >> 16) & 0xff)*(1-t) + ((toColor >> 16) & 0xff)*(t));
                int G = (int) (((fromColor >> 8) & 0xff)*(1-t) + ((toColor >> 8) & 0xff)*(t));
                int B = (int) ((fromColor & 0xff)*(1-t) + (toColor & 0xff)*(t));
                new ColorCommand(R, G, B, 1000).start();
                try {
                    Thread.sleep(timing);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

//    private int sendColor(int color) {
//        int R = (color >> 16) & 0xff;
//        int G = (color >> 8) & 0xff;
//        int B = color & 0xff;
//        return 0;
//    }

}
