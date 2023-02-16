import { useState, useEffect } from "react";
import { findAll } from "../services/boardGameApi";
import BoardGameCard from "./BoardGameCard";

function BoardGameList() {

  const [games, setGames] = useState([]);

  useEffect(() => {
    findAll()
      .then(setGames);
  }, []);

  return <div className="game-list">
    <section className="banner"></section>
    <section className="games container-fluid" id="games">
      <h1>My Favorite Games!</h1>
      <div className="game-list row row-cols-1 row-cols-sm-2 row-cols-md-3">
        {games.map(game => <BoardGameCard key={game.id} game={game} />)}
      </div>
    </section>
  </div>;
}

export default BoardGameList;