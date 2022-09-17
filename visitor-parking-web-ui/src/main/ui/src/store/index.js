import {createStore} from "vuex";
import {auth} from "./auth.module";
import {visitorParking} from "./visitor-parking.module";

const store = createStore({
    modules: {
        auth,
        visitorParking,
    },
});

export default store;
