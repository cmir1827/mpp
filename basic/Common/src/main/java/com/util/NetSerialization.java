package com.util;

import java.io.*;

/**
 * Created by sergiubulzan on 20/06/2017.
 */
public class NetSerialization {
        public static byte[] serialize(Object obj) throws IOException {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(out);
            os.writeObject(obj);
            return out.toByteArray();
        }
        public static Object deserialize(byte[] data) throws IOException, ClassNotFoundException {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            ObjectInputStream is = new ObjectInputStream(in);
            return is.readObject();
        }
}
