package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirRequest;
import com.travix.medusa.busyflights.domain.crazyair.CrazyAirResponse;
import com.travix.medusa.busyflights.utility.FlightDateOrganizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CrazyAirService implements IFlightService{

  @Override
  public List<BusyFlightsResponse> searchProviderFlights(BusyFlightsRequest request) {

    List<BusyFlightsResponse> result = new ArrayList<>();
    BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();

    // CrazyAir
    // 1. Convert BusyFlightsRequest req to CrazyAirRequest req
    // 2. Call CrazyAir API - Not provided
    // 3. Convert api1 result list to my result list
    CrazyAirRequest crazyAirRequest = new CrazyAirRequest();
    crazyAirRequest.setDepartureDate(request.getDepartureDate());
    crazyAirRequest.setReturnDate(request.getReturnDate());
    crazyAirRequest.setOrigin(request.getOrigin());
    crazyAirRequest.setDestination(request.getDestination());
    crazyAirRequest.setPassengerCount(request.getNumberOfPassengers());

    //Todo Should be called REST CrazyAir API
    List<CrazyAirResponse> crazyAirResponses = simulateCrazyAirResponses();

    for (CrazyAirResponse crazyAirResponse : crazyAirResponses) {
      busyFlightsResponse.setSupplier("CrazyAir");
      busyFlightsResponse.setAirline(crazyAirResponse.getAirline());
      busyFlightsResponse.setFare(crazyAirResponse.getPrice());
//      busyFlightsResponse.set(crazyAirResponse.getCabinclass()); //E for Economy and B for Business
      busyFlightsResponse.setDepartureAirportCode(crazyAirResponse.getDepartureAirportCode());
      busyFlightsResponse.setDestinationAirportCode(crazyAirResponse.getDestinationAirportCode());
      busyFlightsResponse.setDepartureDate(crazyAirResponse.getDepartureDate());
      busyFlightsResponse.setArrivalDate(crazyAirResponse.getArrivalDate());
      result.add(busyFlightsResponse);
    }
    return result;
  }

  private List<CrazyAirResponse> simulateCrazyAirResponses() {
    CrazyAirResponse crazyAirResponses[] = new CrazyAirResponse[2];
    List<CrazyAirResponse> crazyAirResponseList = new ArrayList<>(2);
    crazyAirResponses[0] = new CrazyAirResponse();
    crazyAirResponses[0].setAirline("CrazyAir");
    crazyAirResponses[0].setDepartureDate(FlightDateOrganizer.dateConventor(2019, 2, 2));
    crazyAirResponses[0].setArrivalDate(FlightDateOrganizer.dateConventor(2019, 2, 15));
    crazyAirResponses[0].setDepartureAirportCode("KUL");
    crazyAirResponses[0].setDepartureAirportCode("AMS");
    crazyAirResponses[0].setPrice(2100);
    crazyAirResponses[0].setCabinclass("E");
    crazyAirResponseList.add(crazyAirResponses[0]);

    crazyAirResponses[1] = new CrazyAirResponse();
    crazyAirResponses[1].setAirline("CrazyAir");
    crazyAirResponses[1].setDepartureDate(FlightDateOrganizer.dateConventor(2019, 3, 20));
    crazyAirResponses[1].setArrivalDate(FlightDateOrganizer.dateConventor(2019, 3, 28));
    crazyAirResponses[1].setDepartureAirportCode("KUL");
    crazyAirResponses[1].setDepartureAirportCode("AMS");
    crazyAirResponses[1].setPrice(4100);
    crazyAirResponses[1].setCabinclass("E");
    crazyAirResponseList.add(crazyAirResponses[1]);

    return crazyAirResponseList;
  }
}
