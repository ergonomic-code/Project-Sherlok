package pro.azhidkov.training.project_sherlok.model

import org.springframework.data.jdbc.repository.query.Query
import org.springframework.stereotype.Repository
import pro.azhidkov.training.project_sherlok.platform.SmartRepository

@Repository
interface ProductsRepo : SmartRepository<Product, Long> {

    // До SDJ 3 единственным вариантом без ручных запросов в JdbcTemplate или подключения был
    // только анти паттерн OR NULL - не повторяйте этого дома
    // см. https://www.youtube.com/watch?v=h927yUAdTD0
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
