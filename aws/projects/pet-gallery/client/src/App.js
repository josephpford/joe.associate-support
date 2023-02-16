import { HashRouter as Router, Switch, Route } from 'react-router-dom';
import Home from "./components/Home";
import NavBar from './components/NavBar';
import PetForm from './components/PetForm';

function App() {
  return (
    <Router>
      <NavBar></NavBar>
      <div className="container">
        <Switch>
          <Route path={['/edit/:id', '/add']}>
            <PetForm></PetForm>
          </Route>
          <Route path="/">
            <Home></Home>
          </Route>
        </Switch>
      </div>
    </Router>
  );
}

export default App;
