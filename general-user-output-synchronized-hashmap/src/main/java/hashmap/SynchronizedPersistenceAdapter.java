package hashmap;

import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class SynchronizedPersistenceAdapter implements GeneralUserDataOutputPort {
    private final Map<String, GeneralUserEntity> users;

    public SynchronizedPersistenceAdapter() {
        users = new HashMap<>();
    }

    @Override
    public synchronized void saveGeneralUser(String id, String email, int age) {
        users.put(id, new GeneralUserEntity(email, age));
        System.out.printf("저장된 데이터 (synchronized): 아이디 = %s, 이메일 = %s, 나이 = %d \n", id, email, age);
    }
}
