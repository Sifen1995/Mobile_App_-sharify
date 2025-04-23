import bcrypt from 'bcryptjs';
import jwt from 'jsonwebtoken';

import userModel from '../models/userModel.js';  
import transporter from '../config/nodeMailer.js';

export const register = async(req,res) =>{
     

    const{name,email,password} = req.body
    if (!name || !email || !password){
        return res.json({success:false,message:'MIssing detaile'})

    }
    try{
        const existingUser = await userModel.findOne({email})

        if (existingUser){
            return res.json({success: false,message:"user already exists"})

        }
        const hashedPassword = await bcrypt.hash(password,10);

        const user = new userModel({name,email,password: hashedPassword})

        await user.save();

        const token = jwt.sign({id:user._id},process.env.JWT_SECRET,{expiresIn:'7d'});

        res.cookie('token',token,{
            httpOnly: true,
            secure: process.env.Node_ENV == 'production',
            sameSite:process.env.NODE_ENV =='production'? 'none':'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000


        });
        //sending welcome email
        const mailOptions = {
            from: process.env.SENDER_EMAIL,
            to: email,
            subject:"Welcome to Zufan",
            text: `welcome email_id: ${email}`
        }
        // Fix the typo when sending email
        await transporter.sendMail(mailOptions); // NOT trasnporter
        return res.json({ success: true, message: 'User registered successfully' });


    }catch(error){
        res.json({success: false, message:error.message})
    }
}
export const login = async(req,res) =>{
    const {email,password} = req.body;
    if(!email || !password){
        res.json({success: false,message : 'email and password are requred'})

    }
    try{
        const user = await userModel.findOne({email});
        if(!user){
            return res.json({success:false,message:'Invalid email'})

        }
        const isMatch = await bcrypt.compare(password,user.password)
        if(!isMatch){
            return res.json({ success: false, message: 'Invalid password' });

        }

        const token = jwt.sign({id:user._id},process.env.JWT_SECRET,{expiresIn:'7d'});

        res.cookie('token',token,{
            httpOnly: true,
            secure: process.env.Node_ENV == 'production',
            sameSite:process.env.NODE_ENV =='production'? 'none':'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000


        });
        return res.json({ success: true, message: 'User sign in successfully',role: user.role});


    }catch(error){
        return res.json({success:false,message: error.message})
    }

}

export const loginAdmin = async (req, res) => {
    const { email, password } = req.body;

    if (!email || !password) {
        return res.json({ success: false, message: 'Email and password are required' });
    }

    try {
        const admin = await userModel.findOne({ email, role: 'admin' }); // Make sure role is defined in the schema
        if (!admin) {
            return res.status(404).json({ success: false, message: 'Admin not found' });
        }

        const isMatch = await bcrypt.compare(password, admin.password);
        if (!isMatch) {
            return res.status(401).json({ success: false, message: 'Invalid password' });
        }

        const token = jwt.sign({ id: admin._id }, process.env.JWT_SECRET, {
            expiresIn: '7d'
        });

        res.cookie('token', token, {
            httpOnly: true,
            secure: process.env.NODE_ENV === 'production',
            sameSite: process.env.NODE_ENV === 'production' ? 'none' : 'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000
        });

        return res.json({ success: true, message: 'Admin signed in successfully',role: user.role });

    } catch (err) {
        return res.status(500).json({ success: false, message: err.message});
    }
};


export const logout = async (req,res) =>{
    try{
        res.clearCookie('token',{
            httpOnly: true,
            secure: process.env.Node_ENV == 'production',
            sameSite:process.env.NODE_ENV =='production'? 'none':'strict',
            maxAge: 7 * 24 * 60 * 60 * 1000

        })

        return res.json({success:true,message:'Logged out'})
        
    }catch(error){
        return res.json({success:false,message: error.message})
    }

}

export const sendVerifyOtp = async(req,res) =>{
    try{
        const {userId} = req.body;
        const user = await userModel.findById(userId);
        if(user.isAccountVerified){

            return res.json({success:false,message:"Account already verified"})
        }
        const otp = String(Math.floor(100000 + Math.random()* 900000));
        user.verifyOtp = otp;
        user.verifyOtpExpireAt = Date.now() + 24 * 60 * 60 * 1000

        await user.save();
        const mailOptions = {
            from: process.env.SENDER_EMAIL,
            to: user.email,
            subject:"Account verification OTP",
            text: `Your otp  : ${otp} verifiy your account`
        }
        await transporter.sendMail(mailOptions);
        
        res.json({success:true,message:'verification Otp send on email'})



    } catch(error){
        return res.json({success:false,message:error.message})
    }
}

export const verifyEmail = async (req,res) => {
    const {userId,otp} = req.body;

    if(!userId || !otp){
        return res.json({success:false,message:
            'Missing Detaile'
        })
    }
    try{
        const user = await userModel.findById(userId);
        if(!user){
            return res.json({success:false, message:"user not found"})
        }

        if(user.verifyOtp == '' || user.verifyOtp !== otp){
            return res.json({success:false, message:"invalid otp"})
        }
        if (user.verifyOtpExpireAt < Date.now()){
            return res.json({success:false, message:"otp expired"})

        }
        user.isAccountVerified = true;
        user.verifyOtp = ''
        user.verifyOtpExpireAt = 0

        await user.save()
        return res.json({success:true,message:'email verified successfully'})

    }catch(error){
        return res.json({success:false, message:error.message})
    }

}


//check if user is authenticated
export const isAuthenticated = async(req,res) =>{
    try {
        return res.json({success:true});
        
    } catch (error) {
        return res.json({success:false,message:error.message})
        
    }

}

//send password reset otp

export const sendResetOtp = async(req,res) =>{
    const {email} = req.body;

    if (!email){
        return res.json({success:false,message:"email required"})
    }
    try {

        const user = await userModel.findOne({email});
        if(!user){
            return res.json({success:false,message:"user not found"});
        }
        const otp = String(Math.floor(100000 + Math.random()* 900000));
        user.resetOtp = otp;
        user.resetOtpExpireAt = Date.now() + 15 * 60 * 1000

        await user.save();
        const mailOptions = {
            from: process.env.SENDER_EMAIL,
            to: user.email,
            subject:"Password Reset",
            text: `Your otp  : ${otp} reset your password`
        };
        await transporter.sendMail(mailOptions);
        
        res.json({success:true,message:'reset otp send successfully'})
        
    } catch (error) {
        
        return res.json({success:false,message:error.message})
    }
}

export const resetPassword = async(req,res) =>{
    const {email,otp,newPassword} = req.body;
    if(!email || !otp || !newPassword){
        return res.json({success:false,message:
            'Missing Detaile'
        })
    }
    try {
        const user = await userModel.findOne({email});
        if(!user){
            return res.json({success:false, message:"user not found"})


        }
        if(user.resetOtp == '' || user.resetOtp !== otp){
            return res.json({success:false, message:"invalid otp"})
        }
        if (user.resetOtpExpireAt < Date.now()){
            return res.json({success:false, message:"otp expired"})

        }
        const hashedNewPassword = await bcrypt.hash(newPassword,10);
        user.password  = hashedNewPassword
        user.resetOtp = "";
        user.resetOtpExpireAt = 0;

        await user.save();
        return res.json({success:true, message:"password has been reset successfully"})
        
    } catch (error) {
        return res.json({success:false, message:error.message})
        
    }
    
}