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
        semaphore = new Semaphore(1); // 하나의 쓰레드만 접근 가능
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        try {
            semaphore.acquire();
            users.put(id, new GeneralUserEntity(email, age));
            System.out.printf("저장된 데이터 (Semaphore): 아이디 = %s, 이메일 = %s, 나이 = %d \n", id, email, age);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            semaphore.release();
        }
    }
}

