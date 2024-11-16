package id.my.andka.bstkj.model

sealed class PadButton(val label: String) {
    object Clear : PadButton("C")
    object Delete : PadButton("⌫")
    object Decimal : PadButton(".")
    object DoubleZero : PadButton("00")
    data class Number(val value: String) : PadButton(value)
}