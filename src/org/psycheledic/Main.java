package org.psycheledic;

import org.psycheledic.commands.SendImageCommand;


public class Main {

    public static void main(String args[]) {
        new Main().loop();
    }

    private void loop() {
        new SendImageCommand("Images/cards.png");
    }


}
