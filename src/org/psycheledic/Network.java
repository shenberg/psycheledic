package org.psycheledic;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashSet;

public class Network {

    private static final int PORT = 5000;
    public static final String BROADCAST_IP = "255.255.255.255";
    public static final String[] IPS = {
            "192.168.137.201",
            "192.168.137.202",
            "192.168.137.203",
            "192.168.137.204",
            "192.168.137.205",
            "192.168.137.206",
            "192.168.137.207"
//            "192.168.137.209"
//            "10.0.0.70"
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
            if (ip.equals(Network.BROADCAST_IP)) {
                HashSet<String> acks = new HashSet<String>();
                while (acks.size() < Network.IPS.length) {
                    String ackIp = getAck(data[1]);
                    Utils.debug("Got ack for " + data[1] + " from " + ackIp);
                    if (ackIp!=null) {
                        acks.add(ackIp);
                    }
                }
                return true;
            } else {
                return (("/"+ip).equalsIgnoreCase(getAck(data[1])));
            }
        } catch (IOException e) {
            // do nothing
        } finally {
            seq++;
            seq %= 256;
        }
        return false;
    }

    private String getAck(byte seq) throws IOException {
        byte[] recData = new byte[1024];
        DatagramPacket recPacket = new DatagramPacket(recData, 1024);
        socket.receive(recPacket);
        recData = recPacket.getData();
        if (Utils.DEBUG) {
            String str = "" + recPacket.getLength() + " / ";
            for (int i = 0; i < recPacket.getLength(); i++) {
                str += recData[i] + " ";
            }
            Utils.debug(str);
        }
        if (recData[1] == seq && recData[0] == PacketType.ACK.ordinal()) {
            return recPacket.getAddress().toString();
        }
        return null;
    }
}
