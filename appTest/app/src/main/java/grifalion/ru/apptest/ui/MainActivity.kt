package grifalion.ru.apptest.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import grifalion.ru.apptest.R
import grifalion.ru.apptest.databinding.ActivityMainBinding
import grifalion.ru.apptest.ui.fragments.main.MainFragment
import grifalion.ru.apptest.ui.fragments.favourite.FavouriteFragment



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBottomView()
    }

    private fun initBottomView(){
        replaceFragment(MainFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId){
                R.id.home ->  replaceFragment(MainFragment())
                R.id.favorites -> replaceFragment(FavouriteFragment())
            }
            true
        }
    }

    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.place_holder,fragment)
            .commit()
    }
}


