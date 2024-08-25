package pro.azhidkov.training.project_sherlok.fixture

import org.springframework.data.jdbc.core.mapping.AggregateReference
import pro.azhidkov.training.project_sherlok.model.Producer
import pro.azhidkov.training.project_sherlok.model.Product


object ProductsObjectMother {

    fun aProduct(
        name: String = faker.commerce().productName(),
        description: String = faker.lorem().sentence(),
        producer: AggregateReference<Producer, Long> = AggregateReference.to(0)
    ) = Product(
        name,
        description,
        producer
    )

}