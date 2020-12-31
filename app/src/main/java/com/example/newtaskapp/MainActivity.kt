package com.example.newtaskapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import java.util.*

lateinit var tabLayout: TabLayout
lateinit var tabViewPager: ViewPager2
private lateinit var TaskButton: Button


class MainActivity : AppCompatActivity() {
    interface Callbacks{
        fun onTaskSelected(taskId: UUID)

    }
    private var callbacks: Callbacks? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tabLayout=findViewById(R.id.tabs)
        tabViewPager=findViewById(R.id.pager)

        TaskButton = findViewById(R.id.add) as Button
        tabViewPager.adapter = object: FragmentStateAdapter(this){
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0 ->ToDoFragment.newInstance("TODO","")
                    1->PrograssFragment.newInstance("second","")
                    2->DoneFragment.newInstance("third","")
                    else ->ToDoFragment.newInstance("first","")
                }
            }
            override fun getItemCount(): Int {
                return 3
            }

        }
        TabLayoutMediator(tabLayout,tabViewPager){ tab ,postion ->
            when (postion){
                0 ->{
                    tab.setText("Done")
                }
                1->{tab.setText("Prograss")
                    }
                2->{tab.setText("ToDo")
                }
                else -> null
            }

        }.attach()
        TaskButton.setOnClickListener {
                val intent = Intent(this,InputDialogFragment::class.java)
            startActivity(intent)
            }}

    }
