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
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Question
import org.json.JSONObject
import java.io.Serializable
import android.R.attr.keySet
import android.content.Intent.getIntent

class MultipleChoiceFragment : Fragment() {
    private val Preferences_name : String = "Prefs"
    private var Preferences: SharedPreferences? = null

    companion object {
        fun newInstance(choices: ArrayList<Alternative>, answers: Bundle, last_question: Boolean, index: Int, frame: ArrayList<FrameLayout>, question: Question, delegate: FragmentDelegate?): MultipleChoiceFragment {
            val fragment = MultipleChoiceFragment()
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

    var answers: ArrayList<String> = ArrayList()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_multiple_choice, container, false)
        val choices: ArrayList<*> = arguments["choices"] as ArrayList<*>
        val question_field: TextView = view.findViewById(R.id.question_body) as TextView
        val question: Question = arguments["question"] as Question
        question_field.text = question.question
        setChoices(view, choices)
        setButton(view, choices, question)
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

    fun setButton(view: View, choices: ArrayList<*>, question: Question) {
        val button: Button = view.findViewById(R.id.button_next) as Button
        val last_question: Boolean = arguments["last_question"] as Boolean
        if (last_question) {
            button.text = "Finish"
        }
        button.setOnClickListener {
            val ll: LinearLayout = view.findViewById(R.id.multiple_options_layout) as LinearLayout
            val count = ll.childCount
            var my_picks: ArrayList<Int> = ArrayList()
            for (i in 0 until count) {
                val v = ll.getChildAt(i)
                if (v is CheckBox) {
                    val id: Int = getAlternativeId(v.text as String, choices)
                    if (v.isChecked and !(my_picks.contains(id))) {
                        my_picks.add(id)
                    }
                }
            }
            var answer: ArrayList<Serializable> = ArrayList()
            answer.add(question.type)
            answer.add(my_picks)
            val answers_bundle: Bundle = this.arguments["answers"] as Bundle
            answers_bundle.putSerializable(question.id.toString(), answer)
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
                    creatUserSurvey(answers_bundle)

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

    fun getAlternativeId(text: String, choices: ArrayList<*>): Int {
        for (i in 0 until choices.count()) {
            var choice: Alternative = choices[i] as Alternative
            if (choice.value == text) {
                return choice.id
            }
        }
        return 0
    }

    fun creatUserSurvey(bundle: Bundle?) {
        val poll_id = bundle!!.getSerializable("poll_id") as Int
        bundle.remove("poll_id")
        Preferences = this.activity.getSharedPreferences(Preferences_name, Context.MODE_PRIVATE)
        val user_id : Int = Preferences!!.getInt("id", 0)
        val user_survey : JSONObject = JSONObject()
        user_survey.put("survey", poll_id)
        user_survey.put("user",user_id)
        user_survey.put("finished",true)

        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/user_surveys/"

        val jsonRequest = JsonObjectRequest(url, user_survey,
                Response.Listener { response ->
                    println("Response is: $response")
                    val us_id : Int = response.getInt("id")
                    sendAnswers(bundle,us_id)
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    //Toast.makeText(this, "Wrong Username/Password.", Toast.LENGTH_SHORT).show()
                    println("That didn't work!")
                })
        queue.add(jsonRequest)

    }

    fun sendAnswers(bundle: Bundle?, us_id: Int) {

        for (key in bundle!!.keySet()) {
            val answer : JSONObject = JSONObject()
            answer.put("question", key)
            answer.put("user_survey",us_id)
            val values  = bundle.getSerializable(key) as ArrayList<Serializable>
            val type = values.get(0)
            answer.put("type",type)
            if (type == 0){
                answer.put("answer",values.get(1))
            }
            else if (type == 1){
                answer.put("answer","null")
            }
            else if (type == 2){
                answer.put("answer","null")
            }

            val queue = Volley.newRequestQueue(context)
            val url = "https://app-api.assadi.io/api/answers/"

            val jsonRequest = JsonObjectRequest(url, answer,
                    Response.Listener { response ->
                        println("Response is: $response")
                        val ans_id : Int = response.getInt("id")
                        if (type == 1){
                            val alt = values.get(1) as Int
                            val mult = ArrayList<Int>()
                            mult.add(alt)
                            sendAlternatives(mult,ans_id)
                        }
                        else if (type == 2){
                            val mult = values.get(1) as ArrayList<Int>
                            sendAlternatives(mult,ans_id)
                        }
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        println("That didn't work!")
                    })
            queue.add(jsonRequest)
        }
    }

    fun sendAlternatives(mult: ArrayList<Int>, ans_id: Int){
        for (i in 0..(mult.size - 1)) {
            val alternative : JSONObject = JSONObject()
            alternative.put("answer", ans_id)
            alternative.put("alternative", mult.get(i))
            val queue = Volley.newRequestQueue(context)
            val url = "https://app-api.assadi.io/api/alternative_answers/"

            val jsonRequest = JsonObjectRequest(url, alternative,
                    Response.Listener { response ->
                        println("Response is: $response")
                    },
                    Response.ErrorListener { error ->
                        error.printStackTrace()
                        println("That didn't work!")
                    })
            queue.add(jsonRequest)
        }
    }
}
