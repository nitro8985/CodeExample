package utils;

import com.github.javafaker.Faker;
import models.PersonalData;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.Locale;

public class PersonalDataProvider {

    private static final Faker faker = new Faker(new Locale("ru-RU"));


    @DataProvider
    public static Object[][] fakeData(Method method) {
        return method.getName().contains("AcceptsValidDataTest")
                ? new PersonalData[][]{{generateData()}, {generateData()}, {generateData()}}
                : new PersonalData[][]{{generateData()}};
    }

    private static PersonalData generateData() {
        String password = faker.bothify("??#??##?");
        return new PersonalData(faker.name().fullName(),
                faker.bothify("???####@gmail.com"),
                faker.numerify("89#########"),
                password,
                password);
    }
}
