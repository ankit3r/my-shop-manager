package com.gyanhub.myshopmanager.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gyanhub.myshopmanager.databinding.ActivityFragmentHolderBinding

class FragmentHolderActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFragmentHolderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFragmentHolderBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}