package pojo.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


@Data
public class Bookingdates{

	@JsonProperty("checkin")
	private String checkIn;

	@JsonProperty("checkout")
	private String checkOut;

}