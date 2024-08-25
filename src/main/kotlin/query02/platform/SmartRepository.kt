package pro.azhidkov.training.project_sherlok.query02.platform

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.query.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.NoRepositoryBean


@NoRepositoryBean
interface SmartRepository<T, ID> : CrudRepository<T, ID> {

    fun findPage(query: Query, pageRequest: Pageable): Page<T>

}