package pro.azhidkov.training.project_sherlok.query02

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Query
import org.springframework.stereotype.Service
import pro.azhidkov.training.project_sherlok.model.Product
import pro.azhidkov.training.project_sherlok.query02.platform.OR
import pro.azhidkov.training.project_sherlok.query02.platform.isLikeIfNotNull


@Service
class ProductSearchService02(
    private val productsRepo: ProductsSmartRepo
) {

    fun findProducts(
        name: String?,
        description: String?,
        pageRequest: Pageable
    ): Page<pro.azhidkov.training.project_sherlok.query02.Product> {
        val byNameOrDescr =
            (Product::name isLikeIfNotNull name) OR
                    (Product::description isLikeIfNotNull description)

        return productsRepo.findPage(Query.query(byNameOrDescr), pageRequest)
    }

}