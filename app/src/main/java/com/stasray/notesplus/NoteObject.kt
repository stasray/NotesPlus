package com.stasray.notesplus

import java.util.*

data class NoteObject(var text : String?, var date: Date?, var completed: Boolean?) {
    constructor() : this(null, null, null)
}
