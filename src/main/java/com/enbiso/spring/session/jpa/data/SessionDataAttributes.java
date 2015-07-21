package com.enbiso.spring.session.jpa.data;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by FarFLK on 7/21/2015.
 */
public class SessionDataAttributes {
    private Map<String, Object> attributes;

    public SessionDataAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public SessionDataAttributes(byte[] data) {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new ByteArrayInputStream(data));
            attributes = (HashMap)objectInputStream.readObject();
            objectInputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> getAttributes() {
        return attributes;
    }

    public byte[] getBytes() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer;
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(out);
            objectOutputStream.writeObject(attributes);
            buffer = out.toByteArray();
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
        return buffer;
    }
}
