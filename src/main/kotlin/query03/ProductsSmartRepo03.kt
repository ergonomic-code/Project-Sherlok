package pro.azhidkov.training.project_sherlok.query03

import org.springframework.stereotype.Repository
import pro.azhidkov.training.project_sherlok.platform.SmartRepository

@Repository
interface ProductsSmartRepo03 : SmartRepository<Product, Long>
