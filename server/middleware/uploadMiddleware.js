import multer from "multer";
import path from "path";
import fs from "fs";

 
const storage = multer.diskStorage({
  destination: function (req, file, cb) {
    const type = req.body.type || 'general';  
    const dir = `./upload/images/${type}`;
    fs.mkdirSync(dir, { recursive: true });
    cb(null, dir);
  },
  filename: function (req, file, cb) {
    cb(null, `${file.fieldname}_${Date.now()}${path.extname(file.originalname)}`);
  }
});

const upload = multer({ storage: storage });

export default upload;
