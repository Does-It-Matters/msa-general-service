package com.example.msa.general.service.application.service;

import com.example.msa.general.service.application.port.in.GeneralUser;
import com.example.msa.general.service.application.port.in.GeneralUserDataInputPort;
import com.example.msa.general.service.application.port.out.GeneralUserDataOutputPort;
import com.example.msa.general.service.application.port.out.GeneralUserDTO;
import com.example.msa.general.service.application.port.out.UserAuthApiOutputPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * <b> 일반 유저 서비스 </b>
 */
@Service
@RequiredArgsConstructor
public class GeneralService implements GeneralUserDataInputPort {
    private static final Logger logger = Logger.getLogger(GeneralService.class.getName());

    private final UserAuthApiOutputPort userAuthAPI;
    private final GeneralUserDataOutputPort persistenceAdapter;

    /**
     * <b> 일반 유저 회원가입 로직 수행 </b>
     *
     * @param userId 아이디
     * @param pw 비밀번호
     * @param role 역할
     * @return 회원가입 성공 여부
     */
    @Override
    public boolean signUp(String userId, String pw, String role) {
        try {
            // 외부 서비스에 회원가입 요청
            final HttpResponse<String> response = userAuthAPI.requestUserSignUp(userId, pw, role);

            // 외부 서비스 응답 코드에 따라 처리
            if (response.statusCode() == 200 || response.statusCode() == 201) {
                persistenceAdapter.saveGeneralUser(userId, "email", 19);
                return true;
            } else {
                logger.log(Level.WARNING, "회원가입 실패, 응답 코드: " + response.statusCode());
                return false;
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "네트워크 오류 발생: " + e.getMessage(), e);
            return false;
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "회원가입 처리 중 인터럽트 발생: " + e.getMessage(), e);
            return false;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "예상치 못한 오류 발생: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * <b> 유저 정보 조회 </b>
     *
     * @param id 유저 아이디
     * @return GeneralUserDTO 유저 정보
     */
    public GeneralUser getUser(String id) {
        GeneralUserDTO user = persistenceAdapter.getUser(id);
        if (user != null) {
            logger.log(Level.INFO, "유저 정보 조회 성공: " + user.getId());
        } else {
            logger.log(Level.WARNING, "유저 정보 조회 실패: 아이디 " + id + "가 존재하지 않습니다.");
        }
        return new User(user.getId(), user.getEmail(), user.getAge());
    }

    /**
     * <b> 유저 정보 수정 </b>
     *
     * @param id 수정할 유저 아이디
     * @param newEmail 수정할 이메일
     * @param newAge 수정할 나이
     * @return 수정 성공 여부
     */
    public boolean updateUser(String id, String newEmail, int newAge) {
        try {
            persistenceAdapter.updateUser(id, newEmail, newAge);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "유저 정보 수정 실패: " + e.getMessage(), e);
            return false;
        }
    }

    /**
     * <b> 유저 정보 삭제 </b>
     *
     * @param id 삭제할 유저 아이디
     * @return 삭제 성공 여부
     */
    public boolean deleteUser(String id) {
        try {
            persistenceAdapter.deleteUser(id);
            return true;
        } catch (Exception e) {
            logger.log(Level.SEVERE, "유저 삭제 실패: " + e.getMessage(), e);
            return false;
        }
    }
}
