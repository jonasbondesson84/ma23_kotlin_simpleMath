package com.example.simplemath

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


    class HighScoreAdapter(private val context: Context, var highScoreList: List<HighScoreUser>): RecyclerView.Adapter<HighScoreAdapter.HighScoreViewHolder>() {

    inner class HighScoreViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvName)
        val tvScore: TextView = itemView.findViewById(R.id.tvScore)
        val tvMethod: TextView = itemView.findViewById(R.id.tvMethod)
        val tvDifficulty: TextView = itemView.findViewById(R.id.tvDifficulty)
        val tvDate: TextView = itemView.findViewById(R.id.tvDate)

    }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HighScoreViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_highscore, parent, false)
            return HighScoreViewHolder(view)
        }

        override fun onBindViewHolder(holder: HighScoreViewHolder, position: Int) {
            holder.apply {
                tvName.text = highScoreList[position].name
                tvScore.text = highScoreList[position].score.toString()
                tvMethod.text = getMethodSymbol(highScoreList[position].method)
                tvDifficulty.text = getDifficultyName(highScoreList[position].difficulty)
                tvDate.text = highScoreList[position].date
            }
        }

        override fun getItemCount(): Int {
            return highScoreList.size
        }

        fun getMethodSymbol(method: String): String {
            return when(method) {
                "addition" -> {
                    "+"
                }
                "subtraction" -> {
                    "-"
                }
                "division" -> {
                    "/"
                }
                else -> {
                    "*"
                }
            }
        }

       fun getDifficultyName(difficulty: Int): String {
           return when (difficulty){
               0 -> {
                   context.getString(R.string.easy)
               }
               1 -> {
                   context.getString(R.string.medium)
               }
               else -> {
                   context.getString(R.string.hard)
               }
           }
       }
    }