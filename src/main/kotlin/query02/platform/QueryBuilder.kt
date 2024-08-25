package pro.azhidkov.training.project_sherlok.query02.platform

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

infix fun String.isLikeIfNotNull(value: String?): Criteria =
    if (value != null) {
        Criteria.where(this).like("%$value%")
    } else {
        Criteria.empty()
    }

infix fun String.isILike(value: String): Criteria =
    Criteria.where(this).like("%$value%").ignoreCase(true)

infix fun String.isILikeIfNotNull(value: String?): Criteria =
    if (value != null) {
        Criteria.where(this).like("%$value%").ignoreCase(true)
    } else {
        Criteria.empty()
    }

infix fun <T, V> KProperty1<T, V>.isLikeIfNotNull(value: String?): Criteria {
    return if (value != null) {
        this.columnName().isLike(value)
    } else {
        Criteria.empty()
    }
}

infix fun <T, V> KProperty1<T, V>.isILikeIfNotNull(value: String?): Criteria {
    return if (value != null) {
        this.columnName().isILike(value)
    } else {
        Criteria.empty()
    }
}

infix fun <T, V> KProperty1<T, V>.`in`(values: Set<V>): Criteria {
    return Criteria.where(this.name)
        .`in`(values)
}

fun KProperty1<*, *>.columnName() =
    this.annotations.filterIsInstance<Column>()
        .map { it.value }
        .firstOrNull()
        ?: this.name