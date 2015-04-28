package org.psycheledic.commands;

import java.io.File;
import java.util.Random;

public class ImageFolderCommand extends SendImageCommand {

    private File imagesDir;

    public ImageFolderCommand(File folder) {
        imagesDir = folder;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                File[] files = imagesDir.listFiles();
                if (files==null) {
                    return;
                }
                int count = files.length;
                Random r = new Random(System.currentTimeMillis());

                while (!stopped) {
                    File f = files[r.nextInt(count)];
                    System.out.println("Showing image: " + f.getAbsolutePath());
                    loadImage(f);

                    broadcast();
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
