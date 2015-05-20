package org.psycheledic.commands;

import org.psycheledic.Main;
import org.psycheledic.Network;
import org.psycheledic.Utils;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public abstract class AbstractCommand {

    protected ByteArrayOutputStream bos;
    protected DataOutputStream dos;

    protected int delay = 0;
    protected int repeatCount = 0; // infinite by default
    public String ip = Network.BROADCAST_IP;

    protected boolean stopped = false;

    protected AbstractCommand() {
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    synchronized (Main.mutex) {
                        Main.mutex.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stop();
            }
        }).start();
    }

    public void start() {
        stopped = false;
        if (ip == Network.BROADCAST_IP) {
            broadcast();
        } else {
            sendto(ip);
        }
    }

    protected void broadcast() {
        //TODO: use broadcast IP
        sendto(Network.BROADCAST_IP);
//        for (String ip : Network.IPS) {
//            sendto(ip);
//        }

    }

    public abstract void sendto(String ip);

    public void stop() {
        stopped = true;
    }

    protected void addTimingParams() throws IOException {
        Utils.writeLittleEndianInt(dos, delay);
        Utils.writeLittleEndianInt(dos, repeatCount);

    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
