package org.psycheledic.commands;

import org.psycheledic.Network;
import org.psycheledic.PacketType;
import org.psycheledic.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class SendImageCommand extends AbstractCommand {

    protected BufferedImage mImage;
    private byte[] imageData;
    private int columnDelay = 0;

    protected SendImageCommand() {
    }

    public SendImageCommand(String filename) {
        this(new File(filename));
    }
    public SendImageCommand(File f) {
        loadImage(f);
    }

    protected void loadImage(File f) {
        try {
            mImage = ImageIO.read(f);
            imageData = Utils.convertImageToByteArray(mImage);
        } catch (IOException e) {
            System.out.println("failed to load image");
            e.printStackTrace();
            mImage = null;
            imageData = null;
        }
    }


    @Override
    public void sendto(String ip) {
        sendPicture(ip);
    }

    private void sendPicture(String ip) {
        try {
            long pre = System.currentTimeMillis();
            short height = (short) mImage.getHeight();
            short width = (short) mImage.getWidth();

            // Sending the NEW_PIC command
            bos.reset();
            dos.writeByte(PacketType.NEW_PIC.ordinal());
            dos.writeByte(0);
            Utils.writeLittleEndianShort(dos, width);
            Utils.writeLittleEndianShort(dos, (short) Utils.MAX_HEIGHT);
            System.out.println("Sending the NEW_PIC command " + width + " / " + height + " to " + ip);
            byte[] data = bos.toByteArray();
            if (!Network.get().sendPacket(data, ip)) {
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
                if (!Network.get().sendPacket(data, ip)) {
                    System.out.println("Failed!");
                }
            }

            // Sending SHOW_PIC
            System.out.println("Sending SHOW_PIC to " + ip);
            bos.reset();
            dos.writeByte(PacketType.SHOW_PIC.ordinal());
            dos.writeByte(0);
            addTimingParams();
            Utils.writeLittleEndianInt(dos, columnDelay);
            data = bos.toByteArray();
            if (!Network.get().sendPacket(data, ip)) {
                System.out.println("Failed!");
            }
            System.out.println ("Mlliseconds " + (System.currentTimeMillis()-pre));
        } catch (IOException e) {
            // do nothing
        }
    }

    public void setColumnDelay(int columnDelay) {
        this.columnDelay = columnDelay;
    }

//    private static final int[] gammaRed = {
//            0, 3, 5, 9, 11, 15, 17, 19, 23, 27, 29, 31, 35, 37, 41, 45, 47, 51, 53, 57, 59, 63, 65, 69, 73, 75, 79, 81, 85, 87, 91, 93, 97, 101, 103, 107, 111, 115, 117, 121, 125, 127, 131, 135, 139, 141, 145, 149, 153, 157, 159, 163, 167, 171, 175, 177, 181, 185, 189, 193, 197, 201, 203, 207
//    };
//
//    private static final int[] gammaGreen = {
//            0, 1, 5, 7, 9, 13, 15, 17, 21, 23, 25, 27, 31, 33, 35, 39, 41, 43, 47, 49, 51, 55, 57, 59, 63, 65, 69, 71, 73, 77, 79, 81, 85, 87, 91, 93, 95, 99, 101, 105, 107, 109, 113, 115, 119, 121, 125, 127, 131, 133, 137, 139, 143, 145, 149, 151, 155, 157, 159, 163, 167, 169, 173, 175
//    };
//
//    private static final int[] gammaBlue = {
//            0, 3, 7, 11, 15, 17, 21, 25, 29, 33, 35, 39, 43, 47, 51, 55, 57, 61, 65, 69, 73, 77, 81, 85, 89, 93, 97, 101, 105, 109, 113, 117, 121, 125, 131, 135, 139, 143, 147, 151, 155, 159, 163, 169, 173, 177, 181, 185, 191, 195, 199, 203, 209, 213, 217, 221, 225, 231, 235, 241, 245, 249, 253, 255
//    };
//
//    private void writeRGB(DataOutputStream dos, int color) throws IOException {
//        // Gamma correction
//        dos.writeByte(gammaRed[((color >> 16) & 0xff) / 4]);
//        dos.writeByte(gammaGreen[((color >> 8) & 0xff) / 4]);
//        dos.writeByte(gammaBlue[(color & 0xff) / 4]);
//
//    }
//
//    private static byte gammaCorrect(byte b) {
//        double f = b / 255.0;
//
//        return (byte) (Math.pow(f, 1.5) * 255);
//    }

}
