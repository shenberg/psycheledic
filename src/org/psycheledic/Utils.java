package org.psycheledic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.image.BufferedImage;
import java.io.*;

public class Utils {

    public static final boolean DEBUG = false;
    public static final int MAX_HEIGHT = 90;
    public static final int BLACK_CORRECTION = 25;

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
        if (height>MAX_HEIGHT || width>MAX_HEIGHT) {
            System.err.println("Error: Image height/width more than 90, using partial image");
            height = (short) Math.max(height, MAX_HEIGHT);
            width = (short) Math.max(width, MAX_HEIGHT);
        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int col = 0; col < width; col++) {
            for (int row = height - 1; row >= 0; row--) {
                int color = img.getRGB(col, row);
                bos.write(Math.max(0, ((color >> 16) & 0xff) - BLACK_CORRECTION));
                bos.write(Math.max(0, ((color >> 8) & 0xff) - BLACK_CORRECTION));
                bos.write(Math.max(0, (color & 0xff) - BLACK_CORRECTION));
            }
        }
        return bos.toByteArray();
    }

    public static void debug(String str) {
        if (DEBUG) {
            System.out.println(str);
        }
    }


    public static void playMP3(String filename) {
        try {
            FileInputStream fis = new FileInputStream(filename);
            new Player(fis).play();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }
}
