package org.psycheledic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class Network {

    private static final int PORT = 5000;
    private static final String BROADCAST_IP = "192.168.137.255";
    public static final String[] IPS = {
//            "192.168.137.202",
            "192.168.137.201"
    };
    public static final short MAX_LEN = 1024;

    private DatagramSocket socket;
    private byte seq = 0;

    private Network() {
        try {
            socket = new DatagramSocket(PORT);
            socket.setSoTimeout(2000);
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
            data[1] = seq;

            InetAddress addr = InetAddress.getByName(ip);
            DatagramPacket datagramPacket = new DatagramPacket(data, data.length, addr, PORT);
            socket.send(datagramPacket);
            byte[] recData = new byte[1024];
            DatagramPacket recPacket = new DatagramPacket(recData, 1024);
            socket.receive(recPacket);
            String str = "" + recPacket.getLength() + " / ";
            recData = recPacket.getData();
            for (int i = 0; i < recPacket.getLength(); i++) {
                str += recData[i] + " ";
            }
            Utils.debug(str);
            if (recData[1] == data[1] && recData[0] == PacketType.ACK.ordinal()) {
                return true;
            }
        } catch (IOException e) {
            // do nothing
        } finally {
            seq++;
            seq %= 256;
        }
        return false;
    }

}
