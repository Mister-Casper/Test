package com.test.test.presentation.movieCharacters

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.test.test.R
import com.test.test.domain.model.CharacterListing

@Composable
fun CharacterItem(character: CharacterListing, modifier: Modifier = Modifier) {
    Card(modifier = modifier) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(text = stringResource(id = R.string.character_name,character.characterName), fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Text(text =  stringResource(id = R.string.character_gender,character.characterGender), fontSize = 16.sp)
            Text(text = stringResource(id = R.string.character_year,character.characterBirthday), fontSize = 16.sp)
        }
    }
}