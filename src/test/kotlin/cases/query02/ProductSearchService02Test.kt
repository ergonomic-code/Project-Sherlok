package pro.azhidkov.training.project_sherlok.cases.query02

import io.kotest.inspectors.forAll
import io.kotest.inspectors.forAny
import io.kotest.matchers.Matcher
import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.compose.any
import io.kotest.matchers.string.contain
import io.kotest.matchers.string.shouldContain
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.springframework.data.domain.Pageable
import pro.azhidkov.training.project_sherlok.backgrounds.Backgrounds
import pro.azhidkov.training.project_sherlok.fixture.ProductsObjectMother.aProduct
import pro.azhidkov.training.project_sherlok.fixture.faker
import pro.azhidkov.training.project_sherlok.platform.SherlokTest
import pro.azhidkov.training.project_sherlok.query02.ProductSearchService02


@Import(Backgrounds::class, ProductSearchService02::class)
@DisplayName("Операция поиска продукта - 02")
class ProductSearchService02Test(
    @Autowired private val backgrounds: Backgrounds,
    @Autowired private val productSearchService02: ProductSearchService02
) : SherlokTest() {

    @Test
    fun `должа возвращать все продукты, если не переданы никакие критерии поиска`() {
        // Сетап
        val productsCount = 3
        backgrounds.createAProducts(productsCount)

        // Действие
        val products = productSearchService02.findProducts(null, null, Pageable.unpaged())

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
        val products = productSearchService02.findProducts(null, null, Pageable.ofSize(pageSize))

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
        backgrounds.addProducts(listOf(nonMatching, matchingByName, matchingByDescr))

        // Действие
        val matchingProducts =
            productSearchService02.findProducts(name = namePart, description = descrPart, Pageable.unpaged())
                .toList()

        // Проверка
        matchingProducts.forAll { Matcher.any(contain(namePart), contain(descrPart)) }
        matchingProducts.forAny { it.name shouldContain namePart }
        matchingProducts.forAny { it.description shouldContain descrPart }
    }

}