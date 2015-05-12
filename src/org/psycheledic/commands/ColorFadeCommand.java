package org.psycheledic.commands;

import org.psycheledic.Network;
import org.psycheledic.Utils;

public class ColorFadeCommand extends AbstractCommand {

    private static final double FADE_SPEED = 200;

    private int offsetMillies;
    private int[] colors;
    private int timing;
    public ColorFadeCommand(final int timing, final int offsetMillies,  final int... colors) {

        this.offsetMillies = offsetMillies;
        this.colors = colors;
        this.timing = timing;
    }

    @Override
    public void start() {
        if (offsetMillies==0) {
            startThread(Network.BROADCAST_IP, timing, colors);
        } else {
            for (String ip : Network.IPS) {
                startThread(ip, timing, colors);
                try {
                    Thread.sleep(offsetMillies);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void startThread(final String ip, final int timing,  final int... colors) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                int len = colors.length;
                int fromColor;
                int toColor;
                int i = 0;
                while(!stopped) {
                    fromColor = colors[i];
                    i = (i+1) % len ;
                    toColor = colors[i];

                    long pre = System.currentTimeMillis();
                    for (int j=0; j<=FADE_SPEED; j++) {
                        if (stopped) {
                            break;
                        }
                        double t = 1/FADE_SPEED*j;
                        int R = (int) (((fromColor >> 16) & 0xff)*(1-t) + ((toColor >> 16) & 0xff)*(t));
                        int G = (int) (((fromColor >> 8) & 0xff)*(1-t) + ((toColor >> 8) & 0xff)*(t));
                        int B = (int) ((fromColor & 0xff)*(1-t) + (toColor & 0xff)*(t));
                        ColorCommand command = new ColorCommand(R, G, B, 1000);
                                command.ip = ip;
                                command.start();
                        try {
                            Thread.sleep(timing);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Utils.debug("Time for full color: " + (System.currentTimeMillis() - pre));
                }
            }
        }).start();

    }

    @Override
    public void sendto(String ip) {
    }

    //    private int sendColor(int color) {
//        int R = (color >> 16) & 0xff;
//        int G = (color >> 8) & 0xff;
//        int B = color & 0xff;
//        return 0;
//    }

}
