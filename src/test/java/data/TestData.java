package data;

import com.github.javafaker.Faker;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site/";

    //Генерируем фейковые данные для пользователя
    public static final Faker user = new Faker();
    public static final String EMAIL = "email" + System.currentTimeMillis() + "@mail.ru";
    public static final String PASSWORD = user.regexify("[0-9]{4}");
    public static final String NAME = user.name().firstName() + System.currentTimeMillis();

    //Тестовые данные для заказа
    public static final List<String> INGREDIENTS = Arrays.asList("61c0c5a71d1f82001bdaaa74", "61c0c5a71d1f82001bdaaa75");
}
