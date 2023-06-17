package com.dichvutaxi.lunarcalendar.models

import com.dichvutaxi.lunarcalendar.interfaces.ICommand

class CommandInvoker {
    private val commands = Hashtable<Int, ICommand>()

    fun setCommand(id: Int, command: ICommand) {
        if (!commands.containsKey(id)) {
            commands[id] = command
        }
    }

    @Throws(Exception::class)
    fun executeCommand(id: Int, date: DateObject) {
        if (commands.containsKey(id)) {
            commands[id]?.execute(date)
        }
    }
}
