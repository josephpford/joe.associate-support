import { Link } from 'react-router-dom';

function NavBar() {
  return <nav className="navbar navbar-expand-lg bg-light">
    <div className="container">
      <Link to="/" className="navbar-brand">Dev 10 Pet Gallery</Link>
      <div className="navbar-nav">
        <Link to="/add" className="nav-link active" aria-current="page">Add a Pet</Link>
      </div>
    </div>
  </nav>
}

export default NavBar;