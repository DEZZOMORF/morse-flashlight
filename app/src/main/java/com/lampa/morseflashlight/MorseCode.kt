package com.lampa.morseflashlight

enum class MorseCode(private val char: Char) {
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
    ONE('1'),
    TWO('2'),
    THREE('3'),
    FOUR('4'),
    FIVE('5'),
    SIX('6'),
    SEVEN('7'),
    EIGHT('8'),
    NINE('9'),
    ZERO('0');

    val symbolsString: List<MorseSymbol>
        get() = when (this) {
            A -> listOf(MorseSymbol.DOT, MorseSymbol.DASH)
            B -> listOf(MorseSymbol.DASH, MorseSymbol.DOT,MorseSymbol.DOT,MorseSymbol.DOT)
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
            ONE -> listOf(MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            TWO -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
            THREE ->  listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH, MorseSymbol.DASH)
            FOUR ->  listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DASH)
            FIVE -> listOf(MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            SIX -> listOf(MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            SEVEN -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT, MorseSymbol.DOT)
            EIGHT -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT, MorseSymbol.DOT)
            NINE -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DOT)
            ZERO -> listOf(MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH, MorseSymbol.DASH)
        }

    companion object {
        fun convertCharToMorseCode(char: Char): List<MorseSymbol> {
            return values().find { it.char == char }?.symbolsString ?: emptyList()
        }
    }
}