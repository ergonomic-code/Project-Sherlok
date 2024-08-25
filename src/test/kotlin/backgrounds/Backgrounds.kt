package pro.azhidkov.training.project_sherlok.backgrounds

import org.springframework.data.jdbc.core.mapping.AggregateReference
import org.springframework.stereotype.Component
import pro.azhidkov.training.project_sherlok.fixture.ProducersObjectMother.aProducer
import pro.azhidkov.training.project_sherlok.fixture.ProductsObjectMother
import pro.azhidkov.training.project_sherlok.model.Producer
import pro.azhidkov.training.project_sherlok.model.ProducersRepo
import pro.azhidkov.training.project_sherlok.model.Product
import pro.azhidkov.training.project_sherlok.model.ProductsRepo

data class AProductFixture(
    val product: Product,
    val producer: Producer
)

@Component
class Backgrounds(
    private val producersRepo: ProducersRepo,
    private val productsRepo: ProductsRepo
) {

    fun createAProduct(): AProductFixture {
        val producer = producersRepo.save(aProducer())
        val product = productsRepo.save(ProductsObjectMother.aProduct(producer = AggregateReference.to(producer.id)))
        return AProductFixture(product, producer)
    }

    fun createAProducts(count: Int): List<AProductFixture> {
        return (1..count).map { createAProduct() }
    }

    fun addProducts(products: List<Product>) {
        val needProducer = products.any { it.producer.id == 0L }
        var toSave = products
        if (needProducer) {
            val producer = producersRepo.save(aProducer())
            toSave = toSave.map {
                if (it.producer.id == 0L) it.copy(producer = AggregateReference.to(producer.id)) else it
            }
        }

        productsRepo.saveAll(toSave)
    }

}