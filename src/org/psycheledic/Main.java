package org.psycheledic;

import org.psycheledic.commands.*;

import java.io.File;

public class Main {

    public static void main(String args[]) {
//        File imagesDir = new File("Images");
//        AbstractCommand command = new ImageFolderCommand(imagesDir);
//        command.start();
//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        command.stop();

//        offDelay(3);
//        AbstractCommand command = new ColorCommand(100, 0, 0, 50);
//        command.setDelay(100);
//        command.start();

//        offDelay(1);
        new ColorFadeCommand(100, 100<<16, 100<<8, 100);
//        File imagesDir = new File("Images");
//        File f = new File(imagesDir, "uv_face2.jpeg");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        command = new SendImageCommand(f.getAbsolutePath());
////        command.delay = 1000;
//        command.start();

        offDelay(3);
    }

    private static void offDelay(int seconds) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AbstractCommand command = new OffCommand();
//        command.delay = 500;
//        command.repeatCount = 5;
        command.start();
    }

}
