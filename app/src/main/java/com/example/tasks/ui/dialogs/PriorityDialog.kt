package com.example.tasks.ui.dialogs

import android.app.AlertDialog
import android.app.Dialog
import android.content.res.ColorStateList
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.tasks.data.model.Priority
import com.example.tasks.databinding.PriorityDialogBinding
import com.google.android.material.slider.Slider


class PriorityDialog(private var currentPriority: Priority) : DialogFragment() {
    companion object {
        const val TAG = "PriorityDialog"
    }

    interface PriorityDialogListener {
        fun onPrioritySave(priority: Priority)
    }

    var priorityDialogListener: PriorityDialogListener? = null

    private var _binding: PriorityDialogBinding? = null
    private val binding
        get() = _binding!!

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = requireActivity().layoutInflater
        _binding = PriorityDialogBinding.inflate(inflater)
        builder.setView(binding.root)

        binding.cancelPriorityBtn.setOnClickListener { dismiss() }
        binding.savePriorityBtn.setOnClickListener {
            priorityDialogListener?.onPrioritySave(currentPriority)
            dismiss()
        }

        binding.prioritySlider.setLabelFormatter { value ->
            Priority.fromValue(value.toInt()).toString()
        }

        binding.prioritySlider.addOnChangeListener { slider, value, _ ->
            currentPriority = Priority.fromValue(value.toInt())
            updateSliderColor(slider)
        }

        binding.prioritySlider.value = currentPriority.value.toFloat()
        return builder.create()
    }

    private fun updateSliderColor(slider: Slider) {
        val priorityColor = ColorStateList.valueOf(requireContext().getColor(currentPriority.color))
        slider.trackActiveTintList = priorityColor
        slider.thumbTintList = priorityColor
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
