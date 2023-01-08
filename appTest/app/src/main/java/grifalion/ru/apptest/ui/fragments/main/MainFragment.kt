package grifalion.ru.apptest.ui.fragments.main

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import grifalion.ru.apptest.databinding.FragmentFirstBinding
import grifalion.ru.apptest.model.NameModel
import grifalion.ru.apptest.repository.AgeRepository
import grifalion.ru.apptest.viemodel.AgeViewModel
import grifalion.ru.apptest.viemodel.AgeViewModelFactory
import grifalion.ru.apptest.viemodel.NameViewModel


class MainFragment : Fragment() {

    private lateinit var binding: FragmentFirstBinding
    private lateinit var viewModelAge: AgeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentFirstBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    fun init() = with(binding){
        val viewModel = ViewModelProvider(requireActivity()).get(NameViewModel::class.java)
        viewModel.initDatabase()
        tvInfo.visibility = View.VISIBLE

        edSearchName.setOnKeyListener({ _ , keyKode, event ->
            if (keyKode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP && edSearchName.text.toString().isNotBlank()) {
                getAge()
            }
            else if(edSearchName.text.toString().isBlank() && keyKode==KeyEvent.KEYCODE_ENTER){
                Toast.makeText(requireContext(),"Некорректно введено имя. Введите имя без пробелов.", Toast.LENGTH_SHORT).show()
                hideAgeElements()
            }
            false
        })

        tvAddFavorite.setOnClickListener {
            val name = binding.edSearchName.text.toString()
            viewModel.insert(NameModel(name = name, isCheckBoxVisible = false, isCheckedBox = false))
            Toast.makeText(requireContext(),"Имя ${binding.edSearchName.text.toString()} добавлено", Toast.LENGTH_SHORT).show()
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,"${edSearchName.text.toString()}, возраст: ${tvAge.text.toString()} лет")
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
    }

    private fun getAge(){
        val viewModelFactory = AgeViewModelFactory(AgeRepository())
        viewModelAge = ViewModelProvider(this,viewModelFactory).get(AgeViewModel::class.java)
        val myAge = binding.edSearchName.text.toString()
        viewModelAge.getCustomPosts(myAge)

        viewModelAge.myCustomPost.observe(viewLifecycleOwner, Observer { response ->
            if(response.isSuccessful) {
                response.body()?.age?.let { age ->
                    showAgeElements()
                    binding.tvAge.text = age.toString()
                }
            }
            if (response.body()?.age == null) {
                Toast.makeText(requireContext(), "Некорректно введено имя. Используйте только латинский алфавит", Toast.LENGTH_SHORT).show()
                hideAgeElements()
            }
        })
    }

     private fun showAgeElements() = with(binding){
         tvInfo.visibility = View.GONE
         tvAge.visibility = View.VISIBLE
         tvAddFavorite.visibility = View.VISIBLE
         btnShare.visibility = View.VISIBLE
    }

    private fun hideAgeElements() = with(binding){
        tvAddFavorite.visibility = View.GONE
        tvAge.visibility = View.GONE
        btnShare.visibility = View.GONE
        tvInfo.visibility = View.VISIBLE
    }
}