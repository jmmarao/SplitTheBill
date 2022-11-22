package br.edu.ifsp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import br.edu.ifsp.splitthebill.R
import br.edu.ifsp.splitthebill.databinding.ActivityBillBinding
import br.edu.ifsp.splitthebill.model.Bill
import br.edu.ifsp.splitthebill.model.Constant.EXTRA_BILL
import br.edu.ifsp.splitthebill.model.Constant.VIEW_BILL
import kotlin.random.Random

class BillActivity : AppCompatActivity() {
    private val activityBillBinding: ActivityBillBinding by lazy {
        ActivityBillBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityBillBinding.root)

        val receivedBill = intent.getParcelableExtra<Bill>(EXTRA_BILL)

        receivedBill?.let { _receivedBill ->
            with(activityBillBinding) {
                with(_receivedBill) {
                    nameEditText.setText(name)
                    productEditText.setText(product)
                    amountEditText.setText(amount.toString())
                }
            }
        }

        val viewBill = intent.getBooleanExtra(VIEW_BILL, false)
        if (viewBill) {
            activityBillBinding.nameEditText.isEnabled = false
            activityBillBinding.productEditText.isEnabled = false
            activityBillBinding.amountEditText.isEnabled = false
            activityBillBinding.saveButton.visibility = View.GONE
        }

        activityBillBinding.saveButton.setOnClickListener {
            val bill = Bill(
                id = receivedBill?.id ?: Random(System.currentTimeMillis()).nextInt(),
                name = activityBillBinding.nameEditText.text.toString(),
                product = activityBillBinding.productEditText.text.toString(),
                amount = activityBillBinding.amountEditText.text.toString().toDouble()
            )

            val resultIntent = Intent()
            resultIntent.putExtra(EXTRA_BILL, bill)
            setResult(RESULT_OK, resultIntent)
            finish()
        }
    }
}