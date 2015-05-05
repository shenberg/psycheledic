package org.psycheledic.commands;

public class SpeedColorCommand extends ColorCommand {

    private long lastMovement = System.currentTimeMillis();
    public SpeedColorCommand(MouseListener ml) {
        super(0, 0, 0, 0);

        ml.addListener(new MouseListener.MouseMovedListener() {
            @Override
            public void mouseMoved(int delta) {
                int colorSpeed = 2 * Math.abs(delta);
                colorSpeed = Math.min(colorSpeed, 255);
                R = colorSpeed;
                B = 255 - colorSpeed;
                broadcast();
                lastMovement = System.currentTimeMillis();
                System.out.println("Speed color : " + colorSpeed);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!stopped) {
                    if ((System.currentTimeMillis() - lastMovement) > 1000) {
                        if (R!=0 || B!=255) {
                            System.out.println("No movement");
                            R = 0;
                            B = 255;
                            broadcast();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                }
            }
        }).start();
    }

}