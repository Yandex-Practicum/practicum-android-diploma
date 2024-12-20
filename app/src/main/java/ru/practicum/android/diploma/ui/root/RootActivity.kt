package ru.practicum.android.diploma.ui.root

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.practicum.android.diploma.R
import ru.practicum.android.diploma.databinding.ActivityRootBinding

class RootActivity : AppCompatActivity() {
    private var binding: ActivityRootBinding? = null

    // private lateinit var networkClient: RetrofitNetworkClient // for test button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityRootBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.container_view) as NavHostFragment
        val navController = navHostFragment.navController

        val bottomNavigationView = binding?.bottomNavigationView
        bottomNavigationView?.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.vacancyFragment -> {
                    bottomNavigationView?.visibility = View.GONE
                }
                else -> {
                    bottomNavigationView?.visibility = View.VISIBLE
                }
            }
        }

        // start of test block
//        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        networkClient = RetrofitNetworkClient(connectivityManager)
//        binding?.testButton?.setOnClickListener { findVacancy("5621764775") }
        // end of test block
    }
    // start of test block
//    private fun findVacancy(vacancyName: String) {
//        CoroutineScope(Dispatchers.Main).launch {
//            val vacancySearchRequest = VacancySearchRequest(vacancyName)
//            val response = withContext(Dispatchers.IO) {
//                networkClient.doRequest(vacancySearchRequest)
//            }
//            if (response.resultCode == 200) {
//                Log.d("RootActivity", "Успешный вывод вакансий: ${response.resultCode}")
//                Toast.makeText(this@RootActivity, "Запрос выполнен успешно!", Toast.LENGTH_SHORT).show()
//            } else {
//                Log.e("RootActivity", "Ошибка при выполнении запроса: ${response.resultCode}")
//                Toast.makeText(this@RootActivity, "Ошибка: ${response.resultCode}", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }
    //  end of test block
}
