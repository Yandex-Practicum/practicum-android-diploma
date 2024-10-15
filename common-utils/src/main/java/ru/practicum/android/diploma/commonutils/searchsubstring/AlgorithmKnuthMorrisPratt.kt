package ru.practicum.android.diploma.commonutils.searchsubstring

object AlgorithmKnuthMorrisPratt {

    fun searchSubstringsInString(text: String, substrings: String): Boolean {
        return if (text.isEmpty() || substrings.isEmpty() || text.length < substrings.length) {
            false
        } else {
            baseSearchSubstringsInString(text, substrings)
        }
    }

    private fun baseSearchSubstringsInString(text: String, substrings: String): Boolean {
        val lowerText = text.lowercase()
        val lowerSubstrings = substrings.lowercase()
        val prefixArray = prefixFunction(lowerText)

        var textIndex = 0
        var substringIndex = 0

        while (textIndex < lowerText.length) {
            if (lowerText[textIndex] == lowerSubstrings[substringIndex]) {
                textIndex++
                substringIndex++

                if (substringIndex == lowerSubstrings.length) {
                    return true
                }
            } else if (substringIndex != 0) {
                substringIndex = prefixArray[substringIndex - 1]
            } else {
                textIndex++
            }
        }
        return false
    }

    private fun prefixFunction(text: String): IntArray {
        val prefixFun = IntArray(text.length)
        prefixFun[0] = 0
        for (i in 1 until text.length) {
            var j = prefixFun[i - 1]
            while (j > 0 && text[i] != text[j]) j = prefixFun[j - 1]
            if (text[i] == text[j]) j += 1
            prefixFun[i] = j
        }
        return prefixFun
    }
}
