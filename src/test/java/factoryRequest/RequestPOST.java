package factoryRequest;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RequestPOST implements IRequest {
    @Override
    public Response send(RequestInfo requestInfo) {
        Response response = given()
                                .headers(requestInfo.getHeaders())
                                //.params(requestInfo.getParams()) para añadir parametros usamos otro Map
                                .body(requestInfo.getBody())
                                .log().all().
                            when()
                                .post(requestInfo.getUrl());
        response.then().log().all();
        return response;
    }
}
