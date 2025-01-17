import http from 'k6/http';
import { sleep, check } from 'k6';

export const options = {
  vus: 10,
  iterations: 10,
};

const BASE_URL = 'http://localhost:8082/api/v1/general';

export default function () {
  // 회원가입 요청 (1회)
  const signUpRes = http.post(`${BASE_URL}/sign-up`, JSON.stringify({
    userId: 'testuser',
    pw: 'password123',
    role: 'USER',
  }), { headers: { 'Content-Type': 'application/json' } });

  check(signUpRes, { '회원가입 성공': (res) => res.status === 200 });
  sleep(0.01);

  // 로그인 요청 (3회)
  for (let i = 0; i < 3; i++) {
    const loginRes = http.get(`${BASE_URL}/testuser`);
    check(loginRes, { '로그인 성공': (res) => res.status === 200 });
    sleep(0.01);
  }

  // 정보 수정 요청 (5회)
  for (let i = 0; i < 5; i++) {
    const updateRes = http.put(`${BASE_URL}/testuser?newEmail=email${i}&newAge=${20 + i}`, null, {
      headers: { 'Content-Type': 'application/json' },
    });
    check(updateRes, { '정보 수정 성공': (res) => res.status === 200 });
    sleep(0.01);
  }

  // 로그인 요청 (7회)
  for (let i = 0; i < 7; i++) {
    const loginRes = http.get(`${BASE_URL}/testuser`);
    check(loginRes, { '로그인 성공': (res) => res.status === 200 });
    sleep(0.01);
  }

  // 삭제 요청 (1회)
  const deleteRes = http.del(`${BASE_URL}/testuser`, null, {
    headers: { 'Content-Type': 'application/json' },
  });
  check(deleteRes, { '삭제 성공': (res) => res.status === 200 });
  sleep(0.01);
}
