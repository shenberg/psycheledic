package org.psycheledic.commands;

import org.psycheledic.Network;
import org.psycheledic.PacketType;
import org.psycheledic.Utils;

import java.io.IOException;

public class ColorCommand extends AbstractCommand {

    protected int R;
    protected int G;
    protected int B;
    protected int duration;

    public ColorCommand(int R, int G, int B, int duration) {
        this.R = R;
        this.G = G;
        this.B = B;
        this.duration = duration;
    }

    @Override
    public void sendto(String ip) {
        try {
            bos.reset();
            dos.writeByte(PacketType.COLOR.ordinal());
            dos.writeByte(0);
            addTimingParams();
            Utils.writeLittleEndianInt(dos, duration);
            dos.writeByte(R);
            dos.writeByte(G);
            dos.writeByte(B);
            System.out.println("Sending COLOR command " + R + "/" + G +"/" + B + " to " + ip);
            byte[] data = bos.toByteArray();
            if (!Network.get().sendPacket(data, ip)) {
                System.out.println("COLOR failed");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
