import express from "express";
import { getItemDetails,borrowItem,getBorrowedItems,updateBorrowedItemNote,removeBorrowedItem } from "../../controllers/user/borrowController.js";
import userAuth from "../../middleware/userAuth.js";

const BorrowRouter = express.Router();

 
BorrowRouter.get('/item/:id', getItemDetails);
BorrowRouter.put("/borrow-item/:id", userAuth,borrowItem);
BorrowRouter.get("/borrowed-items", userAuth, getBorrowedItems);
BorrowRouter.put("/borrowed-item/note/:id", userAuth, updateBorrowedItemNote);
BorrowRouter.delete("/borrowed-item/:id", userAuth, removeBorrowedItem);

export default BorrowRouter;