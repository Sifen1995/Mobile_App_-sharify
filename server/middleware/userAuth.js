import jwt from 'jsonwebtoken';

const userAuth = async(req,res,next)=>{
   const token = req.cookies.token || req.headers.authorization?.split(' ')[1];

    if(!token){
        return res.json({success:false})
    }
    try{
        const tokenDecode = jwt.verify(token,process.env.JWT_SECRET);
        if (tokenDecode.id){
            // FIXED: Initialize req.body if undefined before setting userId
            req.body = req.body || {};
            req.body.userId = tokenDecode.id;
            next();
        }else{
            return res.json({success:false,message:"not authorized"}) // Added return
        }
    }catch(error){
        return res.json({success:false,message:error.message}) // Added return
    }
}

export default userAuth;