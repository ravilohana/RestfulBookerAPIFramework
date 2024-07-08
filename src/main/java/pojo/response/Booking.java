package pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import pojo.request.BookingDates;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Booking{

	@JsonProperty("firstname")
	private String firstName;

	@JsonProperty("lastname")
	private String lastName;

	@JsonProperty("totalprice")
	private int totalPrice;

	@JsonProperty("depositpaid")
	private boolean depositPaid;

	@JsonProperty("bookingdates")
	private BookingDates bookingDates;

	@JsonProperty("additionalneeds")
	private String additionalNeeds;

}