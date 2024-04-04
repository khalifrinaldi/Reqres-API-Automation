package data.steps;

import data.utilities.Helper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import java.io.InputStream;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresAPIStep {

    private Response response;

    private String requestBody = "";
    private String PROPERTYFILE = "src/test/resources/constants.properties";

    @Given("User send {string} to the API {string}")
    public void userSendGETToTheAPI(String method, String url) {
        switch (method) {
            case "GET" :
                response = given().get(Helper.getPropertyValue(PROPERTYFILE, url));
                break;
            case "POST" :
                response = given().header("Content-Type", "application/json").body(requestBody).when()
                        .post(Helper.getPropertyValue(PROPERTYFILE, url));
                break;
            case "PATCH" :
                response = given().header("Content-Type", "application/json").body(requestBody).when()
                        .patch(Helper.getPropertyValue(PROPERTYFILE, url));
                break;
        }
    }

    @Then("the status code is {int}")
    public void theStatusCodeIs(int code) {
        response.then().assertThat().statusCode(code);
    }

    @Then("response content type is JSON")
    public void responseContentTypeIsJSON() {
        response.then().assertThat().contentType(ContentType.JSON);
    }

    @Then("validate schema json response {string}")
    public void validateSchemaJsonResponse(String json){
        InputStream jsonschema = getClass().getClassLoader().getResourceAsStream("jsonschema/" + json + ".json");
        response.then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(jsonschema));
    }

    @Then("the body response content should be matched string")
    public void theBodyResponseContentShouldBeMatchedString(DataTable table){
        List<List<String>> data = table.asLists();
        for (int i = 1; i < data.size(); i++) {
            response.then().assertThat().body(data.get(i).get(0), equalTo((data.get(i).get(1))));
        }
    }

    @Then("the body response content should be matched integer")
    public void theBodyResponseContentShouldBeMatchedInteger(DataTable table){
        List<List<String>> data = table.asLists();
        for (int i = 1; i < data.size(); i++) {
            response.then().assertThat().body(data.get(i).get(0), equalTo(Integer.parseInt(data.get(i).get(1))));
        }
    }

    @Given("User setup bodyuser has request body")
    public void userSetupBodyuserHasRequestBody(String body) {
        requestBody = body;
    }

    @Then("the body response content should be not null")
    public void theBodyResponseContentShouldBeNotNull(DataTable table){
        List<List<String>> data = table.asLists();
        for (int i = 1; i < data.size(); i++) {
            response.then().assertThat().body(data.get(i).get(0), notNullValue());
        }
    }
}
