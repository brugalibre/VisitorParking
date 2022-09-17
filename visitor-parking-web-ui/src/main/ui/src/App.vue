<template>
  <div id="app">
    <nav class="navbar navbar-expand navbar-dark bg-dark">
      <a href="/" class="navbar-brand">Besucherparking Verwaltung</a>
      <div class="navbar-nav mr-auto">
        <li class="nav-item">
          <router-link :to=this.visitorParkingOverviewPath class="nav-link"
                       v-on:click="this.hasElementClicked = true">
            <font-awesome-icon icon="fa-solid fa-square-parking"/>
            Besucherparking Verwaltung
          </router-link>
        </li>
        <li class="nav-item">
          <router-link :to=this.visitorParkingVerifyPath class="nav-link"
                       v-on:click="this.hasElementClicked = true">
            <font-awesome-icon icon="fa-solid fa-magnifying-glass-location"/>
            Fahrzeugschild Abfragen
          </router-link>
        </li>
        <li v-if="showCheckParkingStatus" class="nav-item">
          <router-link :to=this.visitorParkingAdminPath class="nav-link">Admin Board</router-link>
        </li>
        <li v-if="showModeratorBoard" class="nav-item">
          <router-link :to=this.visitorParkingModPath class="nav-link">Moderator Board</router-link>
        </li>
      </div>

      <div v-if="!currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link :to=this.visitorParkingRegisterPath class="nav-link">
            <font-awesome-icon icon="user-plus"/>
            Sign Up
          </router-link>
        </li>
        <li class="nav-item">
          <router-link :to=this.visitorParkingLoginPath class="nav-link">
            <font-awesome-icon icon="sign-in-alt"/>
            Login
          </router-link>
        </li>
      </div>
      <div v-if="currentUser" class="navbar-nav ml-auto">
        <li class="nav-item">
          <router-link :to=this.visitorParkingProfilePath class="nav-link">
            <font-awesome-icon icon="user"/>
            {{ currentUser.username }}
          </router-link>
        </li>
        <li class="nav-item">
          <a class="nav-link" @click.prevent="logOut">
            <font-awesome-icon icon="sign-out-alt"/>
            LogOut
          </a>
        </li>
      </div>
    </nav>

    <div class="container">
      <router-view/>
    </div>

  </div>
</template>

<script>
import RouterConstants from "@/router-constants";

export default {
  data() {
    return {
      hasElementClicked: false,
      visitorParkingOverviewPath: RouterConstants.VISITOR_PARKING_OVERVIEW_PATH,
      visitorParkingVerifyPath: RouterConstants.VISITOR_PARKING_VERIFY_PATH,
      visitorParkingLoginPath: RouterConstants.LOGIN_PATH,
      visitorParkingRegisterPath: RouterConstants.REGISTER_PATH,
      visitorParkingProfilePath: RouterConstants.PROFILE_PATH,
      visitorParkingAdminPath: RouterConstants.ADMIN_PATH,
      visitorParkingModPath: RouterConstants.MOD_PATH,
    };
  },
  computed: {
    currentUser() {
      return this.$store.state.auth.user;
    },
    showAdminBoard() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_ADMIN');
      }
      return false;
    },
    showCheckParkingStatus() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_ADMIN');
      }
      return false;
    },
    showModeratorBoard() {
      if (this.currentUser && this.currentUser['roles']) {
        return this.currentUser['roles'].includes('ROLE_MODERATOR');
      }
      return false;
    }
  },
  methods: {
    logOut() {
      this.$store.dispatch('auth/logout');
      this.$router.push('/login');
    }
  }
};
</script>
<style>

.centered-flex {
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
}

.attr-label {
  text-align: right;
  margin-right: 15px;
}

.error-details {
  border-radius: 10px;
}

.tile {
  padding: 10px;
  margin: 10px;
  box-shadow: inset 0 3px 6px rgba(0, 0, 0, 0.16), 0 4px 6px rgba(0, 0, 0, 0.45);
  border-radius: 10px;
}

.red-border {
  padding: 10px;
  margin: 10px;
  border: darkred solid 2px;
}

.grid-container-60-40 {
  display: grid;
  grid-template-columns: 60% 40%;
  column-gap: 10px;
  row-gap: 10px;
  padding-right: 10px;
}

.spinner {
  position: fixed;
  width: 230px;
  height: 230px;
  top: 50%;
  left: 50%;
  z-index: 2;

}

.welcomeMsg {
  position: fixed;
  width: 230px;
  height: 230px;
  transform: translate(-50%, -50%);
  top: 50%;
  left: 50%;
  z-index: 1;
}

.blurBackground {
  background: rgba(255, 255, 255, 0.6);
  backdrop-filter: blur(5px);
  height: 100vh;
  width: 200vh;
  position: fixed;
  top: 0%;
  left: 0%;
  z-index: 1;
}
</style>
