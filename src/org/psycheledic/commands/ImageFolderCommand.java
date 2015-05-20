package org.psycheledic.commands;

import org.psycheledic.Network;

import javax.imageio.IIOException;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class ImageFolderCommand extends SendImageCommand {

    private File imagesDir;
    public long picDelay = 500;
    public boolean randomOrder = true;

    public ImageFolderCommand(File folder) {
        imagesDir = folder;
    }

    private static ArrayList<File> fileList(File folder) {
        File[] files = folder.listFiles();
        if (files == null) {
            return null;
        }

        ArrayList<File> fileList = new ArrayList<File>(files.length);
        for(File file : files) {
            if (file.isFile()) {
                fileList.add(file);
            }
        }
        return fileList;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ArrayList<File> files = fileList(imagesDir);
                int count = files.size();
                Random r = new Random(System.currentTimeMillis());

                while (!stopped) {
                    if (randomOrder) {
                        File f = files.get(r.nextInt(files.size()));
                        showImage(f);
                    } else {
                        for (File f : fileList(imagesDir)) {
                            showImage(f);
                        }
                    }
                }
            }
        }).start();
    }

    private void showImage(File f) {
        if (stopped) {
            return;
        }
        System.out.println("Showing image: " + f.getAbsolutePath());
        loadImage(f);
        if (mImage == null) {
            return;
        }
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
