package pro.azhidkov.training.project_sherlok.query03

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.relational.core.sql.*
import org.springframework.data.relational.core.sql.Comparison.create
import org.springframework.data.relational.core.sql.SQL.literalOf
import org.springframework.data.relational.core.sql.render.SqlRenderer
import org.springframework.stereotype.Service


@Service
class ProductSearchService03(
    private val productsRepo: ProductsSmartRepo03
) {

    fun findProducts(
        name: String?,
        description: String?,
        producerName: String?,
        pageRequest: Pageable
    ): Page<Product> {
        val product = Table.create("product")
        val producer = Table.create("producer")

        val query = Select.builder()

            .select(product.all())
            .from(product)
            .join(producer).on(create(product["producer"], "=", producer["id"]))

            .where(TrueCondition.INSTANCE)
            .andWhereIfPresent(name) { Like.create(product["name"], literalOf("%$it%")) }
            .andWhereIfPresent(description) { Like.create(product["description"], literalOf("%$it%")) }
            .andWhereIfPresent(producerName) { Like.create(producer["name"], literalOf("%$it%")) }

            .build()
            .let { SqlRenderer.create().render(it) }

        val params = mapOf(
            "name" to name,
            "description" to description,
            "producerName" to producerName,
        )

        return productsRepo.findPage(query, params, pageRequest)
    }


}

private fun SelectBuilder.SelectWhereAndOr.andWhereIfPresent(
    param: String?,
    condition: (String) -> Condition
): SelectBuilder.SelectWhereAndOr =
    if (param != null) {
        this.or(condition(param))
    } else {
        this
    }

private fun Table.all() =
    AsteriskFromTable.create(this)

private operator fun Table.get(columnName: String): Expression =
    Column.create(columnName, this)
