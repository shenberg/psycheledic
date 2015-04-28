package org.psycheledic.commands;

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

    protected boolean stopped = false;

    protected AbstractCommand() {
        bos = new ByteArrayOutputStream();
        dos = new DataOutputStream(bos);
    }
    public void start(){
        broadcast();
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

    public void setDelay(int delay){
        this.delay=delay;
    }

    public void setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
    }
}
