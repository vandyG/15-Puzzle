package games.gameOfFifteen

/*
 * This function should return the parity of the permutation.
 * true - the permutation is even
 * false - the permutation is odd
 * https://en.wikipedia.org/wiki/Parity_of_a_permutation

 * If the game of fifteen is started with the wrong parity, you can't get the correct result
 *   (numbers sorted in the right order, empty cell at last).
 * Thus the initial permutation should be correct.
 */
fun isEven(permutation: List<Int>): Boolean {

    val hasZero = permutation.contains(0)
    var curr = 0
    var transpositions = 0

    /*fun update(index: Int) {
        list[curr] = true
        curr = permutation[index] - 1
        transpositions++
    }*/

//    if (curr != permutation[curr] - 1 && !list[curr]) {
//        curr = permutation[curr] - 1
//        transpositions++
//    } else {
//
//    }

    val list = mutableListOf<Boolean>()
    repeat(permutation.size) { list.add(false) }



    while (list.any { !it }) {
        /* when {
             list[curr] -> transpositions--.also { curr = list.indexOfFirst { !it } }
             curr != permutation[curr] - 1 -> update(curr)
             else -> list.set(curr, true).also { curr = list.indexOfFirst { !it } }
         }*/

        val perIndex = if(hasZero) permutation[curr] else permutation[curr] - 1

        if (!list[curr]) {
            list[curr] = true
            if (!list[perIndex] && curr != perIndex) {
                curr = perIndex
                transpositions++
                continue
            }
        }
        curr = list.indexOfFirst { !it }
    }
    return (transpositions % 2) == 0
}