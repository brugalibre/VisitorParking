export const visitorParking = {
    namespaced: true,
    state: () => ({
        isLoading: false,
        residentDto: {
            id: null,
            name: null,
            facilityDto: null,
            visitorParkingCardDtos: [],
        },
        facilityDto: {
            name: '',
        },
    }),

    actions: {
        setResidentDto(context, residentDto) {
            context.commit("setResidentDto", residentDto);
        },
        setFacilityDto(context, facilityDto) {
            context.commit("setFacilityDto", facilityDto);
        },
        setIsLoading(context, isLoading) {
            context.commit("setIsLoading", isLoading);
        }
    },
    mutations: {
        setResidentDto(state, residentDto) {
            state.residentDto = residentDto;
        },
        setFacilityDto(state, facilityDto) {
            state.facilityDto = facilityDto;
        },
        setIsLoading(state, isLoading) {
            state.isLoading = isLoading;
        },
    },
    getters: {
        residentDto(state) {
            return state.residentDto;
        },
        facilityDto(state) {
            return state.facilityDto;
        },
        isLoading(state) {
            return state.isLoading;
        }
    }
};
