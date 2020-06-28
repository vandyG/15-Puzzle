package games.gameOfFifteen

import board.Cell
import board.Direction
import board.GameBoard
import board.createGameBoard
import games.game.Game

/*
 * Implement the Game of Fifteen (https://en.wikipedia.org/wiki/15_puzzle).
 * When you finish, you can play the game by executing 'PlayGameOfFifteen'.
 */

fun newGameOfFifteen(initializer: GameOfFifteenInitializer = RandomGameInitializer()): Game =
        GameOfFifteen(initializer)

class GameOfFifteen(private val initializer: GameOfFifteenInitializer) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        val initialList: MutableList<Int?> = initializer.initialPermutation.toMutableList()
        initialList.add(null)

        var i = 0

        board.run {
            getAllCells().forEach { cell -> this[cell] = initialList[i++] }
        }
    }

    override fun canMove(): Boolean = true

    override fun hasWon(): Boolean {
        var seq = 1
        return board.all { it == null || it == seq++ }
    }

    override fun processMove(direction: Direction) = with(board) {
        val emptyCell = find { it == null }!!
        val cellToMove = emptyCell.getNeighbour(direction.reversed()) ?: return
        set(emptyCell, get(cellToMove)).also { set(cellToMove, null) }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i,j)) }
}