package edu.uniandes.moni.model.dto

data class TutoringDTO(
    val description: String,
    val inUniversity: Boolean,
    val price: String,
    val title: String,
    val topic: String,
    val tutorEmail: String?,
    val reviews: Array<String>,
    val scores: Array<Int>,
    val id: String
) {
    constructor() : this("", false, "", "", "", "", arrayOf<String>(), arrayOf<Int>(), "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TutoringDTO

        if (!reviews.contentEquals(other.reviews)) return false
        if (!scores.contentEquals(other.scores)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = reviews.contentHashCode()
        result = 31 * result + scores.contentHashCode()
        return result
    }
}
