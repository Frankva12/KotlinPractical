package com.franciscostanleyvasconceloszelaya.userssp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.franciscostanleyvasconceloszelaya.userssp.databinding.ActivityMainBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var userAdapter: UserAdapter
    private lateinit var linearLayoutManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferences = getPreferences(Context.MODE_PRIVATE)

        var isFirstTime = preferences.getBoolean(getString(R.string.sp_first_time), true)
        Log.i("SP", "${getString(R.string.sp_first_time)} $isFirstTime")

        if (isFirstTime) {
            val dialogView = layoutInflater.inflate(R.layout.dialog_register, null)
            MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setView(dialogView)
                .setCancelable(false)
                .setPositiveButton(R.string.dialog_confirm) { _, _ ->
                    val username = dialogView.findViewById<TextInputEditText>(R.id.etUserName)
                        .text.toString()
                    with(preferences.edit()) {
                        putBoolean(getString(R.string.sp_first_time), false)
                        putString(getString(R.string.sp_username), username)
                            .apply()
                    }
                    Toast.makeText(this, R.string.register_success, Toast.LENGTH_SHORT)
                        .show()
                }
                .setNeutralButton(R.string.dialog_invite_user) { _, _ ->
                    Toast.makeText(this, "Welcome Guest", Toast.LENGTH_SHORT)
                        .show()
                    isFirstTime = false
                }
                .show()
        } else {
            val username = preferences.getString(
                getString(R.string.sp_username),
                getString(R.string.hint_username)
            )
            Toast.makeText(this, "Welcome $username", Toast.LENGTH_SHORT)
                .show()
        }

        userAdapter = UserAdapter(getUsers(), this)
        linearLayoutManager = LinearLayoutManager(this)

        binding.recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = linearLayoutManager
            adapter = userAdapter
        }
    }

    private fun getUsers(): MutableList<User> {
        val users = mutableListOf<User>()

        val alain = User(
            1,
            "Alain",
            "Nicolas",
            "https://www.eluniverso.com/resizer/G0VXbXbtCUsveoP0A36FPaUR_wA=/1005x670/smart/filters:quality(70)/cloudfront-us-east-1.images.arcpublishing.com/eluniverso/HW3QRSTWTBEIRIBGLBKHUT5Q4Y.jpg"
        )
        val samanta = User(
            2,
            "Samanta",
            "Messa",
            "https://blog.edenred.es/wp-content/uploads/2016/08/gestionar-personas-toxicas.jpg"
        )
        val judith = User(
            3,
            "Judith",
            "Galdamez",
            "https://i.pinimg.com/280x280_RS/0f/66/73/0f66738ecd559dc5e275a720d626ecf5.jpg"
        )
        val frank = User(
            4,
            "Frank",
            "Vasconcelos",
            "https://pbs.twimg.com/profile_images/506597503475535872/GSF_YSHC_400x400.jpeg"
        )

        users.add(alain)
        users.add(samanta)
        users.add(judith)
        users.add(frank)
        users.add(alain)
        users.add(samanta)
        users.add(judith)
        users.add(frank)
        users.add(alain)
        users.add(samanta)
        users.add(judith)
        users.add(frank)
        users.add(alain)
        users.add(samanta)
        users.add(judith)
        users.add(frank)
        users.add(alain)
        users.add(samanta)
        users.add(judith)
        users.add(frank)

        return users
    }

    override fun onClick(user: User, position: Int) {
        Toast.makeText(this, "$position: ${user.getFullName()}", Toast.LENGTH_SHORT)
            .show()
    }
}