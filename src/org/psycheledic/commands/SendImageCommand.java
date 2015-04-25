package org.psycheledic.commands;

import org.psycheledic.Network;
import org.psycheledic.PacketType;
import org.psycheledic.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;

public class SendImageCommand {

    private BufferedImage mImage;
    private byte[] imageData;

    public SendImageCommand(String filename) {
        try {
            mImage = ImageIO.read(new File(filename));
            convertToByteArray();
            sendPicture();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void convertToByteArray() throws IOException {
        short height = (short) mImage.getHeight();
        short width = (short) mImage.getWidth();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bos);
        for (int col = 0; col < width; col++) {
            for (int row = height - 1; row >= 0; row--) {
                int color = mImage.getRGB(col, row);
                dos.writeByte((color >> 16) & 0xff);
                dos.writeByte((color >> 8) & 0xff);
                dos.writeByte(color & 0xff);
            }
        }
        imageData = bos.toByteArray();
    }


    private void sendPicture() {
        try {
            short height = (short) mImage.getHeight();
            short width = (short) mImage.getWidth();

            // Sending the NEW_PIC command
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            DataOutputStream dos = new DataOutputStream(bos);
            dos.writeByte(PacketType.NEW_PIC.ordinal());
            dos.writeByte(0);
            Utils.writeLittleEndianShort(dos, width);
            Utils.writeLittleEndianShort(dos, height);
            System.out.println("Sending the NEW_PIC command " + width + " / " + height);
            byte[] data = bos.toByteArray();
            if (!Network.get().sendPacket(data, Network.IP)) {
                System.out.println("NEW_PIC failed");
                return;
            }

            // Sending Data
            short offset = 0;
            while (offset < imageData.length) {
                short len = (short) Math.min(Network.MAX_LEN, (short) (imageData.length - offset));
                Utils.debug("Sending data : " + offset + " / " + len + " (out of " + imageData.length + ")");
                bos.reset();
                dos.writeByte(PacketType.DATA.ordinal());
                dos.writeByte(0);
                Utils.writeLittleEndianShort(dos, offset);
                Utils.writeLittleEndianShort(dos, len);
                Utils.writeLittleEndianShort(dos, (short) imageData.length);
                dos.write(imageData, offset, len);
                offset += len;
                data = bos.toByteArray();
                if (!Network.get().sendPacket(data, Network.IP)) {
                    System.out.println("Failed!");
                }
            }

            System.out.println("Sending SHOW_PIC");
            bos.reset();
            dos.writeByte(PacketType.SHOW_PIC.ordinal());
            dos.writeByte(0);
            data = bos.toByteArray();
            if (!Network.get().sendPacket(data, Network.IP)) {
                System.out.println("Failed!");
            }
        } catch (IOException e) {
            // do nothing
        }
    }

}
