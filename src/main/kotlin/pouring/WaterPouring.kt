package pouring

// Glass
data class Glass(val capacity: Int, val current: Int = 0) {

    init {
        require(capacity > 0)
        require(current in 0..capacity)
    }

    override fun toString() = "$current/$capacity"
    val missing: Int
        get() = capacity - current

    fun empty(): Glass = copy(current = 0)

    fun fill(): Glass = copy(current = capacity)

    operator fun plus(quantity: Int): Glass = copy(current = Math.min(capacity, current + quantity))
    operator fun minus(quantity: Int): Glass = copy(current = Math.max(0, current - quantity))
    val isEmpty by lazy { current == 0 }
    val isFill by lazy { current == capacity }
}

// States
data class State(val glasses: List<Glass>) {
    constructor(vararg array: Glass) : this(array.toList())

    override fun toString() = glasses.joinToString()

    fun process(move: Move): State = State(
            glasses.mapIndexed { index, glass ->
                when (move) {
                    is Empty -> if (index == move.index) glass.empty() else glass
                    is Fill -> if (index == move.index) glass.fill() else glass
                    is Pour -> when (index) {
                        move.from -> glass - Math.min(glass.current, glasses[move.to].missing)
                        move.to -> glass + Math.min(glass.missing, glasses[move.from].current)
                        else -> glass
                    }
                }
            })

    fun availableMoves(): List<Move> {
        val indexNotEmpty = glasses
                .mapIndexed { index, glass -> index to glass }
                .filterNot { (_, glass) -> glass.isEmpty }
                .map { it.first }

        val indexNotFill = glasses
                .mapIndexed { index, glass -> index to glass }
                .filterNot { (_, glass) -> glass.isFill }
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

// Moves
sealed class Move

data class Empty(val index: Int) : Move() {
    init {
        require(index >= 0)
    }

    override fun toString() = "Empty $index      "
}

data class Fill(val index: Int) : Move() {
    init {
        require(index >= 0)
    }

    override fun toString() = "Fill $index       "
}

data class Pour(val from: Int, val to: Int) : Move() {
    init {
        require(from >= 0)
        require(to >= 0)
        require(from != to)
    }

    override fun toString() = "Pour $from into $to"
}

// Alias type to improve readability
typealias StateWithHistory = Pair<State, List<Move>>

fun solve(from: State, to: State): List<Move> {
    operator fun State.plus(move: Move): State = this.process(move)

    tailrec fun solveAux(currents: List<StateWithHistory>, visited: Set<State> = emptySet()): List<Move> {
        // Check end state
        val result: StateWithHistory? = currents.find { it.first == to }
        if (result != null) {
            return result.second
        }

        // Compute values for recursion
        val next = currents.flatMap { (state, history) ->
            state.availableMoves()
                    .map { move -> (state + move) to (history + move) }
                    .filterNot { (state, _) -> visited.contains(state) }
        }
        val newVisited = visited + next.map { it.first }

        // Avoid infinite loop
        if (currents == next) {
            val statesVisited = visited.joinToString(limit = 10, prefix = "{", postfix = "}")
            throw IllegalStateException("Cannot create new states, $statesVisited")
        }

        // recursion
        return solveAux(next, newVisited)
    }

    // check glasses size
    if (from.glasses.size != to.glasses.size) {
        throw IllegalArgumentException("Invalid initial and final state: number of glasses should be the same")
    }
    // Check same capacity
    val sameCapacity = from.glasses.zip(to.glasses)
            .all { (glass1, glass2) -> glass1.capacity == glass2.capacity }
    if (!sameCapacity) {
        throw IllegalArgumentException("Invalid initial and final state: glasses capacity should bbe the same")
    }

    return solveAux(listOf(from to emptyList()))
}

// Main
fun main(args: Array<String>): Unit {
    val initialState = State(Glass(8), Glass(5), Glass(3))
    val expectedState = State(Glass(capacity = 8, current = 4), Glass(5), Glass(3))

    val result = solve(from = initialState, to = expectedState)

    println("Solve $initialState --> $expectedState in ${result.size} moves:")
    result.fold(initialState) { acc, move ->
        val nextState = acc.process(move)
        println("$move  ---> $nextState") // Acceptable side-effect
        nextState
    }
}
