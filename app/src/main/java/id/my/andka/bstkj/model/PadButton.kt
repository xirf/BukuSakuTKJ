package id.my.andka.bstkj.model

sealed class PadButton(val label: String) {
    object Clear : PadButton("C")
    object Delete : PadButton("⌫")
    object DoubleZero : PadButton("00")
    object TripleZero : PadButton("000")
    data class Number(val value: String) : PadButton(value)
}