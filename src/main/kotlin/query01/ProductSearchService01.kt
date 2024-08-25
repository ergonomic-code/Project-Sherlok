package pro.azhidkov.training.project_sherlok.query01

import org.springframework.stereotype.Service
import pro.azhidkov.training.project_sherlok.model.Product
import pro.azhidkov.training.project_sherlok.model.ProductsRepo


@Service
class ProductSearchService01(
    private val productsRepo: ProductsRepo
) {

    fun findProducts(name: String?, description: String?): Iterable<Product> {
        return productsRepo.search(name, description)
    }

}