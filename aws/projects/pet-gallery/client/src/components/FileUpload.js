import { useState } from 'react';
import { uploadFile } from '../services/fileService';

function FileUpload({ onSuccess, onError }) {

  const [selectedFile, setSelectedFile] = useState();

  const handleChange = (evt) => {
    setSelectedFile(evt.target.files[0]);
  }

  const handleUpload = () => {
    onError([]);
    if (!selectedFile) {
      onError(['Choose a file to upload.']);
      return;
    }
    if (selectedFile.size / 1024 / 1024 > 1) {
      onError(['Choose a smaller file (1 MB maximum).']);
      return;
    }
    const formData = new FormData();
    formData.append('type', 'file');
    formData.append('file', selectedFile);

    uploadFile(formData)
      .then(data => {
        onSuccess(data);
      })
      .catch(errs => {
        onError(errs);
      });
  };

  return <div className="input-group mb-3">
    <input type="file" className="form-control" id="fileInput" onChange={handleChange} />
    <label className="input-group-text" onClick={handleUpload}>Upload</label>
  </div>;
}

export default FileUpload;