package com.sdp.mousetrap


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sdp.mousetrap.DB.Alternative
import com.sdp.mousetrap.DB.Poll
import com.sdp.mousetrap.DB.Question
import com.squareup.picasso.Picasso
import java.util.*


class PollFragment : Fragment() {
    companion object {
        fun newInstance(poll: Poll): PollFragment {
            val fragment = PollFragment()
            val args = Bundle()
            args.putSerializable("poll", poll)
            fragment.arguments = args
            return fragment
        }
    }

    var delegate: FragmentDelegate? = null
    val answers = Bundle()
    val questions: MutableList<Question> = ArrayList()
    val alternatives: MutableList<Alternative> = ArrayList()

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        if (context is FragmentDelegate) {
            delegate = context
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        var poll : Poll = arguments.getSerializable("poll") as Poll
        var view : View = inflater.inflate(R.layout.fragment_poll, container, false)
        val startButton = view.findViewById(R.id.start_button) as Button
        startButton.isClickable=false
        createQuestions(poll, view)
        //setUpPollIntro(poll, view)
        return view
    }

    fun createQuestions(poll: Poll, view: View) {
        val poll_id : Int = poll.id
        val queue = Volley.newRequestQueue(context)
        val url = "https://app-api.assadi.io/api/surveys/$poll_id"

        val jsonObjectRequest = JsonObjectRequest(url,null,
                Response.Listener { response ->
                    val Qs = response.getJSONArray("questions")
                    for (i in 0..(Qs.length() - 1)) {
                        val item = Qs.getJSONObject(i)
                        println("Response is: $item")
                        val id = item.getString("url").removeSurrounding("http://127.0.0.1:8000/api/questions/","/").toInt()
                        val description = item.getString("question")
                        val type = item.getString("type").toInt()
                        questions.add(Question(id,poll, description, type))
                        if (type != 0){
                            val As = item.getJSONArray("alternatives")
                            for (j in 0..(As.length() - 1)) {
                                val item2 = As.getJSONObject(j)
                                println("Alternative is: $item2")
                                val id2 = item2.getString("url").removeSurrounding("http://127.0.0.1:8000/api/alternatives/","/").toInt()
                                alternatives.add(Alternative(id2, questions[i], item2.getString("value")))
                            }
                        }
                    }
                    setUpPollIntro(poll, view)
                },
                Response.ErrorListener { error ->
                    error.printStackTrace()
                    println("Network error!")
                })

        queue.add(jsonObjectRequest)
    }

    fun setUpPollIntro(poll: Poll, view: View) {
        val description = view.findViewById(R.id.description) as TextView
        val logo = view.findViewById(R.id.logo) as ImageView
        val startButton = view.findViewById(R.id.start_button) as Button
        description.text = poll.description
        logo.loadUrl(poll.image_url)



        startButton.setOnClickListener {
            var builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation Message")
            builder.setMessage("Are you sure you want to answer this poll?")
            builder.setPositiveButton("YES") {dialog, which ->
                startButton.visibility = GONE
                val layouts: ArrayList<FrameLayout> = ArrayList()
                layouts.add(view.findViewById(R.id.question1_frame)  as FrameLayout)
                layouts.add(view.findViewById(R.id.question2_frame)  as FrameLayout)
                layouts.add(view.findViewById(R.id.question3_frame)  as FrameLayout)
                layouts[0].visibility = VISIBLE
                var last_question = false
                for (i in 0 until questions.count()) {
                    if (i == questions.count() -1) {
                        last_question = !last_question
                    }
                    var question: Question = questions[i]
                    when {
                        question.type == 0 -> {
                            //create open question in layouts[i]
                            var transaction = delegate!!.createFragmentManager().beginTransaction()
                            transaction.replace(layouts[i].id, OpenQuestionFragment.newInstance(answers, last_question, i, layouts, question.question, delegate))
                            transaction.commit()
                        }
                        question.type == 1 -> {
                            //create unique choice question in layouts[i]
                            val my_alternatives: ArrayList<Alternative> = ArrayList()
                            for (j in 0 until alternatives.count()) {
                                if (alternatives[j].question == question) {
                                    my_alternatives.add(alternatives[j])
                                }
                            }
                            var transaction = delegate!!.createFragmentManager().beginTransaction()
                            transaction.replace(layouts[i].id, UniqueChoiceFragment.newInstance(my_alternatives, answers, last_question, i, layouts, question.question, delegate))
                            transaction.commit()
                        }
                        question.type == 2 -> {
                            //create multiple choice question in layouts[i]
                            val my_alternatives: ArrayList<Alternative> = ArrayList()
                            for (j in 0 until alternatives.count()) {
                                if (alternatives[j].question == question) {
                                    my_alternatives.add(alternatives[j])
                                }
                            }
                            var transaction = delegate!!.createFragmentManager().beginTransaction()
                            transaction.replace(layouts[i].id, MultipleChoiceFragment.newInstance(my_alternatives, answers, last_question, i, layouts, question.question, delegate))
                            transaction.commit()
                        }
                    }
                }
                /*var transaction = delegate!!.createFragmentManager().beginTransaction()
                transaction.replace(R.id.question1_frame, OpenQuestionFragment())
                transaction.commit()
                startButton.setOnClickListener {
                    var ll2: FrameLayout = view.findViewById(R.id.question2_frame) as FrameLayout
                    ll1.visibility = GONE
                    ll2.visibility = VISIBLE
                    var lista: ArrayList<String> = ArrayList()
                    lista.add("Opcion 1")
                    lista.add("Opcion 2")
                    lista.add("Opcion 3")
                    lista.add("Opcion 4")
                    lista.add("Opcion 5")
                    transaction = delegate!!.createFragmentManager().beginTransaction()
                    transaction.replace(R.id.question2_frame, UniqueChoiceFragment.newInstance(lista))
                    transaction.commit()
                }*/
            }
            builder.setNegativeButton("NO") {dialog, which ->
                Toast.makeText(context, "Operation cancelled", Toast.LENGTH_SHORT).show()
            }
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }
        startButton.isClickable=true
    }

    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }
}
