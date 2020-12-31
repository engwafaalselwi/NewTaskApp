package com.example.newtaskapp
import android.app.AlertDialog
import android.app.Dialog
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import java.util.*


private const val REQUEST_DATE = 0
private const val DIALOG_DATE = "DialogDate"
class InputDialogFragment:DialogFragment() ,DatePickerFragment.Callbacks{

    val task = Task()
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val view=activity?.layoutInflater?.inflate(R.layout.dialog_add,null)
        val titleEditText=view?.findViewById(R.id.title) as EditText
        val detailsEditText=view?.findViewById(R.id.details) as EditText
        val saveButton =view?.findViewById(R.id.save) as Button
        saveButton.setOnClickListener {
          DatePickerFragment().apply {
              DatePickerFragment.newInstance(task.time_end).apply {
                  setTargetFragment(this@InputDialogFragment, REQUEST_DATE)
                  show(this@InputDialogFragment.requireFragmentManager(), DIALOG_DATE)
              }}}

        saveButton.setText(task.time_end.toString())

        return AlertDialog.Builder(requireContext(),R.style.ThemeOverlay_AppCompat_Dialog_Alert)
            .setView(view)
            .setPositiveButton("Add"){ dialog,_ ->
                val task=Task(
                    UUID.randomUUID(),
                    titleEditText.text.toString(),
                    detailsEditText.text.toString() ,
                    task.time_end
                )
                targetFragment?.let { fragment ->
                    (fragment as Callbacks).addTask(task)
                }
            }.setNegativeButton("Cancel"){dialog,_ ->
                dialog.cancel()
           }.create()

                  }
interface Callbacks {
    fun addTask(task: Task)

}
    override fun onDateSelected(date: Date) {
        task.time_end=date
    }
}