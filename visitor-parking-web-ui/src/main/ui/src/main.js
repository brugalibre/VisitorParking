import {createApp} from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vueLoading from 'vue-loading-overlay';
import "bootstrap";
import "bootstrap/dist/css/bootstrap.min.css";
import {FontAwesomeIcon} from './plugins/font-awesome'
import veeValidatePlugin from "./plugins/validation";

createApp(App)
    .use(router)
    .use(store)
    .use(vueLoading)
    .use(veeValidatePlugin)
    .component("font-awesome-icon", FontAwesomeIcon)
    .mount("#app");
