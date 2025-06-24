package com.example.medical4you.enums

enum class Specialization(val displayName: String) {
    CARDIOLOGY("Cardiology"),
    PEDIATRICS("Pediatrics"),
    DERMATOLOGY("Dermatology"),
    NEUROLOGY("Neurology"),
    ENT("ENT"), // Ear, Nose, Throat
    ORTHOPEDICS("Orthopedics"),
    PSYCHIATRY("Psychiatry"),
    OPHTHALMOLOGY("Ophthalmology"),
    ONCOLOGY("Oncology"),
    GYNECOLOGY("Gynecology"),
    GENERAL_MEDICINE("General Medicine");

    override fun toString(): String = displayName
}