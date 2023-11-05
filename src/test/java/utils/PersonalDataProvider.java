package utils;

import com.github.javafaker.Faker;
import models.PersonalData;
import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PersonalDataProvider {

    @DataProvider
    public static Object[][] fakeData(Method method) {
        Faker faker = new Faker(new Locale("ru-RU"));
        List<PersonalData> pd = new ArrayList<>();
        for (int i = 0; i < 3; ++i) {
            String password = faker.bothify("??#??##?");
            pd.add(new PersonalData(faker.name().fullName()
                    , faker.bothify("???####@gmail.com")
                    , faker.numerify("89#########")
                    , password
                    , password));
        }
        return method.getName().contains("AcceptsValidDataTest")
                ? new PersonalData[][]{{pd.get(0)}, {pd.get(1)}, {pd.get(2)}}
                : new PersonalData[][]{{pd.get(0)}};
    }
}
