package com.travix.medusa.busyflights.service;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import org.springframework.stereotype.Component;

import java.util.List;

public interface IFlightService {
  List<BusyFlightsResponse> searchProviderFlights(BusyFlightsRequest request);
}