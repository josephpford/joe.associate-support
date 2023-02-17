import { useState } from "react";
import { Routes, Route } from "react-router-dom";
import Home from "./components/utilities/Home";
import Navi from "./components/utilities/Navi";
import MessageFactory from "./components/utilities/MessageFactory";
import About from "./components/utilities/About";
import NotFound from "./components/utilities/NotFound";
import ValentineFactory from "./components/valentines/ValentineFactory";
import Valentines from "./components/valentines/Valentines";
import ValentineForm from "./components/valentines/ValentineForm";
import ValentineDelete from "./components/valentines/ValentineDelete";

import Container from 'react-bootstrap/Container';

function App() {
  const [messages, setMessages] = useState([]);

  const makeId = () => {
    let id = "";
    let characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    for ( var i = 0; i < 12; i++ ) {
      id += characters.charAt(Math.floor(Math.random() * 36));
    }
    return id;
  }

  return (
    <>
      <Navi />
      <Container className="pt-5 mt-5">
        <MessageFactory messages={messages} setMessages={setMessages} />
        <Routes>
          <Route path="/" element={< Home />} />
          <Route path="/valentines" element={
            <ValentineFactory  
              messages={messages} 
              setMessages={setMessages} 
              makeId={makeId} 
            />
          } />
          <Route path="/valentines_SPA" element={
            <Valentines
              messages={messages} 
              setMessages={setMessages} 
              makeId={makeId} 
            />
          } />
          <Route path="/valentines/add" element={
            <ValentineForm 
              messages={messages} 
              setMessages={setMessages} 
              makeId={makeId} 
            />
          } />
          <Route path="/valentines/edit/:valentineId" element={
            <ValentineForm 
              messages={messages} 
              setMessages={setMessages} 
              makeId={makeId} 
            />
          } />
          <Route path="/valentines/delete/:valentineId" element={ 
            <ValentineDelete 
              messages={messages} 
              setMessages={setMessages} 
              makeId={makeId} 
            />
          } />
          <Route path="/about" element={<About />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
      </Container>
    </>
  );
}

export default App;