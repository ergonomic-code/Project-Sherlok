package pro.azhidkov.training.project_sherlok.fixture

import net.datafaker.Faker
import java.util.*


var faker = Faker(Locale.of("ru-RU"), Random(1))
    private set

fun resetFaker() {
    faker = Faker(Locale.of("ru-RU"), Random(1))
}