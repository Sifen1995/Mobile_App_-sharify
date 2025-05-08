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
        const userId = req.user.id; // Get user ID from authentication

        // Find item and update availability & borrowedBy
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