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

    static void fillColumn(byte[] column, int index, double equalizerHeight) {
        final int HEIGHT = 90;
        for (int i = 0; i < (int)(equalizerHeight*HEIGHT); i++) {
            column[index*HEIGHT*3 + i*3] = (i >= 30 ) ? (byte)127 : 0;
            column[index*HEIGHT*3 + i*3 + 1] = (i < 60 ? (byte)127 : 0);
            column[index*HEIGHT*3 + i*3 + 2] = 0;
        }
        for(int i = (int)(equalizerHeight*HEIGHT); i < HEIGHT; i++) {
            column[index*HEIGHT*3 + i*3] = 0;
            column[index*HEIGHT*3 + i*3 + 1] = 0;
            column[index*HEIGHT*3 + i*3 + 2] = 0;
        }
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
                final int COLUMNS = 5;
                byte[] column = new byte[ImmediateColumnCommand.columnSize()*COLUMNS];
                double equalizerHeight = 0;
                while (true) {
                    double realLevel = level;// - 400;
                    if (realLevel < 0) realLevel = 0;
                    realLevel /= 2000;
                    if (realLevel > 1.0) realLevel = 1.0;
                    double newEqualizerHeight = (equalizerHeight + realLevel) * 0.5;
                    for (int i = 0; i < COLUMNS; i++) {
                        fillColumn(column, i, ((i+1)*newEqualizerHeight + (5-i)*equalizerHeight)/6.0);
                    }
                    equalizerHeight = newEqualizerHeight;
                    (new ImmediateColumnCommand(column, COLUMNS)).start();
                }
        //    }
        //}).start();
    }
}
