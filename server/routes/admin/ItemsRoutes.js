import express from 'express';
import isAdmin from '../../middleware/isAdmin.js';
import upload from '../../middleware/uploadMiddleware.js'
import { addItem, handleUpload,deleteItem,updateItem ,getAllItems,getStatistics} from '../../controllers/admin/ItemController.js';
import userAuth from '../../middleware/userAuth.js';

 

const Adminrouter = express.Router();
 
Adminrouter.post('/add-Item', upload.single('image'),addItem);
Adminrouter.delete('/delete-item/:id', userAuth, deleteItem);
Adminrouter.put('/update-item/:id', upload.single('image'), updateItem);
Adminrouter.get('/items', userAuth,getAllItems);
Adminrouter.get("/statistics", getStatistics);


export default Adminrouter;