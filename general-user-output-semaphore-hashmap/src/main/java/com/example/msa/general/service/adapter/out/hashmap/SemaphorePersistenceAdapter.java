package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Semaphore;

@Component
public class SemaphorePersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;
    private final Semaphore semaphore;

    public SemaphorePersistenceAdapter() {
        users = new HashMap<>();
        semaphore = new Semaphore(1);
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        try {
            semaphore.acquire();
            users.put(id, new GeneralUserEntity(id, email, age));
            System.out.printf("저장된 데이터 (Semaphore): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public GeneralUserEntity getUser(String id) {
        try {
            semaphore.acquire();
            GeneralUserEntity user = users.get(id);
            System.out.printf("조회된 데이터 (Semaphore): %s\n", user);
            return user;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void updateUser(String id, String newEmail, int newAge) {
        try {
            semaphore.acquire();
            users.put(id, new GeneralUserEntity(id, newEmail, newAge));
            System.out.printf("업데이트된 데이터 (Semaphore): 아이디 = %s, 새 이메일 = %s, 새 나이 = %d\n", id, newEmail, newAge);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }

    @Override
    public void deleteUser(String id) {
        try {
            semaphore.acquire();
            users.remove(id);
            System.out.printf("삭제된 데이터 (Semaphore): 아이디 = %s\n", id);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}


