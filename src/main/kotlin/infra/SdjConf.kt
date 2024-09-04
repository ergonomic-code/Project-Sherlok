package pro.azhidkov.training.project_sherlok.infra

import org.springframework.context.annotation.Configuration
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories
import pro.azhidkov.training.project_sherlok.ProjectSherlokApp
import pro.azhidkov.training.project_sherlok.platform.SmartJdbcRepositoryFactoryBean
import pro.azhidkov.training.project_sherlok.platform.SmartRepository


@EnableJdbcRepositories(
    repositoryFactoryBeanClass = SmartJdbcRepositoryFactoryBean::class,
    repositoryBaseClass = SmartRepository::class,
    basePackageClasses = [ProjectSherlokApp::class]
)
@Configuration
class SdjConf : AbstractJdbcConfiguration()