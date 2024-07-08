package util;


import pojo.request.BookingDates;
import pojo.request.CreateBookingRequest;

import java.util.HashMap;
import java.util.Map;

public class ApiRequestHelper {

    // Create booking api request payload using map
    public static Map<String, Object> createBookingPayload(String firstName,
                                                           String lastName,
                                                           int totalPrice,
                                                           boolean depositPaid,
                                                           String checkInDate,
                                                           String checkOutDate,
                                                           String additionalNeeds) {

        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> bookingDates = new HashMap<>();

        bookingDates.put("checkin", checkInDate);
        bookingDates.put("checkout", checkOutDate);
        requestBody.put("firstname", firstName);
        requestBody.put("lastname", lastName);
        requestBody.put("totalprice", totalPrice);
        requestBody.put("depositpaid", depositPaid);
        requestBody.put("bookingdates", bookingDates);
        requestBody.put("additionalneeds", additionalNeeds);


        return requestBody;


    }


    // Create booking api request payload using POJO's
    public static CreateBookingRequest createBookingRequestPOJO(String firstName,
                                                                String lastName,
                                                                int totalPrice,
                                                                boolean depositPaid,
                                                                String checkInDate,
                                                                String checkOutDate,
                                                                String additionalNeeds) {

        CreateBookingRequest createBookingRequest = new CreateBookingRequest();
        createBookingRequest.setFirstName(firstName);
        createBookingRequest.setLastName(lastName);
        createBookingRequest.setTotalPrice(totalPrice);
        createBookingRequest.setDepositPaid(depositPaid);

        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckIn(checkInDate);
        bookingDates.setCheckOut(checkOutDate);

        createBookingRequest.setBookingDates(bookingDates);

        createBookingRequest.setAdditionalNeeds(additionalNeeds);


        return createBookingRequest;


    }
}
