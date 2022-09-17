<template>
  <div class="centered-flex">
    <div class="centered-flex card card-container visitor-parking-card-overview-container">
      <div v-show="isLoading" class="blurBackground">
      <span v-show="isLoading"
            class="spinner spinner-border"
      ></span>
      </div>
      <header class="jumbotron tile">
        <h3>Parkkarten Verwaltung für {{ resident.name }}</h3>
        <park-car v-bind:residentId="resident.id"
                  @refreshVisitorParkingCards="refreshVisitorParkingCardOverview()"
        ></park-car>
        <visitor-parking-cards
            :visitor-parking-cards="resident.visitorParkingCards"
            :resident-id="resident.id"
            @refreshVisitorParkingCards="refreshVisitorParkingCardOverview()">
        </visitor-parking-cards>
      </header>
    </div>
  </div>
</template>

<script>
import VisitorParkingCards from "@/components/parking/VisitorParkingCards";
import ResidentService from "@/services/parking/resident.service";
import ParkCar from "@/components/parking/ParkCar";
import LoggingService from "@/services/log/logging.service";
import UserService from "@/services/user/user.service";

export default {
  name: "VisitorParkingOverview",
  components: {ParkCar, VisitorParkingCards},
  data() {
    return {
      refreshVisitorParkingCardsKey: 0,
      refreshVisitorParkingCardOverviewKey: 0,
    };
  },
  computed: {
    isLoading: function () {
      return this.$store.state.visitorParking.isLoading;
    },
    resident: function () {
      return this.$store.state.visitorParking.residentDto;
    },
  },
  methods: {
    refreshVisitorParkingCardOverview: function () {
      this.$store.dispatch('visitorParking/setIsLoading', true);
      setTimeout(() => {
        this.fetchResident()
        this.$store.dispatch('visitorParking/setIsLoading', false);
      }, 750);
    },
    fetchResident() {
      ResidentService.getResident(UserService.getCurrentUserId())
          .then(response => response.data)
          .then(data => this.$store.dispatch('visitorParking/setResidentDto', data))
          .catch(error => LoggingService.logError(',', error));
    }
  },
  mounted() {
    this.fetchResident();
  },
};
</script>
<style>
.visitor-parking-card-overview-container {
  max-width: 60%;
  display: flex;
  justify-content: center;
}
</style>