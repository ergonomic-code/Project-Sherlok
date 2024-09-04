package pro.azhidkov.training.project_sherlok.platform

import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.jdbc.core.JdbcAggregateTemplate
import org.springframework.data.jdbc.core.convert.DataAccessStrategy
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactory
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.relational.core.mapping.RelationalPersistentEntity
import org.springframework.data.repository.core.RepositoryInformation
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import pro.azhidkov.training.project_sherlok.reading03.platform.SmartRepositoryImpl


class SmartJdbcRepositoryFactory(
    dataAccessStrategy: DataAccessStrategy,
    private val context: RelationalMappingContext,
    private val converter: JdbcConverter,
    dialect: Dialect,
    publisher: ApplicationEventPublisher,
    private val operations: NamedParameterJdbcOperations
) : JdbcRepositoryFactory(dataAccessStrategy, context, converter, dialect, publisher, operations) {

    private val jdbcAggregateOperations = JdbcAggregateTemplate(publisher, context, converter, dataAccessStrategy)

    override fun getTargetRepository(repositoryInformation: RepositoryInformation): Any {
        @Suppress("UNCHECKED_CAST")
        return SmartRepositoryImpl<Any, Any>(
            jdbcAggregateOperations,
            operations,
            context.getPersistentEntity(repositoryInformation.domainType) as RelationalPersistentEntity<Any>,
            converter
        )
    }

}