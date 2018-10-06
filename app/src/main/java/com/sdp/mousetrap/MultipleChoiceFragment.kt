package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.CheckBox
import android.widget.TextView


class MultipleChoiceFragment : Fragment() {
    companion object {
        fun newInstance(choices: ArrayList<String>): MultipleChoiceFragment {
            val fragment = MultipleChoiceFragment()
            val args = Bundle()
            args.putSerializable("choices", choices)
            fragment.arguments = args
            return fragment
        }
    }

    var answers: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multiple_choice, container, false)
        val choices: ArrayList<*> = arguments["choices"] as ArrayList<*>
        setChoices(view, choices)
        val button_next = view.findViewById(R.id.button_next)
        button_next.setOnClickListener {
            val ll: LinearLayout = view.findViewById(R.id.multiple_options_layout) as LinearLayout
            val count = ll.childCount
            for (i in 0 until count) {
                val v = ll.getChildAt(i)
                if (v is CheckBox) {
                    if (v.isChecked and !(answers.contains(v.text))) {
                        answers.add(v.text as String)
                    }
                }
            }
        }
        return view
    }


    fun setChoices(view: View, choices: ArrayList<*>) {
        val ll: LinearLayout = view.findViewById(R.id.multiple_options_layout) as LinearLayout
        for (i in 0 until choices.count()) {
            val cb = CheckBox(context)
            cb.text = choices[i] as CharSequence?
            ll.addView(cb)
        }
    }


}
