package br.edu.ifsp.splitthebill.adapter

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import br.edu.ifsp.splitthebill.R
import br.edu.ifsp.splitthebill.model.Bill

class BillAdapter(
    context: Context,
    private val billList: MutableList<Bill>
) : ArrayAdapter<Bill>(context, R.layout.tile_bill, billList) {
    private data class TileBillHolder(val nameTextView: TextView, val amountTextView: TextView)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val bill = billList[position]
        var billTileView = convertView

        if (billTileView == null) {
            billTileView = (context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater)
                .inflate(R.layout.tile_bill, parent, false)

            val tileBillHolder = TileBillHolder(
                billTileView.findViewById(R.id.nameTextView),
                billTileView.findViewById(R.id.amountTextView)
            )
            billTileView.tag = tileBillHolder
        }

        with(billTileView?.tag as TileBillHolder) {
            nameTextView.text = bill.name
            amountTextView.text = bill.amount.toString()
        }

        return billTileView
    }
}