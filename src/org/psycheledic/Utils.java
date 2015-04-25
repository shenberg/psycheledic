package org.psycheledic;

import java.io.DataOutputStream;
import java.io.IOException;

public class Utils {

    public static final boolean DEBUG = false;

    private Utils() {
        // Utility class;
    }

    public static void writeLittleEndianShort(DataOutputStream dos, short s) throws IOException {
        dos.writeByte(s & 0xff);
        dos.writeByte(s >> 8);
    }

    public static void debug(String str) {
        if (DEBUG) {
            System.out.println(str);
        }
    }
}
