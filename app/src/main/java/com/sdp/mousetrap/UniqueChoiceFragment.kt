package com.sdp.mousetrap


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.sdp.mousetrap.DB.Alternative
import com.sdp.mousetrap.DB.Question


class UniqueChoiceFragment : Fragment() {
    companion object {
        fun newInstance(choices: ArrayList<Alternative>, answers: Bundle, last_question: Boolean, index: Int, frame: ArrayList<FrameLayout>, question: Question, delegate: FragmentDelegate?): UniqueChoiceFragment {
            val fragment = UniqueChoiceFragment()
            val args = Bundle()
            args.putSerializable("choices", choices)
            args.putBundle("answers", answers)
            args.putBoolean("last_question", last_question)
            args.putInt("index", index)
            args.putSerializable("frame", frame)
            args.putSerializable("question", question)
            args.putSerializable("delegate", delegate)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.fragment_unique_choice, container, false)
        val choices: ArrayList<*> = arguments["choices"] as ArrayList<*>
        val question_field: TextView = view.findViewById(R.id.question_body) as TextView
        val question: Question = arguments["question"] as Question
        question_field.text = question.question
        setChoices(view, choices)
        setButton(view, choices, question)
        return view
    }


    private fun setChoices(view: View, choices: ArrayList<*>) {
        val ll: RadioGroup = view.findViewById(R.id.multiple_alternatives_group) as RadioGroup
        for (i in 0 until choices.count()) {
            val cb = RadioButton(context)
            val alternative: Alternative = choices[i] as Alternative
            cb.text = alternative.value
            ll.addView(cb)
        }
    }

    fun setButton(view: View, choices: ArrayList<*>, question: Question) {
        val button: Button = view.findViewById(R.id.button_next) as Button
        val last_question: Boolean = arguments["last_question"] as Boolean
        if (last_question) {
            button.text = "Finish"
        }
        button.setOnClickListener {
            val radioGroup: RadioGroup = view.findViewById(R.id.multiple_alternatives_group) as RadioGroup
            val selectedOption = radioGroup.checkedRadioButtonId
            if (selectedOption != -1) {
                val selectButton: RadioButton = view.findViewById(selectedOption) as RadioButton
                var answer: ArrayList<Int> = ArrayList()
                answer.add(question.type)
                answer.add(getAlternativeId(selectButton.text.toString(), choices))
                val answers = this.arguments["answers"] as Bundle
                answers.putSerializable(question.id.toString(), answer)
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

    fun getAlternativeId(text: String, choices: ArrayList<*>): Int {
        for (i in 0 until choices.count()) {
            var choice: Alternative = choices[i] as Alternative
            if (choice.value == text) {
                return choice.id
            }
        }
        return 0
    }
}
