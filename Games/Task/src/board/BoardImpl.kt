package board

import board.Direction.*
import java.lang.IllegalArgumentException

open class SquareBoardImpl(w: Int) : SquareBoard {

    override val width: Int = w
    private val cells: List<Cell>

    init {
        cells = (1..w).flatMap { i -> (1..w).map { j -> Cell(i, j) } }
    }

    override fun getCellOrNull(i: Int, j: Int): Cell? = cells.firstOrNull { it.i == i && it.j == j }

    override fun getCell(i: Int, j: Int): Cell = getCellOrNull(i, j)
            ?: throw IllegalArgumentException("Board does not contain the cell")

    override fun getAllCells(): Collection<Cell> = cells

    override fun getRow(i: Int, jRange: IntProgression): List<Cell> = jRange.filter { it in 1..width }.map { j -> cells[((i - 1) * width) + j - 1] }

    override fun getColumn(iRange: IntProgression, j: Int): List<Cell> = iRange.filter { it in 1..width }.map { i -> cells[((i - 1) * width) + j - 1] }

    override fun Cell.getNeighbour(direction: Direction): Cell? = when (direction) {
        UP -> getCellOrNull(i - 1, j)
        DOWN -> getCellOrNull(i + 1, j)
        LEFT -> getCellOrNull(i, j - 1)
        RIGHT -> getCellOrNull(i, j + 1)
    }
}

class GameBoardImpl<T>(w: Int) : SquareBoardImpl(w), GameBoard<T> {
    private val board: MutableMap<Cell, T?> = getAllCells().associateBy({ it }, { null }).toMutableMap()

    override fun get(cell: Cell): T? = board[cell]

    override fun set(cell: Cell, value: T?) {
        board[cell] = value
    }

    override fun filter(predicate: (T?) -> Boolean): Collection<Cell> = board.filterValues(predicate).keys

    override fun find(predicate: (T?) -> Boolean): Cell? = board.filterValues(predicate).keys.firstOrNull()

    override fun any(predicate: (T?) -> Boolean): Boolean = board.values.any(predicate)

    override fun all(predicate: (T?) -> Boolean): Boolean = board.values.all(predicate)
}

fun createSquareBoard(width: Int): SquareBoard = SquareBoardImpl(width)
fun <T> createGameBoard(width: Int): GameBoard<T> = GameBoardImpl<T>(width)

