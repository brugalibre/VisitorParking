import dayjs from "dayjs";
class DateService {
    formatDate(dateString) {
        const date = dayjs(dateString);
        return date.format('DD.MM.YYYY HH:mm');
    }
}

export default new DateService();
