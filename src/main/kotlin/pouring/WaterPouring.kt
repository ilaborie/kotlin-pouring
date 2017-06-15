package pouring

data class Glass(val capacity: Int, val current: Int = 0) {

    init {
        require(capacity > 0)
        require(current >= 0)
        require(capacity >= current)
    }

    override fun toString() = "$current/$capacity"
    val missing: Int
        get() = capacity - current

    fun empty(): Glass = copy(current = 0)

    fun fill(): Glass = copy(current = capacity)

    operator fun plus(quantity: Int): Glass = copy(current = Math.min(capacity, current + quantity))
    operator fun minus(quantity: Int): Glass = copy(current = Math.max(0, current - quantity))
    fun isEmpty() = current == 0
    fun isFill() = current == capacity
}

data class State(val glasses: List<Glass>) {
    override fun toString() = glasses.joinToString()

    fun process(move: Move): State = State(
            glasses.mapIndexed { index, glass ->
                when (move) {
                    is Empty -> if (index == move.glassIndex) glass.empty() else glass
                    is Fill -> if (index == move.glassIndex) glass.fill() else glass
                    is Pour -> when (index) {
                        move.fromGlassIndex -> glass - Math.min(glass.current, glasses[move.toGlassIndex].missing)
                        move.toGlassIndex -> glass + Math.min(glass.missing, glasses[move.fromGlassIndex].current)
                        else -> glass
                    }
                }
            })

    fun availableMoves(): List<Move> {
        val indexNotEmpty = glasses
                .mapIndexed { index, glass -> Pair(index, glass) }
                .filterNot { (_, glass) -> glass.isEmpty() }
                .map { it.first }

        val indexNotFill = glasses
                .mapIndexed { index, glass -> Pair(index, glass) }
                .filterNot { (_, glass) -> glass.isFill() }
                .map { it.first }

        val empties: List<Move> = indexNotEmpty.map { Empty(it) }
        val fills: List<Move> = indexNotFill.map { Fill(it) }
        val pours: List<Move> = indexNotEmpty
                .flatMap { from -> indexNotFill.map { to -> Pair(from, to) } }
                .filterNot { (from, to) -> from == to }
                .map { (from, to) -> Pour(from, to) }

        return empties + fills + pours
    }
}

sealed class Move
data class Empty(val glassIndex: Int) : Move() {
    override fun toString() = "Empty $glassIndex      "
}

data class Fill(val glassIndex: Int) : Move() {
    override fun toString() = "Fill $glassIndex       "
}

data class Pour(val fromGlassIndex: Int, val toGlassIndex: Int) : Move() {
    override fun toString() = "Pour $fromGlassIndex into $toGlassIndex"
}

typealias StateWithHistory = Pair<State, List<Move>>

fun solve(from: State, to: State): List<Move> {
    tailrec fun solveAux(currents: List<StateWithHistory>, visited: Set<State>): List<Move> {
        val result: StateWithHistory? = currents.find { it.first == to }
        if (result != null) {
            return result.second
        }

        val next = currents.flatMap { (state, history) ->
            state.availableMoves()
                    .map { move -> Pair(state.process(move), history + move) }
                    .filterNot { (state, _) -> visited.contains(state) }
        }
        val newVisited = visited + next.map { it.first }

        return solveAux(next, newVisited)
    }
    return solveAux(listOf(Pair(from, emptyList())), emptySet())
}

fun main(args: Array<String>): Unit {
    val initialState = State(listOf(Glass(8), Glass(5), Glass(3)))
    val expectedState = State(listOf(Glass(capacity = 8, current = 4), Glass(5), Glass(3)))

    val result = solve(from = initialState, to = expectedState)
    println("Solve $initialState --> $expectedState in ${result.size} moves:")
    var currentState = initialState
    result.forEach { move ->
        val nextState = currentState.process(move)
        println("$move  ---> $nextState")
        currentState = nextState
    }
}
