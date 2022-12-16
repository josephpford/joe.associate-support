import { useState, useEffect } from 'react';
import { Link, useParams, useHistory } from 'react-router-dom';
import { findAll as findCategories } from '../services/categoryApi';
import { findById, add, update } from '../services/boardGameApi';
import ErrorList from './ErrorList';

const EMPTY_GAME = {
  id: 0,
  name: '',
  imageUrl: '',
  minimumPlayers: 1,
  maximumPlayers: 4,
  rating: 5.0,
  inPrint: true,
  categories: []
};

function BoardGameForm() {

  const { id } = useParams();
  const [categories, setCategories] = useState([]);
  const [game, setGame] = useState(EMPTY_GAME);
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  useEffect(() => {
    findCategories()
      .then(setCategories);
  }, [])

  useEffect(() => {
    if (id) {
      findById(id)
        .then(setGame);
    } else {
      setGame(EMPTY_GAME);
    }
  }, [id]);

  const handleChange = (evt) => {

    const nextGame = { ...game };

    if (evt.target.type === 'checkbox') {
      nextGame[evt.target.name] = evt.target.checked;
    } else {
      let nextValue = evt.target.value;
      if (evt.target.type === 'number') {
        nextValue = parseFloat(nextValue, 10);
        if (isNaN(nextValue)) {
          nextValue = evt.target.value;
        }
      }
      nextGame[evt.target.name] = nextValue;
    }
    setGame(nextGame);
  };

  const handleCategoryChange = (evt) => {
    const nextGame = { ...game };

    const categoryId = parseInt(evt.target.value, 10);

    if (evt.target.checked) {
      const category = categories.find(c => c.id === categoryId);
      nextGame.categories = [...nextGame.categories, category];
    } else {
      nextGame.categories = nextGame.categories.filter(c => c.id !== categoryId);
    }
    setGame(nextGame);
  }

  const handleSubmit = (evt) => {
    evt.preventDefault();

    setErrors([]);

    ((id && update(game)) || add(game)) // abusing JS truthiness and boolean expressions
      .then(data => {
        if (!data || data.id) {
          history.push('/');
        } else {
          setErrors(data);
        }
      })
      .catch(() => setErrors(['Something unexpected happened!']));
  };

  const gameHasCategory = (category) => {
    return game.categories.findIndex(c => c.id === category.id) > -1;
  }

  return <section className="form container-fluid">
    <h1>New Board Game</h1>
    <ErrorList errors={errors} />
    <form onSubmit={handleSubmit}>
      <div className="mb-3">
        <label htmlFor="name" className="form-label">Name</label>
        <input type="text" name="name" value={game.name} onChange={handleChange} className="form-control" id="name" placeholder="Name the game" required />
      </div>
      <div className="mb-3">
        <label htmlFor="imageUrl" className="form-label">Image URL</label>
        <input type="text" name="imageUrl" value={game.imageUrl} onChange={handleChange} className="form-control" id="imageUrl" required />
      </div>
      <div className="mb-3">
        <label htmlFor="minimumPlayers" className="form-label">Min players</label>
        <input type="number" name="minimumPlayers" value={game.minimumPlayers} onChange={handleChange} className="form-control" id="minimumPlayers" min="1" max="99" required />
      </div>
      <div className="mb-3">
        <label htmlFor="maximumPlayers" className="form-label">Max players</label>
        <input type="number" name="maximumPlayers" value={game.maximumPlayers} onChange={handleChange} className="form-control" id="maximumPlayers" min="1" max="99" required />
      </div>
      <div className="mb-3">
        <label htmlFor="rating" className="form-label">BGG Rating</label>
        <input type="number" name="rating" value={game.rating} onChange={handleChange} className="form-control" id="rating" min="1" max="10" step="0.01" />
      </div>
      <div className="form-check">
        <input className="form-check-input" name="inPrint" checked={game.inPrint} onChange={handleChange} type="checkbox" value="inPrint" id="inPrint" />
        <label className="form-check-label" htmlFor="inPrint">
          In print
        </label>
      </div>
      <h3>Categories</h3>
      {categories.map(c => <>
        <div className="form-check" key={c.id}>
          <input className="form-check-input"
            name="category"
            checked={gameHasCategory(c)}
            onChange={handleCategoryChange}
            type="checkbox"
            value={c.id}
            id={`category-${c.id}`} />
          <label className="form-check-label" htmlFor="inPrint">
            {c.name}
          </label>
        </div>
      </>)}
      <div className="mb-3">
        <button type="submit" className="btn btn-primary m-3">Save</button>
        <Link to="/" className="btn btn-secondary m-3">Cancel</Link>
      </div>
    </form>
  </section>;
}

export default BoardGameForm;