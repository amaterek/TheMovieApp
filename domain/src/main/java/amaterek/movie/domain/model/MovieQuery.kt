package amaterek.movie.domain.model

data class MovieQuery(
    val type: Type,
    val sortBy: SortBy,
    val genres: Set<MovieGenre>
) {
    sealed interface Type {
        data class ByCategory(val category: MovieCategory) : Type
        data class ByPhrase(val phrase: String) : Type
    }

    enum class SortBy {
        POPULARITY_DESCENDING,
        POPULARITY_ASCENDING,
        RELEASE_DATE_DESCENDING,
        RELEASE_DATE_ASCENDING,
        REVENUE_DESCENDING,
        REVENUE_ASCENDING,
        PRIMARY_RELEASE_DATE_DESCENDING,
        PRIMARY_RELEASE_DATE_ASCENDING,
        ORIGINAL_TITLE_DESCENDING,
        ORIGINAL_TITLE_ASCENDING,
        VOTE_AVERAGE_DESCENDING,
        VOTE_AVERAGE_ASCENDING,
        VOTE_COUNT_DESCENDING,
        VOTE_COUNT_ASCENDING,
    }
}