import axios from 'axios';
import authHeader from '../auth/auth-header';

const API_URL = '/api/v1/visitor-parking-management';

class UserService {
    getUserBoard() {
        return axios.get(API_URL + 'user', {headers: authHeader()});
    }

    getModeratorBoard() {
        return axios.get(API_URL + 'mod', {headers: authHeader()});
    }

    getAdminBoard() {
        return axios.get(API_URL + 'admin', {headers: authHeader()});
    }

    getCurrentUserId() {
        let user = localStorage.getItem('user');
        return JSON.parse(user).userId;
    }
}

export default new UserService();
