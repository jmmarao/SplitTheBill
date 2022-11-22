package br.edu.ifsp.splitthebill.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.AdapterContextMenuInfo
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import br.edu.ifsp.splitthebill.R
import br.edu.ifsp.splitthebill.adapter.BillAdapter
import br.edu.ifsp.splitthebill.databinding.ActivityMainBinding
import br.edu.ifsp.splitthebill.model.Bill
import br.edu.ifsp.splitthebill.model.Constant.EXTRA_BILL
import br.edu.ifsp.splitthebill.model.Constant.VIEW_BILL

class MainActivity : AppCompatActivity() {
    private val activityMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val billList: MutableList<Bill> = mutableListOf()
    private lateinit var billAdapter: BillAdapter
    private lateinit var billActivityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activityMainBinding.root)

        billAdapter = BillAdapter(this, billList)
        activityMainBinding.billsListView.adapter = billAdapter

        billActivityResultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
        ) { result ->
            if (result.resultCode == RESULT_OK) {
                val bill = result.data?.getParcelableExtra<Bill>(EXTRA_BILL)

                bill?.let { _bill ->
                    val position = billList.indexOfFirst { it.id == _bill.id }
                    if (position != -1) {
                        billList[position] = _bill
                    } else {
                        billList.add(_bill)
                    }
                    billAdapter.notifyDataSetChanged()
                }
            }
        }

        registerForContextMenu(activityMainBinding.billsListView)

        activityMainBinding.billsListView.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val bill = billList[position]
                val billIntent = Intent(this@MainActivity, BillActivity::class.java)
                billIntent.putExtra(EXTRA_BILL, bill)
                billIntent.putExtra(VIEW_BILL, true)
                startActivity(billIntent)
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.addBillMi -> {
                billActivityResultLauncher.launch(Intent(this, BillActivity::class.java))
                true
            }
            else -> {
                false
            }
        }
    }

    override fun onCreateContextMenu(
        menu: ContextMenu?,
        v: View?,
        menuInfo: ContextMenu.ContextMenuInfo?
    ) {
        menuInflater.inflate(R.menu.context_menu_main, menu)
    }

    override fun onContextItemSelected(item: MenuItem): Boolean {
        val position = (item.menuInfo as AdapterContextMenuInfo).position
        return when (item.itemId) {
            R.id.removeBillMi -> {
                billList.removeAt(position)
                billAdapter.notifyDataSetChanged()
                true
            }
            R.id.editBillMi -> {
                val bill = billList[position]
                val billIntent = Intent(this, BillActivity::class.java)
                billIntent.putExtra(EXTRA_BILL, bill)
                billIntent.putExtra(VIEW_BILL, false)
                billActivityResultLauncher.launch(billIntent)
                true
            }
            else -> {
                false
            }
        }
    }
}