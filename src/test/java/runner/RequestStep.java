package runner;

import factoryRequest.FactoryRequest;
import factoryRequest.RequestInfo;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import util.ConfigurationEnv;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.equalTo;

public class RequestStep {
    Response response;
    RequestInfo info = new RequestInfo();
    Map<String,String> varMap=  new HashMap<>();

    @Given("I have access todo.ly")
    public void iHaveAccessTodoLy() {
        String credential = Base64.getEncoder().encodeToString((ConfigurationEnv.user+":"+ConfigurationEnv.pwd).getBytes());
        info.setHeaders("Authorization","Basic "+credential);
    }

    @When("I send {word} {} with body")
    public void iSendPOSTApiProjectsJsonWithBody(String method, String url, String body) {
        info.setUrl(ConfigurationEnv.host +replaceValueVariable(url)).setBody(body);
        if (varMap.containsKey("authToken")) {
            info.setHeaders("Token",  varMap.get("authToken"));
        }
        response = FactoryRequest.make(method).send(info);
    }

    @Then("response code should be {int}")
    public void responseCodeShouldBe(int expectedResult) {
        response.then().statusCode(expectedResult);
        System.out.println("Response body: " + response.getBody().asString());
    }

    @And("the attribute {word} {string} should be {string}")
    public void theAttributeShouldBe(String type, String attribute, String expectedResult) {
        if (type.contains("boolean") && !attribute.contains("TokenString"))
            response.then().body(attribute,equalTo(Boolean.valueOf(expectedResult)));
        else
            if (!attribute.contains("TokenString"))
                response.then().body(attribute,equalTo(expectedResult));
    }

    @And("save {string} in the variable {string}")
    public void saveInTheVariable(String attribute, String variable) {
        varMap.put(variable, response.then().extract().path(attribute)+"");
    }

    private String replaceValueVariable(String value){
        for (String key:varMap.keySet()){
            value= value.replace(key,varMap.get(key));
        }
        return value;
    }
}
