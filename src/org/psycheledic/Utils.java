package org.psycheledic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Random;

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
            height = (short) Math.min(height, MAX_HEIGHT);
            width = (short) Math.min(width, MAX_HEIGHT);
        }
        int footer = 0;
        int header = 0;
        if (height<MAX_HEIGHT) {
            header = new Random(System.currentTimeMillis()).nextInt(MAX_HEIGHT-height);
            footer = MAX_HEIGHT-height-header;
            System.out.println("Header Footer " +header + " / " + footer);

        }
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        for (int col = 0; col < width; col++) {
            for (int row = MAX_HEIGHT-1; row >= 0; row--) {
                int color = 0x010101;
                if (row<=MAX_HEIGHT-1-header && row>=footer) {
                    color = img.getRGB(col, (row - footer));
//                    color = 0xff0000;
//                } else {
//                    System.out.println("Sending black for  " + row );

                }
                int R = Math.max(0, ((color >> 16) & 0xff) - BLACK_CORRECTION);
                int G = Math.max(0, ((color >> 8) & 0xff) - BLACK_CORRECTION);
                int B = Math.max(0, (color & 0xff) - BLACK_CORRECTION);
//                System.out.println(R + " / " + G + " / " + B + " / " + bos.size());
                bos.write(R/3);
                bos.write(G/3);
                bos.write(B/3);
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
