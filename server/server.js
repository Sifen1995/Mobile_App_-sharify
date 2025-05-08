import express from "express";
import cors from "cors";
import 'dotenv/config';
import cookieParser from "cookie-parser";
import { connectDB, createAdmin } from "./config/mongodb.js";
import authRouter from './routes/authRoutes.js'
import userRouter from "./routes/userRoutes.js";
import Adminrouter from "./routes/admin/ItemsRoutes.js";
import BorrowRouter from "./routes/user/BorrowRoutes.js";

const app = express();
const Port = process.env.PORT || 4000
connectDB();
createAdmin();

app.use(express.json());
app.use(express.urlencoded({ extended: true }));

app.use(cookieParser());
app.use(cors({
    credentials: true,
    origin: "*"
}));


app.get('/',(req,res)=>res.send("API working fine"));
app.use("/images", express.static("upload/images/general")); 

app.use('/api/auth',authRouter)
app.use('/api/user',userRouter)
app.use('/api/admin', Adminrouter); 
app.use('/api/borrow',BorrowRouter ); 
 

app.listen(Port,'0.0.0.0',() => console.log(`sever started on PORT:${Port}`));