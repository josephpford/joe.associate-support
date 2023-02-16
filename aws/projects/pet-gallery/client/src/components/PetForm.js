import { useState, useEffect } from 'react';
import { Link, useParams, useHistory } from 'react-router-dom';
import { findById, save } from '../services/petService';
import Errors from './Errors';
import FileUpload from './FileUpload';

const EMPTY_PET = {
  petId: 0,
  name: '',
  imageUrl: ''
};

function PetForm() {

  const history = useHistory();

  const { id } = useParams();

  const [pet, setPet] = useState(EMPTY_PET);

  const [showFileUpload, setShowFileUpload] = useState(false);
  const [errors, setErrors] = useState([]);

  useEffect(() => {
    if (id) {
      findById(id)
        .then(setPet)
        .catch(err => {
          console.log(err)
        });
    } else {
      setPet(EMPTY_PET);
      setShowFileUpload(true);
    }
  }, [id]);

  const handleChange = (evt) => {
    setErrors([]);
    const nextPet = { ...pet };
    nextPet[evt.target.name] = evt.target.value;
    setPet(nextPet);
  };

  const handleFileClick = () => {
    setShowFileUpload(!showFileUpload);
  };

  const handleFileUploadSuccess = (imageUrl) => {
    const nextPet = { ...pet, imageUrl: imageUrl };
    setPet(nextPet);
    setShowFileUpload(false);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();
    save(pet)
      .then(() => {
        history.push('/');
      })
      .catch(setErrors);
  };


  return <>
    <h3>{(id && `Edit ${pet.name}`) || 'Add Pet'}</h3>
    <form onSubmit={handleSubmit}>
      <label htmlFor="name" className="form-label">Name</label>
      <div className="mb-3">
        <input type="text" className="form-control" id="name" name="name" value={pet.name} onChange={handleChange} />
      </div>
      <label htmlFor="imageUrl" className="form-label">Image</label>
      {!showFileUpload &&
        <div className="input-group mb-3">
          <input type="text" className="form-control" id="imageUrl" name="imageUrl"
            aria-describedby="file-button" value={pet.imageUrl} onChange={handleChange} />
          <button className="btn btn-outline-secondary" type="button" id="file-button" onClick={handleFileClick}>Upload a New Image</button>
        </div>
      }
      {showFileUpload && <FileUpload onSuccess={handleFileUploadSuccess} onError={setErrors}></FileUpload>}
      <button type="submit" className="btn btn-outline-primary">Submit</button>
      <Link to="/" className="btn btn-outline-secondary">Cancel</Link>
    </form>
    <Errors errors={errors}></Errors>
  </>;
}

export default PetForm;