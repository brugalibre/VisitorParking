import axios from 'axios';
import authHeader from '../auth/auth-header';
import UserService from "@/services/user/user.service";

const API_URL = '/api/v1/visitor-parking-management';

class FacilityService {

    getCurrentFacilityId() {
        return axios.get(API_URL + '/facilityId4UserId/' + UserService.getCurrentUserId(), {headers: authHeader()});
    }
    getFacility(facilityId) {
        return axios.get(API_URL + '/facility/' + facilityId, {headers: authHeader()});
    }

}

export default new FacilityService();
