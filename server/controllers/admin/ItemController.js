import ItemModel from "../../models/ItemModel.js";
import UserModel from "../../models/userModel.js";
import fs from "fs";
import path from "path";

import { configDotenv } from "dotenv";


export const handleUpload = (req, res) => {
    if (!req.file) {
      return res.status(400).json({ success: 0, message: "No file uploaded" });
    }
  
    const type = req.body.type || 'general';
    const imageUrl = `${req.protocol}://${req.get('host')}/images/${type}/${req.file.filename}`;
  
    res.json({
      success: true,
      image_url: imageUrl
    });
  };
  





 
export const addItem = async (req, res) => {
     
    console.log("Received fields:", {
        name: req.body.name,
        smalldescription: req.body.smalldescription,
        description: req.body.description,
        termsAndConditions: req.body.termsAndConditions,
        telephon: req.body.telephon,
        address: req.body.address || req.body.Address  
    });
    console.log("Received file:", req.file);

    try {
        if (!req.file) {
            return res.status(400).json({ success: false, message: "No image uploaded" });
        }

        const item = new ItemModel({
            image: `/images/${req.file.filename}`,
            name: req.body.name,
            smalldescription: req.body.smalldescription,
            description: req.body.description,
            termsAndConditions: req.body.termsAndConditions,
            telephon: req.body.telephon,
            address: req.body.address || req.body.Address 
        });

        await item.save();

        res.status(201).json({
            success: true,
            message: "Item added successfully",
            item: item.toObject() 
        });

    } catch (error) {
        console.error("Add item error:", error);
        res.status(500).json({ success: false, message: error.message });
    }
};

 
export const deleteItem = async (req, res) => {
    try {
        const { id } = req.params;

        // Find and delete the item
        const deletedItem = await ItemModel.findByIdAndDelete(id);

        if (!deletedItem) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }

        res.status(200).json({ success: true, message: "Item deleted successfully" });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};



 

export const updateItem = async (req, res) => {
    try {
        const { id } = req.params;
        const updateData = req.body;

        // Check if item exists
        const item = await ItemModel.findById(id);
        if (!item) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }

        // If a new file is uploaded, delete the old file
        if (req.file) {
            const oldImagePath = path.join("upload/images", item.image);
            if (fs.existsSync(oldImagePath)) {
                fs.unlinkSync(oldImagePath);  // Delete the old image
            }
            updateData.image = `/images/${req.file.filename}`; // Store new image path
        }

        // Update item details
        const updatedItem = await ItemModel.findByIdAndUpdate(id, updateData, { new: true });

        res.status(200).json({ success: true, message: "Item updated successfully", updatedItem });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

 

export const getAllItems = async (req, res) => {
    try {
        const items = await ItemModel.find({});

        const itemDetails = items.map(item => ({
            id: item.id,
            image: `http://localhost:4000${item.image.replace(/^http:\/\/localhost:4000/, '')}`,  
            name: item.name,
            smalldescription: item.smalldescription,
            isAvailable: item.isAvailable,
        }));

        console.log("✅ Sending Image URLs:", itemDetails.map(i => i.image)); // ✅ Debugging output

        res.status(200).json({ items: itemDetails });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

 

export const getStatistics = async (req, res) => {
    try {
         
        const totalUsers = await UserModel.countDocuments();

         
        const availableItems = await ItemModel.countDocuments({ isAvailable: true });

        res.status(200).json({ 
            success: true, 
            statistics: { totalUsers, availableItems } 
        });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};