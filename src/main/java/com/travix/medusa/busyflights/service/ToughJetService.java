package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetRequest;
import com.travix.medusa.busyflights.domain.toughjet.ToughJetResponse;
import com.travix.medusa.busyflights.utility.FlightDateOrganizer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ToughJetService implements IFlightService {

  @Override
  public List<BusyFlightsResponse> searchProviderFlights(BusyFlightsRequest request) {
    List<BusyFlightsResponse> result = new ArrayList<>();
    BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse();

    // 1. Convert BusyFlightsRequest req to ToughJetRequest req
    // 2. Call ToughJet API - Not provided
    // 3. Convert ToughJet API result list to my result list
    ToughJetRequest toughJetRequest = new ToughJetRequest();
    toughJetRequest.setFrom(request.getOrigin());
    toughJetRequest.setTo(request.getDepartureDate());
    toughJetRequest.setInboundDate(request.getReturnDate());
    toughJetRequest.setOutboundDate(request.getDepartureDate());
    toughJetRequest.setNumberOfAdults(request.getNumberOfPassengers());


    //Todo Should be called REST ToughJet API
    List<ToughJetResponse> toughJetResponses = simulateToughJetResponses();

    for (ToughJetResponse toughJetResponse : toughJetResponses) {
      busyFlightsResponse.setSupplier("ToughJet");
      busyFlightsResponse.setAirline(toughJetResponse.getCarrier());
      busyFlightsResponse.setFare((toughJetResponse.getBasePrice() * (100-toughJetResponse.getDiscount()))/100 + toughJetResponse.getTax());
      busyFlightsResponse.setDepartureAirportCode(toughJetResponse.getDepartureAirportName());
      busyFlightsResponse.setDestinationAirportCode(toughJetResponse.getArrivalAirportName());
      busyFlightsResponse.setDepartureDate(toughJetResponse.getOutboundDateTime());
      busyFlightsResponse.setArrivalDate(toughJetResponse.getInboundDateTime());
      result.add(busyFlightsResponse);
    }
    return result;
  }

  private List<ToughJetResponse> simulateToughJetResponses() {
    ToughJetResponse toughJetResponse[] = new ToughJetResponse[2];
    List<ToughJetResponse> toughJetResponseLists = new ArrayList<>(2);

    toughJetResponse[0] = new ToughJetResponse();
    toughJetResponse[0].setCarrier("ToughJet");
    toughJetResponse[0].setInboundDateTime(FlightDateOrganizer.dateConventor(2019,2,28));
    toughJetResponse[0].setOutboundDateTime(FlightDateOrganizer.dateConventor(2019,3,1));
    toughJetResponse[0].setArrivalAirportName("KUL");
    toughJetResponse[0].setDepartureAirportName("AMS");
    toughJetResponse[0].setBasePrice(3100);
    toughJetResponse[0].setDiscount(2);
    toughJetResponse[0].setTax(12.0);
    toughJetResponseLists.add(toughJetResponse[0]);

    toughJetResponse[1] = new ToughJetResponse();
    toughJetResponse[1].setCarrier("ToughJet");
    toughJetResponse[1].setInboundDateTime(FlightDateOrganizer.dateConventor(2019,3,15));
    toughJetResponse[1].setOutboundDateTime(FlightDateOrganizer.dateConventor(2019,3,17));
    toughJetResponse[1].setArrivalAirportName("KUL");
    toughJetResponse[1].setDepartureAirportName("AMS");
    toughJetResponse[1].setBasePrice(2600);
    toughJetResponse[1].setDiscount(3);
    toughJetResponse[1].setTax(13.0);
    toughJetResponseLists.add(toughJetResponse[1]);

    return toughJetResponseLists;
  }
}
