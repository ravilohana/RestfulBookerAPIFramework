package pojo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class Bookingdates{

	@JsonProperty("checkin")
	private String checkIn;

	@JsonProperty("checkout")
	private String checkOut;

}