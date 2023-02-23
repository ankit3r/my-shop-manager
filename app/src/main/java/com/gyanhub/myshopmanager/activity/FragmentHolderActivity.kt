package com.gyanhub.myshopmanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import com.gyanhub.myshopmanager.adapters.HistoryAdapter
import com.gyanhub.myshopmanager.application.MyApplication
import com.gyanhub.myshopmanager.databinding.ActivityFragmentHolderBinding
import com.gyanhub.myshopmanager.model.MyShopModel
import com.gyanhub.myshopmanager.viewModel.MainFactory
import com.gyanhub.myshopmanager.viewModel.MainViewModel


class FragmentHolderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFragmentHolderBinding
    private lateinit var mainModel: MainViewModel
    private  var id :Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = (application as MyApplication).MyShopRepository
        mainModel = ViewModelProvider(this, MainFactory(repository))[MainViewModel::class.java]
        id = intent.getIntExtra("id",0)
        val data = mainModel.getHistoryById(id)
        title = "${data.itemName} History"
        binding.rcHistory.adapter = HistoryAdapter(data.history)


    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }


}