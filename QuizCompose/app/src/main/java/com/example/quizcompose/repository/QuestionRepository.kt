package com.example.quizcompose.repository

import com.example.quizcompose.data.DataOrException
import com.example.quizcompose.model.QuestionItem
import com.example.quizcompose.network.QuestionApi
import javax.inject.Inject

class QuestionRepository @Inject constructor(private val api: QuestionApi) {
    private val listOfQuestions = DataOrException<ArrayList<QuestionItem>, Boolean, Exception>()
}