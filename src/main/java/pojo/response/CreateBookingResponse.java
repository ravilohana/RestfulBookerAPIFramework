package pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class CreateBookingResponse {

	@JsonProperty("booking")
	private Booking booking;

	@JsonProperty("bookingid")
	private int bookingId;


}