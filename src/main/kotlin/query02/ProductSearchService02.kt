package pro.azhidkov.training.project_sherlok.query02

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Criteria
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import pro.azhidkov.training.project_sherlok.model.Product
import pro.azhidkov.training.project_sherlok.platform.OR
import pro.azhidkov.training.project_sherlok.platform.isLikeIfNotNull


@Service
class ProductSearchService02(
    private val productsRepo: ProductsSmartRepo
) {

    @Suppress("UNUSED_VARIABLE")
    fun findProducts(
        name: String?,
        description: String?,
        pageRequest: Pageable
    ): Page<pro.azhidkov.training.project_sherlok.query02.Product> {

        // Пример простого критерия
        val criteriaExample = Criteria
            .where("name").like(name ?: "")
            .and("description").like(description ?: "")

        // Пример динамического критерия
        val plainByNameOrDescr = Criteria.empty()
            .let { if (name != null) it.or(Criteria.where("name").like("%$name%").ignoreCase(true)) else it }
            .let {
                if (description != null) it.or(
                    Criteria.where("description").like("%$description%").ignoreCase(true)
                ) else it
            }

        // Пример динамического критерия на базе DSL
        val byNameOrDescr =
            (Product::name isLikeIfNotNull name) OR
                    (Product::description isLikeIfNotNull description)

        return productsRepo.findPage(Query.query(byNameOrDescr), pageRequest)
    }

}