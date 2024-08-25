package pro.azhidkov.training.project_sherlok.platform

import org.junit.jupiter.api.BeforeEach
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.test.context.ActiveProfiles
import pro.azhidkov.training.project_sherlok.fixture.resetFaker


@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJdbcTest
abstract class SherlokTest {

    @BeforeEach
    fun resetFakerInstance() {
        resetFaker()
    }

}