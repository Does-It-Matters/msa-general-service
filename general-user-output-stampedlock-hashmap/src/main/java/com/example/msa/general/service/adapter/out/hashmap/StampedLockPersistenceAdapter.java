package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.StampedLock;

@Component
public class StampedLockPersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;
    private final StampedLock lock;

    public StampedLockPersistenceAdapter() {
        users = new HashMap<>();
        lock = new StampedLock();
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        long stamp = lock.writeLock();
        try {
            users.put(id, new GeneralUserEntity(id, email, age));
            System.out.printf("저장된 데이터 (StampedLock): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public GeneralUserEntity getUser(String id) {
        long stamp = lock.readLock();
        try {
            return users.get(id);
        } finally {
            lock.unlockRead(stamp);
        }
    }

    @Override
    public void updateUser(String id, String email, int age) {
        long stamp = lock.writeLock();
        try {
            if (users.containsKey(id)) {
                users.put(id, new GeneralUserEntity(id, email, age));
                System.out.printf("수정된 데이터 (StampedLock): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
            } else {
                System.out.printf("수정 실패: 아이디 %s가 존재하지 않습니다.\n", id);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }

    @Override
    public void deleteUser(String id) {
        long stamp = lock.writeLock();
        try {
            if (users.remove(id) != null) {
                System.out.printf("삭제된 데이터 (StampedLock): 아이디 = %s\n", id);
            } else {
                System.out.printf("삭제 실패: 아이디 %s가 존재하지 않습니다.\n", id);
            }
        } finally {
            lock.unlockWrite(stamp);
        }
    }
}
