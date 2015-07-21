package com.enbiso.spring.session.jpa.data;

import javax.persistence.*;

@Entity
@Table(name = "session_entity")
public class SessionData {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "creation_time")
    private Long creationTime;

    @Column(name = "last_access_time")
    private Long lastAccessedTime;

    @Lob
    @Column(name = "data", length = 100000)
    private byte[] data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(long creationTime) {
        this.creationTime = creationTime;
    }

    public long getLastAccessedTime() {
        return lastAccessedTime;
    }

    public void setLastAccessedTime(long lastAccessedTime) {
        this.lastAccessedTime = lastAccessedTime;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
