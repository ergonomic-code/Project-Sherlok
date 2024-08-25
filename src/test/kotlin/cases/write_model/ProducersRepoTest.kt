package pro.azhidkov.training.project_sherlok.cases.write_model

import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.repository.findByIdOrNull
import pro.azhidkov.training.project_sherlok.fixture.ProducersObjectMother.aProducer
import pro.azhidkov.training.project_sherlok.model.ProducersRepo
import pro.azhidkov.training.project_sherlok.platform.SherlokTest


@DisplayName("Репозиторий производителей")
class ProducersRepoTest(
    @Autowired val repo: ProducersRepo
) : SherlokTest() {

    @Test
    fun `должен сохранять агрегаты`() {
        // Сетап
        val producer = aProducer()

        // Действие
        val id = repo.save(producer).id

        // Проверка
        id shouldNotBe 0
        val loadedProducer = repo.findByIdOrNull(id)
        loadedProducer shouldNotBe null
        loadedProducer!!.name shouldBe producer.name
        loadedProducer.address shouldBe producer.address
    }

}