package com.sdp.mousetrap


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sdp.mousetrap.DB.Alternative
import android.R.attr.keySet
import android.app.FragmentManager


class MultipleChoiceFragment : Fragment() {
    companion object {
        fun newInstance(choices: ArrayList<Alternative>, answers: Bundle, last_question: Boolean, index: Int, frame: ArrayList<FrameLayout>, question: String, delegate: FragmentDelegate?): MultipleChoiceFragment {
            val fragment = MultipleChoiceFragment()
            val args = Bundle()
            args.putSerializable("choices", choices)
            args.putBundle("answers", answers)
            args.putBoolean("last_question", last_question)
            args.putInt("index", index)
            args.putSerializable("frame", frame)
            args.putString("question", question)
            args.putSerializable("delegate", delegate)
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
        val question_field: TextView = view.findViewById(R.id.question_body) as TextView
        question_field.text = arguments["question"].toString()
        setChoices(view, choices)
        setButton(view)
        return view
    }


    private fun setChoices(view: View, choices: ArrayList<*>) {
        val ll: LinearLayout = view.findViewById(R.id.multiple_options_layout) as LinearLayout
        for (i in 0 until choices.count()) {
            val cb = CheckBox(context)
            val alternative: Alternative = choices[i] as Alternative
            cb.text = alternative.value
            ll.addView(cb)
        }
    }

    fun setButton(view: View) {
        val button: Button = view.findViewById(R.id.button_next) as Button
        val last_question: Boolean = arguments["last_question"] as Boolean
        if (last_question) {
            button.text = "Finish"
        }
        button.setOnClickListener {
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
            val answers_bundle: Bundle = this.arguments["answers"] as Bundle
            answers_bundle.putSerializable(arguments["question"].toString(), answers)
            val i: Int = arguments["index"] as Int
            val frames: ArrayList<FrameLayout> = this.arguments["frame"] as ArrayList<FrameLayout>
            frames[i].visibility = View.GONE
            if (!(arguments["last_question"] as Boolean)) {
                frames[i + 1].visibility = View.VISIBLE
            }
            else {
                var builder = AlertDialog.Builder(context)
                builder.setTitle("All done")
                builder.setMessage("Would you like to go back to the home page?")
                builder.setPositiveButton("YES") { dialog, which ->

                    /**
                    *!!!!!!!!!!!!
                    *HERE IS WHERE YOU MUST SEND THE ANSWERS AND INFORMATION BACK TO THE SERVER
                    *!!!!!!!!!!!!
                    */

                    val delegate: FragmentDelegate = arguments["delegate"] as FragmentDelegate
                    val fm: android.support.v4.app.FragmentManager = delegate.createFragmentManager()
                    for (i in 0 until fm.backStackEntryCount) {
                        fm.popBackStack()
                    }
                    val transaction = fm.beginTransaction()
                    transaction.replace(R.id.main_frame, HomeFragment())
                    transaction.commit()
                }
                var dialog: AlertDialog = builder.create()
                dialog.show()
            }
        }
    }
}
