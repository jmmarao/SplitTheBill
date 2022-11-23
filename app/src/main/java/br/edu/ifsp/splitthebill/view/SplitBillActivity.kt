package br.edu.ifsp.splitthebill.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import br.edu.ifsp.splitthebill.adapter.SplitBillAdapter
import br.edu.ifsp.splitthebill.databinding.ActivitySplitBillBinding
import br.edu.ifsp.splitthebill.model.Bill
import br.edu.ifsp.splitthebill.model.Constant.EXTRA_SPLIT_BILL

class SplitBillActivity : AppCompatActivity() {
    private val activitySplitBillActivity: ActivitySplitBillBinding by lazy {
        ActivitySplitBillBinding.inflate(layoutInflater)
    }

    private lateinit var adapter: SplitBillAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activitySplitBillActivity.root)

        val billList = intent.getParcelableArrayListExtra<Bill>(EXTRA_SPLIT_BILL)
        var amountTotal = 0.0
        val splitAmount: Double

        if (!billList.isNullOrEmpty()) {
            for (i in 0 until billList.count()) {
                amountTotal += billList[i].amount
            }
            splitAmount = amountTotal / billList.size

            adapter = SplitBillAdapter(this, billList, splitAmount)
            activitySplitBillActivity.splitBillListView.adapter = adapter
        }
    }
}