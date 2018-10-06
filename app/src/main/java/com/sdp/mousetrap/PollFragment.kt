package com.sdp.mousetrap


import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.*
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_poll.*


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
        setUpPollIntro(poll, view)
        return view
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
                var lista: ArrayList<String> = ArrayList()
                lista.add("Opcion 1")
                lista.add("Opcion 2")
                lista.add("Opcion 3")
                lista.add("Opcion 4")
                lista.add("Opcion 5")
                val transaction = delegate!!.createFragmentManager().beginTransaction()
                transaction.replace(R.id.question_frame, MultipleChoiceFragment.newInstance(lista))
                transaction.addToBackStack(null)
                transaction.commit()
            }
            builder.setNegativeButton("NO") {dialog, which ->
                Toast.makeText(context, "Operation cancelled", Toast.LENGTH_SHORT).show()
            }
            var dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }


}
