package apis;

import http.BaseApi;
import io.restassured.http.Method;
import io.restassured.response.Response;

import static constants.ApiPaths.DELETE_BOOKING;
import static constants.ApiPaths.GET_BOOKING;

public class DeleteBookingApi extends BaseApi {


    public DeleteBookingApi(){
        super();
        super.logAllRequestData().logAllResponseData();

    }

    public Response deleteBookingApiById(int bookingID,String username,String password){
        super.setBasePath(DELETE_BOOKING.getApiPath());
        super.setPathParam("bookingId",bookingID);
        super.setBasicAuth(username,password);
        return super.sendRequest(DELETE_BOOKING.getHttpMethodType());
    }





}
