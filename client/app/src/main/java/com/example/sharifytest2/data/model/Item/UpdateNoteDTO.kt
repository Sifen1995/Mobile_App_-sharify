package com.example.sharifytest2.data.model.Item

import com.example.sharifytest2.domain.models.userItemModel.UpdateNote



data class UpdateNoteDTO(
    val note: String
) {
    fun toDomainModel(): UpdateNote {
        return UpdateNote(note = this.note)
    }
}