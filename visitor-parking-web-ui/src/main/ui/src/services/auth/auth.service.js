import axios from 'axios';

const API_URL = '/api/auth/';

class AuthService {
    login(user) {
        return axios.post(API_URL + 'login', user)
            .then(response => {
                console.log('Response from login ' + JSON.stringify(response.data));
                if (response.data.accessToken) {
                    localStorage.setItem('user', JSON.stringify(response.data));
                }
                return response.data;
            });
    }

    logout() {
        localStorage.removeItem('user');
    }

    register(user) {
        return axios.post(API_URL + 'register', user);
    }
}

export default new AuthService();
