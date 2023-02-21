package com.gyanhub.myshopmanager.activity

import android.annotation.SuppressLint
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gyanhub.myshopmanager.R
import com.gyanhub.myshopmanager.adapters.ItemClick
import com.gyanhub.myshopmanager.adapters.MainActivityAdapter
import com.gyanhub.myshopmanager.databinding.ActivityMainBinding
import com.gyanhub.myshopmanager.model.ItemsHistory
import com.gyanhub.myshopmanager.model.MyShopModel
import com.gyanhub.myshopmanager.repository.MyShopRepo
import com.gyanhub.myshopmanager.roomDB.RoomDb
import com.gyanhub.myshopmanager.viewModel.MainFactory
import com.gyanhub.myshopmanager.viewModel.MainViewModel

class MainActivity : AppCompatActivity(), ItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var searchView: SearchView
    private lateinit var mainAdapter: MainActivityAdapter
    private lateinit var mainModel: MainViewModel
    private lateinit var dialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rcView()
        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            mainModel.getItem()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        mainModel = ViewModelProvider(
            this,
            MainFactory(MyShopRepo(RoomDb.getDatabase(applicationContext).DatabaseDao()))
        )[MainViewModel::class.java]

        mainModel.getItem()

        mainModel.data.observe(this) {
            mainAdapter = MainActivityAdapter(this, it,this)
            binding.rcViewMain.adapter = mainAdapter
        }

        binding.btnAddItems.setOnClickListener {
            bottomSheet()
        }


        dialog = Dialog(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val searchItem = menu!!.findItem(R.id.action_search)
        searchView = searchItem.actionView as SearchView
        searchView.queryHint = "Search Item"

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                hideKeyboard(searchView)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        return super.onCreateOptionsMenu(menu)
    }

    fun hideKeyboard(view: View) {
        val inputMethodManager =
            view.context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
        view.clearFocus()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun bottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.apply_bottom_sheet)
        val btn = bottomSheetDialog.findViewById<Button>(R.id.btnAddItem)
        val itemName = bottomSheetDialog.findViewById<EditText>(R.id.eTxtItemName)
        val itemCount = bottomSheetDialog.findViewById<EditText>(R.id.eTxtTotalNo)
        btn?.setOnClickListener {
            mainModel.addShopItem(
                MyShopModel(
                    0,
                    "${itemName?.text}", itemCount?.text.toString().toInt(),
                    itemCount?.text.toString().toInt(),
                    0, listOf(ItemsHistory("", "", 0))
                )
            )
            mainModel.getItem()
            mainAdapter.notifyDataSetChanged()
            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.show()
    }

    private fun rcView() {
        val displayMetrics = resources.displayMetrics
        val screenWidthDp = displayMetrics.widthPixels / displayMetrics.density

        val columnCount = when {
            screenWidthDp >= 1200 -> 5
            screenWidthDp >= 800 -> 4
            screenWidthDp >= 600 -> 3
            screenWidthDp >= 390 -> 2
            else -> 1
        }

        val layoutManager = GridLayoutManager(this, columnCount)
        binding.rcViewMain.layoutManager = layoutManager
    }

    private fun dilogeBox(item:MyShopModel){
        dialog.show()
        dialog.setContentView(R.layout.dialog_layout)
       dialog.findViewById<TextView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.txtItemNameD).text = item.itemName
        dialog.findViewById<TextView>(R.id.txtTotalItemD).text = getString(R.string.total_item,item.totalItem.toString())
        dialog.findViewById<TextView>(R.id.txtItemUsedD).text = getString(R.string.used_item,item.itemUsed.toString())
        dialog.findViewById<TextView>(R.id.txtItemAva).text = getString(R.string.ava_item,item.itemAva.toString())
    }

    override fun onItemClick(item: MyShopModel) {
        dilogeBox(item)
    }
}