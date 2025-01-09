package com.example.msa.general.service.adapter.out.hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDTO;
import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * <b> 동기화된 일반 유저 영속성 어댑터 </b>
 *
 * - synchronized 키워드를 사용하여 스레드 안전성 보장
 * - 데이터베이스 대신 해시맵을 활용하여 데이터를 저장
 */
@Component
public class SynchronizedPersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;

    public SynchronizedPersistenceAdapter() {
        users = new HashMap<>();
    }

    @Override
    public synchronized void saveGeneralUser(String id, String email, int age) {
        users.put(id, new GeneralUserEntity(id, email, age));
        System.out.printf("저장된 데이터 (synchronized): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
    }

    @Override
    public synchronized GeneralUserDTO getUser(String id) {
        return users.get(id);
    }

    @Override
    public synchronized void updateUser(String id, String email, int age) {
        if (users.containsKey(id)) {
            users.put(id, new GeneralUserEntity(id, email, age));
            System.out.printf("수정된 데이터 (synchronized): 아이디 = %s, 이메일 = %s, 나이 = %d\n", id, email, age);
        } else {
            System.out.printf("수정 실패: 아이디 %s가 존재하지 않습니다.\n", id);
        }
    }

    @Override
    public synchronized void deleteUser(String id) {
        if (users.remove(id) != null) {
            System.out.printf("삭제된 데이터 (synchronized): 아이디 = %s\n", id);
        } else {
            System.out.printf("삭제 실패: 아이디 %s가 존재하지 않습니다.\n", id);
        }
    }
}
