import { useContext } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from '../contexts/AuthContext';

function BoardGameCard({ game }) {

  const auth = useContext(AuthContext);

  return <div className="col">
    <div className="card h-100">
      <img src={game.imageUrl} alt={game.name} className="card-img-top" />
      <div className="card-body">
        <h3 className="card-title">{game.name}</h3>
        <p>{game.minimumPlayers}-{game.maximumPlayers} Players</p>
      </div>
      <div className="card-footer">
        <Link to={`/edit/${game.id}`} className="btn btn-primary m-3">Edit</Link>
        {auth.isAdmin() && <Link to={`/delete/${game.id}`} className="btn btn-danger m-3">Delete</Link>}
      </div>
    </div>
  </div>;
}

export default BoardGameCard;
