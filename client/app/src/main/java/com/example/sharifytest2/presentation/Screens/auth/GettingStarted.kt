import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.sharifytest2.presentation.Components.authComponents.ButtonComponent
import com.example.sharifytest2.presentation.Components.authComponents.HeadingTextComponent
import com.example.sharifytest2.presentation.Components.authComponents.NormalTextComponent
import com.example.sharifytest2.R
import com.example.sharifytest2.navigation.Screen

@Composable
fun GettingStarted(navController: NavController) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween // 👈 Ensures proper spacing
        ) {
            Column(
                modifier = Modifier.weight(1f), // 👈 Centers Image & Text
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.landing),
                    contentDescription = "Getting Started Image",
                    // Adjust image size if needed
                )

                HeadingTextComponent("SHARIFY")
                NormalTextComponent("Join our vibrant sharing community today")
            }

            ButtonComponent(
                value = "Get Started",
                function = { navController.navigate(Screen.SignUp.route) },

                )
        }
    }
}

@Preview
@Composable
private fun GETTING() {
    GettingStarted(navController = rememberNavController())

}