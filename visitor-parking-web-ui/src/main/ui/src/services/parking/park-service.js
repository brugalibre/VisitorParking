import axios from 'axios';
import authHeader from "@/services/auth/auth-header";

const API_URL = '/api/v1/visitor-parking-management/parkservice';
const CAR_PLATE_NO_REGEX = '(^[A-Z]{2}\\s[0-9]{1,6}$)';

class ParkService {
    revokeParkedCar4PlateNo(residentId, carPlateNo) {
        return axios.delete(API_URL + '/parkedCar/' + residentId + '/' + carPlateNo, {headers: authHeader()});
    }

    parkCar4PlateNo(residentId, carPlateNo) {
        const parkCarRequest = {
            car2ParkPlateNo: carPlateNo,
        }
        console.log('parking plate-no: ' + JSON.stringify(parkCarRequest));
        return axios.post(API_URL + '/parkCar/' + residentId, parkCarRequest, {headers: authHeader()});
    }

    hasParkedCarAssigned(facilityId, carPlateNo) {
        console.log('header: ' + JSON.stringify(authHeader()));
        return axios.get(API_URL + '/isParkedCarAssigned/' + facilityId + '/' + carPlateNo, {headers: authHeader()});
    }

    isPlateNoValid(carPlateNo) {
        return carPlateNo && carPlateNo.match(CAR_PLATE_NO_REGEX);
    }
}

export default new ParkService();
