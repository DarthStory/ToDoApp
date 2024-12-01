package com.example.todoapp

import java.util.UUID

class TaskItem (var name: String, var desc: String, var id: UUID = UUID.randomUUID()) {

}