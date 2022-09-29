package it.paolomazza.newsapp.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

open class BaseFragment<T:ViewDataBinding>(@LayoutRes private val contentLayoutId: Int):Fragment(contentLayoutId) {

    private lateinit var _binding: T
    protected val binding: T
        get() = _binding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(
                layoutInflater,
                contentLayoutId,
                container,
                false
        )
        return _binding.root
    }

    fun showToolbar(showToolbar:Boolean){
        (activity as AppCompatActivity).apply {
            supportActionBar?.setDisplayHomeAsUpEnabled(showToolbar)
            supportActionBar?.setDisplayShowHomeEnabled(showToolbar)
        }
    }

}