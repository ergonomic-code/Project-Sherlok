package pro.azhidkov.training.project_sherlok.platform

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationEventPublisher
import org.springframework.data.jdbc.core.convert.DataAccessStrategy
import org.springframework.data.jdbc.core.convert.JdbcConverter
import org.springframework.data.jdbc.repository.support.JdbcRepositoryFactoryBean
import org.springframework.data.relational.core.dialect.Dialect
import org.springframework.data.relational.core.mapping.RelationalMappingContext
import org.springframework.data.repository.Repository
import org.springframework.data.repository.core.support.RepositoryFactorySupport
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations
import java.io.Serializable


class SmartJdbcRepositoryFactoryBean<R : Repository<E, ID>, E, ID : Serializable>(
    repositoryInterface: Class<out R>
) : JdbcRepositoryFactoryBean<R, E, ID>(
    repositoryInterface
) {

    @Autowired
    private lateinit var dataAccessStrategy: DataAccessStrategy

    @Autowired
    private lateinit var context: RelationalMappingContext

    @Autowired
    private lateinit var converter: JdbcConverter

    @Autowired
    private lateinit var dialect: Dialect

    @Autowired
    private lateinit var publisher: ApplicationEventPublisher

    @Autowired
    private lateinit var operations: NamedParameterJdbcOperations

    override fun doCreateRepositoryFactory(): RepositoryFactorySupport {
        return SmartJdbcRepositoryFactory(dataAccessStrategy, context, converter, dialect, publisher, operations)
    }

}