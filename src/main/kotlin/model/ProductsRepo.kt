package pro.azhidkov.training.project_sherlok.model

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.stereotype.Repository
import pro.azhidkov.training.project_sherlok.platform.SmartRepository

@Repository
interface ProductsRepo : SmartRepository<Product, Long> {

    @Query(
        """
            SELECT * FROM product
            WHERE 
                (name ilike '%' || :name || '%' OR :name IS NULL) OR 
                (description ilike '%' || :description || '%' OR :description IS NULL)
        """
    )
    fun search(name: String?, description: String?): Iterable<Product>

}
