package catalysts.school.ccc29

import catalysts.output
import java.util.*

private val buildings = LinkedList<Int>()
private val seen = HashSet<Point>()

private fun preprocess(row: Int, column: Int) {
    val p = Point(row, column)

    val height = map[row][column]
    if (height == 0) return
    if (p in seen) return
    seen.add(p)

    buildings.push(1)
    preprocess(row + 1, column, height)
    preprocess(row - 1, column, height)
    preprocess(row, column + 1, height)
    preprocess(row, column - 1, height)
}

private fun preprocess(row: Int, column: Int, height: Int) {
    val p = Point(row, column)
    if (p in seen) return

    if (row !in 0 until rows) return
    if (column !in 0 until columns) return

    if (map[row][column] == height) {
        buildings.push(buildings.pop() + 1)
        seen.add(p)
        preprocess(row + 1, column, height)
        preprocess(row - 1, column, height)
        preprocess(row, column + 1, height)
        preprocess(row, column - 1, height)
    }
}

fun main(): Unit = Scanner(System.`in`).use { input ->
    rows = input.nextInt()
    columns = input.nextInt()

    map = Array(rows) { IntArray((columns)) }

    for (row in 0 until rows) {
        for (column in 0 until columns) {
            val height = input.nextInt()
            map[row][column] = height
        }
    }

    for (row in 0 until rows) {
        for (column in 0 until columns) {
            preprocess(row, column)
        }
    }

    println(buildings)

    val list = buildings.sortedBy { it }
            .mapIndexed { index, i -> Pair(index, i) }

    output(list.joinToString(" ") { "${it.first} ${it.second}" })
}