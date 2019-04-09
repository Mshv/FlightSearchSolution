package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.CrazyAirService;
import com.travix.medusa.busyflights.service.FlightService;
import com.travix.medusa.busyflights.service.IFlightService;
import com.travix.medusa.busyflights.service.ToughJetService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


import java.util.*;
import java.util.stream.Stream;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BusyFlightsApplicationTests {

  FlightService flightService = null;
  private List<IFlightService> iFlightServiceList = null;

  @Mock
  CrazyAirService crazyAirService;

  @Mock
  ToughJetService toughJetService;

  @Before
  public void setUp() {
    iFlightServiceList = Arrays.asList(crazyAirService, toughJetService);
    flightService = new FlightService(iFlightServiceList);

  }

  @Test
  public void searchFlightsTest() throws Exception {
    BusyFlightsRequest request = new BusyFlightsRequest();
    List<BusyFlightsResponse> busyFlightsResponseList = new ArrayList<>();

    List<IFlightService> mockedIFlightServiceResult = Arrays.asList(
        new IFlightService() {
          @Override
          public List<BusyFlightsResponse> searchProviderFlights(BusyFlightsRequest request) {
            BusyFlightsResponse busyFlightsResponse = new BusyFlightsResponse(
                "Air Canada",
                "CrazyAir",
                2100,
                "KUL",
                "AMS",
                "2019-04-04T10:15:30",
                "2019-04-08T18:25:35");
            busyFlightsResponseList.add(busyFlightsResponse);
            BusyFlightsResponse busyFlightsResponse2 = new BusyFlightsResponse(
                "US Canada",
                "CrazyAir",
                2000,
                "KUL",
                "AMS",
                "2019-05-04T10:15:30",
                "2019-05-08T18:25:35");
            busyFlightsResponseList.add(busyFlightsResponse2);
            return busyFlightsResponseList;
          }
        }
    );

    for (IFlightService provider : mockedIFlightServiceResult) {
      when(provider.searchProviderFlights(any(BusyFlightsRequest.class))).thenReturn(mockedIFlightServiceResult.get(0).searchProviderFlights(any(BusyFlightsRequest.class)));
    }
    assertEquals(mockedIFlightServiceResult, flightService.searchFlights(request));
    verify(iFlightServiceList.get(0)).searchProviderFlights(any(BusyFlightsRequest.class));

//    FlightService.IFlightService webServiceApi = mock(FlightService.IFlightService.class);
//    FlightService.IFlightService webServiceApi2 = mock(FlightService.IFlightService.class);
//    FlightService underTest = new FlightService(Arrays.asList(webServiceApi, webServiceApi2));
//    FlightService.Request request = new FlightService.Request();
//    request.setName("Amir");
//    List<FlightService.Response> mockedWSResult = Arrays.asList(
//        new FlightService.Response("Ali"),
//        new FlightService.Response("Jafar")
//    );
//    List<FlightService.Response> mockedWSResult2 = Arrays.asList(
//        new FlightService.Response("Amir"),
//        new FlightService.Response("Mehdi")
//    );
//    when(webServiceApi.searchFlights(any(FlightService.Request.class))).thenReturn(mockedWSResult);
//    when(webServiceApi2.searchFlights(any(FlightService.Request.class))).thenReturn(mockedWSResult2);
//
//    List<FlightService.Response> responseList = underTest.searchFlights(request);
//    //responseList.sort(Comparator.comparing(Controller.Response::getLastName));
//
//    assertEquals(4, responseList.size());
//    assertEquals("Ali", responseList.get(0).getLastName());
//    assertEquals("Amir", responseList.get(1).getLastName());
//    assertEquals("Jafar", responseList.get(2).getLastName());
//    assertEquals("Mehdi", responseList.get(3).getLastName());
  }
}
