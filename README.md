Water Pouring Problem
===

See <https://www.youtube.com/watch?v=q6M_pco_5Vo> or <https://en.wikipedia.org/wiki/Water_pouring_puzzle>


Kotlin documentation
---

<https://kotlinlang.org/docs/reference/>

<https://kotlinlang.org/api/latest/jvm/stdlib/index.html>

Guideline (try to implements with immutable data structure)
---

01. Transform `HelloWorld` to a Kotlin class
02. Remove boilerplate

03. create a `Glass` data class with `capacity`, and `current` Int, with 0 as default value for `current`
04. create `empty` method returning another `Glass` using `copy` method into `Glass`
05. outside the class, create an extension method `fill` returning another `Glass` using `copy` method

06. create a `State` data class with a `List` of `Glass`
07. (bonus) create a secondary constructor for `State` with a `vararg` of `Glass`

08. create a `Move` class
09. create a `Empty` data class with a glass `index` (an Int) that extends the `Move` class
    override `toString` using String template
10. create a `Fill` data class with a glass `index` (an Int) that extends the `Move` class
    override `toString` using String template
11. create a `Pour` data class with `from` and `to` glass index that extends the `Move` class
    override `toString` using String template
12. (bonus) seal the `Move` class
13. (bonus) create a computed property (custom getter) to get the missing part into a `Glass` (`capacity - current`)

14. define a `solve` function with form and to `State` and returning a `List` of `Move`
    do not implement the function, use `TODO()` for the moment
15. create a `main` method, into this method
    create initial state 0/8, 0/5, and 0/3 `Glass`
    create final state 4/8, 0/5, and 0/3 `Glass`
16. call `solve` to obtain result
    display all moves using `forEach` with a lambda

17. create a `process` method that taking a `Move` a returning a new `State` into the `State`
    use `mapIndexed` on glasses, a `when` to match different `Move`
    Note: `if-then-else` is a expression (i.e. can return a value)
18. Into the `Glass` class (or with an extension method)
    create the method (operator) than allow the syntax `newGlass = glass + 4`
    create the method (operator) than allow the syntax `newGlass = glass - 3`
    use these operators in `State#process` method

19. create method `availableMoves`  returning `List` of available `Move` into the `State` Class
    Note: avoid empty an already emptied class
    Note: avoid fill an already filled class
    Note: you cannot pour a glass to itself
20. (bonus) create helper function `isEmpty` and `isFill` in `Glass` 
21. (bonus) transform helper function `isEmpty` and `isFill` to lazy val in `Glass` 

22. create a private `solveAux` recursive function
    with a `List` of `Pair` of `State` and `List` of `Move`,
    with a target `State`,
    and with a `Set` of already visited `State`
    returning the `List` of `Move`
    skip implementation with `TODO()`
23. implements `solve` function with the `solveAux` method
24. for the `sloveAux` real implementation 
    first implement the terminal case
25. then create the next `List` of `Pair<State, List<Move>>`
26. create the new `List` of visited `State`
27. finish the recursion
28. (bonus) use a `typealias` for `Pair<State, List<Move>>`
29. (bonus) use a `tailrec`
30. (bonus) make `solveAux` an internal function into `solve`, remove the `target` argument (closure)

31. (bonus) safety check for data class using `init` block
32. (bonus) safety check `solve` and `solveAux`

33. drink non-empty glass ;-)

A solution
---

```
Solve 0/8, 0/5, 0/3 --> 4/8, 0/5, 0/3 in 8 moves:
Fill 1       (0/8, 5/5, 0/3)
Pour 1 in 0  (5/8, 0/5, 0/3)
Fill 1       (5/8, 5/5, 0/3)
Pour 0 in 2  (2/8, 5/5, 3/3)
Empty 2      (2/8, 5/5, 0/3)
Pour 1 in 0  (7/8, 0/5, 0/3)
Pour 0 in 2  (4/8, 0/5, 3/3)
Empty 2      (4/8, 0/5, 0/3)
```