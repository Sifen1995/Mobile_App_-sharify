import express from 'express'
import { getUserData ,updateProfile} from '../controllers/auth/userController.js';
import userAuth from '../middleware/userAuth.js';
import upload from '../middleware/uploadMiddleware.js'
 


const userRouter = express.Router();
userRouter.get('/data',userAuth,getUserData)
 
userRouter.put('/profile/:id',upload.single('image'), (req, res) => {
    
   
    updateProfile(req, res);

});

export default userRouter;