package com.example.quizapp

object Constants {

    const val USER_NAME: String = "user_name"
    const val TOTAL_QUESTIONS: String = "total_questions"
    const val CORRECT_ANSWERS: String = "correct_answers"

    fun getQuestions(): ArrayList<Question> {
        val questionList = arrayListOf<Question>()

        val que1 = Question(
            1,
            "Qual a raça dessa foto?",
            R.drawable.border,
            "Boiadeiro australiano",
            "Boxer",
            "Border collie", "Cocker spaniel inglês", 3

        )
        questionList.add(que1)

        val que2 = Question(
            2,
            "Qual a raça dessa foto?",
            R.drawable.cocker,
            "Boiadeiro australiano",
            "Boxer",
            "Border collie", "Cocker spaniel inglês", 4

        )
        questionList.add(que2)

        val que3 = Question(
            3,
            "Qual a raça dessa foto?",
            R.drawable.shiba,
            "Shiba",
            "Papillon",
            "Lhasa apso", "Jack russell terrier", 1

        )
        questionList.add(que3)

        val que4 = Question(
            4,
            "Qual a raça dessa foto?",
            R.drawable.jack,
            "Lhasa apso",
            "Jack russell terrier",
            "Papillon", "Cocker spaniel inglês", 2

        )
        questionList.add(que4)

        val que5 = Question(
            5,
            "Qual a raça dessa foto?",
            R.drawable.lucky,
            "Lulu da Pomerânia",
            "Boxer",
            "Papillon", "Cocker spaniel inglês", 1

        )
        questionList.add(que5)

        val que6 = Question(
            6,
            "Qual a raça dessa foto?",
            R.drawable.toddy,
            "Shiba",
            "Papillon",
            "Lhasa apso", "Dachshund", 4

        )
        questionList.add(que6)

        return questionList
    }
}