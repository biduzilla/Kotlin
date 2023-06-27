package com.example.quizcompose.repository

import android.util.Log
import com.example.quizcompose.data.DataOrException
import com.example.quizcompose.model.QuestionItem
import com.example.quizcompose.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val dataOrException = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()

    suspend fun getAllQuestions(): DataOrException<ArrayList<QuestionItem>, Boolean, Exception> {
        try {
            dataOrException.loading = true
            dataOrException.data = api.getAllQuestions()

            if (dataOrException.data.toString().isNotEmpty()) {
                dataOrException.loading = false
            }
        } catch (e: Exception) {
            dataOrException.e = e
            Log.i("infoteste", "getAllQuestions: ${dataOrException.e!!.localizedMessage}")
        }

        return dataOrException
    }

}