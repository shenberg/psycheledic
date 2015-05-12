package org.psycheledic.commands;

import org.psycheledic.Network;

import java.io.File;
import java.util.Random;

public class SmallImageFolderCommand extends SendImageCommand {

    private File imagesDir;
    public long picDelay = 500;
    public boolean randomOrder = true;

    public SmallImageFolderCommand(File folder) {
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
                    if (randomOrder) {
                        File f = files[r.nextInt(count)];
                        showImage(f);
                    } else {
                        for (File f : imagesDir.listFiles()) {
                            showImage(f);
                        }
                    }
                }
            }
        }).start();
    }

    private void showImage(File f) {
        System.out.println("Showing image: " + f.getAbsolutePath());
        loadImage(f);
//                        setColumnDelay(2);
        if (ip == Network.BROADCAST_IP) {
            broadcast();
        } else {
            sendto(ip);
        }
        try {
            Thread.sleep(picDelay);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
