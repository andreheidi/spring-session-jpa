package com.enbiso.spring.session.jpa;


import com.enbiso.spring.session.jpa.data.SessionData;
import com.enbiso.spring.session.jpa.data.SessionDataAttributes;
import com.enbiso.spring.session.jpa.data.SessionDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.session.ExpiringSession;
import org.springframework.session.MapSession;
import org.springframework.session.SessionRepository;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

public class JpaSessionRepository implements SessionRepository<ExpiringSession>{

    @Value("${spring.session-timeout}")
    private Integer maxInactiveInterval;

    @Autowired
    private SessionDataRepository dataRepository;

    @Override
    public ExpiringSession createSession() {
        ExpiringSession result = new MapSession();
        if(maxInactiveInterval != null) {
            result.setMaxInactiveIntervalInSeconds(maxInactiveInterval);
        }
        return result;
    }

    @Transactional
    @Override
    public void save(ExpiringSession session) {
        SessionData sessionData = new SessionData();
        sessionData.setId(session.getId());
        sessionData.setLastAccessedTime(session.getLastAccessedTime());
        sessionData.setCreationTime(session.getCreationTime());
        Map<String, Object> attributes = new HashMap<String, Object>();
        for (String attrName : session.getAttributeNames()) {
            attributes.put(attrName, session.getAttribute(attrName));
        }
        SessionDataAttributes dataAttributes = new SessionDataAttributes(attributes);
        sessionData.setData(dataAttributes.getBytes());
        dataRepository.save(sessionData);
    }

    @Transactional
    @Override
    public ExpiringSession getSession(String id) {
        SessionData sessionData = dataRepository.findOne(id);
        MapSession mapSession = new MapSession();
        mapSession.setId(sessionData.getId());
        mapSession.setLastAccessedTime(sessionData.getLastAccessedTime());
        mapSession.setCreationTime(sessionData.getCreationTime());

        SessionDataAttributes dataAttributes = new SessionDataAttributes(sessionData.getData());
        for (Map.Entry<String, Object> attribute : dataAttributes.getAttributes().entrySet()) {
            mapSession.setAttribute(attribute.getKey(), attribute.getValue());
        }
        return mapSession;
    }

    @Override
    public void delete(String id) {
        dataRepository.delete(id);
    }
}
