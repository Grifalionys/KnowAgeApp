package grifalion.ru.apptest.ui.fragments.favourite

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import grifalion.ru.apptest.R
import grifalion.ru.apptest.adapter.NameAdapter
import grifalion.ru.apptest.databinding.FragmentSecondBinding
import grifalion.ru.apptest.model.NameModel
import grifalion.ru.apptest.viemodel.NameViewModel



class FavouriteFragment : Fragment() {
    private lateinit var binding: FragmentSecondBinding
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: NameAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentSecondBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()

    }

     private fun init(){
        val viewModel = ViewModelProvider(this).get(NameViewModel::class.java)
        viewModel.initDatabase()
        recyclerView = binding.rcView
        adapter = NameAdapter(
            {show -> showDelete(show) },
            {items -> showCheckBoxes(items)}
        )
         recyclerView.adapter = adapter
         viewModel.getAllNames()
         viewModel.getNames.observe(viewLifecycleOwner,{ namelist->
            adapter.addName(namelist.asReversed())
        })
        binding.btnDelete.setOnClickListener {
            delete()
        }
        showDelete(false)
    }

    private fun showDelete(show: Boolean){
        binding.btnDelete?.isVisible = show

    }
    private fun showCheckBoxes(data: List<NameModel>){
        val mappedItems = data.map {
            it.copy(isCheckBoxVisible = true)
        }
        adapter.addName(mappedItems)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.btnDelete -> {delete()}
        }
        return super.onOptionsItemSelected(item)
    }
    private fun hideCheckBox(){

    }


    private fun delete() {
            val viewModel = ViewModelProvider(this).get(NameViewModel::class.java)
            viewModel.initDatabase()

            val builder = AlertDialog.Builder(requireContext())
            val customView = LayoutInflater.from(requireContext()).inflate(R.layout.alert_dialog, null)
            builder.setView(customView)
            val dialog = builder.create()

            val btnYes = customView.findViewById<Button>(R.id.btnYes)
            val btnNo = customView.findViewById<Button>(R.id.btnNo)

            btnYes.setOnClickListener {

                showDelete(false)
                viewModel.deleteNamesByList(adapter.getItems())
                viewModel.getAllNames()

                dialog.dismiss()
            }
            btnNo.setOnClickListener {
                viewModel.getAllNames(true)
                showDelete(false)
                dialog.dismiss()
            }
            dialog.show()
    }
}