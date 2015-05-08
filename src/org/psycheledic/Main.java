package org.psycheledic;

import org.psycheledic.commands.*;

import java.io.*;

public class Main {

    public static void main(String args[]) throws IOException {
//        AbstractCommand command;
          new ColorFadeCommand(10, 20, 100<<16, 100<<8, 100);
//offDelay(0);
//        File imagesDir = new File("AtariPlaneBoxesCOLOREDslow2");
//        ImageFolderCommand command = new ImageFolderCommand(imagesDir);
//
////        command.ip = "192.168.137.201";
//        command.start();


//        File imagesDir = new File("Images");
//        File f = new File(imagesDir, "Mushroom_wallpaper.jpg");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        SendImageCommand command = new SendImageCommand(f.getAbsolutePath());
////        command.ip="192.168.137.209";
////        command.setDelay(1000);
////                command.setRepeatCount(2);
//        command.setColumnDelay(2);
//        command.start();



//        MediaPlayer.get().playDir("Music");
//
//        MouseListener ml = new MouseListener();
//        ml.addListener(new MouseListener.MouseMovedListener() {
//            int R = 128;
//            int B = 128;
//
//            @Override
//            public void mouseMoved(int delta) {
//                R+=delta;
//                if (R<0) R=0;
//                if (R>255) R = 255;
//                B-=delta;
//                if (B<0) B=0;
//                if (B>255) B = 255;
//                new ColorCommand(R, 0, B, 0).start();
//            }
//        });
//        new SpeedColorCommand(ml);
////
//        while (true) {
//        new CylonCommand();
//
//        }



//        try {
//            Thread.sleep(30000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        command.stop();

//        offDelay(3);
//        command = new ColorCommand(100, 0, 0, 50);
//        command.setDelay(100);
//        command.start();
//
//        offDelay(1);
//        Thread.sleep(5000);




//        File imagesDir = new File("superheroes");
//        command = new ImageFolderCommand(imagesDir);
//         command.start();
//        File imagesDir = new File("UpDownColors");
//        File f = new File(imagesDir, "LaLinea5.png");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        command = new SendImageCommand(f.getAbsolutePath());
////        command.setDelay(1000);
//        command.ip = "192.168.137.205";
//        command.start();
////
////        try {
////            Thread.sleep(5000);
////        } catch (InterruptedException e) {
////            e.printStackTrace();
////        }
//
//        f = new File(imagesDir, "midburn.jpg");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        command = new SendImageCommand(f.getAbsolutePath());
////        command.setDelay(1000);
////        command.setRepeatCount(2);
//        command.start();

//        offDelay(5);
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
