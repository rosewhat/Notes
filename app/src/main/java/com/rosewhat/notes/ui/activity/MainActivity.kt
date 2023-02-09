package com.rosewhat.notes.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.rosewhat.notes.databinding.ActivityMainBinding
import com.rosewhat.notes.ui.adapters.InfoListAdapter
import com.rosewhat.notes.ui.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    private lateinit var viewModel: MainViewModel
    private lateinit var infoAdapter: InfoListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupRecyclerView()
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.infoList.observe(this, Observer {
            // новый поток с вычислениями
            infoAdapter.submitList(it)
        })
        binding.buttonAddInfoList.setOnClickListener {
            val intent = InfoItemActivity.newIntentAddItem(this)
            startActivity(intent)
        }
    }

    private fun setupRecyclerView() {
        infoAdapter = InfoListAdapter()
        with(binding.rvInfoList) {
            adapter = infoAdapter
            recycledViewPool.setMaxRecycledViews(
                InfoListAdapter.VIEW_TYPE_ENABLED,
                InfoListAdapter.MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                InfoListAdapter.VIEW_TYPE_DISABLED,
                InfoListAdapter.MAX_POOL_SIZE
            )
        }

        setupLongClickListener()
        setupClickListener()
        setupSwipeListener()
    }


    private fun setupClickListener() {
        infoAdapter.onInfoItemClickListener = {
            val intent = InfoItemActivity.newIntentEditITem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setupLongClickListener() {
        infoAdapter.onInfoListLongClickListener = {
            viewModel.changeInfo(infoItem = it)
        }
    }

    private fun setupSwipeListener() {
        val callback = object :
            ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
            ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // элемент, который свайпнули
                // получить текущий список
                val item = infoAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteInfo(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvInfoList)
    }


}
