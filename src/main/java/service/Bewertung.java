package service;

import com.github.fge.jsonschema.SchemaVersion;
import com.github.fge.jsonschema.cfg.ValidationConfiguration;
import com.github.fge.jsonschema.main.JsonSchemaFactory;
import cucumber.api.PendingException;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class Bewertung {

    private static boolean isInitialised = false;
    private static HashMap config = new HashMap();
    private static ArrayList<String> manufacturers;
    private static ArrayList<String> mainTypes;
    private RequestSpecification request;
    private Response response;
    /**
     * Set JSON Schema checking to draft-08
     * see https://json-schema.org/
     */
    private JsonSchemaFactory jsonSchemaFactory = JsonSchemaFactory.newBuilder()
            .setValidationConfiguration(
                    ValidationConfiguration.newBuilder()
                            .setDefaultVersion(SchemaVersion.DRAFTV4).freeze())
            .freeze();

    /**
     * Initialise Environment config
     * Set baseUri
     */
    Bewertung() {
        if (!isInitialised) {
            try {
                Reader reader = Files.newBufferedReader(Paths.get("src", "test", "Resources", "/Environment.csv"));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());

                csvParser.getRecords().forEach(e -> config.put(e.get("Name"), e.get("Value")));

            } catch (IOException e) {
                throw new PendingException("Please check config file ");
            }


            isInitialised = true;
        }
        request = given().baseUri(config.get("baseUri").toString());
    }

    public void getCustomerKey() {
        request.param("wa_key", config.get("wa_key").toString());

    }

    /**
     * Set random locale from any available in Java
     */
    public void getLocale() {
        String locale = getRandom(
                Arrays.stream(DateFormat.getAvailableLocales()).
                        map(e -> Objects.toString(e, null)).
                        collect(Collectors.toCollection(ArrayList::new)));
        request.param("locale", locale);


    }

    public void setIncorrectLocale() {
        request.param("locale", "######");
    }

    public void getRequest(String service, String type) {
        request.basePath(config.get(service).toString());
        switch (type) {
            case "GET":
                response = request.get();
                break;
            case "PUT":
                response = request.put();
                break;
            case "POST":
                response = request.post();
                break;
            default:
                request.get();
        }
    }

    public void checkStatus(String status) {
        response.then().assertThat().statusCode(Integer.parseInt(status));
    }

    public void checkStatus() {
        checkStatus("200");
    }

    public void checkJsonByScheme() {
        response.then().assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json").using(jsonSchemaFactory));

    }

    /**
     * Store Responce Data from service (wkda part)
     *
     * @param service name of the service
     */
    public void getDataListFromResponse(String service) {
        ArrayList dataList = new ArrayList<String>(new HashMap(response.jsonPath().get("wkda")).keySet());
        switch (service) {

            case "Makers":
                manufacturers = dataList;
                break;

            case "Models":
                mainTypes = dataList;
                break;

            default:
                throw new cucumber.api.PendingException("Need to implement part of the service");
        }
    }

    public void checkResponseMessage(String message) {
        assertEquals(response.jsonPath().get("message"), message);
    }

    public void selectServiceParameter(String service, boolean incorrect) {

        switch (service) {

            case "Maker":
                request.param("manufacturer",
                        incorrect ? "&^%%$%^" : getRandom(manufacturers));
                break;

            case "Model":
                request.param("main-type", incorrect ? "&^%%$%^" : getRandom(mainTypes));
                break;

            default:
                throw new cucumber.api.PendingException("Need to implement part of the service");
        }

    }

    public void checkWKDAResponceIsEmpty() {
        assertEquals(response.jsonPath().get("wkda").toString(), "{}");
    }

    private String getRandom(ArrayList<String> list) {

        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

}
