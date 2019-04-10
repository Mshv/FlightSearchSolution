package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.FlightService;
import com.travix.medusa.busyflights.service.IFlightService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

//@RunWith(SpringRunner.class)
//@SpringBootTest
@RunWith(MockitoJUnitRunner.class)
public class BusyFlightsApplicationTests {

  FlightService flightService = null;

  @Mock
  IFlightService crazyAirService;

  @Mock
  IFlightService toughJetService;

  @Before
  public void setUp() {
    List<IFlightService> iFlightServiceList = Arrays.asList(crazyAirService, toughJetService);
    flightService = spy(new FlightService(iFlightServiceList));
  }

  @Test
  public void numberOfPassengersTest() throws IllegalArgumentException {
    BusyFlightsRequest busyFlightsRequest = mock(BusyFlightsRequest.class);
    when(busyFlightsRequest.getNumberOfPassengers()).thenReturn(5);
    Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
      flightService.searchFlights(busyFlightsRequest);
    });
    assertEquals("Number Of Passengers - Maximum 4 passengers", exception.getMessage());
  }

  @Test
  public void searchFlightsTest() throws Exception {
    BusyFlightsRequest request = new BusyFlightsRequest();
    List<BusyFlightsResponse> busyFlightsResponseList = new ArrayList<>();

    List<BusyFlightsResponse> crazyResponseList = new ArrayList<>();
    BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse(
        "Air Canada",
        "CrazyAir",
        2100,
        "KUL",
        "AMS",
        "2019-04-04T10:15:30",
        "2019-04-08T18:25:35");
    crazyResponseList.add(busyFlightsResponse);
    BusyFlightsResponse busyFlightsResponse2 = new BusyFlightsResponse(
        "US Canada",
        "CrazyAir",
        2000,
        "KUL",
        "AMS",
        "2019-05-04T10:15:30",
        "2019-05-08T18:25:35");
    crazyResponseList.add(busyFlightsResponse2);
    when(crazyAirService.searchProviderFlights(any(BusyFlightsRequest.class)))
        .thenReturn(crazyResponseList);

    List<BusyFlightsResponse> toughJetResponseList = new ArrayList<>();
    BusyFlightsResponse busyFlightsResponse3 = new BusyFlightsResponse(
        "Air Canada",
        "CrazyAir",
        2150,
        "KUL",
        "AMS",
        "2019-04-04T10:15:30",
        "2019-04-08T18:25:35");
    toughJetResponseList.add(busyFlightsResponse3);
    BusyFlightsResponse busyFlightsResponse4 = new BusyFlightsResponse(
        "US Canada",
        "CrazyAir",
        2300,
        "KUL",
        "AMS",
        "2019-05-04T10:15:30",
        "2019-05-08T18:25:35");
    toughJetResponseList.add(busyFlightsResponse4);
    when(toughJetService.searchProviderFlights(any(BusyFlightsRequest.class))).thenReturn(toughJetResponseList);

    busyFlightsResponseList.add(busyFlightsResponse2);
    busyFlightsResponseList.add(busyFlightsResponse);
    busyFlightsResponseList.add(busyFlightsResponse3);
    busyFlightsResponseList.add(busyFlightsResponse4);

    assertEquals(busyFlightsResponseList, flightService.searchFlights(request));
    verify(crazyAirService).searchProviderFlights(any(BusyFlightsRequest.class));
    verify(toughJetService).searchProviderFlights(any(BusyFlightsRequest.class));
  }
}
