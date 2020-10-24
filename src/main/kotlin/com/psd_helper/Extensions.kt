package com.psd_helper

//region String fill utilities
fun String.spaceFill(length: Int) = this.charFill(length, ' ')
fun String.zeroFill(length: Int) = this.charFill(length, '0')
fun String.ca2Fill(length: Int) = this.charFill(length, this[0])

fun String.charFill(length: Int, char: Char): String {
    var toFill = length - this.length
    toFill = if(toFill<=0) 0 else toFill

    return char.toString().repeat(toFill) + this
}
//endregion

//region Math write utilities
fun Int.superScript() = this.toString().map { Constants.SUPERSCRIPT[it.toString().toInt()]}.joinToString("")
fun String.superScript() = this.toInt().superScript()

fun Int.subScript() = this.toString().map { Constants.SUPERSCRIPT[it.toString().toInt()]}.joinToString("")
fun String.subScript() = this.toInt().subScript()
//endregion

//string reverse chunking
fun String.rtlChunk(length: Int) = this.reversed().chunked(length).map { it.reversed() }.reversed()
fun CharSequence.rtlChunk(length: Int) = this.toString().rtlChunk(length)