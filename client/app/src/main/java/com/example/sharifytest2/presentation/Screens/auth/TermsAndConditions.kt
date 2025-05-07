import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.material3.Text
import androidx.navigation.NavController
import com.example.sharifytest2.presentation.Components.authComponents.HeadingTextComponent
import com.example.sharifytest2.navigation.Screen


@Composable
fun TermsAndConditions(navController: NavController) {
   val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

   // Add a back press callback
   DisposableEffect(backDispatcher) {
      val callback = object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
            // Navigate back to the sign-up screen
            navController.navigate(Screen.SignUp.route)
         }
      }
      backDispatcher?.addCallback(callback)

      // Cleanup the callback when the composable is removed
      onDispose {
         callback.remove()
      }
   }

   // UI content
   HeadingTextComponent(value = "Terms and Conditions")
   Text(text = "Your terms and conditions content goes here.")
}