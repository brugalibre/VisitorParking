import axios from 'axios';
import authHeader from '../auth/auth-header';

const API_URL = '/api/v1/visitor-parking-management';

class UserService {

    getResident(userId) {
        return axios.get(API_URL + '/resident/' + userId, {headers: authHeader()});
    }
}

export default new UserService();
