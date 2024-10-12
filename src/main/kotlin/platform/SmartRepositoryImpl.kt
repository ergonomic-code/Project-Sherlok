package pro.azhidkov.training.project_sherlok.reading03.platform

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.jdbc.core.JdbcAggregateOperations
import org.springframework.data.jdbc.core.convert.EntityRowMapper
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.repository.support.SimpleJdbcRepository
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity
import org.springframework.data.relational.core.query.Query
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import pro.azhidkov.training.project_sherlok.platform.SmartRepository
import pro.azhidkov.training.project_sherlok.platform.queryForPage


class SmartRepositoryImpl<T : Any, ID>(
    private val jdbcAggregateTemplate: JdbcAggregateOperations,
    private val operations: NamedParameterJdbcOperations,
    private val entity: RelationalPersistentEntity<T>,
    jdbcConverter: JdbcConverter,
) : SimpleJdbcRepository<T, ID>(jdbcAggregateTemplate, entity, jdbcConverter), SmartRepository<T, ID> {

    protected val rowMapper = EntityRowMapper(entity, jdbcConverter)

    override fun findPage(query: Query, pageRequest: Pageable): Page<T> {
        return jdbcAggregateTemplate.findAll(query, entity.type, pageRequest)
    }

    override fun findPage(
        query: String,
        paramMap: Map<String, Any?>,
        pageRequest: Pageable,
    ): Page<T> {
        //  RowMapper из SDJ
        return operations.queryForPage(query, paramMap, pageRequest, rowMapper)
    }

}

private fun Query.pageRequest() =
    if (limit == -1) Pageable.unpaged()
    else PageRequest.of((offset / limit).toInt(), limit, sort)
