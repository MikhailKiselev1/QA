package tests.api;

import data.request.LoginRequest;
import data.request.LoginUnRequest;
import data.response.*;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class APITests {

    @Test
    public void testsAvatarsNames() {
        /**
         * Если в Assert size() и count() будут не равны,
         * то значит, что метод distinct() удалил повторяющиеся
         * имена "avatar" и элементы не уникальны.
         * */
        User user = given()
                .contentType("application/json")
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().as(User.class);

        List<String> avatar = new ArrayList<>();
        user.getData().stream().forEach(el -> avatar.add(el.getAvatar()));

        Assert.assertTrue(avatar.size() == avatar.stream().distinct().count(), "Элементы avatar не уникальны");

    }


    @Test
    public void testLoginSuccessful(){
        LoginRequest loginRequest = new LoginRequest("eve.holt@reqres.in", "cityslicka");

        given()
                .when()
                .body(loginRequest)
                .contentType("application/json")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .extract().as(LoginResponse.class);
    }

    @Test
    public void testLoginUnSuccessful(){
        LoginUnRequest loginUnRequest = new LoginUnRequest("sydney@fife");

        given()
                .when()
                .body(loginUnRequest)
                .contentType("application/json")
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(400)
                .extract().as(LoginUnResponse.class);
    }


    @Test
    public void testsListResource() {
        /**
         * Если в цикле предыдущий элемент больше действующего,
         * то цикл завершится и boolean станет false
         * */
        Resource resource = given()
                .contentType("application/json")
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().body()
                .statusCode(200)
                .extract().body().as(Resource.class);


        int year = 0;
        List<DataResource> resourceList = resource.getData();

        boolean value = false;
        for (int a = 0; a < resourceList.size(); a++) {
            if (resourceList.get(a).getYear() > year) {
                value = true;
                year = resourceList.get(a).getYear();
            } else {
                value = false;
                break;
            }
        }

        Assert.assertTrue(value, "Элементы year в List<DataResource> отсортированы не в порядке возрастания");

    }

}
