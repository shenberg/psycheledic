package org.psycheledic.commands;

import org.psycheledic.Network;

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
                if (files == null) {
                    return;
                }
                int count = files.length;
                Random r = new Random(System.currentTimeMillis());

                while (!stopped) {
                    for (File f : imagesDir.listFiles()) {

//                    File f = files[r.nextInt(count)];
                    System.out.println("Showing image: " + f.getAbsolutePath());
                    loadImage(f);
//                        setColumnDelay(2);
                        if (ip== Network.BROADCAST_IP) {
                            broadcast();
                        } else {
                            sendto(ip);
                        }
//                        try {
//                            Thread.sleep(1000);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    try {
//                        Thread.sleep(5000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    }
                }
            }
        }).start();
    }
}
