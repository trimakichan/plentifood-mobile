package com.example.plentifood.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plentifood.data.models.response.Site
import com.example.plentifood.ui.utils.cleanAddress
import com.example.plentifood.ui.utils.toTitleFromSnakeCase

@Composable
fun InfoCard(
    modifier: Modifier,
    site: Site,
    onClickSiteDetail: (Int) -> Unit,
    enableDelete: Boolean = false,
    onClickDelete: () -> Unit = {}
) {

    val services = site.services.map { it.name.toTitleFromSnakeCase() }
    val serviceNames = services.joinToString()

    val orgImage = when (site.organizationType) {
        "food_bank" -> com.example.plentifood.R.drawable.foodbank
        "non_profit" -> com.example.plentifood.R.drawable.nonprofit
        "church" -> com.example.plentifood.R.drawable.church
        "community_center" -> com.example.plentifood.R.drawable.community
        "others" -> com.example.plentifood.R.drawable.others
        else -> com.example.plentifood.R.drawable.others
    }

    Box(
        modifier = modifier
            .fillMaxWidth(0.95f)
            .clickable { onClickSiteDetail(site.id) }
            .height(130.dp)
            .border(
                BorderStroke(1.dp, MaterialTheme.colorScheme.onSurface),
                RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
            .padding(12.dp),
            contentAlignment = Alignment.Center
        ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            ) {

            Box(
                modifier = Modifier
                    .size(64.dp)
                    .clip(RoundedCornerShape(12.dp))
            ) {
                Image(
                    painter = painterResource(orgImage),
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

            }

            Spacer(Modifier.width(12.dp))
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    site.name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold
                )

                    Text(
                        cleanAddress(site).joinToString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.secondary,
                        lineHeight = 16.sp
                    )

                Row() {

                    Text(
                        "Service Type: ",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        )
                    Text(
                        serviceNames,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.secondary,

                        )
                }





            }
        }

        if (enableDelete) {
            ButtonWithIcon(
                icon = Icons.Outlined.Delete,
                onClick = { onClickDelete() },
                buttonModifier = Modifier
                    .size(32.dp)
                    .align(Alignment.BottomEnd),
                description = "Delete Site Button",
                tint = MaterialTheme.colorScheme.error,
            )
        }

    }


}



