package com.dezzomorf.morseflashlight.`object`

enum class MorseCode(val char: Char) {
    A('A'),
    B('B'),
    C('C'),
    D('D'),
    E('E'),
    F('F'),
    G('G'),
    H('H'),
    I('I'),
    J('J'),
    K('K'),
    L('L'),
    M('M'),
    N('N'),
    O('O'),
    P('P'),
    Q('Q'),
    R('R'),
    S('S'),
    T('T'),
    U('U'),
    V('V'),
    W('W'),
    X('X'),
    Y('Y'),
    Z('Z'),

    А('А'),
    Б('Б'),
    В('В'),
    Г('Г'),
    Ґ('Ґ'),
    Д('Д'),
    Е('Е'),
    Є('Є'),
    Ж('Ж'),
    З('З'),
    И('И'),
    І('І'),
    Ї('Ї'),
    Й('Й'),
    К('К'),
    Л('Л'),
    М('М'),
    Н('Н'),
    О('О'),
    П('П'),
    Р('Р'),
    С('С'),
    Т('Т'),
    У('У'),
    Ф('Ф'),
    Х('Х'),
    Ц('Ц'),
    Ч('Ч'),
    Ш('Ш'),
    Щ('Щ'),
    Ю('Ю'),
    Я('Я'),
    Ь('Ь'),

    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    ZERO('0'),
    SPACE(' ');

    private val morseSymbols: List<MorseSymbol>
        get() = when (this) {
            A -> listOf(MorseSymbol.DOT, MorseSymbol.DASH)
            B -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            C -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            D -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            E -> listOf(MorseSymbol.DOT)
            F -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            G -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            H -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            I -> listOf(MorseSymbol.DOT, MorseSymbol.DOT)
            J -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            K -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH)
            L -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            M -> listOf(MorseSymbol.DASH, MorseSymbol.DASH)
            N -> listOf(MorseSymbol.DASH, MorseSymbol.DOT)
            O -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            P -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            Q -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH)
            R -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            S -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            T -> listOf(MorseSymbol.DASH)
            U -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            V -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            W -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            X -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            Y -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            Z -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)

            А -> listOf(MorseSymbol.DOT, MorseSymbol.DASH)
            Б -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            В -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            Г -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            Ґ -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            Д -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            Е -> listOf(MorseSymbol.DOT)
            Є -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            Ж -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            З -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            И -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            І -> listOf(MorseSymbol.DOT, MorseSymbol.DOT)
            Ї -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            Й -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            К -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH)
            Л -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            М -> listOf(MorseSymbol.DASH, MorseSymbol.DASH)
            Н -> listOf(MorseSymbol.DASH, MorseSymbol.DOT)
            О -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            П -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            Р -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            С -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            Т -> listOf(MorseSymbol.DASH)
            У -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            Ф -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            Х -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            Ц -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT)
            Ч -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            Ш -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH)
            Щ -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            Ю -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            Я -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DASH)
            Ь -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)

            ONE -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            TWO -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            THREE -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            FOUR -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            FIVE -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            SIX -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            SEVEN -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            EIGHT -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            NINE -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            ZERO -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            SPACE -> listOf(MorseSymbol.WORD_END)
        }

    companion object {
        fun getMorseCode(char: Char): MorseCode? = values().find { it.char == char.uppercaseChar() }

        fun getMorseSymbols(morseCode: MorseCode): List<MorseSymbol> {
            return when (morseCode) {
                SPACE -> SPACE.morseSymbols
                else -> morseCode.morseSymbols.toMutableList().apply { add(MorseSymbol.LETTER_END) }
            }
        }
    }
}