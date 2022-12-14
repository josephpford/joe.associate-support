import { useState, useEffect } from 'react';
import { Link, useHistory } from 'react-router-dom';
import { findAll } from '../services/petService';
import { CSSTransition } from 'react-transition-group';

function Home() {

  const [pets, setPets] = useState([]);
  const [currentPet, setCurrentPet] = useState();
  const [isNext, setIsNext] = useState(true);

  const history = useHistory();

  useEffect(() => {
    findAll()
      .then((data) => {
        if (data.length > 0) {
          setCurrentPet(data[0]);
          setPets(data);
        } else {
          history.push("/add");
        }
      })
      .catch((err) => {
        console.log(err);
      });
  }, []);

  const handlePrevious = () => {
    let nextIndex = pets.indexOf(currentPet);

    if (nextIndex < 1) {
      nextIndex = pets.length.length;
    }
    nextIndex = nextIndex - 1;

    setCurrentPet(pets[nextIndex]);
    setIsNext(false);
  };

  const handleNext = () => {
    let nextIndex = pets.indexOf(currentPet);

    if (nextIndex == pets.length - 1) {
      nextIndex = -1;
    }

    nextIndex = nextIndex + 1;

    setCurrentPet(pets[nextIndex]);
    setIsNext(false);
  };

  const handlePetClick = (pet) => {
    setCurrentPet(pet);
    setIsNext(false);
  };

  if (pets.length === 0) {
    return null;
  }

  return <>
    <div className="carousel position-absolute top-50 start-50 translate-middle">
      <CSSTransition
        timeout={300}
        classNames={{
          enter: isNext ? 'enter-next' : 'enter-prev',
          enterActive: 'enter-active',
          leave: 'leave',
          leaveActive: isNext ? 'leave-active-next' : 'leave-active-prev'
        }}>
        <div className="carousel_slide" key={currentPet.petId}>
          <Link to={`/edit/${currentPet.petId}`}>
            <img className="img-fluid" src={currentPet.imageUrl} alt={currentPet.name} />
          </Link>
        </div>
      </CSSTransition>
      <button className="carousel_control carousel_control__prev" onClick={handlePrevious}><span></span></button>
      <button className="carousel_control carousel_control__next" onClick={handleNext}><span></span></button>
      <div className="carousel_history">
        <ul>
          {pets.map(p => <li key={p.petId}>
            <button className={p === currentPet ? 'active' : ''} onClick={() => handlePetClick(p)}></button>
          </li>)}
        </ul>
      </div>
    </div>
  </>;
}

export default Home;