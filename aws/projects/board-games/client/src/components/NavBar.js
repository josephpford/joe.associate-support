import { useContext } from 'react';
import { Link } from 'react-router-dom';
import AuthContext from '../contexts/AuthContext';

function NavBar() {

  const auth = useContext(AuthContext);

  const handleLogOut = () => {
    auth.logout();
  }

  return <header>
    <Link to="/">
      <div className="logo">
        GAME<br />
        SCAPE
      </div>
    </Link>
    <nav>
      <Link to="/">Home</Link>
      {auth.user.roles.includes('ADMIN') && <Link to="/add">Add a Game</Link>}
      <a href="https://www.gamescapesf.com/" rel="noreferrer" target="_blank">Gamescape SF</a>
      {auth.user.username
        ? <span>{auth.user.username} 
          <button className="btn btn-secondary" onClick={handleLogOut}>Log out</button></span>
        : <Link to="/login">Log in</Link>
      }
    </nav>
  </header>
}

export default NavBar;