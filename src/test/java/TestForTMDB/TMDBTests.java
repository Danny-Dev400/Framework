package TestForTMDB;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.*;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TMDBTests {

    static String apiKey = "";
    static String token = "";
    static JSONObject obj;
    static Session newSession;
    int listToDelete = 8193333;

    @BeforeClass
    public static void setUp(){
        baseURI = "https://api.themoviedb.org/3";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./src/test/java/Secret/credentials.json"))
        {
            obj = (JSONObject) jsonParser.parse(reader);
            apiKey = (String) obj.get("apiKey");

            token = given().params("api_key",apiKey)
                    .when().get("/authentication/token/new")
                    .then().statusCode(200)
                    .and().extract().path("request_token");

            System.out.println("El token es" + token);
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    @DisplayName("Validating token to login")
    @Description("The page needs a validate token to do a login action")
    public void Test1(){
        baseURI = "https://api.themoviedb.org/3";
        JSONObject user = new JSONObject();
        user.put("username",obj.get("username"));
        user.put("password",obj.get("password"));
        user.put("request_token",token);

        given().contentType(ContentType.JSON).accept(ContentType.JSON).body(user.toJSONString())
                .when().post("/authentication/token/validate_with_login?api_key="+apiKey)
                .then().statusCode(200);

        //System.out.println(token);
    }

    @Test
    @DisplayName("Sign in with valid user")
    @Description("Extract the user and password of a valid user and log in TMDB")
    public void Test2(){
        baseURI = "https://api.themoviedb.org/3";
        JSONObject createSession = new JSONObject();
        createSession.put("request_token",token);

        String sessionId = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(createSession.toJSONString())
                .when().post("/authentication/session/new?api_key="+apiKey)
                .then().statusCode(200).log().body()
                .and().extract().path("session_id");

        newSession = Session.getInstance(sessionId);
    }

    @Test
    @DisplayName("Create List")
    @Description("Creating a list")
    public void Test3(){
        baseURI = "https://api.themoviedb.org/3";
        JSONObject list = new JSONObject();
        list.put("name","My firt list");
        list.put("description","This is the description of my second");
        list.put("language","en");

        listToDelete = given().contentType(ContentType.JSON).accept(ContentType.JSON).body(list.toJSONString())
                .when().post("/list?api_key="+apiKey+"&session_id="+newSession.getSessionId())
                .then().statusCode(201)
                .and().extract().path("list_id");

        System.out.println(listToDelete);
    }

    @Test
    @DisplayName("Get Details List")
    @Description("Get request to a list of the user")
    public void Test4(){
        baseURI = "https://api.themoviedb.org/3";

        given().contentType(ContentType.JSON)
                .when().get("/list/"+listToDelete+"?api_key="+apiKey)
                .then().statusCode(200).log().body();
    }

    @Test
    @DisplayName("Add movie to List")
    @Description("Adding a movie into a list")
    public void Test5(){
        baseURI = "https://api.themoviedb.org/3";
        JSONParser jsonParser = new JSONParser();
        try (FileReader reader = new FileReader("./src/test/java/Data/data.json")) {
            JSONObject obj2 = (JSONObject) jsonParser.parse(reader);
            JSONArray jArr = (JSONArray) obj2.get("movies");
            Iterator<String> iterator = jArr.iterator();

            while(iterator.hasNext()){
                JSONObject movie = new JSONObject();
                movie.put("media_id",iterator.next());

                given().contentType(ContentType.JSON).accept(ContentType.JSON).body(movie.toJSONString())
                        .when().post(String.format("/list/%s/add_item",listToDelete)+"?api_key="+apiKey+"&session_id="+newSession.getSessionId())
                        .then().statusCode(201);

                System.out.println(baseURI+String.format("/list/%s/add_item",listToDelete)+"?api_key="+apiKey+"&session_id="+newSession.getSessionId());
                System.out.println(movie);
            }
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }

    }
}
