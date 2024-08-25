package pro.azhidkov.training.project_sherlok.query02.infra

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import pro.azhidkov.training.project_sherlok.ProjectSherlokApp
import pro.azhidkov.training.project_sherlok.reading03.platform.SmartRepositoryImpl


@EnableJdbcRepositories(
    repositoryBaseClass = SmartRepositoryImpl::class,
    basePackageClasses = [ProjectSherlokApp::class]
)
@Configuration
class SdjConf : AbstractJdbcConfiguration()