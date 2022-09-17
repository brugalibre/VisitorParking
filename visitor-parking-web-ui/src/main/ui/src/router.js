import {createRouter, createWebHistory} from "vue-router";
import VisitorParkingVerify from "./components/parking/VisitorParkingVerify.vue";
import VisitorParkingOverview from "./components/parking/VisitorParkingOverview.vue";
import Login from "./components/auth/Login.vue";
import Register from "./components/auth/Register.vue";
import RouterConstants from "@/router-constants";
// lazy-loaded
const Profile = () => import("./components/admin/Profile.vue")
const BoardAdmin = () => import("./components/admin/BoardAdmin.vue")
const BoardModerator = () => import("./components/admin/BoardModerator.vue")
const BoardUser = () => import("./components/user/BoardUser.vue")

const routes = [

    {
        path: RouterConstants.VISITOR_PARKING_OVERVIEW_PATH,
        component: VisitorParkingOverview,
    },
    {
        path: RouterConstants.VISITOR_PARKING_VERIFY_PATH,
        component: VisitorParkingVerify,
    },
    {
        path: RouterConstants.LOGIN_PATH,
        component: Login,
    },
    {
        path: RouterConstants.REGISTER_PATH,
        component: Register,
    },
    {
        path: RouterConstants.PROFILE_PATH,
        name: "profile",
        // lazy-loaded
        component: Profile,
    },
    {
        path: RouterConstants.MOD_PATH,
        name: "moderator",
        // lazy-loaded
        component: BoardModerator,
    },
    {
        path: RouterConstants.ADMIN_PATH,
        name: "admin",
        // lazy-loaded
        component: BoardAdmin,
    },
    {
        path: RouterConstants.LOGIN_PATH,
        name: "user",
        // lazy-loaded
        component: BoardUser,
    },
];

const router = createRouter({
    history: createWebHistory(),
    routes,
});

router.beforeEach((to, from, next) => {
    const publicPages = [RouterConstants.LOGIN_PATH, RouterConstants.REGISTER_PATH];
    const authRequired = !publicPages.includes(to.path);
    const loggedIn = localStorage.getItem('user');
    // trying to access a restricted page + not logged in
    // redirect to login page
    if (authRequired && !loggedIn) {
        next(RouterConstants.LOGIN_PATH);
    } else {
        next();
    }
});

export default router;