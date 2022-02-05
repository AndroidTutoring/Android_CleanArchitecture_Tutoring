package com.example.presentation.util

import timber.log.Timber

class TimberDebugTree : Timber.DebugTree() {

    //stack trace 내용 커스톰
    override fun createStackElementTag(element: StackTraceElement): String {
        return "(${element.fileName}:${element.lineNumber}) #${element.methodName}"
    }
}