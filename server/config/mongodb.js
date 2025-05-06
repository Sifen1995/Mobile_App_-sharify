import mongoose from "mongoose";

const connectDB = async () => {
  try {
    const conn = await mongoose.connect(`${process.env.MONGODB_URL}/Sharify`, {
      serverSelectionTimeoutMS: 5000 // 5 second connection timeout
    });
    console.log(`✅ MongoDB Connected: ${conn.connection.host}`);
    return conn; // Return the connection object
  } catch (error) {
    console.error("❌ Database connection error:", error.message);
    process.exit(1);
  }
};

// Separate admin creation function
 
import User from '../models/userModel.js';
import bcrypt from 'bcryptjs';

const createAdmin = async () => {
    try {
        

        // 2. Check for existing admin
        const adminEmail = "admin@gmail.com";
        const existingAdmin = await User.findOne({ email: adminEmail });

        if (existingAdmin) {
            console.log("⚠️ Admin already exists");
            return;
        }

        // 3. Create new admin
        const hashedPassword = await bcrypt.hash("admin", 12);
        await User.create({
            name: "Admin",
            email: adminEmail,
            password: hashedPassword,
            role: "admin"
        });
        console.log("✅ Admin created successfully");

    } catch (error) {
        console.error("❌ Error:", error.message);
        console.log("Please ensure MongoDB is running and accessible");
    }  
};


 
const initializeApp = async () => {
  await connectDB();
  await createAdmin();
};

export { connectDB, createAdmin };  
 