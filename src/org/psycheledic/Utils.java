package org.psycheledic;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Utils {

    public static final boolean DEBUG = false;

    private Utils() {
        // Utility class;
    }

    public static void writeLittleEndianInt(DataOutputStream dos, int i) throws IOException {
        dos.writeByte(i & 0xff);
        dos.writeByte((i >> 8) & 0xff);
        dos.writeByte((i >> 16) & 0xff);
        dos.writeByte((i >> 24) & 0xff);
    }
    public static void writeLittleEndianShort(DataOutputStream dos, short s) throws IOException {
        dos.writeByte(s & 0xff);
        dos.writeByte(s >> 8);
    }

    public static byte[] convertImageToByteArray(BufferedImage img) throws IOException {
        short height = (short) img.getHeight();
        short width = (short) img.getWidth();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int col = 0; col < width; col++) {
            for (int row = height - 1; row >= 0; row--) {
                int color = img.getRGB(col, row);
                bos.write(((color >> 16) & 0xff));
                bos.write(((color >> 8) & 0xff));
                bos.write((color & 0xff));
            }
        }
        return bos.toByteArray();
    }

    public static void debug(String str) {
        if (DEBUG) {
            System.out.println(str);
        }
    }
}
