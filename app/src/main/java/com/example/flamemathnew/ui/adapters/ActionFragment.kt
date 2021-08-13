package com.example.flamemathnew.ui.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.flamemathnew.R


class ActionFragment : Fragment() {
    private var text: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        text = requireArguments().getString("type")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val result = inflater.inflate(R.layout.fragment_action, container, false)
        val pageHeader = result.findViewById<View>(R.id.displayText) as TextView
        pageHeader.text = text
        when (text) {
        }
        return result
    }

    companion object {
        fun newInstance(type: String?): ActionFragment {
            val fragment = ActionFragment()
            val args = Bundle()
            args.putString("type", type)
            fragment.arguments = args
            return fragment
        }
    }
}