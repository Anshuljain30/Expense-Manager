package com.example.expensemanager.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.expensemanager.R
import com.example.expensemanager.data.Expense
import com.example.expensemanager.data.ExpenseCategory
import com.example.expensemanager.data.ExpenseType
import com.example.expensemanager.databinding.DialogAddTransactionBinding
import com.example.expensemanager.viewmodel.ExpenseViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class AddTransactionDialogFragment : DialogFragment() {
    private var _binding: DialogAddTransactionBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ExpenseViewModel by viewModels()
    private var selectedDate: Date = Date()
    private var expenseToEdit: Expense? = null
    private var transactionType: ExpenseType = ExpenseType.EXPENSE

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, com.google.android.material.R.style.ThemeOverlay_MaterialComponents_Dialog_Alert)
        arguments?.let {
            transactionType = it.getSerializable("transaction_type") as? ExpenseType ?: ExpenseType.EXPENSE
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogAddTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupClickListeners()
    }

    private fun setupViews() {
        // Set initial transaction type
        binding.typeRadioGroup.check(
            when (transactionType) {
                ExpenseType.INCOME -> binding.incomeRadioButton.id
                ExpenseType.EXPENSE -> binding.expenseRadioButton.id
            }
        )

        // Setup category spinner
        val categories = ExpenseCategory.entries.map { it.name }
        val categoryAdapter = ArrayAdapter(
            requireContext(),
            R.layout.item_dropdown,
            R.id.text1,
            categories
        )
        (binding.categorySpinner as? AutoCompleteTextView)?.apply {
            setAdapter(categoryAdapter)
            setText(categories.first(), false)
        }

        // Setup type radio group
        binding.typeRadioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                binding.incomeRadioButton.id -> {
                    binding.amountInputLayout.prefixText = getString(R.string.currency_symbol)
                    binding.amountInputLayout.suffixText = "+"
                    binding.amountInputLayout.setStartIconTintList(
                        requireContext().getColorStateList(R.color.income_green)
                    )
                }
                binding.expenseRadioButton.id -> {
                    binding.amountInputLayout.prefixText = getString(R.string.currency_symbol)
                    binding.amountInputLayout.suffixText = "-"
                    binding.amountInputLayout.setStartIconTintList(
                        requireContext().getColorStateList(R.color.expense_red)
                    )
                }
            }
        }

        // Set initial date
        updateDateButton()

        // Trigger initial setup of amount input layout
        binding.typeRadioGroup.check(
            when (transactionType) {
                ExpenseType.INCOME -> binding.incomeRadioButton.id
                ExpenseType.EXPENSE -> binding.expenseRadioButton.id
            }
        )
    }

    private fun setupClickListeners() {
        binding.dateButton.setOnClickListener {
            showDatePicker()
        }

        binding.saveButton.setOnClickListener {
            saveTransaction()
        }

        binding.cancelButton.setOnClickListener {
            dismiss()
        }
    }

    private fun showDatePicker() {
        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .setSelection(selectedDate.time)
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            selectedDate = Date(selection)
            updateDateButton()
        }

        datePicker.show(parentFragmentManager, "date_picker")
    }

    private fun updateDateButton() {
        val dateFormat = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
        val formattedDate = dateFormat.format(selectedDate).uppercase()
        binding.dateButton.text = formattedDate
    }

    private fun saveTransaction() {
        val amount = binding.amountEditText.text.toString().toDoubleOrNull() ?: return
        val category = binding.categorySpinner.text.toString()
        if (category.isBlank()) {
            binding.categorySpinner.error = "Please select a category"
            return
        }
        val description = binding.descriptionEditText.text.toString()
        val type = if (binding.typeRadioGroup.checkedRadioButtonId == binding.incomeRadioButton.id) {
            ExpenseType.INCOME
        } else {
            ExpenseType.EXPENSE
        }

        val transaction = expenseToEdit?.copy(
            amount = amount,
            category = category,
            description = description,
            date = selectedDate,
            type = type
        ) ?: Expense(
            amount = amount,
            category = category,
            description = description,
            date = selectedDate,
            type = type
        )

        if (expenseToEdit == null) {
            viewModel.insertExpense(transaction)
        } else {
            viewModel.updateExpense(transaction)
        }

        dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance(expense: Expense? = null): AddTransactionDialogFragment {
            return AddTransactionDialogFragment().apply {
                expenseToEdit = expense
            }
        }
    }
} 