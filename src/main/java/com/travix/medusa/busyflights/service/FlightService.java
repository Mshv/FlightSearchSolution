package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
public class FlightService {

  List<IFlightService> providers;

  @Autowired
  public FlightService(List<IFlightService> providers) {
    this.providers = providers;
  }

  public List<BusyFlightsResponse> searchFlights(BusyFlightsRequest request) throws IllegalArgumentException {
    List<BusyFlightsResponse> result = new ArrayList<>();

    if (request.getNumberOfPassengers() > 4) {
      throw new IllegalArgumentException("Number Of Passengers - Maximum 4 passengers");
    }

    for (IFlightService provider : providers) {
      List<BusyFlightsResponse> responses = provider.searchProviderFlights(request);
      result.addAll(responses);
    }

    // Sort
    result.sort(Comparator.comparing(BusyFlightsResponse::getFare));
    return result;
  }
}
