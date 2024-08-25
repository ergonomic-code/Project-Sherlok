package pro.azhidkov.training.project_sherlok.query02

import org.springframework.stereotype.Repository
import pro.azhidkov.training.project_sherlok.query02.platform.SmartRepository

@Repository
interface ProductsSmartRepo : SmartRepository<Product, Long>
