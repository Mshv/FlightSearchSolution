package com.travix.medusa.busyflights;

import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsRequest;
import com.travix.medusa.busyflights.domain.busyflights.BusyFlightsResponse;
import com.travix.medusa.busyflights.service.FlightService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

@SpringBootApplication
public class BusyFlightsApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext applicationContext = SpringApplication.run(BusyFlightsApplication.class, args);
		FlightService flightService = applicationContext.getBean(FlightService.class);

		BusyFlightsRequest request = new BusyFlightsRequest();
		request.setNumberOfPassengers(3);
		request.setOrigin("KUL");
		request.setDestination("AMS");
		request.setDepartureDate("2019-04-04T10:15:30");
		request.setReturnDate("2019-04-08T18:25:35");

		List<BusyFlightsResponse> responses = null;
		try {
			responses = flightService.searchFlights(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		responses.forEach(response -> System.out.println("response : Airline:  "+ response.getAirline() +
				                                                       " Supplier: "+ response.getSupplier()+
																															 " Fare:     "+response.getFare()));
	}
}
