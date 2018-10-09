package com.sdp.mousetrap


import android.app.AlertDialog
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView


class OpenQuestionFragment : Fragment() {
    companion object {
        fun newInstance(answers: Bundle, last_question: Boolean, index: Int, frame: ArrayList<FrameLayout>, question: String, delegate: FragmentDelegate?): OpenQuestionFragment {
            val fragment = OpenQuestionFragment()
            val args = Bundle()
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_open_question, container, false)
        val question_field: TextView = view.findViewById(R.id.question_body) as TextView
        question_field.text = arguments["question"].toString()
        setButton(view)
        return view
    }

    fun setButton(view: View) {
        val button: Button = view.findViewById(R.id.button_next) as Button
        val last_question: Boolean = arguments["last_question"] as Boolean
        if (last_question) {
            button.text = "Finish"
        }
        button.setOnClickListener {
            val editText: EditText = view.findViewById(R.id.answer_block) as EditText
            val answers = this.arguments["answers"] as Bundle
            val i: Int = arguments["index"] as Int
            val frames: ArrayList<FrameLayout> = this.arguments["frame"] as ArrayList<FrameLayout>
            answers.putString(arguments["question"].toString(), editText.text.toString())
            frames[i].visibility = GONE
            if (!(arguments["last_question"] as Boolean)) {
                frames[i + 1].visibility = VISIBLE
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
