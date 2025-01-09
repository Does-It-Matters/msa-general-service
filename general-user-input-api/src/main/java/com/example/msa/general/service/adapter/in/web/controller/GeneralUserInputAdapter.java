package com.example.msa.general.service.adapter.in.web.controller;

import com.example.msa.general.service.adapter.in.web.dto.request.GeneralSignUpRequest;
import com.example.msa.general.service.adapter.in.web.dto.response.GeneralSignUpResponse;
import org.springframework.http.ResponseEntity;

/**
 * <b> 일반 유저 입력 어댑터 </b>
 * HTTP 요청을 받아 서비스 계층으로 전달하고 응답을 처리
 */
public interface GeneralUserInputAdapter {

    // 회원가입 처리
    ResponseEntity<GeneralSignUpResponse> signUp(GeneralSignUpRequest body);

    // 유저 정보 조회 처리
//    ResponseEntity<GeneralUser> getUser(String id);

    // 유저 정보 수정 처리
    ResponseEntity<GeneralSignUpResponse> updateUser(String id, String newEmail, int newAge);

    // 유저 정보 삭제 처리
    ResponseEntity<GeneralSignUpResponse> deleteUser(String id);
}
