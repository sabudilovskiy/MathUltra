package com.example.flamemathnew.backend.back.Logger

import java.io.PrintWriter

object Log {
    private val commits = ArrayList<Commit>()

    fun commit(commit: String, tag: Tag) {
        commits.add(Commit(commit, tag))
    }

    fun clear() {
        commits.clear()
    }

    fun print_log() {
        val printWriter = PrintWriter(System.out, true)
        for (i in commits.indices) {
            printWriter.println(commits[i].message)
        }
    }

    fun get_log(): ArrayList<String> {
        val answer: ArrayList<String> = arrayListOf()
        for (commit in commits) answer.add(commit.message)
        return answer
    }

    fun get_tags(): ArrayList<String> {
        val answer: ArrayList<String> = arrayListOf()
        for (commit in commits) answer.add(commit.tag.name)
        return answer
    }
}

enum class Tag {
    BASE_RULES, METHOD_RULES, METHOD_DESCRIPTION, SOLUTION, OBJECT, SKIPPED, PROCEEDING, ANSWER
}

class Commit(_message: String, _tag: Tag) {
    val message: String = _message
    val tag: Tag = _tag
}
