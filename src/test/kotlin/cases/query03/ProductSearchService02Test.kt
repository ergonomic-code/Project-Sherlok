package pro.azhidkov.training.project_sherlok.cases.query03

import io.kotest.inspectors.forOne
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.core.mapping.AggregateReference
import pro.azhidkov.training.project_sherlok.backgrounds.Backgrounds
import pro.azhidkov.training.project_sherlok.fixture.ProducersObjectMother
import pro.azhidkov.training.project_sherlok.fixture.ProductsObjectMother.aProduct
import pro.azhidkov.training.project_sherlok.fixture.faker
import pro.azhidkov.training.project_sherlok.platform.SherlokTest
import pro.azhidkov.training.project_sherlok.query03.ProductSearchService03


@Import(Backgrounds::class, ProductSearchService03::class)
@DisplayName("Операция поиска продукта - 03")
class ProductSearchService03Test(
    @Autowired private val backgrounds: Backgrounds,
    @Autowired private val productSearchService03: ProductSearchService03
) : SherlokTest() {

    @Test
    fun `должа возвращать все продукты, если не переданы никакие критерии поиска`() {
        // Сетап
        val productsCount = 3
        backgrounds.createAProducts(productsCount)

        // Действие
        val products = productSearchService03.findProducts(null, null, null, Pageable.unpaged())

        // Проверка
        products shouldHaveSize productsCount
    }

    @Test
    fun `должа возвращать запрошенный размер страницы, если не переданы никакие критерии поиска`() {
        // Сетап
        val productsCount = 10
        val pageSize = 5
        backgrounds.createAProducts(productsCount)

        // Действие
        val products = productSearchService03.findProducts(null, null, null, Pageable.ofSize(pageSize))

        // Проверка
        products shouldHaveSize pageSize
    }

    @Test
    fun `должна возвращать только продукты, которые содержат в имени или описании требуемые строки`() {
        // Сетап
        val nonMatchingText = "Non Matching"
        val nonMatching = aProduct(name = nonMatchingText, description = nonMatchingText)
        val namePart = "Matebook"
        val matchingByName = aProduct("Huawei $namePart 14")
        val descrPart = "высокопроизводительный"
        val matchingByDescr = aProduct(description = faker.lorem().sentence() + descrPart + faker.lorem().sentence())
        val producerNamePart = faker.company().name()
        val producer = backgrounds.createProducer(ProducersObjectMother.aProducer(name = "$producerNamePart Ltd."))
        val matchingByProducerName = aProduct(producer = AggregateReference.to(producer.id))
        backgrounds.addProducts(listOf(nonMatching, matchingByName, matchingByDescr, matchingByProducerName))

        // Действие
        val matchingProducts =
            productSearchService03.findProducts(
                name = namePart,
                description = descrPart,
                producerNamePart,
                Pageable.unpaged()
            )
                .toList()

        // Проверка
        matchingProducts.forOne { it.name shouldContain namePart }
        matchingProducts.forOne { it.description shouldContain descrPart }
        matchingProducts.forOne { it.producer.id shouldBe producer.id }
    }

}