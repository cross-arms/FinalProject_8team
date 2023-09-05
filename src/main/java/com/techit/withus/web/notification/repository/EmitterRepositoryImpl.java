package com.techit.withus.web.notification.repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Repository
public class EmitterRepositoryImpl implements EmitterRepository{
    private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();
    private final Map<String ,Object> eventCache = new ConcurrentHashMap<>();

    public SseEmitter save(String emitterId, SseEmitter sseEmitter){
        emitters.put(emitterId, sseEmitter);
        return sseEmitter;
    }

    public void saveEventCache(String eventCacheId, Object event){
        eventCache.put(eventCacheId, event);
    }

    public Map<String, SseEmitter> findAllEmitterStartWithMemberId(String memberId){
        return emitters
            .entrySet()
            .stream()
            .filter(emitter -> emitter.getKey().startsWith(memberId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<String, Object> findAllEventCacheStartWithMemberId(String memberId){
        return eventCache.entrySet().stream().filter(entry -> entry.getKey().startsWith(memberId))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void deleteById(String id){
        emitters.remove(id);
    }

    public void deleteAllEmitterStartWithId(String memberId){
        emitters.forEach((key, value) -> {
            if(key.startsWith(memberId)){
                emitters.remove(key);
            }
        });
    }

    public void deleteAllEventCacheStartWithId(String memberId){
        emitters.forEach((key, value) -> {
            if(key.startsWith(memberId)){
                emitters.remove(key);
            }
        });
    }
}
