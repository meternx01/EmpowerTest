package com.example.empowertest.ui

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.empowertest.data.Beneficiary

class BeneficiaryAdapter(
    private val beneficiaries: List<Beneficiary>,
    private val onItemClicked: (Beneficiary) -> Unit
) : RecyclerView.Adapter<BeneficiaryAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val nameTextView: TextView = TextView(view.context).apply {
            textSize = 18f // Set text size in sp
            setTypeface(null, Typeface.BOLD) // Set text style to bold
        }
        val detailsTextView: TextView = TextView(view.context)
        val container: LinearLayout = LinearLayout(view.context).apply {
            orientation = LinearLayout.VERTICAL
            // Set margin dp
            val marginDp = 16
            val marginPx = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, marginDp.toFloat(), resources.displayMetrics
            ).toInt()

            // Set layout params
            val layoutParams = ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            ).apply {
                setMargins(marginPx, marginPx, marginPx, marginPx)
            }
            // Apply layout params
            this.layoutParams = layoutParams

            // Add the views to the container
            addView(nameTextView)
            addView(detailsTextView)
        }

        init {
            (view as ViewGroup).addView(container)
        }

    }


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val beneficiary = beneficiaries[position]
        holder.nameTextView.text = "${beneficiary.firstName} ${beneficiary.lastName}"
        holder.detailsTextView.text = "${beneficiary.beneType} - ${
            when (beneficiary.designationCode) {
                "P" -> "Primary"
                "C" -> "Contingent"
                else -> beneficiary.designationCode
            }
        }"
        holder.container.setOnClickListener {
            onItemClicked(beneficiary)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LinearLayout(parent.context).apply {
            orientation = LinearLayout.VERTICAL
        }
        return ViewHolder(view)
    }

    override fun getItemCount() = beneficiaries.size


}
