package com.example.empowertest

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.empowertest.data.Beneficiary
import com.example.empowertest.data.BeneficiaryAddress
import com.example.empowertest.ui.BeneficiaryAdapter
import org.json.JSONArray

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Read JSON data from the Beneficiaries.json file
        val beneficiaryList = readJSON(this)

        // Create the RecyclerView and set its adapter
        val recyclerView = RecyclerView(this).apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = BeneficiaryAdapter(beneficiaryList) { beneficiary ->
                showBeneficiaryDetails(beneficiary)
            }
        }

        // Set the RecyclerView as the content view
        val layout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            addView(recyclerView)
        }

        // Set the content view to the layout
        setContentView(layout)
    }

    // Show a dialog with beneficiary details
    private fun showBeneficiaryDetails(beneficiary: Beneficiary) {

        val details = """
            SSN: ${beneficiary.socialSecurityNumber}
            DOB: ${beneficiary.dateOfBirth}
            Phone: ${beneficiary.phoneNumber}
            Address: ${beneficiary.beneficiaryAddress.firstLineMailing},
            ${beneficiary.beneficiaryAddress.city}, ${beneficiary.beneficiaryAddress.stateCode}, ${beneficiary.beneficiaryAddress.zipCode}, ${beneficiary.beneficiaryAddress.country}
        """.trimIndent()

        AlertDialog.Builder(this)
            .setTitle("${beneficiary.firstName} ${beneficiary.lastName}")
            .setMessage(details)
            .setPositiveButton("OK", null)
            .show()
    }

    fun readJSON(context: Context): List<Beneficiary> {
        val beneficiaries = mutableListOf<Beneficiary>()
        try {
            val inputStream = context.assets.open("Beneficiaries.json")
            val size = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()

            val json = String(buffer, Charsets.UTF_8)

            //iterate over each json object
            for (i in 0 until JSONArray(json).length()) {
                val jsonObject = JSONArray(json).getJSONObject(i)

                val addressObject = jsonObject.getJSONObject("beneficiaryAddress")
                val address = BeneficiaryAddress(
                    firstLineMailing = addressObject.getString("firstLineMailing"),
                    scndLineMailing = addressObject.optString("scndLineMailing", null),
                    city = addressObject.getString("city"),
                    zipCode = addressObject.getString("zipCode"),
                    stateCode = addressObject.getString("stateCode"),
                    country = addressObject.getString("country")
                )

                val beneficiary = Beneficiary(
                    firstName = jsonObject.getString("firstName"),
                    lastName = jsonObject.getString("lastName"),
                    designationCode = jsonObject.getString("designationCode"),
                    beneType = jsonObject.getString("beneType"),
                    socialSecurityNumber = jsonObject.getString("socialSecurityNumber"),
                    dateOfBirth = jsonObject.getString("dateOfBirth"),
                    middleName = jsonObject.getString("middleName"),
                    phoneNumber = jsonObject.getString("phoneNumber"),
                    beneficiaryAddress = address
                )
                beneficiaries.add(beneficiary)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return beneficiaries

    }




//    private fun readJSON(context: Context): List<Beneficiary> {
//        val jsonString =
//            context.assets.open("Beneficiaries.json").bufferedReader().use { it.readText() }
//        val gson = Gson()
//        val beneficiaryListType = object : TypeToken<List<Beneficiary>>() {}.type
//        return gson.fromJson(jsonString, beneficiaryListType)
//    }


}

