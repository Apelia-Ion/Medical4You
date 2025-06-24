package com.example.medical4you.enums

enum class MedicalService(val displayName: String) {
    GENERAL_CHECKUP("General Checkup"),
    VACCINATION("Vaccination"),
    LAB_ANALYSIS("Lab Analysis"),
    DIAGNOSTIC("Diagnostic Services"),
    SURGERY("Surgery"),
    FOLLOW_UP("Follow-up"),
    TELEMEDICINE("Telemedicine"),
    EMERGENCY("Emergency Care"),
    PHYSIOTHERAPY("Physiotherapy"),
    MENTAL_HEALTH("Mental Health");

    override fun toString(): String {
        return displayName
    }
}