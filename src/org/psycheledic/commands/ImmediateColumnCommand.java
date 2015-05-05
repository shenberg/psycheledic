package org.psycheledic.commands;

import org.psycheledic.Network;
import org.psycheledic.PacketType;

import java.io.IOException;

/**
 * Created by shenberg on 5/5/15.
 */
public class ImmediateColumnCommand extends AbstractCommand {
    final static int PIXELS = 90;
    final static int PIXEL_SIZE = 3;

    public static int columnSize() {
        return PIXEL_SIZE*PIXELS;
    }


    public byte[] data;

    public void setColumn(byte[] column) {
        for(int i = 0; i < column.length; i++) {
            data[i + 2] = column[i];
        }
    }

    public ImmediateColumnCommand(byte[] column) {
        data = new byte[PIXELS*PIXEL_SIZE + 2];
        data[0] = (byte)PacketType.IMMEDIATE_COLUMN.ordinal();
        setColumn(column);
    }

    @Override
    public void sendto(String ip) {
        if (!Network.get().sendPacket(data, ip)) {
            System.out.println("COLOR failed");
        }
    }
}
