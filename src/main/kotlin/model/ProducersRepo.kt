package pro.azhidkov.training.project_sherlok.model

import org.springframework.stereotype.Repository
import pro.azhidkov.training.project_sherlok.platform.SmartRepository


@Repository
interface ProducersRepo : SmartRepository<Producer, Long>