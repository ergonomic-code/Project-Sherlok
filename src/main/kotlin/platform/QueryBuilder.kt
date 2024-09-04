package pro.azhidkov.training.project_sherlok.platform

import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.query.Criteria
import kotlin.reflect.KProperty1


@Suppress("FunctionName")
infix fun Criteria.AND(criteria: Criteria): Criteria =
    when {
        this != Criteria.empty() && criteria != Criteria.empty() -> this.and(criteria)
        this == Criteria.empty() -> criteria
        else -> this
    }

@Suppress("FunctionName")
infix fun Criteria.OR(criteria: Criteria): Criteria =
    when {
        this != Criteria.empty() && criteria != Criteria.empty() -> this.or(criteria)
        this == Criteria.empty() -> criteria
        else -> this
    }

infix fun String.isLike(value: String): Criteria =
    Criteria.where(this).like("%$value%")

infix fun <T, V> KProperty1<T, V>.isLikeIfNotNull(value: String?): Criteria {
    return if (value != null) {
        this.columnName().isLike(value)
    } else {
        Criteria.empty()
    }
}

fun KProperty1<*, *>.columnName() =
    this.annotations.filterIsInstance<Column>()
        .map { it.value }
        .firstOrNull()
        ?: this.name