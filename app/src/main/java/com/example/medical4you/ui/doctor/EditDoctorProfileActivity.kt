package com.example.medical4you.ui.doctor

import android.app.AlertDialog
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.medical4you.R
import com.example.medical4you.data.MedicalAppDatabase
import com.example.medical4you.enums.LocationType
import com.example.medical4you.enums.MedicalService
import com.example.medical4you.enums.Specialization
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditDoctorProfileActivity : AppCompatActivity() {

    private lateinit var etName: EditText
    private lateinit var spSpecialization: Spinner
    private lateinit var spLocation: Spinner
    private lateinit var tvServices: TextView
    private lateinit var btnSave: Button

    private val selectedServices = mutableListOf<String>()
    private lateinit var allServiceNames: Array<String>
    private lateinit var checkedItems: BooleanArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_doctor_profile)

        etName = findViewById(R.id.et_doctor_name)
        spSpecialization = findViewById(R.id.sp_specialization)
        spLocation = findViewById(R.id.sp_location)
        tvServices = findViewById(R.id.tv_selected_services)
        btnSave = findViewById(R.id.btn_save)

        spSpecialization.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            Specialization.values()
        )

        spLocation.adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            LocationType.values()
        )

        allServiceNames = MedicalService.values().map { it.toString() }.toTypedArray()
        checkedItems = BooleanArray(allServiceNames.size) { false }

        val doctorId = getSharedPreferences("user_prefs", MODE_PRIVATE).getInt("doctor_id", -1)
        if (doctorId == -1) {
            Toast.makeText(this, "Doctor ID not found.", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        val db = MedicalAppDatabase.getDatabase(this)
        val doctorDao = db.doctorDao()

        lifecycleScope.launch {
            val doctor = withContext(Dispatchers.IO) {
                doctorDao.getDoctorByUserId(doctorId)
            }

            if (doctor != null) {
                etName.setText(doctor.name)

                val specIndex = Specialization.values().indexOfFirst {
                    it.displayName == doctor.specialization
                }
                spSpecialization.setSelection(specIndex)

                val locIndex = LocationType.values().indexOfFirst {
                    it.displayName == doctor.location
                }
                spLocation.setSelection(locIndex)

                val existingServices = doctor.services.split(",").map { it.trim() }
                for (i in allServiceNames.indices) {
                    if (allServiceNames[i] in existingServices) {
                        checkedItems[i] = true
                        selectedServices.add(allServiceNames[i])
                    }
                }
                tvServices.text = selectedServices.joinToString(", ")
            }
        }

        tvServices.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Select Medical Services")
                .setMultiChoiceItems(allServiceNames, checkedItems) { _, which, isChecked ->
                    if (isChecked) {
                        selectedServices.add(allServiceNames[which])
                    } else {
                        selectedServices.remove(allServiceNames[which])
                    }
                }
                .setPositiveButton("OK") { _, _ ->
                    tvServices.text = selectedServices.joinToString(", ")
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        btnSave.setOnClickListener {
            val updatedName = etName.text.toString().trim()
            val selectedSpec = spSpecialization.selectedItem as Specialization
            val selectedLoc = spLocation.selectedItem as LocationType
            val updatedServices = selectedServices.joinToString(", ")

            if (updatedName.isEmpty()) {
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    doctorDao.updateDoctor(
                        doctorId,
                        updatedName,
                        selectedSpec.displayName,
                        selectedLoc.displayName,
                        updatedServices
                    )
                }
                Toast.makeText(this@EditDoctorProfileActivity, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}