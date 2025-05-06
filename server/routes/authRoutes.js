import express from 'express'
import { login, logout, register,deleteAccount } from '../controllers/auth/authController.js';

import userAuth from '../middleware/userAuth.js'
const authRouter = express.Router();
authRouter.post('/register',register)
authRouter.post('/login',login)
authRouter.post('/logout',logout)
authRouter.delete("/delete-account", userAuth, deleteAccount);


export default authRouter;