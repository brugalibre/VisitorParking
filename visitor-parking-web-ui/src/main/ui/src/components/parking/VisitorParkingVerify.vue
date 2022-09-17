<template>
  <div class="centered-flex">
    <div class="card card-container visitor-parking-card-overview-container">
      <header className="jumbotron tile">
        <h3>Fahrzeugschild abfragen</h3>
        <div className="park-car-verify-container">
          <input
              id="parkedCarPlateNo"
              v-on:keyup.enter="this.checkIfHasParkedCarAssigned(this.parkedCarPlateNo)"
              v-model="parkedCarPlateNo"
              className="parkedCarPlateNoInput"
              name="parkedCarPlateNo"
              placeholder="Nummernschild"
              type="text"
              required
          >
          <span></span>
          <button className="container-element element-left btn btn-primary btn-block"
                  :disabled="!this.isParkedCarPlateNoValid"
                  v-on:click="this.checkIfHasParkedCarAssigned(this.parkedCarPlateNo)">
          <span className="fa-2xl">
            <font-awesome-icon icon="fa-solid fa-square-parking"/>
          </span>
            Abfrage senden
          </button>
        </div>
        <div class="verify-result">
          <div v-show="this.hasParkedCarAssigned === false" class="btn-warning">
            <label style="padding: 5px">Fahrzeug '{{ this.parkedCarPlateNo }}' ist nicht geparkt!</label>
          </div>
          <div v-show="this.hasParkedCarAssigned" class="btn-info">
            <label style="padding: 5px">Fahrzeug '{{ this.parkedCarPlateNo }}' ist geparkt!</label>
          </div>
          <div v-show="this.errorDetails" className="error-details tile btn-danger" style="justify-self: center">
            <label style="padding: 5px"> {{ this.errorDetails }} </label>
          </div>
        </div>
      </header>
    </div>
  </div>
</template>

<script>
import ParkService from "@/services/parking/park-service";
import LoggingService from "@/services/log/logging.service";
import FacilityService from "@/services/parking/facility.service";

export default {
  name: "VisitorParkingVerify",
  props: ['residentId'],
  data() {
    return {
      hasParkedCarAssigned: null,
      errorDetails: null,
      parkedCarPlateNo: '',
    };
  },
  watch: {
    parkedCarPlateNo: {
      handler: function (newParkedCarPlateNo, oldParkedCarPlateNo) {
        console.log("Changed parkedCarPlateNo from " + newParkedCarPlateNo + " to " + oldParkedCarPlateNo);
        this.hasParkedCarAssigned = null;
      },
    },
  },
  computed: {
    facility: function () {
      return this.$store.state.visitorParking.facilityDto;
    },
    isParkedCarPlateNoValid() {
      return ParkService.isPlateNoValid(this.parkedCarPlateNo);
    }
  },
  methods: {
    fetchFacility() {
      FacilityService.getCurrentFacilityId()
          .then(response => response.data)
          .then(facilityId => FacilityService.getFacility(facilityId))
          .then(response => this.$store.dispatch('visitorParking/setFacilityDto', response.data))
          .catch(error => LoggingService.logError(',', error));
    },
    checkIfHasParkedCarAssigned: function (parkedCarPlateNo) {
      this.hasParkedCarAssigned = null,
          ParkService.hasParkedCarAssigned(this.facility.id, parkedCarPlateNo)
              .then(data => {
                this.errorDetails = null;
                this.hasParkedCarAssigned = data && data.data;
                console.log(data);
                console.log(this.hasParkedCarAssigned);
              })
              .catch(error => {
                const errorText = LoggingService.extractErrorText(error);
                LoggingService.logError('Fehler bei der Abfrage, ob Fahrzeug \'' + parkedCarPlateNo + '!', errorText);
                this.errorDetails = errorText;
              });
    }
  },
  mounted() {
    this.fetchFacility();
  },
};
</script>
<style>
.park-car-verify-container {
  display: grid;
  padding-right: 10px;
  grid-template-columns: 55% 5% 40%;
}

.visitor-parking-card-overview-container {
  width: 50%;
  display: flex;
  justify-content: center;
}

.verify-result {
  padding-top: 10px;
  margin: 0 0;
  width: 54%;
}
</style>