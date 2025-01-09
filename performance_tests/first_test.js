import http from 'k6/http';
import { check } from 'k6';
import { sleep } from 'k6';

const BASE_URL = 'http://localhost:8080/api/v1/general';

export const signUp = () => {
    const payload = JSON.stringify({
        userId: 'testuser',
        pw: 'testpassword',
        role: 'user',
    });

    const params = {
        headers: {
            'Content-Type': 'application/json',
        },
    };

    const response = http.post(`${BASE_URL}/sign-up`, payload, params);
    check(response, {
        'is status 200': (r) => r.status === 200,
        'response contains success message': (r) =>
            r.body.includes('회원가입 성공'),
    });

    sleep(1);
};

export const getUser = () => {
    const userId = 'testuser';

    const response = http.get(`${BASE_URL}/${userId}`);
    check(response, {
        'is status 200': (r) => r.status === 200,
        'response contains userId': (r) => r.body.includes(userId),
    });

    sleep(1);
};

export const updateUser = () => {
    const userId = 'testuser';
    const updateParams = {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
        },
    };

    const response = http.put(
        `${BASE_URL}/${userId}`,
        'newEmail=testuser@example.com&newAge=30',
        updateParams
    );
    check(response, {
        'is status 200': (r) => r.status === 200,
        'response contains update success message': (r) =>
            r.body.includes('유저 정보 수정 성공'),
    });

    sleep(1);
};

export const deleteUser = () => {
    const userId = 'testuser';

    const response = http.del(`${BASE_URL}/${userId}`);
    check(response, {
        'is status 200': (r) => r.status === 200,
        'response contains delete success message': (r) =>
            r.body.includes('유저 정보 삭제 성공'),
    });

    sleep(1);
};

export default function () {
    signUp();
    getUser();
    updateUser();
    deleteUser();
}
