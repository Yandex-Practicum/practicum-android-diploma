package ru.practicum.android.diploma.commonutils.searchsubstring

object AlgorithmKnuthMorrisPratt {

    fun searchSubstringsInString(text: String, substrings: String): Boolean {
        if (text.isEmpty() || substrings.isEmpty() || text.length < substrings.length) return false

        val _text = text.lowercase()
        val _substrings = substrings.lowercase()

        val prefixFun = prefixFunction(_text)
        val substringEntryIndices: MutableList<Int> = ArrayList()

        var i = 0
        var j = 0

        do {
            if (_text[i] == _substrings[j]) {
                i++
                j++
            }
            if (j == _substrings.length) {
                substringEntryIndices.add(i - j)
                j = prefixFun[j - 1]
            } else if (i < _text.length && _text[i] != _substrings[j]) {
                if (j != 0) j = prefixFun[j - 1]
                else i++
            }
        } while (i < _text.length)

        return substringEntryIndices.size > 0
    }

    private fun prefixFunction(text: String): IntArray {
        val prefixFun = IntArray(text.length)
        prefixFun[0] = 0
        for (i in 1 until text.length) {
            var j = prefixFun[i - 1]
            while (j > 0 && (text[i] != text[j])) j = prefixFun[j - 1]
            if (text[i] == text[j]) j += 1
            prefixFun[i] = j
        }
        return prefixFun
    }
}
