package com.example.msa.general.service.adapter.in.web.controller;

import com.example.msa.general.service.adapter.in.web.dto.request.GeneralSignUpRequest;
import com.example.msa.general.service.adapter.in.web.dto.response.GeneralSignUpResponse;
import com.example.msa.general.service.application.port.in.GeneralUserDataInputPort;
import com.example.msa.general.service.application.port.in.GeneralUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <b> 일반 유저 컨트롤러 </b>
 * <p>
 * - 일반 유저 정보 관리 <br>
 * </p>
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class GeneralController implements GeneralUserInputAdapter {

    private final GeneralUserDataInputPort generalService;

    /**
     * <b> 회원가입 처리 </b>
     *
     * @param body 요청 데이터
     * @return 회원가입 성공/실패 응답
     */
    @PostMapping("/v1/general/sign-up")
    @Override
    public ResponseEntity<GeneralSignUpResponse> signUp(@RequestBody GeneralSignUpRequest body) {
        boolean signUpSuccess = generalService.signUp(body.getUserId(), body.getPw(), body.getRole());

        if (signUpSuccess) {
            return ResponseEntity.ok(new GeneralSignUpResponse("회원가입 성공"));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new GeneralSignUpResponse("회원가입 실패"));
    }

    /**
     * <b> 유저 정보 조회 </b>
     *
     * @param id 조회할 유저의 ID
     * @return 유저 정보 또는 404 Not Found 응답
     */
    @GetMapping("/v1/general/{id}")
//    @Override
    public ResponseEntity<GeneralUser> getUser(@PathVariable String id) {
        GeneralUser user = generalService.getUser(id);

        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);  // 404 Not Found
        }
    }

    /**
     * <b> 유저 정보 수정 </b>
     *
     * @param id 수정할 유저의 ID
     * @param newEmail 새로운 이메일
     * @param newAge 새로운 나이
     * @return 수정 성공/실패 응답
     */
    @PutMapping("/v1/general/{id}")
    @Override
    public ResponseEntity<GeneralSignUpResponse> updateUser(@PathVariable String id,
                                                            @RequestParam String newEmail,
                                                            @RequestParam int newAge) {
        boolean updateSuccess = generalService.updateUser(id, newEmail, newAge);

        if (updateSuccess) {
            return ResponseEntity.ok(new GeneralSignUpResponse("유저 정보 수정 성공"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralSignUpResponse("유저 정보 수정 실패"));
        }
    }

    /**
     * <b> 유저 정보 삭제 </b>
     *
     * @param id 삭제할 유저의 ID
     * @return 삭제 성공/실패 응답
     */
    @DeleteMapping("/v1/general/{id}")
    @Override
    public ResponseEntity<GeneralSignUpResponse> deleteUser(@PathVariable String id) {
        boolean deleteSuccess = generalService.deleteUser(id);

        if (deleteSuccess) {
            return ResponseEntity.ok(new GeneralSignUpResponse("유저 정보 삭제 성공"));
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new GeneralSignUpResponse("유저 정보 삭제 실패"));
        }
    }
}
