import ItemModel from "../../models/ItemModel.js";

export const getItemDetails = async (req, res) => {
    try {
        const { id } = req.params;
        const item = await ItemModel.findById(id);

        if (!item) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }

        res.status(200).json({ success: true, item });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};


export const borrowItem = async (req, res) => {
    try {
        const { id } = req.params;
        const userId = req.user.id;  

        
        const item = await ItemModel.findByIdAndUpdate(
            id,
            {borrowedBy: userId },
            { new: true }
        );

        if (!item) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }

        res.status(200).json({ success: true });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};


export const getBorrowedItems = async (req, res) => {
    try {
        const userId = req.user.id;  

         
        const items = await ItemModel.find({ borrowedBy: userId });

        
        const formattedItems = items.map(item => ({
            id: item.id,
            name: item.name,
            image: item.image,
            smalldescription: item.smalldescription,
            note: item.note
            
        }));

        res.status(200).json({borrowedItems: formattedItems });
    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};


export const updateBorrowedItemNote = async (req, res) => {
    try {
        const { id } = req.params;
        const { note } = req.body;
        const userId = req.user.id;
 
        const item = await ItemModel.findById(id);
        if (!item) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }
        if (String(item.borrowedBy) !== String(userId)) {
            return res.status(403).json({ success: false, message: "You can only update notes for your own borrowed items" });
        }

         
        item.note = note;
        await item.save();
 
        res.status(200).json({success: true,note: item.note });

    } catch (error) {
        res.status(500).json({ success: false, message: error.message });
    }
};

export const removeBorrowedItem = async (req, res) => {
    try {
        const { id } = req.params;
        const userId = req.user.id;  
 
        const item = await ItemModel.findById(id);
        if (!item) {
            return res.status(404).json({ success: false, message: "Item not found" });
        }
        if (String(item.borrowedBy) !== String(userId)) {
            return res.status(403).json({ success: false, message: "You can only remove items you borrowed" });
        }

    
        item.borrowedBy = null;
        item.isAvailable = true;
        await item.save();

        res.status(200).json({ success: true, message: "Item removed from borrowed list successfully" });
    } catch (error) {
        res.status(500).json({ success: false, message: "Internal server error" });
    }
};