package fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.example.myapplication.R


class Fragment3 : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_3, container, false)


        //Categories of expenses
        //val categories = arrayOf("Cinema", "Comestique", "Cours", "School", "Hobbit", "Love")
        //val categories = resources.getStringArray(R.array.categories)
        val spinner = v.findViewById<Spinner>(R.id.spinner)

        if (spinner != null) {
            //val arrayAdapter = ArrayAdapter(v.context, R.layout.spinner_layout, R.id.text, categories)
            //spinner.adapter = arrayAdapter
            val imageName = arrayOf("Video", "PrifilePic", "Checked", "UnChecked")
            val image = intArrayOf(R.drawable.ic_f1, R.drawable.ic_f4, R.drawable.ic_f2, R.drawable.ic_f3)
            val spinnerCustomAdapter = SpinnerCustomAdapter(v.context, image, imageName);
            spinner.adapter=spinnerCustomAdapter

        }

        return v
    }
    class SpinnerCustomAdapter(internal var context: Context, internal var flags: IntArray, internal var Network: Array<String>) : BaseAdapter() { internal var inflter: LayoutInflater
        init {
            inflter = LayoutInflater.from(context)
        }
        override fun getCount(): Int {
            return flags.size
        }
        override fun getItem(i: Int): Any? {
            return null
        }
        override fun getItemId(i: Int): Long {
            return 0
        }
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            view = inflter.inflate(R.layout.custom_spinner_items, null)
            val icon = view.findViewById(R.id.spinner_imageView) as ImageView
            val names = view.findViewById(R.id.spinner_textView) as TextView
            icon.setImageResource(flags[i])
            names.text = Network[i]
            return view
        }
    }
}