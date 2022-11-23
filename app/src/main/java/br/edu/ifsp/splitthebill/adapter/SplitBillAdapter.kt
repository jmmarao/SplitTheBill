package br.edu.ifsp.splitthebill.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.splitthebill.R
import br.edu.ifsp.splitthebill.model.Bill
import br.edu.ifsp.splitthebill.model.Constant.LABEL_TO_PAY
import br.edu.ifsp.splitthebill.model.Constant.LABEL_TO_RECEIVE
import kotlin.math.abs

class SplitBillAdapter(
    context: Context,
    private val billList: ArrayList<Bill>,
    private val splitAmount: Double
) : ArrayAdapter<Bill>(context, R.layout.tile_bill, billList) {
    private data class TileBillHolder(
        val nameTextView: TextView,
        val amountLabelTextView: TextView,
        val amountTextView: TextView
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bill = billList[position]
        var billTileView = convertView

        if (billTileView == null) {
            billTileView =
                (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                    .inflate(R.layout.tile_bill, parent, false)

            val tileBillHolder = TileBillHolder(
                billTileView.findViewById(R.id.nameTextView),
                billTileView.findViewById(R.id.amountLabelTextView),
                billTileView.findViewById(R.id.amountTextView)
            )
            billTileView.tag = tileBillHolder
        }

        with(billTileView?.tag as TileBillHolder) {
            nameTextView.text = bill.name

            amountLabelTextView.text =
                if (bill.amount < splitAmount)
                    LABEL_TO_PAY
                else
                    LABEL_TO_RECEIVE

            amountTextView.text = (abs(bill.amount - splitAmount)).toString()
        }
        return billTileView
    }
}