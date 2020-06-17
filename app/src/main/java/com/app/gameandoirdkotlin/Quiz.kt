package com.app.gameandoirdkotlin


class Quiz{

    var question:String = ""
    var reponse1: String = ""
    var reponse2: String = ""
    var reponse3: String = ""
    var reponseCorrect:Int
    init {
        var n1: Int = (0..500).random()
        var n2: Int = (0..500).random()
        var signe: Int = (0..3).random()
        var rep:Int = 0

        when (signe) {
            0 -> {
                rep = n1 + n2
                question = "${n1}+${n2} = ?"
            }
            1 -> {
                rep = n1 - n2
                question = "${n1}-${n2} = ?"
            }
            2 -> {
                rep = n1 * n2
                question = "${n1}*${n2} = ?"
            }
            3 -> {
                rep = n1 / n2
                question = "${n1}/${n2} = ?"
            }
        }
        var reponseCorrect = (1..3).random()
        this.reponseCorrect = reponseCorrect
        when (reponseCorrect) {
            1 -> {
                reponse1 = rep.toString()
                reponse2 = ((rep-100)..(rep+100)).random().toString()
                reponse3 = ((rep-100)..(rep+100)).random().toString()
            }
            2 -> {
                reponse1 = ((rep-100)..(rep+100)).random().toString()
                reponse2 = rep.toString()
                reponse3 = ((rep-100)..(rep+100)).random().toString()
            }
            3 -> {
                reponse1 = ((rep-100)..(rep+100)).random().toString()
                reponse2 = ((rep-100)..(rep+100)).random().toString()
                reponse3 = rep.toString()
            }
        }
    }


    fun format():Quiz{
        question = "\t\t\t\t"+question
        reponse1 = "\t\t\t\t"+reponse1
        reponse2 = "\t\t\t\t"+reponse2
        reponse3 = "\t\t\t\t"+reponse3
        return this
    }

    fun isCorrect(reponseId:Int):Boolean{
        if(reponseId==reponseCorrect){
            return true
        }
        return false
    }
}