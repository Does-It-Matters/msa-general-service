package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDTO;
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
        users.put(id, new GeneralUserEntity(id, email, age));
        System.out.printf("저장된 데이터 (ConcurrentHashMap): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
    }

    @Override
    public GeneralUserDTO getUser(String id) {
        GeneralUserDTO user = users.get(id);
        System.out.printf("조회된 데이터 (ConcurrentHashMap): %s\n", user);
        return user;
    }

    @Override
    public void updateUser(String id, String newEmail, int newAge) {
        users.computeIfPresent(id, (key, user) -> new GeneralUserEntity(id, newEmail, newAge));
        System.out.printf("업데이트된 데이터 (ConcurrentHashMap): 아이디 = %s, 새 이메일 = %s, 새 나이 = %d\n", id, newEmail, newAge);
    }

    @Override
    public void deleteUser(String id) {
        users.remove(id);
        System.out.printf("삭제된 데이터 (ConcurrentHashMap): 아이디 = %s\n", id);
    }
}
