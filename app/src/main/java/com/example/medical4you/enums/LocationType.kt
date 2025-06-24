package com.example.medical4you.enums

enum class LocationType(val displayName: String) {
    BUCHAREST("Bucharest"),
    CLUJ("Cluj"),
    IASI("Iasi"),
    TIMISOARA("Timisoara"),
    BRASOV("Brasov");

    override fun toString(): String = displayName
}