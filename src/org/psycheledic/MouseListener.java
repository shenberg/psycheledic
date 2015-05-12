package org.psycheledic;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class MouseListener {

    private boolean stopped = false;
    private List<MouseMovedListener> mListeners;

    private static MouseListener _instance;
    public static MouseListener get() {
        if (_instance == null) {
            _instance = new MouseListener();
        }
        return _instance;
    }

    private MouseListener() {
        mListeners = new LinkedList<MouseMovedListener>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    File f = new File("/dev/input/mice");
                    FileInputStream fis = new FileInputStream(f);
                    BufferedInputStream bis = new BufferedInputStream(fis);
                    int count = 0;
                    int deltaX = 0;
                    while (!stopped) {
                        int status = bis.read();
                        byte x = (byte) bis.read();
                        byte y = (byte) bis.read();
                        System.out.println("Mouse: " + status + " / " + x + " / " + y);
                        count++;
                        deltaX += y;
                        if (count == 10) {
                            for (MouseMovedListener l : mListeners) {
                                l.mouseMoved(deltaX / 10);
                            }
                            count = 0;
                            deltaX = 0;
                        }
                        Thread.sleep(10);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    public void addListener(MouseMovedListener mml) {
        mListeners.add(mml);
    }

    public void removeListener(MouseMovedListener mml) {
        mListeners.remove(mml);
    }

    public void stop() {
        stopped = true;
    }

    public interface MouseMovedListener {
        public void mouseMoved(int delta);
    }
}
