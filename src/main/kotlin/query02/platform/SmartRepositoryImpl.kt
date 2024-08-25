package pro.azhidkov.training.project_sherlok.reading03.platform

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository
import org.springframework.data.mapping.PersistentEntity
import org.springframework.data.relational.core.query.Query
import pro.azhidkov.training.project_sherlok.query02.platform.SmartRepository


class SmartRepositoryImpl<T : Any, ID>(
    private val jdbcAggregateTemplate: JdbcAggregateOperations,
    private val entity: PersistentEntity<T, *>,
    jdbcConverter: JdbcConverter,
) : SimpleJdbcRepository<T, ID>(jdbcAggregateTemplate, entity, jdbcConverter), SmartRepository<T, ID> {

    override fun findPage(query: Query, pageRequest: Pageable): Page<T> {
        return jdbcAggregateTemplate.findAll(query, entity.type, pageRequest)
    }

}