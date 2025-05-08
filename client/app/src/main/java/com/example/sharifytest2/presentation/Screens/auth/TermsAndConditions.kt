import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.sharifytest2.presentation.Components.authComponents.HeadingTextComponent
import com.example.sharifytest2.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TermsAndConditions(navController: NavController) {
   // Handle back button to go to SignUp screen
   val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
   DisposableEffect(backDispatcher) {
      val callback = object : OnBackPressedCallback(true) {
         override fun handleOnBackPressed() {
            navController.navigate(Screen.SignUp.route)
         }
      }
      backDispatcher?.addCallback(callback)
      onDispose {
         callback.remove()
      }
   }

   var isChecked by remember { mutableStateOf(false) }

   Scaffold(
      topBar = {
         TopAppBar(
            title = {
               Text(
                  text = "Terms & Conditions",
                  style = MaterialTheme.typography.titleMedium,
                  fontWeight = FontWeight.ExtraBold
               )
            },
            navigationIcon = {
               IconButton(onClick = {
                  navController.navigate(Screen.SignUp.route)
               }) {
                  Icon(
                     imageVector = Icons.Default.ArrowBack,
                     contentDescription = "Back"
                  )
               }
            }
         )

      },
      content = { padding ->
         Column(
            modifier = Modifier
               .fillMaxSize()
               .padding(padding)
               .padding(16.dp),
            verticalArrangement = Arrangement.SpaceBetween
         ) {
            Column(
               modifier = Modifier
                  .weight(1f)
                  .verticalScroll(rememberScrollState())
            ) {
               HeadingTextComponent(value = "Welcome to Sharify!")
               Spacer(modifier = Modifier.height(12.dp))

               Text(
                  text = "Please read the following Terms and Conditions carefully before using the app.",
                  fontSize = 16.sp,
                  color = Color.DarkGray
               )
               Spacer(modifier = Modifier.height(16.dp))

               TermsSection("1. User Responsibilities") {
                  BulletPoint("You must be at least 18 years old to use Sharify.")
                  BulletPoint("You are solely responsible for the items you list or borrow.")
                  BulletPoint("You must ensure that items you lend are safe and in working condition.")
               }

               TermsSection("2. Borrowing and Lending") {
                  BulletPoint("You agree to return items on time and in original condition.")
                  BulletPoint("Lost or damaged items must be compensated as agreed with the lender.")
                  BulletPoint("All borrowing activities must be done within Sharify's system.")
               }

               TermsSection("3. Account and Privacy") {
                  BulletPoint("You are responsible for maintaining your account credentials.")
                  BulletPoint("We collect minimal personal data, only to ensure platform integrity.")
                  BulletPoint("Data is stored locally and is never sold or shared with third parties.")
               }

               TermsSection("4. Misuse and Termination") {
                  BulletPoint("Violating guidelines may lead to temporary or permanent account suspension.")
                  BulletPoint("Sharify has the right to remove inappropriate listings or users.")
               }



            }


         }
      }
   )
}

@Composable
fun TermsSection(title: String, content: @Composable () -> Unit) {
   Text(
      text = title,
      fontWeight = FontWeight.Bold,
      fontSize = 16.sp,
      color = Color.Black
   )
   Spacer(modifier = Modifier.height(6.dp))
   content()
   Spacer(modifier = Modifier.height(16.dp))
}

@Composable
fun BulletPoint(text: String) {
   Row(modifier = Modifier.padding(start = 12.dp, top = 4.dp)) {
      Text("•", fontWeight = FontWeight.Bold)
      Spacer(modifier = Modifier.width(6.dp))
      Text(text, fontSize = 15.sp, color = Color.DarkGray)
   }
}
