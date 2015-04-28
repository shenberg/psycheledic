package org.psycheledic;

import org.psycheledic.commands.AbstractCommand;
import org.psycheledic.commands.ColorCommand;
import org.psycheledic.commands.OffCommand;

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

        AbstractCommand command = new ColorCommand(255, 0, 0, 50);
        command.setDelay(100);
        command.start();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        command = new OffCommand();
//        command.delay = 500;
//        command.repeatCount = 5;
        command.start();

//        File imagesDir = new File("Images");
//        File f = new File(imagesDir, "colortest.bmp");
//        System.out.println("Showing image: " + f.getAbsolutePath());
//        command = new SendImageCommand(f.getAbsolutePath());
//        command.delay = 1000;
//        command.broadcast();

    }


}
