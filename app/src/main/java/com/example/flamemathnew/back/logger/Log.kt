package com.example.flamemathnew.back.logger

import java.io.PrintWriter

object Log {
    private val commits = mutableListOf<Commit>()
//    fun add(`object`: String, commit: String) {
//        objects.add(`object`)
//        commits.add(commit)
//    }
    fun commit(commit : String, tag : Tag){
        commits.add(Commit(commit, tag))
    }
    fun clear() {
        commits.clear()
    }
    fun printLog() {
        val printWriter = PrintWriter(System.out, true)
        for (i in commits.indices) {
            printWriter.println(commits[i].message)
        }
    }
    fun getLog() : MutableList<String>{
        val answer : MutableList<String> = mutableListOf()
        for (commit in commits) answer.add(commit.message)
        return answer
    }
    fun getTags() : MutableList<String>{
        val answer : MutableList<String> = mutableListOf()
        for (commit in commits) answer.add(commit.tag.name)
        return answer
    }
}

enum class Tag{
    BASE_RULES, METHOD_RULES, METHOD_DESCRIPTION, SOLUTION, OBJECT, SKIPPED, PROCEEDING, ANSWER
}
