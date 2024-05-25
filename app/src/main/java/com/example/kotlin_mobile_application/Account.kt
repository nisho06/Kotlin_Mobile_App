package com.example.kotlin_mobile_application

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.android.volley.toolbox.Volley

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

private lateinit var nameTextView: TextView


/**
 * A simple [Fragment] subclass.
 * Use the [Account.newInstance] factory method to
 * create an instance of this fragment.
 */
class Account : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_account, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val firstName = arguments?.getString(Account.ARG_FIRSTNAME)
        val lastName = arguments?.getString(Account.ARG_LASTNAME)

        val nameToDisplay = firstName + " " + lastName;

        nameTextView = view.findViewById<TextView>(R.id.name)
        nameTextView.text = nameToDisplay;

        val signOutButton = view.findViewById<Button>(R.id.btnSignOut)
        signOutButton.setOnClickListener {
            // Perform sign-out action here
            signOut();
        }
    }

    companion object {

        private const val ARG_FIRSTNAME = "firstName";
        private const val ARG_LASTNAME = "lastName";
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Account.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(firstName: String?, lastName: String? ) =
            Account().apply {
                arguments = Bundle().apply {
                    putString(ARG_FIRSTNAME, firstName)
                    putString(ARG_LASTNAME, lastName)
                }
            }
    }

    private fun signOut() {
        // Clear user session, navigate to sign-in screen, etc.
        // Clearing shared preferences:
        val sharedPreferences = requireActivity().getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()

        (requireActivity() as UserHome).navigateToSignIn()
    }
}