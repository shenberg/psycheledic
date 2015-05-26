package org.psycheledic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioListener;
import javazoom.jl.player.Player;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created by user on 08-May-15.
 */
public class MediaPlayer {

    private static MediaPlayer _instance;
    private Thread mPlayerThread;
    private Player mPlayer;
    private volatile AudioListener mListener;

    private MediaPlayer() {
    }

    public static MediaPlayer get() {
        if (_instance == null) {
            _instance = new MediaPlayer();
        }
        return _instance;
    }
    public synchronized void setListener(AudioListener listener) {
        mListener = listener;
        if (mPlayer != null) {
            mPlayer.setListener(listener);
        }
    }

    public void playDir(final String dirName) {
        if (mPlayerThread != null) {
            mPlayerThread.interrupt();
        }
        if (mPlayer != null) {
            mPlayer.close();
        }
        mPlayerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                File dir = new File(dirName);
                for (File f : dir.listFiles()) {
                    try {
                        FileInputStream fis = new FileInputStream(f);
                        System.out.println("Playing file: "+ f.getAbsolutePath());
                        mPlayer = new Player(fis, null, mListener);
                        mPlayer.play();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (JavaLayerException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
        mPlayerThread.start();
    }

    public void playMP3(final String filename) {
        if (mPlayerThread != null) {
            mPlayerThread.interrupt();
        }
        if (mPlayer != null) {
            mPlayer.close();
        }
        mPlayerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    FileInputStream fis = new FileInputStream(filename);
                    System.out.println("Playing file: "+ filename);
                    mPlayer = new Player(fis, null, mListener);
                    mPlayer.play();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (JavaLayerException e) {
                    e.printStackTrace();
                }
            }
        });
        mPlayerThread.start();

    }

    public void playDirAccel(final String dirName) {
        if (mPlayerThread != null) {
            mPlayerThread.interrupt();
        }
        if (mPlayer != null) {
            mPlayer.close();
        }
        mPlayerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                int delay = 1000;
                File dir = new File(dirName);
                while (true) {
                    for (File f : dir.listFiles()) {
                        try {
                            FileInputStream fis = new FileInputStream(f);
                            System.out.println("Playing file: " + f.getAbsolutePath());
                            mPlayer = new Player(fis);
                            mPlayer.play();
                            Thread.sleep(delay);
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                        } catch (JavaLayerException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
                    delay-=50;
                    if (delay<0 )
                        delay = 0;
                    System.out.println("ticking delay " + delay);
                }
            }
        });
        mPlayerThread.start();

    }

}
