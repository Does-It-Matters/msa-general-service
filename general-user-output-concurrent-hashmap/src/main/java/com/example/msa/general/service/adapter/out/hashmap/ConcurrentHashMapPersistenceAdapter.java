package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

@Component
public class ConcurrentHashMapPersistenceAdapter implements GeneralUserDataOutputPort {
    private final ConcurrentHashMap<String, GeneralUserEntity> users;

    public ConcurrentHashMapPersistenceAdapter() {
        users = new ConcurrentHashMap<>();
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        users.put(id, new GeneralUserEntity(email, age));
        System.out.printf("저장된 데이터 (ConcurrentHashMap): 아이디 = %s, 이메일 = %s, 나이 = %d \n", id, email, age);
    }
}
