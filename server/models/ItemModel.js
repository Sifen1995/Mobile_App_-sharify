import mongoose from "mongoose";

const ItemSchema = new mongoose.Schema(
  {
    image: {type:String,required:true},
    name: {type:String,required:true},
    smalldescription: String,
    description: String,  
    isAvailable: {type:Boolean,default: true},
    termsAndConditions:String,
    telephon: String,
    address:String,
    borrowedBy: { type: mongoose.Schema.Types.ObjectId, ref: "User", default: null },
    note: { type: String, default: "" }
  },
   
);
const ItemModel = mongoose.models.Item || mongoose.model('Item',ItemSchema);

export default ItemModel;
 