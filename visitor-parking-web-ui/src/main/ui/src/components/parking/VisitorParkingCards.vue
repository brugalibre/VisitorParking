<template>
  <div>
    <header class="jumbotron">
      <div class="visitor-parking-cards-container">
        <h3>Verfügbare Besucher-Parkkarten</h3>
        <label v-show="this.availableVisitorParkingCards.length === 0" class="no-parking-cards-available-msg">
          Keine freien Parkkarten verfügbar
        </label>
        <div class="visitor-parking-card"
             v-for="visitorParkingCard in this.availableVisitorParkingCards" :key="visitorParkingCard"
             v-on:click="revokeVisitorParkingCard(visitorParkingCard.parkedCarPlateNo)"
        >
          <div class="tile btn-secondary">
            <div class="grid-container-60-40">
              <label class="attr-label"> Karten-Nr.</label>
              <span>{{ visitorParkingCard.parkingCardNr }}</span>
            </div>
          </div>
        </div>
        <h3>Belegte Besucher-Parkkarten</h3>
        <label v-show="this.occupiedVisitorParkingCards.length === 0" class="no-parking-cards-available-msg">
          Keine Parkkarten belegt
        </label>
        <div class="visitor-parking-card"
             v-for="visitorParkingCard in this.occupiedVisitorParkingCards" :key="visitorParkingCard">
          <div class="tile">
            <div class="grid-container-60-40" v-if=" visitorParkingCard.parkedCarPlateNo">
              <label class="attr-label">Nummernschild</label>
              <span>{{ visitorParkingCard.parkedCarPlateNo }}</span>
            </div>
            <div class="grid-container-60-40" v-if=" visitorParkingCard.assignedSince">
              <label class="attr-label">Geparkt seit</label>
              <span>{{ formatDate(visitorParkingCard.assignedSince) }}</span>
            </div>
            <div class="grid-container-60-40">
              <label class="attr-label">Karten-Nr.</label>
              <span>{{ visitorParkingCard.parkingCardNr }}</span>
            </div>
            <button class="btn btn-primary btn-block"
                    v-on:click="revokeVisitorParkingCard(visitorParkingCard.parkedCarPlateNo)">
              <span class="fa-layers fa-fw fa-3x">
                <font-awesome-icon icon="fa-solid fa-square-parking fa-inverse fa-stack-1x"/>
                <font-awesome-icon icon="fa-solid fa-slash fa-stack-2x"/>
              </span>
              Parkkarte zurücklegen
            </button>
          </div>
        </div>
      </div>
      <div v-show="this.errorDetails" class="error-details tile btn-danger" style="justify-self: center">
        Fehlo: {{ this.errorDetails }}
      </div>
    </header>
  </div>
</template>

<script>

import ParkService from '@/services/parking/park-service';
import LoggingService from '@/services/log/logging.service';
import DateService from '@/services/util/date.service';

export default {
  name: 'VisitorParkingCards',
  props: ['visitorParkingCards', 'residentId'],
  data() {
    return {
      errorDetails: null
    };
  },
  computed: {
    availableVisitorParkingCards() {
      if (!this.visitorParkingCards) {
        return {};
      }
      return this.visitorParkingCards.filter(visitorParkingCard => visitorParkingCard.isAvailable);
    },
    occupiedVisitorParkingCards() {
      if (!this.visitorParkingCards) {
        return {};
      }
      return this.visitorParkingCards.filter(visitorParkingCard => !visitorParkingCard.isAvailable);
    }
  },
  methods: {
    formatDate: function (dateAsString) {
      return DateService.formatDate(dateAsString);
    },
    revokeVisitorParkingCard: function (parkedCarPlateNo) {
      if (parkedCarPlateNo) {
        ParkService.revokeParkedCar4PlateNo(this.residentId, parkedCarPlateNo)
            .then(() => this.errorDetails = null)
            .catch(error => {
              const errorText = LoggingService.extractErrorText(error);
              LoggingService.logError('Fehler beim Zurückgeben der Parkkarte!', errorText);
              this.errorDetails = errorText;
            })
            .finally(() => {
              this.$store.dispatch('visitorParking/setIsLoading', false);
              this.$emit('refreshVisitorParkingCards');
            });
      }
    }
  }
};
</script>
<style>
.visitor-parking-cards-container {
  display: flex;
  flex-direction: column;
  justify-content: space-between;
}

.visitor-parking-card {
  padding: 0 0 30px 0;
}

.visitor-parking-card:last-child {
  padding: 0;
}

.no-parking-cards-available-msg {
  text-align: center;
  font-style: italic;
}
</style>
