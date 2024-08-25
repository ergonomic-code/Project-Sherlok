package pro.azhidkov.training.project_sherlok.fixture

import pro.azhidkov.training.project_sherlok.model.Producer


object ProducersObjectMother {

    fun aProducer(
        name: String = faker.company().name(),
        address: String = faker.address().streetAddress(),
    ) = Producer(
        name = name,
        address = address
    )

}