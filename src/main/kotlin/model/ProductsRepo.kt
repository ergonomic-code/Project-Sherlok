package pro.azhidkov.training.project_sherlok.model

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductsRepo : CrudRepository<Product, Long>
