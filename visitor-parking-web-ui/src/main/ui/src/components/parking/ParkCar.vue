<template>
  <div>
    <header class="jumbotron">
      <h3>Fahrzeug parkieren</h3>
      <div class="park-car-container">
        <input
            id="parkedCarPlateNo"
            v-on:keyup.enter="this.parkCar4PlateNo(this.parkedCarPlateNo)"
            v-model="parkedCarPlateNo"
            class="parkedCarPlateNoInput"
            name="parkedCarPlateNo"
            placeholder="Nummernschild"
            type="text"
            required
        >
        <span></span>
        <button class="container-element element-left btn btn-primary btn-block"
                :disabled="!this.isParkedCarPlateNoValid"
                v-on:click="this.parkCar4PlateNo(this.parkedCarPlateNo)">
          <span class="fa-2xl">
            <font-awesome-icon icon="fa-solid fa-square-parking"/>
          </span>
          Speichern
        </button>
      </div>
      <div v-show="this.errorDetails" class="error-details tile btn-danger" style="justify-self: center">
        {{ this.errorDetails }}
      </div>
    </header>
  </div>
</template>

<script>
import ParkService from "@/services/parking/park-service";
import LoggingService from "@/services/log/logging.service";

export default {
  name: "ParkCar",
  props: ['residentId'],
  data() {
    return {
      errorDetails: null,
      parkedCarPlateNo: '',
    };
  },
  computed: {
    isParkedCarPlateNoValid() {
      return ParkService.isPlateNoValid(this.parkedCarPlateNo);
    }
  },
  methods: {
    parkCar4PlateNo: function (parkedCarPlateNo) {
      ParkService.parkCar4PlateNo(this.residentId, parkedCarPlateNo)
          .then(response => {
            this.errorDetails = null;
            console.log('response: ' + JSON.stringify(response));
          })
          .catch(error => {
            const errorText = LoggingService.extractErrorText(error);
            console.log('fehlo: ' + JSON.stringify(error));
            LoggingService.logError("Fehler beim Parkieren des Fahrzeuges " + parkedCarPlateNo + "!", errorText);
            this.errorDetails = errorText;
          })
          .finally(() => {
            this.parkedCarPlateNo = null;
            this.$emit('refreshVisitorParkingCards');
          });
    }
  }
};
</script>
<style>
.park-car-container {
  display: grid;
  padding-right: 10px;
  grid-template-columns: 60% 5% 35%;
}
</style>