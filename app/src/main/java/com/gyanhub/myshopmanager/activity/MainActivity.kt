package com.gyanhub.myshopmanager.activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.gyanhub.myshopmanager.R
import com.gyanhub.myshopmanager.adapters.ItemClick
import com.gyanhub.myshopmanager.adapters.MainActivityAdapter
import com.gyanhub.myshopmanager.application.MyApplication
import com.gyanhub.myshopmanager.databinding.ActivityMainBinding
import com.gyanhub.myshopmanager.model.ItemsHistory
import com.gyanhub.myshopmanager.model.MyShopModel
import com.gyanhub.myshopmanager.viewModel.MainFactory
import com.gyanhub.myshopmanager.viewModel.MainViewModel
import java.util.*

@SuppressLint("NotifyDataSetChanged")
class MainActivity : AppCompatActivity(), ItemClick {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: MainActivityAdapter
    private lateinit var mainModel: MainViewModel
    private lateinit var dialog: Dialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        rcView()
        val repository = (application as MyApplication).MyShopRepository
        mainModel = ViewModelProvider(this, MainFactory(repository))[MainViewModel::class.java]
        mainModel.getItem()

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.swipeRefreshLayout.isRefreshing = true
            mainModel.getItem()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        mainModel.data.observe(this) {
            mainAdapter = MainActivityAdapter(this, it, this)
            binding.rcViewMain.adapter = mainAdapter
        }

        binding.btnAddItems.setOnClickListener {
            bottomSheet()
        }

        dialog = Dialog(this)
    }


    private fun bottomSheet() {
        val bottomSheetDialog = BottomSheetDialog(this)
        bottomSheetDialog.setContentView(R.layout.apply_bottom_sheet)
        val btn = bottomSheetDialog.findViewById<Button>(R.id.btnAddItem)
        val itemName = bottomSheetDialog.findViewById<EditText>(R.id.eTxtItemName)
        val itemCount = bottomSheetDialog.findViewById<EditText>(R.id.eTxtTotalNo)
        btn?.setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDate =
                "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${
                    calendar.get(Calendar.YEAR)
                }"
            val currentTime =
                "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"

            mainModel.addShopItem(
                MyShopModel(
                    0,
                    "${itemName?.text}",
                    itemCount?.text.toString().toInt(),
                    itemCount?.text.toString().toInt(),
                    0,
                    listOf(
                        ItemsHistory(
                            currentDate,
                            currentTime,
                            itemCount?.text.toString().toInt(),
                            "Add"
                        )
                    )
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

    private fun dilogeBox(item: MyShopModel) {
        dialog.show()
        dialog.setContentView(R.layout.dialog_layout)
        dialog.findViewById<ImageView>(R.id.btnClose).setOnClickListener { dialog.dismiss() }
        dialog.findViewById<TextView>(R.id.txtItemNameD).text = item.itemName
        dialog.findViewById<TextView>(R.id.txtTotalItemD).text =
            getString(R.string.total_item, item.totalItem.toString())
        dialog.findViewById<TextView>(R.id.txtItemUsedD).text =
            getString(R.string.used_item, item.itemUsed.toString())
        dialog.findViewById<TextView>(R.id.txtItemAva).text =
            getString(R.string.ava_item, item.itemAva.toString())
        dialog.findViewById<Button>(R.id.btnAddUsed).setOnClickListener {
            dialog.dismiss()
            updateDialogBox(item)
        }
        dialog.findViewById<Button>(R.id.btnHistory).setOnClickListener {
            val intent = Intent(this, FragmentHolderActivity::class.java)
            intent.putExtra("id", item.id)
            startActivity(intent)
            dialog.dismiss()
        }
    }

    override fun onItemClick(item: MyShopModel) {
        dilogeBox(item)
    }

    private fun updateDialogBox(item: MyShopModel) {

        dialog.show()
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.update_dialoge_layout)
        dialog.findViewById<ImageView>(R.id.btnCloseU).setOnClickListener { dialog.dismiss() }
        val count = dialog.findViewById<EditText>(R.id.eTxtNumber)
        val add = dialog.findViewById<TextView>(R.id.selectAddMore)
        val updateUsed = dialog.findViewById<TextView>(R.id.selectUseUpdate)
        add.setOnClickListener {
            mainModel.type = "Add"
            updateUsed.setBackgroundResource(R.drawable.unselect_item)
            it.setBackgroundResource(R.drawable.select_item)

        }
        updateUsed.setOnClickListener {
            mainModel.type = "Used"
            add.setBackgroundResource(R.drawable.unselect_item)
            it.setBackgroundResource(R.drawable.select_item)
        }

        dialog.findViewById<Button>(R.id.btnUpdate).setOnClickListener {
            val calendar = Calendar.getInstance()
            val currentDate =
                "${calendar.get(Calendar.DAY_OF_MONTH)}-${calendar.get(Calendar.MONTH) + 1}-${
                    calendar.get(Calendar.YEAR)
                }"
            val currentTime =
                "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}"


            if (mainModel.type == "Used") {
                if (count.text.toString().toInt() <= item.itemAva) {
                    val usedItemCount = item.itemUsed + count.text.toString().toInt()
                    val avaItemCount = item.totalItem - usedItemCount
                    mainModel.update(
                        MyShopModel(
                            item.id,
                            item.itemName,
                            avaItemCount,
                            item.totalItem,
                            usedItemCount,
                            item.history.toMutableList().apply {
                                add(
                                    ItemsHistory(
                                        currentDate,
                                        currentTime,
                                        count?.text.toString().toInt(),
                                        mainModel.type
                                    )
                                )
                            }

                        )
                    )
                    mainModel.getItem()
                    mainAdapter.notifyDataSetChanged()
                    dialog.dismiss()
                } else {
                    count.hint = "you enter max ${item.itemAva}"
                    Toast.makeText(
                        this,
                        "You Haven't available that many items \nyou have Available items : ${item.itemAva} ",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else if ( mainModel.type == "Add") {
                val total = item.totalItem + count?.text.toString().toInt()
                val avaItemCount = item.itemAva + count?.text.toString().toInt()
                mainModel.update(
                    MyShopModel(
                        item.id,
                        item.itemName,
                        avaItemCount,
                        total,
                        item.itemUsed,
                        item.history.toMutableList().apply {
                            add(
                                ItemsHistory(
                                    currentDate,
                                    currentTime,
                                    count?.text.toString().toInt(),
                                    mainModel.type
                                )
                            )
                        }
                    )
                )
                mainModel.getItem()
                mainAdapter.notifyDataSetChanged()
                dialog.dismiss()
            } else {
                Toast.makeText(this, "place select update type", Toast.LENGTH_SHORT).show()
            }
        }
    }
}