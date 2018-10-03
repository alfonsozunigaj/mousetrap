package com.sdp.mousetrap


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso


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

        description.text = poll.description
        logo.loadUrl(poll.image_url)
    }

    fun ImageView.loadUrl(url: String) {
        Picasso.with(context).load(url).into(this)
    }


}
