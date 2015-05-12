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
    public int columnCount;

    public void setColumns(byte[] columns) {
        for(int i = 0; i < columns.length; i++) {
            data[i + 3] = columns[i];
        }
    }

    public ImmediateColumnCommand(byte[] column) {
        this(column, 1);
    }

    public ImmediateColumnCommand(byte[] columns, int columnCount) {
        data = new byte[PIXELS*PIXEL_SIZE*columnCount + 3];
        this.columnCount = columnCount;
        //data[0] = (byte)PacketType.IMMEDIATE_COLUMN.ordinal();
        data[0] = (byte)PacketType.COLUMN_STREAM.ordinal();
        data[2] = (byte)columnCount;
        setColumns(columns);

    }

    @Override
    public void sendto(String ip) {
        if (!Network.get().sendPacket(data, ip)) {
            System.out.println("COLOR failed");
        }
    }
}
