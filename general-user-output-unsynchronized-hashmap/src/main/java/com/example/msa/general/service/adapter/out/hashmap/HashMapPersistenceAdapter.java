package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDTO;
import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class HashMapPersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;

    public HashMapPersistenceAdapter() {
        users = new HashMap<>();
    }

    @Override
    public void saveGeneralUser(String id, String email, int age) {
        users.put(id, new GeneralUserEntity(id, email, age));
        System.out.printf("저장된 데이터 (synchronized): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
    }

    @Override
    public GeneralUserDTO getUser(String id) {
        return users.get(id);
    }

    @Override
    public void updateUser(String id, String email, int age) {
        if (users.containsKey(id)) {
            users.put(id, new GeneralUserEntity(id, email, age));
            System.out.printf("수정된 데이터 (synchronized): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
        } else {
            System.out.printf("수정 실패: 아이디 %s가 존재하지 않습니다.\n", id);
        }
    }

    @Override
    public void deleteUser(String id) {
        if (users.remove(id) != null) {
            System.out.printf("삭제된 데이터 (synchronized): 아이디 = %s\n", id);
        } else {
            System.out.printf("삭제 실패: 아이디 %s가 존재하지 않습니다.\n", id);
        }
    }
}
