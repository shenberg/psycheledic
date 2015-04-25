package org.psycheledic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Network {

    private static final int PORT = 5000;
    public static final String IP = "10.0.0.69";
    public static final short MAX_LEN = 1024;

    private DatagramSocket socket;
    private byte seq = 0;

    private Network() {
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
    private static Network _instance;

    public static Network get() {
        if (_instance==null) {
            _instance = new Network();
        }
        return _instance;
    }

    public boolean sendPacket(byte[] data, String ip) {
        try {
            data[1] = seq++;
            InetAddress addr = InetAddress.getByName(ip);
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, addr, PORT);
            socket.send(datagramPacket);
            byte[] recData = new byte[1024];
            DatagramPacket recPacket = new DatagramPacket(recData, 1024);
            socket.receive(recPacket);
            System.out.print("" + recPacket.getLength() + " / ");
            recData = recPacket.getData();
            for (int i = 0; i < recPacket.getLength(); i++) {
                System.out.print(recData[i] + " ");
            }
            System.out.print(" /// ");
            if (recData[1] == data[1]) {
                return true;
            }
        } catch (IOException e) {
            // do nothing
        }
        return false;
    }

}
