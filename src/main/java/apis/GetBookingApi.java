package apis;

import constants.ApiPaths;
import http.BaseApi;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static constants.ApiPaths.GET_BOOKING;
import static constants.ApiPaths.GET_BOOKING_IDS;

public class GetBookingApi extends BaseApi {

    public GetBookingApi() {
        super();
        super.logAllRequestData().logAllResponseData();
    }

    public Response getAllBookingIds(){
        super.setBasePath(GET_BOOKING_IDS.getApiPath());
        return super.sendRequest(GET_BOOKING_IDS.getHttpMethodType());
    }


    public Response getBookingById(int bookingID){
        super.setBasePath(GET_BOOKING.getApiPath());
        super.setPathParam("bookingId",bookingID);
        return  super.sendRequest(GET_BOOKING.getHttpMethodType());
    }


}
