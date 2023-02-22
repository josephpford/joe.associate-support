import { Routes, Route } from "react-router-dom";

import Navi from './utilities/Navi';
import Home from './utilities/Home';
import About from './utilities/About';
import NotFound from './utilities/NotFound';

import Container from 'react-bootstrap/Container';
import Image from 'react-bootstrap/Image';

function App() {
  return (
    <>
      <Navi />
      <Container>
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/about" element={<About />} />
          <Route path="*" element={<NotFound />} />
        </Routes>
        <Image fluid className="my-5" src={process.env.PUBLIC_URL + "/images/inner_senshi.png"} alt="The 'Inner Senshi' of the animated show and comic 'Sailor Moon'. From left to right, Sailor Mercury, Sailor Mars, Sailor Moon, Sailor Jupiter, and Sailor Venus." />
      </Container>
    </>
  );
}

export default App;
