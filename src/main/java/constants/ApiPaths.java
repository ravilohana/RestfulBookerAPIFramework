package constants;


import io.restassured.http.Method;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static io.restassured.http.Method.*;

@AllArgsConstructor
@Getter
public enum ApiPaths {



    GET_BOOKING("/booking/{bookingId}",GET),
    GET_BOOKING_IDS("/booking",GET),
    CREATE_BOOKING("/booking",POST),
    UPDATE_BOOKING("/booking/{bookingId}",PUT),
    PARTIAL_UPDATE_BOOKING("/booking/{bookingId}",PATCH),
    DELETE_BOOKING("/booking/{bookingId}",DELETE);

    private final String apiPath;
    private final Method httpMethodType;





}
