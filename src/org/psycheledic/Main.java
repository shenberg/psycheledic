package org.psycheledic;

import org.psycheledic.commands.*;

import java.io.File;
import java.io.IOException;

public class Main {

    public static final Object mutex = new Object();
    public static void main(String args[]) throws IOException, InterruptedException {
//        initSpeedControl();
//
//
//        MediaPlayer.get().playDir("Music");
//        AbstractCommand command;
//        command = new ColorFadeCommand(10, 20, 100 << 16, 100 << 8, 100);
//        ImageFolderCommand command2 = (ImageFolderCommand) imageFolder("Images");
//        command.start();
//
//        while(true){
//            Thread.sleep(1000);
//        }
//        command.start();
//        Thread.sleep(5000);
//        command.stop();
//        command2.start();
        new CommandListCommand(2*60*1000,
                new TotalRandomImageFolderCommand(new File("ImageSets/Karina")),
                new TotalRandomImageFolderCommand(new File("ImageSets/faces")),
                new TotalRandomImageFolderCommand(new File("ImageSets/cartoon_text")),
                new TotalRandomImageFolderCommand(new File("ImageSets/Fractals")),
                new TotalRandomImageFolderCommand(new File("ImageSets/Mushrooms")),
                new TotalRandomImageFolderCommand(new File("ImageSets/Patterns")),
                new TotalRandomImageFolderCommand(new File("ImageSets/Random_collection")),
                new TotalRandomImageFolderCommand(new File("ImageSets/superheroes")),
                new TotalRandomImageFolderCommand(new File("ImageSets/random2")),
                new TotalRandomImageFolderCommand(new File("ImageSets/watches")),
                new TotalRandomImageFolderCommand(new File("ImageSets/marian"))
                ).start();

//        new CommandListCommand(5000, command, command2).start();
//        Thread.sleep(15000);
//        stopAll();
//        command = new ColorCommand(100, 0, 0, 50);
//        command.setDelay(100);
//        command.ip="192.168.137.201";
//        command.start();

//offDelay(0);


//        MediaPlayer.get().playDirAccel("Sfx");

//        int delay = 1000;
//        while (true) {
//            MediaPlayer.get().playDir("Sfx");
//            Thread.sleep(delay);
//            delay-=10;
//        }


//        MediaPlayer.get().playMP3("Music/Kalimba.mp3");
//        Thread.sleep(2000);
//        MediaPlayer.get().playMP3("Music/Kalimba.mp3");
//        new EqualizerCommand().start();


//
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


        //new PlasmaCommand().start();;
        File imagesDir = new File("ImageSets/faces");
        //TotalRandomImageFolderCommand command = new TotalRandomImageFolderCommand(imagesDir);
        //command.picDelay = 0;
        //command.start();
//        File imagesDir = new File("Images");
//        File f = new File(imagesDir, "fire.png");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        command = new SendImageCommand(f.getAbsolutePath());
////        command.setDelay(1000);
////        command.ip = "192.168.137.205";
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
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        AbstractCommand command = new OffCommand();
        command.start();
    }

    private static void stopAll() {
        System.out.println("Stop all");
        synchronized (mutex) {
            mutex.notifyAll();
        }
    }

    private static void initSpeedControl() {
        MouseListener.get().addListener(new MouseListener.MouseMovedListener() {
            @Override
            public void mouseMoved(int delta) {
                if (delta > 30) {
                    stopAll();
                    imageFile("PaulNew/Pictures/slow.png").start();
                }
            }
        });
    }
    private static AbstractCommand imageFolder(final String folderName) {
        File imagesDir = new File(folderName);
//        ImageFolderCommand command = new ImageFolderCommand(imagesDir);
        ImageFolderCommand command = new TotalRandomImageFolderCommand(imagesDir);

//    command.setRepeatCount(1);
//        command.picDelay = 1000;
//        command.randomOrder = false;

//        command.ip = "192.168.137.201";
        return command;

    }
    private static AbstractCommand imageFile(String filename) {
        File f = new File(filename);
        System.out.println("Showing image: " + f.getAbsolutePath());
        SendImageCommand command = new SendImageCommand(f.getAbsolutePath());
//        command.setColumnDelay(5000);
//        command.ip="10.0.0.70";
//        command.setDelay(1000);
//                command.setRepeatCount(2);
//        command.setColumnDelay(2);
        return command;
    }
}
