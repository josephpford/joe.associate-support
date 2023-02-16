import { useState, useEffect } from 'react';
import { Link, useHistory, useParams } from 'react-router-dom';
import { deleteById, findById } from '../services/boardGameApi';

function DeleteBoardGame() {
  
  const { id } = useParams();
  const [game, setGame] = useState({ name: '', rating: '' });

  const history = useHistory();

  useEffect(() => {
    if (id) {
      findById(id)
        .then(data => setGame(data));
    } 
  }, [id]);

  const handleDeleteClicked = () => {
    deleteById(id)
      .then(() => {
        history.push("/");
      })
      .catch(() => console.error("Something went wrong!"));
  };

  return <section className="games">
    <h1>Delete the following agent?</h1>
    <ul>
      <li>Name: <span>{game.name}</span></li>
      <li>Rating: <span>{game.rating}</span></li>
    </ul>
    <div>
      <button className="btn btn-danger m-5" type="button" onClick={handleDeleteClicked}>Delete Game</button>
      <Link to="/" className="btn btn-warning ml-2">Cancel</Link>
    </div>
  </section>;
}

export default DeleteBoardGame;