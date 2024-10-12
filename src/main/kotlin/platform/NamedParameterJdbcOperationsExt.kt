package pro.azhidkov.training.project_sherlok.platform

import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations


fun <T : Any> NamedParameterJdbcOperations.queryForPage(
    baseSql: String,
    filterParams: Map<String, Any?>,
    pageRequest: Pageable,
    rowMapper: RowMapper<T>
): Page<T> {
    // руками собираем запрос кол-ва сущностей
    val count = this.queryForObject(
        "SELECT count(*) FROM ($baseSql) AS data",
        filterParams,
        Long::class.java
    )!!
    if (count == 0L) {
        val emptyPage: List<T> = emptyList()
        return PageImpl(emptyPage, pageRequest, count)
    }

    // руками собираем запрос страницы
    val dataQuery = buildString {
        appendLine("SELECT * FROM($baseSql) data")
        if (pageRequest.sort.isSorted) {
            append("ORDER BY ")
            appendLine(pageRequest.sort.map { it.property + " " + it.direction.name }.joinToString(", "))
        }
        if (pageRequest.isPaged) {
            appendLine(
                """
                        LIMIT :pageSize
                        OFFSET :offset
                        """
            )
        }
    }
    val pagingParams = if (pageRequest.isPaged) {
        mapOf("pageSize" to pageRequest.pageSize, "offset" to pageRequest.offset)
    } else {
        emptyMap()
    }

    // пробрасываем запрос с пагинацией, параметры и rowMapper агрегата в NamedParameterJdbcOperations
    val data = this.query(
        dataQuery,
        filterParams + pagingParams,
        rowMapper
    )

    return PageImpl(data, pageRequest, count)
}
