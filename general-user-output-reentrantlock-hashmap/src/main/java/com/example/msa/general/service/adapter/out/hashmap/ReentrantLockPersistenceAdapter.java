package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDTO;
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
            users.put(id, new GeneralUserEntity(id, email, age));
            System.out.printf("저장된 데이터 (ReentrantLock): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public GeneralUserDTO getUser(String id) {
        lock.lock();
        try {
            GeneralUserEntity user = users.get(id);
            System.out.printf("조회된 데이터 (ReentrantLock): %s\n", user);
            return user;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void updateUser(String id, String newEmail, int newAge) {
        lock.lock();
        try {
            users.put(id, new GeneralUserEntity(id, newEmail, newAge));
            System.out.printf("업데이트된 데이터 (ReentrantLock): 아이디 = %s, 새 이메일 = %s, 새 나이 = %d\n", id, newEmail, newAge);
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void deleteUser(String id) {
        lock.lock();
        try {
            users.remove(id);
            System.out.printf("삭제된 데이터 (ReentrantLock): 아이디 = %s\n", id);
        } finally {
            lock.unlock();
        }
    }
}
