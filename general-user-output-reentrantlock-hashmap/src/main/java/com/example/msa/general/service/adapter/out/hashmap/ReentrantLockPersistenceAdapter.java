package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import java.util.concurrent.locks.ReentrantLock;

@Component
public class ReentrantLockPersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;
    private final ReentrantLock lock;

    public ReentrantLockPersistenceAdapter() {
        users = new HashMap<>();
        lock = new ReentrantLock();
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        lock.lock();
        try {
            users.put(id, new GeneralUserEntity(email, age));
            System.out.printf("저장된 데이터 (ReentrantLock): 아이디 = %s, 이메일 = %s, 나이 = %d \n", id, email, age);
        } finally {
            lock.unlock();
        }
    }
}
