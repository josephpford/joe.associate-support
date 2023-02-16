import { useState, useContext } from 'react';
import { Link, useHistory } from 'react-router-dom';
import AuthContext from '../contexts/AuthContext';
import ErrorList from './ErrorList';
import { authenticate } from '../services/authApi';

function Login() {

  const auth = useContext(AuthContext);

  const [credentials, setCredentials] = useState({
    username: '',
    password: ''
  });
  const [errors, setErrors] = useState([]);

  const history = useHistory();

  const handleChange = (evt) => {
    const nextCredentials = { ...credentials };
    nextCredentials[evt.target.name] = evt.target.value;
    setCredentials(nextCredentials);
  };

  const handleSubmit = (evt) => {
    evt.preventDefault();

    authenticate(credentials)
      .then(data => {
        if (data.username) {
          auth.onAuthenticated(data);
          history.push('/');
        } else {
          setErrors(data);
        }
      })
      .catch(console.error);
  };

  return <>
    <section className="form container-fluid">
      <h1>New Board Game</h1>
      <ErrorList errors={errors} />
      <form onSubmit={handleSubmit}>
        <div className="mb-3">
          <label htmlFor="username" className="form-label">Username</label>
          <input 
            type="text" 
            name="username" 
            value={credentials.username} 
            onChange={handleChange} 
            className="form-control" 
            id="username" 
            placeholder="Username" 
            required />
        </div>
        <div className="mb-3">
          <label htmlFor="password" className="form-label">Password</label>
          <input 
            type="password" 
            name="password" 
            value={credentials.password} 
            onChange={handleChange} 
            className="form-control" 
            id="password" 
            placeholder="Password"
            required />
        </div>
        <div className="mb-3">
          <button type="submit" className="btn btn-primary m-3">Save</button>
          <Link to="/" className="btn btn-secondary m-3">Cancel</Link>
        </div>
      </form>
      <div className="alert alert-info">
        Don't have an account? register <Link to="/register">here</Link>.
      </div>
    </section>
  </>;
}

export default Login;