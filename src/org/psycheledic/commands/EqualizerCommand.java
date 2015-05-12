package org.psycheledic.commands;

import javazoom.jl.player.AudioListener;
import org.psycheledic.MediaPlayer;

/**
 * Created by shenberg on 5/12/15.
 */
public class EqualizerCommand {

    private volatile double level = 0;

    synchronized void setLevel(double level) {
        this.level = level;
    }
    public void start() {
        MediaPlayer.get().setListener(new AudioListener() {
            @Override
            public void GotSamples(short[] shorts, int len) {
                // calculate sample's total energy
                long total = 0;
                for (int i = 0; i < len; i++) {
                    total += (shorts[i]*shorts[i])/65536;
                }
                setLevel(Math.sqrt(total));
                System.out.println(Math.sqrt(total));
            }
        });
        /*new Thread(new Runnable() {
            @Override
            public void run() {*/
                final int HEIGHT = 90;
                byte[] column = new byte[ImmediateColumnCommand.columnSize()];
                double equalizerHeight = 0;
                while (true) {
                    double realLevel = level;// - 400;
                    if (realLevel < 0) realLevel = 0;
                    realLevel /= 2000;
                    if (realLevel > 1.0) realLevel = 1.0;
                    equalizerHeight = (equalizerHeight + realLevel) * 0.5;
                    for (int i = 0; i < (int)(equalizerHeight*HEIGHT); i++) {
                        column[i*3] = 50;
                        column[i*3 + 1] = 120;
                        column[i*3 + 2] = 30;
                    }
                    for(int i = (int)(equalizerHeight*HEIGHT); i < HEIGHT; i++) {
                        column[i*3] = 0;
                        column[i*3 + 1] = 0;
                        column[i*3 + 2] = 0;
                    }
                    (new ImmediateColumnCommand(column, 1)).start();
                }
        //    }
        //}).start();
    }
}
