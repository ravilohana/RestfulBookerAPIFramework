package http;

import config.PropertyConfig;
import config.PropertyUtil;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.http.Method;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

/**
 *
 * The reason of making it abstract so that any other API class will have to extend this.
 * We will try to bound it so that we cannot create the object directly of base API.
 */

public abstract class BaseApi {


    private final RequestSpecification requestSpecification;


    public BaseApi() {
        this.requestSpecification = RestAssured.given()
                .baseUri(PropertyUtil.getConfig().baseUrl())
                .filter(new AllureRestAssured());
    }


    protected BaseApi setRequestBody(Object object){
        this.requestSpecification.body(object);
        return  this;
    }

    protected  BaseApi setContentType(ContentType contentType){
        this.requestSpecification.contentType(contentType);
        return this;
    }

    protected  void setBasePath(String basePath){
        this.requestSpecification.basePath(basePath);
    }

    protected  void setPathParam(String paramName,Object paramsValue){
        this.requestSpecification.pathParam(paramName,paramsValue);
    }

    protected  BaseApi setBasicAuth(String username,String password){
        this.requestSpecification.auth().preemptive().basic(username, password);
        return this;
    }

    public BaseApi logAllRequestData(){
        this.requestSpecification.filter(new RequestLoggingFilter());
        return this;
    }


    public BaseApi logSpecificRequestDetail(LogDetail logDetail){
        this.requestSpecification.filter(new RequestLoggingFilter(logDetail));
        return this;
    }


    public BaseApi logAllResponseData(){
        this.requestSpecification.filter(new ResponseLoggingFilter());
        return this;
    }

    public BaseApi logSpecificResponseDetail(LogDetail logDetail){
        this.requestSpecification.filter(new ResponseLoggingFilter(logDetail));
        return this;
    }


    protected Response sendRequest(Method methodType){
        final RequestSpecification when = this.requestSpecification.when();
        return switch(methodType){
            case GET -> when.get();
            case PUT -> when.put();
            case POST -> when.post();
            case DELETE -> when.delete();
            case PATCH -> when.patch();
            default -> throw new IllegalArgumentException("Invalid input http method type not supported" + methodType);
        };
    }








}
