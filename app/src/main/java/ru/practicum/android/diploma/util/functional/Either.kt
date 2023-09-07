package ru.practicum.android.diploma.util.functional

/**
 * Класс [Either] (Либо) используется, когда нам нужно передавать один из двух типов объектов между
 * функциями/классами и т.д.
 * Объекты данного класса могут быть экземплярами либо [Left], либо [Right]
 * В соответствии с устоявшейся практикой, [Left] используется для представления ошибок,
 * а [Right] для передачи успешного результата выполнения операции.
 *
 * @see [Left]
 * @see [Right]
 * */
sealed class Either<out L, out R> {

    /** Преставляет левую половину [Either], которая хранит тип ошибки */
    data class Left<out L>(val a: L) : Either<L, Nothing>()

    /** Преставляет правую половину [Either], которая хранит успешный результат */
    data class Right<out R>(val b: R) : Either<Nothing, R>()

    /**
     * Возвращает true, если это экземпляр [Right], в противном случае false
     */
    val isRight get() = this is Right<R>

    /**
     * Возвращает true, если это экземпляр [Left], в противном случае false
     */
    val isLeft get() = this is Left<L>


    /**
     * Вызывает fnL, если это экземпляр [Left], в противном случае вызывает fnR
     *
     * То есть мы передаем в качестве аргументов две функции, одна для обработки ошибки,
     * а вторая для успешного результата, и выбор происходит внутри класса.
     *
     * В результате мы избегаем многоступенчатых условных операторов внутри viewModel или fragment,
     * а код обработки ошибок и данных отделен друг от друга.
     *
     */
    fun fold(fnL: (L) -> Any, fnR: (R) -> Any): Any =
        when (this) {
            is Left -> fnL(a)
            is Right -> fnR(b)
        }

    companion object {
        fun <L, R> left(value: L): Either<L, R> = Left(value)
        fun <L, R> right(value: R): Either<L, R> = Right(value)
    }
}

/**
 * Вызывает переданную функцию только если этот экземпляр - Right,
 * если Left - то просто возвращает нетронутое значение.
 * */
fun <T, L, R> Either<L, R>.flatMap(fn: (R) -> Either<L, T>) =
    when (this) {
        is Either.Left -> Either.Left(a)
        is Either.Right -> fn(b)
    }
