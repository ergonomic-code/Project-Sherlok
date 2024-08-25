package pro.azhidkov.training.project_sherlok.cases.query01

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
import pro.azhidkov.training.project_sherlok.backgrounds.Backgrounds
import pro.azhidkov.training.project_sherlok.fixture.ProductsObjectMother.aProduct
import pro.azhidkov.training.project_sherlok.fixture.faker
import pro.azhidkov.training.project_sherlok.platform.SherlokTest
import pro.azhidkov.training.project_sherlok.query01.ProductSearchService01


@Import(Backgrounds::class, ProductSearchService01::class)
@DisplayName("Операция поиска продукта - 01")
class ProductSearchService02Test(
    @Autowired private val backgrounds: Backgrounds,
    @Autowired private val productSearchService01: ProductSearchService01
) : SherlokTest() {

    @Test
    fun `должа возвращать все продукты, если не переданы никакие критерии поиска`() {
        // Сетап
        val productsCount = 3
        backgrounds.createAProducts(productsCount)

        // Действие
        val products = productSearchService01.findProducts(null, null)

        // Проверка
        products shouldHaveSize productsCount
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
        val matchingProducts = productSearchService01.findProducts(name = namePart, description = descrPart)
            .toList()

        // Проверка
        matchingProducts.forAll { Matcher.any(contain(namePart), contain(descrPart)) }
        matchingProducts.forAny { it.name shouldContain namePart }
        matchingProducts.forAny { it.description shouldContain descrPart }
    }

}