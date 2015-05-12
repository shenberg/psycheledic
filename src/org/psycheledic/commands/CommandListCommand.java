package org.psycheledic.commands;

import java.util.Random;

/**
 * Created by user on 13-May-15.
 */
public class CommandListCommand extends AbstractCommand {

    private AbstractCommand curCommand = null;

    private AbstractCommand[] commands;
    private long delay = 5000;

    public CommandListCommand(long delay, AbstractCommand... commands) {
        this.commands = commands;
        this.delay = delay;
    }

    @Override
    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (!stopped) {
                    if (curCommand != null) {
                        try {
                            Thread.sleep(delay);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        curCommand.stop();
                    }
                    curCommand = commands[new Random(System.currentTimeMillis()).nextInt(commands.length)];
                    curCommand.start();
                }
            }
        }).start();
    }

    @Override
    public void stop() {
        if (curCommand != null) {
            curCommand.stop();
        }
    }

    @Override
    public void sendto(String ip) {

    }
}
