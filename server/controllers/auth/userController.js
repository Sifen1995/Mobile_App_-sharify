import userModel from "../../models/userModel.js";
import path from 'path';
import { fileURLToPath } from 'url';
 
export const getUserData = async(req,res)=>{
    try {
        const {userId} = req.body;

        const user = await userModel.findById(userId);

        if(!user){
            return res.json({success:True, message:"user NOt found"})
        }
        res.json({success:true,
            userData:{
                name:user.name,
                isAccountVerified:user.isAccountVerified
            }
        })

        
    } catch (error) {
        
        return res.json({success:false, message:error.message})
    }
}
 
const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

export const updateProfile = async (req, res) => {
    try {
        const { id } = req.params;
        const { name } = req.body;
        const file = req.file;

        console.log("Received ID:", id);
        console.log("Uploaded File:", file);
        console.log("Body Name:", name);
 
        const user = await userModel.findById(id);
        if (!user) {
            return res.status(404).json({ success: false, message: "User not found" });
        }

       
        if (file) {
            
            if (user.profilePicture) {
                const oldImagePath = path.join(__dirname, "../upload/images", user.profilePicture.replace('/images/', ''));
                if (fs.existsSync(oldImagePath)) {
                    fs.unlinkSync(oldImagePath);
                    console.log("Old image deleted:", oldImagePath);
                }
            }

          
            user.profilePicture = `/images/${file.filename}`;
        }

     
        if (name) {
            user.name = name.replace(/^"|"$/g, '');
        }

        
        await user.save();

        res.status(200).json({
            success: true,
            message: "Profile updated successfully",
            updatedUser:  {
                id: user._id,
                name: user.name,
                email: user.email,
                profilePicture: user.profilePicture
            
    }});

    } catch (error) {
        console.error("Update error:", error.message);
        res.status(500).json({ success: false, message: "Internal server error" });
    }
};


