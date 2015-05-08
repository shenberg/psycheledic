package org.psycheledic.commands;

import org.psycheledic.Network;

import java.io.File;
import java.util.Random;

/**
 * Created by user on 08-May-15.
 */
public class TotalRandomImageFolderCommand extends ImageFolderCommand {

    private Random r;
    public TotalRandomImageFolderCommand(File folder) {
        super(folder);
        r = new Random(System.currentTimeMillis());
    }

    @Override
    protected void broadcast() {
        int ipIndex = r.nextInt(Network.IPS.length);
        String prevIp = this.ip;
        this.ip = Network.IPS[ipIndex];
        super.broadcast();
        this.ip = prevIp;
    }
}
