package org.psycheledic;

import org.psycheledic.commands.SendImageCommand;

import java.io.File;
import java.util.Random;


public class Main {

    public static void main(String args[]) {
        File imagesDir = new File("Images");
        File[] files = imagesDir.listFiles();
        int count = files.length;
        Random r = new Random(System.currentTimeMillis());

        while (true) {
            File f = files[r.nextInt(count)];
            System.out.println("Showing image: " + f.getAbsolutePath());
            new SendImageCommand(f.getAbsolutePath());
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
