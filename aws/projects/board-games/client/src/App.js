import { useState, useEffect, useCallback } from "react";
import { BrowserRouter as Router, Redirect, Route, Switch } from "react-router-dom";
import BoardGameForm from "./components/BoardGameForm";
import BoardGameList from "./components/BoardGameList";
import DeleteBoardGame from "./components/DeleteBoardGame";
import NavBar from "./components/NavBar";
import NotFound from "./NotFound";
import AuthContext from "./contexts/AuthContext";
import Login from "./components/Login";
import { logout, refreshToken } from './services/authApi';
import Register from "./components/Register";

const EMPTY_USER = {
  username: '',
  roles: []
};

const WAIT_TIME = 1000 * 60 * 14;

function App() {

  const [user, setUser] = useState(EMPTY_USER);

  const refreshUser = useCallback(() => {
    refreshToken()
      .then(data => {
        if (data.username) {
          setUser(data);
          setTimeout(refreshUser, WAIT_TIME);
        } else {
          logout();
        }
      });
  }, []);

  const onAuthenticated = useCallback((authenticatedUser) => {
    setUser(authenticatedUser);
    setTimeout(refreshUser, WAIT_TIME)
  }, [refreshUser]);

  useEffect(() => {
    refreshUser();
  }, [refreshUser]);

  const auth = {
    user: user,
    onAuthenticated,
    logout() {
      logout();
      setUser(EMPTY_USER);
    },
    isAdmin() {
      return user.roles.includes('ADMIN')
    }
  };

  return (
    <AuthContext.Provider value={auth}>
      <Router>
        <div className="App">
          <NavBar />
          <main>
            <Switch>
              <Route exact path="/">
                <BoardGameList />
              </Route>
              <Route path="/register">
                <Register />
              </Route>
              <Route path="/login">
                <Login />
              </Route>
              <Route path={["/add", "/edit/:id"]}>
                <BoardGameForm />
              </Route>
              <Route path="/delete/:id">
                {user.username && auth.isAdmin()
                  ? <DeleteBoardGame />
                  : <Redirect to="/login" />
                }
              </Route>
              <Route>
                <NotFound />
              </Route>
            </Switch>
          </main>
        </div>
      </Router>
    </AuthContext.Provider>
  );
}

export default App;
