import { Link } from 'react-router-dom';

function SuccessMessage({ message, redirect, linkText }) {
  return <div className="alert alert-success">
    {message}
    {redirect && <Link to={redirect}>{linkText}</Link>}
  </div>;
}

export default SuccessMessage;